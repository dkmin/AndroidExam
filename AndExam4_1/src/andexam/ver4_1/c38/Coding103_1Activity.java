package andexam.ver4_1.c38;

import andexam.ver4_1.R;
import andexam.ver4_1.R.id;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Coding103_1Activity extends Activity {

	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coding103_1);
		
		et = (EditText) findViewById(R.id.editText1);
		Button btnSend = (Button) findViewById(R.id.button1);
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView input = new TextView(Coding103_1Activity.this); //1
				input.setBackgroundResource(R.drawable.popup_inline_error_above_am);
				input.setTextSize(30);
				input.setText(et.getText().toString());
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); //3
				params1.gravity = Gravity.RIGHT;
				LinearLayout container = (LinearLayout) findViewById(R.id.container);
				container.addView(input, params1);   //4
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coding103_1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
