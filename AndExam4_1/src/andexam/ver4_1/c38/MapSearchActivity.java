package andexam.ver4_1.c38;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import andexam.ver4_1.c38.dto.Place;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MapSearchActivity extends Activity {
    private AQuery mAq;
    private String searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml";
    private final String key = "AIzaSyAjrXnxmyM1go9fYrxO2CgDxvAQx7_vD8A";
    
    private ArrayList<Place> mPlaceList = new ArrayList<Place>();
    
    private GoogleMap mGoogleMap;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        
        mAq = new AQuery(this);
        AQUtility.setDebug(true);
        
        LocationManager mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		String mProvider = mLocMan.getBestProvider(new Criteria(), true);
		mLocation = mLocMan.getLastKnownLocation(mProvider);
        
		FragmentManager fm = getFragmentManager();
		MapFragment mMapFragment = (MapFragment)fm.findFragmentById(R.id.searchfragment);
		mGoogleMap = mMapFragment.getMap();
		
		LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 14));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_search, menu);
        
        MenuItem search = menu.findItem(R.id.searchmap);
        final SearchView sv = (SearchView)search.getActionView();
        sv.setOnQueryTextListener(new OnQueryTextListener() {
            
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MapSearchActivity.this, "검색 완료:" + query, 0).show();
                
                //google places api 호출
                searchPlace(query);
                
                //키보드 내리기
                InputMethodManager mInputMethodManager = (InputMethodManager) MapSearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.toggleSoftInput(0, 0);
                return false;
            }
            
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        
        return true;
    }
    
    private void searchPlace(String query) {
    	String encodedQuery = "";
    	try {
    		encodedQuery = URLEncoder.encode("\"" + query + "\"", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
        String url = searchUrl + "?location=37.481031,126.879192&rankby=distance&sensor=false&key=" 
                    + key + "&name=" + encodedQuery;

        Log.d("ldk", "url=" + url);

        mAq.ajax(url, XmlDom.class, new AjaxCallback<XmlDom>(){

            @Override
            public void callback(String url, XmlDom xml, AjaxStatus status) {
            	Log.d("ldk", "callback: " + status.getCode());
            	Log.d("ldk", "xml: " + xml.toString());
            	
            	mPlaceList.clear();
                
                List<XmlDom> results = xml.tags("result");
                for(XmlDom result : results) {
                	Place place = new Place();
                    place.name = result.text("name");
                    place.vicinity = result.text("vicinity");
                    place.type = result.text("type");
                    try {
	                    place.lat = Double.parseDouble(result.text("lat"));
	                    place.lng = Double.parseDouble(result.text("lng"));
                    } catch(NumberFormatException e) { 
                    } catch(NullPointerException e) {
                    } catch(Exception e) { }
                    mPlaceList.add(place);
                }
                
                displayMap();
            }
            
        });
    }
    
    private void displayMap() {
    	Log.d("ldk", "size(): " + mPlaceList.size());
    }

}