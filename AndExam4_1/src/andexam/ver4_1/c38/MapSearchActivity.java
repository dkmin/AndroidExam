package andexam.ver4_1.c38;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import andexam.ver4_1.R;
import andexam.ver4_1.c38.dto.Place;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setTitle("맵검색");
        mActionBar.setSubtitle("위치기반");
        mActionBar.setDisplayShowTitleEnabled(true);
        
        LocationManager mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		String mProvider = mLocMan.getBestProvider(new Criteria(), true);
		mLocation = mLocMan.getLastKnownLocation(mProvider);
		
		if(mLocation == null) {
		    mLocation = new Location("dummyLocation");
		    mLocation.setLatitude(37.497997);
		    mLocation.setLongitude(127.026960);
		}
        
		FragmentManager fm = getFragmentManager();
		MapFragment mMapFragment = (MapFragment)fm.findFragmentById(R.id.searchfragment);
		mGoogleMap = mMapFragment.getMap();
		
		LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
		
		//현재 위치 찍기
        mGoogleMap.addMarker(new MarkerOptions()
        .position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_icon))
        .title("현재 위치")
        .snippet("서울시 강남구 강남역 2호선")); //Geocoding 필요.
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
    	
        String url = searchUrl + String.format("?location=%f,%f&rankby=distance&sensor=false&key=%s&name=%s",
                    mLocation.getLatitude(), mLocation.getLongitude(), key, encodedQuery);

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
                        Log.d("ldk", "result.text(lat):" + result.tag("geometry").tag("location").text("lat"));
	                    place.lat = Double.parseDouble(result.tag("geometry").tag("location").text("lat"));
	                    place.lng = Double.parseDouble(result.tag("geometry").tag("location").text("lng"));
                    } catch(NumberFormatException e) { 
                        Log.d("ldk", "numberformatException");
                    } catch(NullPointerException e) {
                        Log.d("ldk", "NullPointerException");
                    } catch(Exception e) { 
                        Log.d("ldk", "Exception");
                    }
                    mPlaceList.add(place);
                }
                
                displayListView();
                displayMap();
            }
            
        });
    }
    
    private void displayListView() {
        
        //2. Adapter 만들기
        MyAdapter adapter = new MyAdapter(this, R.layout.list_search);
        
        //3. ListView 연결하기
        ListView list = (ListView) findViewById(R.id.searchlist);
        list.setAdapter(adapter);
    }
    
    private void displayMap() {
    	Log.d("ldk", "size(): " + mPlaceList.size() );
    	
		//마커 표시하기
    	for(Place place : mPlaceList) {
    		Log.d("ldk", "lat=" + place.lat + " lon=" + place.lng);
    		if(place.lat > 0 ) {
    			mGoogleMap.addMarker(new MarkerOptions()
		            .position(new LatLng(place.lat, place.lng))
		            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer))
		            .title(place.name)
		            .snippet(place.vicinity));
    		}
    	}
    }
    
    class MyAdapter extends BaseAdapter{
    	Context context;
    	int layout;
    	LayoutInflater inflater;
    	
    	MyAdapter(Context context, int layout){
    		this.context = context;
    		this.layout = layout;
    		inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPlaceList.size();
		}

		@Override
		public Place getItem(int position) {
			// TODO Auto-generated method stub
			return mPlaceList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			//Viewholder  와 꼬리표를 이용한 성능 향상 
			//findViewById의 리소스 낭비를 막는다. 
			Viewholder holder;
			if(convertView == null){
				convertView = inflater.inflate(layout, parent, false);
				//Viewholder 생성훙 convertView에 꼬리표 부착
				holder = new Viewholder();
				holder.tv1 = (TextView)convertView.findViewById(R.id.tvSearchName);
				holder.tv2 = (TextView)convertView.findViewById(R.id.tvSearchDistance);
				convertView.setTag(holder);
			} else {
				//꼬리표 가져오기
				holder = (Viewholder) convertView.getTag();
			}
			
			//홀더를 이용해서 리소스 할당
			//1) 이름 구하기
			(holder.tv1).setText(mPlaceList.get(position).name); //이름
			
			//2)거리 구하기
			float[] distance = new float[2]; 
			if(mPlaceList.get(position).lat>0) {
    			Location.distanceBetween(mLocation.getLatitude(), mLocation.getLongitude(), 
    			            mPlaceList.get(position).lat, mPlaceList.get(position).lng, distance);
    			(holder.tv2).setText(String.valueOf((int)distance[0]) + "m");
			} else {
			    (holder.tv2).setText("");
			}
			
			return convertView;
		}
    }

}

//findViewById로 찾아야 할 리소스를 정의
class Viewholder{
	TextView tv1; //이름
	TextView tv2; //거리
}