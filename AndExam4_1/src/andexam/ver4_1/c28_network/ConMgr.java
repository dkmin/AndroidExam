package andexam.ver4_1.c28_network;

import andexam.ver4_1.R;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConMgr extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conmgr);

		TextView result = (TextView)findViewById(R.id.result);
		String sResult = "";
		ConnectivityManager mgr = (ConnectivityManager)
			getSystemService(CONNECTIVITY_SERVICE);
		
		NetworkInfo[] ani = mgr.getAllNetworkInfo();
		for (NetworkInfo n : ani) {
			sResult += (n.toString() + "\n\n");
		}

		NetworkInfo ni = mgr.getActiveNetworkInfo();
		if (ni != null) {
			sResult += ("Active : \n" + ni.toString() + "\n");
			result.setText(sResult);
		}
		
		if(mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
			Log.d("LDK", "wifi is available");
		}
		
/*		if(mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
			Log.d("LDK", "mobile is available");
		}*/
	}
}

