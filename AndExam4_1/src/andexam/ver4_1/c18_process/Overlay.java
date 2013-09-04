package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Overlay extends Activity {
	Window win;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		win = getWindow();
		
		win.setContentView(R.layout.overlay);

		showWindow();
	}
	
	private void showWindow(){
		LayoutInflater inflater = (LayoutInflater)getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.overlay2, null);
		LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		win.addContentView(linear, paramlinear);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//showWindow();
	}
	
	
}
