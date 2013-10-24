package andexam.ver4_1.c38.dto;

import java.util.ArrayList;
import java.util.Random;

import andexam.ver4_1.c38.CountDownActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
	private Context mContext;
	private Handler mHandler;
	public ArrayList<Ball> ballList = new  ArrayList<Ball>();
	public int mRadius;
	public int mCurrentTag;
	public Paint fontPaint, successPaint;
	private DisplayMetrics dm;
	
	public int mScore;
	public int mTime;
	
	public boolean mIsGameOver;

	public MyView(Context context) {
		super(context);
		mContext = context;
		
		fontPaint = new Paint();
		fontPaint.setTextSize(100);
		fontPaint.setColor(Color.WHITE);
		
		successPaint = new Paint();
		successPaint.setTextSize(200);
		successPaint.setColor(Color.YELLOW);
		successPaint.setTextAlign(Align.CENTER);
		
		dm = getResources().getDisplayMetrics();
		Random rand = new Random();
		mRadius = dm.widthPixels/15;
		
		mCurrentTag = 10; //10개
		for(int i=0; i<mCurrentTag; i++) {
			int centerX = mRadius + rand.nextInt( dm.widthPixels-mRadius*2);
			int centerY = 200 + mRadius + rand.nextInt( dm.widthPixels-mRadius*2); //HUD:200px
			Ball ball = new Ball(centerX, centerY, mRadius, i+1);
			ballList.add(ball);
		}
		
		
	}
	
	public void init (Handler handler) {
		mHandler = handler;
		//start
        mHandler.sendEmptyMessageDelayed(100, 1000);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawColor(Color.DKGRAY);
	    
	    //time 그리기
	    canvas.drawText(String.format("Time: %02d:%02d", mTime/60, mTime%60),  dm.widthPixels/2, 150, fontPaint);
	    canvas.drawText(String.format("Score: %02d", mScore), 50, 150, fontPaint);
	    
        for (Ball mBall : ballList) {
            if (mBall.mIsDead) {
                mBall.update(0.1f);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = mBall;
                mHandler.sendMessageDelayed(msg, 200);
                break;
            }
        }
        
		for( Ball mBall : ballList) {
			canvas.drawCircle(mBall.centerX, mBall.centerY, mBall.radius, mBall.bgPaint);
			canvas.drawText(String.valueOf(mBall.tag), mBall.centerX, mBall.centerY + mBall.radius/2, mBall.fontPaint);
		}
		
		if(mIsGameOver) {
		    canvas.drawText("성공", dm.widthPixels/2, dm.heightPixels/2, successPaint);
		}
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if(mCurrentTag <= 0) {
    		return true;
    	}
    	
    	Ball currentBall = ballList.get(mCurrentTag-1);
        if (checkContain(currentBall, event)) {
        	mCurrentTag += -1;
        	currentBall.mIsDead = true;
            invalidate();
            ((CountDownActivity)mContext).playSound();
            mScore++;
            
            if(mScore == 10) {
                mHandler.sendEmptyMessageDelayed(10, 1000);
            }
        }
        
        return true;
    }
    
    private boolean checkContain(Ball b, MotionEvent event) {
        double distance = Math.pow(b.centerX - event.getX(), 2) + Math.pow(b.centerY - event.getY(), 2);
        if(distance < mRadius * mRadius) {
            return true;
        }
        else {
            return false;
        }
    }

}
