package andexam.ver4_1.c16_dialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.*;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

public class Popup extends Activity {
	PopupWindow popup;
	View popupview;
	LinearLayout linear;
	
	private Handler mHandler = new Handler();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);

		linear = (LinearLayout)findViewById(R.id.linear);
		popupview = View.inflate(this, R.layout.popupview, null);
		popup = new PopupWindow(popupview,200,150,true);

		final Button btnshow = (Button)findViewById(R.id.btnshow);
		btnshow.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 지정한 좌표에 놓기
				popup.showAtLocation(linear,Gravity.NO_GRAVITY,100,150);
				// 가운데 놓기
				//popup.showAtLocation(linear,Gravity.CENTER,0,0);
				// 가운데 + 50, 80에 놓기
				//popup.showAtLocation(linear,Gravity.CENTER,50,80);
				// 화면 벗어나기
				//popup.showAtLocation(linear,Gravity.NO_GRAVITY,800,1200);
				// 수평 중앙, 수직 바닥 
				//popup.showAtLocation(linear,Gravity.CENTER_HORIZONTAL|
				//	Gravity.BOTTOM,0,0);
				// 수평 중앙, 수직 바닥 + (50,50)
				//popup.showAtLocation(linear,Gravity.CENTER_HORIZONTAL|
				//		Gravity.BOTTOM,50,50);
				// 버튼 아래에 놓기
				//popup.setAnimationStyle(-1);
				//popup.showAsDropDown(btnshow);
			}
		});

		Button btnclose = (Button)popupview.findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				popup.dismiss();
			}
		});
		
		
	}
	
	private void reflectionWindow(){
		//reflection
		try {
			Class clsWindow = Class.forName("android.view.WindowManagerImpl");
			Method m = clsWindow.getMethod("getDefault", null);
			final WindowManager wm = (WindowManager)m.invoke(null, null);
			
			
			final TextView tv = new TextView(this);
			tv.setText("aaaaaaaaaaaaaaaaaaaaaaa");
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); //텍스트 크기 18sp
		    tv.setTextColor(Color.BLUE);                    //글자 색상
			
//			AlertDialog mDialog = new AlertDialog.Builder(this)
//				.setTitle("알립니다.")
//				.setMessage("대화상자를 열었습니다.")
//				.setIcon(R.drawable.androboy)
//				.show();

			final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.format = PixelFormat.TRANSLUCENT;
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
			params.setTitle("Toast");
			wm.addView(tv, params);
			wm.addView(popupview, params);
			mHandler.postDelayed(new Runnable() {

				public void run() {
				wm.removeView(tv);
	
				}

			}, 10000);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	//if activity is on pause, is it possible to show popup???
	//don't work
	@Override
	protected void onPause() {
		super.onPause();
		//this window doesn't depend on activity
		reflectionWindow();
	}
	
	
}
