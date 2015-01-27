package andexam.ver4_1.c38;

import andexam.ver4_1.R;
import andexam.ver4_1.R.id;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;

public class Coding102_1Activity extends Activity {
	
	TextView tv1, tv2, tv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coding101_3);
		
		Button eventBtn = (Button)findViewById(R.id.btnEvent1);
        eventBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(Coding102_1Activity.this, "button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
		
		//edittext------------------------------------------------------------------------------
		CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
		tv1 = (TextView) findViewById(R.id.textView1);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					tv1.setText("체크되었습니다");
				} else {
					tv1.setText("체크가 해제되었습니다");
				}
			}
		});
		
		DatePicker picker = (DatePicker) findViewById(R.id.datePicker1);
		tv2 = (TextView) findViewById(R.id.textView2);
		picker.init(picker.getYear(), picker.getMonth(), picker.getDayOfMonth(), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
				String date = String.format("%d-%02d-%02d", year, monthOfYear+1, dayOfMonth);
				tv2.setText(date);
			}
		});
		
		//edittext------------------------------------------------------------------------------
		EditText et = (EditText) findViewById(R.id.editText1);
		tv3 = (TextView) findViewById(R.id.tvLength);
		
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				tv3.setText(String.valueOf(s.length()));
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coding101_3, menu);
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
