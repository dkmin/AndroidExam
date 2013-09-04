package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ObjectAnimatorTest extends Activity {
	Button mBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.valueanimatortest);
		
		mBtn=(Button)findViewById(R.id.btn);
	}
	
	public void mOnClick(View v) {
		ObjectAnimator anim = ObjectAnimator.ofInt(mBtn, "width", 200, 400);
		anim.setDuration(2000);
		anim.start();
	}	
}
