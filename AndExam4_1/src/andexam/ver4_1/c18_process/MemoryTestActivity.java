package andexam.ver4_1.c18_process;

import java.lang.ref.WeakReference;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.TextView;

public class MemoryTestActivity extends Activity {
	private static Drawable sBackground;  //cause memory leak
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*
		TextView label = new TextView(getApplicationContext());
		label.setText("Leaks are bad");

		if (sBackground == null) {
			sBackground = getResources().getDrawable(R.drawable.large_bitmap);
		}
		label.setBackground(sBackground);
		*/
		
		TextView label = new TextView(getApplicationContext());
		WeakReference<TextView> wtv = new WeakReference<TextView>(label);
		
		if(wtv != null){
			TextView tv = wtv.get();
			if(tv != null){
				sBackground = getResources().getDrawable(R.drawable.large_bitmap);
				setContentView(tv);
			}
		}

		
	}

}
