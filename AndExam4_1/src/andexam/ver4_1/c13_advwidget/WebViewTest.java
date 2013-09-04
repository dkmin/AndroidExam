package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class WebViewTest extends Activity {
	WebView mWeb;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewtest);

		mWeb = (WebView)findViewById(R.id.web);
		mWeb.setWebViewClient(new MyWebClient());
		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		mWeb.loadUrl("http://www.google.com");
		
		mWeb.requestFocus(View.FOCUS_DOWN);
		mWeb.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN:
	                case MotionEvent.ACTION_UP:
	                    if (!v.hasFocus()) {
	                        v.requestFocus();
	                    }
	                    break;
	            }
	            return false;
	        }
	    });
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btngo:
			String url;
			EditText addr = (EditText)findViewById(R.id.address);
			url = addr.getText().toString();
			mWeb.loadUrl(url);
			break;
		case R.id.btnback:
			if (mWeb.canGoBack()) {
				mWeb.goBack();
			}
			break;
		case R.id.btnforward:
			if (mWeb.canGoForward()) {
				mWeb.goForward();
			}
			break;
		case R.id.btnlocal:
			mWeb.loadUrl("file:///android_asset/test.html");
			mWeb.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mWeb.loadUrl("javascript:document.body.style.zoom = '250%'");
					Log.d("ldk", "scale:" + mWeb.getScale());
				}
			}, 1000	);
			
			Log.d("ldk", "scale:" + mWeb.getScale());
			break;
		}
	}
	
	class MyWebClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}