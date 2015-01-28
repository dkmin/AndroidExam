package andexam.ver4_1.c38;

import andexam.ver4_1.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Coding104_1Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coding104_1);
		
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		View view1 = View.inflate(this, R.layout.activity_coding104_list, null);
		container.addView(view1, params1);
		
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		View view2 = View.inflate(this, R.layout.activity_coding104_list, null);
		TextView tvName = (TextView) view2.findViewById(R.id.name);
		tvName.setText("안드로이드 정복1");
		container.addView(view2, params2);
		
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		View view3 = View.inflate(this, R.layout.activity_coding104_list, null);
		TextView tvName3 = (TextView) view3.findViewById(R.id.name);
		tvName3.setText("안드로이드 정복2");
		container.addView(view3, params3);
		
	}

}
