package andexam.ver4_1.c38;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import andexam.ver4_1.c38.dto.Ball;
import andexam.ver4_1.c38.dto.MyView;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;

public class CountDownActivity extends Activity {
	private MyView mv;
	private SoundPool mPool;
	private int mDdok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_count_down);
		
		mv = new MyView(this);
		mv.init(mHandler);
		setContentView(mv);
		
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mDdok = mPool.load(this, R.raw.ddok, 1);
		
		//view의 높이
		Log.d("ldk", "view height:" + mv.getHeight());
	}
	
	public void playSound() {
		mPool.play(mDdok, 1, 1, 0, 0, 1);
	}
	
	public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            //축소
            case 0:
                Ball b = (Ball)(msg.obj);
                if (b.mScale > 0) {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = b;
                    mHandler.sendMessageDelayed(message, 200);
                    mv.invalidate();
                } else {
                    mv.ballList.remove(b);
                    mv.invalidate();
                }
                break;
            }
        }
	};

}
