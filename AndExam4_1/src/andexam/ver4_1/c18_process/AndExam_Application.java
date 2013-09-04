package andexam.ver4_1.c18_process;

import android.app.*;
import android.util.Log;

public class AndExam_Application extends Application {
	private int mMode;
	static final int BEGINNER = 0;
	static final int PREFESSIONAL = 1;
	
	private static AndExam_Application mApplication;
	
	public static AndExam_Application getInstance(){
		return mApplication;
	}

	public void onCreate() {
		super.onCreate();
		mApplication = this;
		mMode = BEGINNER;
		Log.d("ldk", "application onCreate");
		
		try
		{
			String version = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			Log.d("ldk", "application version: " + version);
		}
		catch (Exception e)
		{}
		
		Log.d("ldk", "Process Id: " + android.os.Process.myPid() +  " Thread Id: " + android.os.Process.myTid());
	}

	public void onTerminate() {
		super.onTerminate();
	}
	
	public int getMode() {
		return mMode;
	}
	
	public void setMode(int aMode) {
		mMode = aMode;
	}
	
}