package andexam.ver4_1.c07_output;

import java.util.ArrayList;
import java.util.Random;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.os.*;
import android.util.DisplayMetrics;
import android.view.*;

public class CustomView extends Activity {
    private ArrayList<MyCircle> mMyCircleList = new ArrayList<MyCircle>();
    MyView vw;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
	    private final int RADIUS;
	    
		public MyView(Context context) {
			super(context);
			DisplayMetrics dm = getResources().getDisplayMetrics();
			
			RADIUS = dm.widthPixels/15;
			
			for(int i=1;i<=10; i++) {
			    MyCircle circle = new MyCircle(dm.widthPixels, dm.heightPixels, RADIUS, i);
			    mMyCircleList.add(circle);
			}
		}

		public void onDraw(Canvas canvas) {
            for (MyCircle c : mMyCircleList) {
                if (c.mIsDead) {
                    c.update(0.1f);
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = c;
                    mHandler.sendMessageDelayed(msg, 200);
                    break;
                }
            }
            
            for (MyCircle c : mMyCircleList) {

                canvas.drawCircle(c.centerX, c.centerY, c.radius, c.bgPaint);
                canvas.drawText(String.valueOf(c.tag), c.centerX, c.centerY + c.radius / 2, c.fontPaint);
            }
            

		}

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            for (MyCircle c : mMyCircleList) {
                if (checkContain(c, event)) {
                    c.mIsDead = true;
                    invalidate();
                    break;
                }
            }
            return true;
        }
        
        private boolean checkContain(MyCircle c, MotionEvent event) {
            double distance = Math.pow(c.centerX - event.getX(), 2) + Math.pow(c.centerY - event.getY(), 2);
            if(distance < RADIUS * RADIUS) {
                return true;
            }
            else {
                return false;
            }
        }
	}
	
	public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            //축소
            case 0:
                MyCircle c = (MyCircle)(msg.obj);
                if (c.mScale > 0) {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = c;
                    mHandler.sendMessageDelayed(message, 200);
                    vw.invalidate();
                } else {
                    mMyCircleList.remove(c);
                    vw.invalidate();
                }
                break;
            }
        }
	};
}

class MyCircle {
    private final int RADIUS;
    private int mWidth, mHeight;
    public int centerX, centerY;
    public int radius;
    public int tag;
    public Paint bgPaint, fontPaint;
    public boolean mIsDead;
    public float mScale = 1.0f;
    
    public MyCircle(int mWidth, int mHeight, int radius, int tag) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.tag = tag;
        this.radius = radius;
        RADIUS = radius;
        
        Random rand = new Random();
        
        centerX = radius + rand.nextInt(mWidth - radius * 2);
        centerY = radius + rand.nextInt(mWidth - radius * 2);
        
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setARGB(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        fontPaint = new Paint();
        fontPaint.setColor(Color.WHITE);
        fontPaint.setAntiAlias(true);
        fontPaint.setTextSize(radius);
        fontPaint.setTextAlign(Align.CENTER);
    }
    
    public void update(float scale) {
        this.mScale -= scale;
        radius = (int)(RADIUS * mScale);
        fontPaint.setTextSize(radius);
    }
}
