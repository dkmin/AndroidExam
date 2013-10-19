package andexam.ver4_1.c28_network;

import java.util.List;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import andexam.ver4_1.c28_network.SearchRank.DownThread;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SearchAqueryActivity extends Activity {
    private final String searchUrl = "http://openapi.naver.com/search?" + 
                "key=8f8c1668efc16f634110fb9fd51f15c8&query=nexearch&target=rank";
    
    AQuery mAq;
    private String strResult = "";
    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchrank);
        
        mAq = new AQuery(this);
        tvDisplay = (TextView)findViewById(R.id.result);
    }
    
    public void mOnClick(View v) {

        switch (v.getId()) {
        case R.id.getrankraw:
            mAq.ajax(searchUrl, XmlDom.class, new AjaxCallback<XmlDom>(){

                @Override
                public void callback(String url, XmlDom xml, AjaxStatus status) {
                    //List<XmlDom> items = xml.tags("item");
                    XmlDom item = xml.tag("item");
                    for(int i=1; i<=10; i++) {
                        XmlDom rank = item.tag("R" + i);
                        rank.text("k");
                        strResult += i + "위: " + rank.text("K") + ", " + rank.text("S") + rank.text("V") + "\n";
                        tvDisplay.setText(strResult);
                    }
                }
                
            });
            break;
        }
    }
    
    public void displayRank() {
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_aquery, menu);
        return true;
    }

}
