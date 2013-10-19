package andexam.ver4_1.c38;

import java.util.List;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        
        mAq = new AQuery(this);
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
                mInputMethodManager.toggleSoftInput(0, 0); //hide or toggle
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
        String url = searchUrl + "?location=37.481031,126.879192&rankby=distance&sensor=false&key=" 
                    + key + "&name=" + "\"" + query + "\"";
        mAq.ajax(url, XmlDom.class, new AjaxCallback<XmlDom>(){

            @Override
            public void callback(String url, XmlDom xml, AjaxStatus status) {
                TextView tvPlace = (TextView)findViewById(R.id.tvPlace);
                String strPlace = "";
                
                List<XmlDom> results = xml.tags("result");
                for(XmlDom result : results) {
                    strPlace += result.text("name") + "\n";
                }
                
                tvPlace.setText(strPlace);
            }
            
        });
    }

}