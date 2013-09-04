package andexam.ver4_1.c18_process;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class ApplicationTest extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("ldk", "Process Id: " + android.os.Process.myPid() +  " Thread Id: " + android.os.Process.myTid());
		setContentView(R.layout.applicationtest);

		UpdateNowMode();
		
		//ThreadTest();
	}
	
	private void ThreadTest(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					Log.d("ldk", "Thread Id: " + android.os.Process.myTid());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	void UpdateNowMode() {
		TextView txtMode = (TextView)findViewById(R.id.mode);
		AndExam_Application app = (AndExam_Application)getApplication();
		Log.d("ldk", "Application address: " + app.toString());
		Log.d("ldk", "Application address2: " + AndExam_Application.getInstance().toString());
		
		if (app.getMode() == AndExam_Application.BEGINNER) {
			txtMode.setText("현재 모드 : 초보자 모드");
		} else {
			txtMode.setText("현재 모드 : 숙련자 모드");
		}
	}

	public void mOnClick(View v) {
		AndExam_Application app = (AndExam_Application)getApplication();
		switch (v.getId()) {
		case R.id.beginner:
			app.setMode(AndExam_Application.BEGINNER);
			break;
		case R.id.professional:
			app.setMode(AndExam_Application.PREFESSIONAL);
			break;
		}
		UpdateNowMode();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("ldk", "Thread Id: " + Thread.currentThread().getId() +  " Activity onDestory");
	}
	
}


