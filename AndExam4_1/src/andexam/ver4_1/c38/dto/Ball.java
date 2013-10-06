package andexam.ver4_1.c38.dto;

import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class Ball {
	public final int RADIUS;  //화면해상도에 일정한 반지름크기 : 화면넓이/10
	public int centerX, centerY;
	public int radius; //가변 반지름
	public Paint bgPaint, fontPaint;
	public int tag; //숫자
    public boolean mIsDead;
    public float mScale = 1.0f;
	
	public Ball(int centerX, int centerY, int radius, int tag) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		RADIUS = radius;
		this.tag = tag;
		
		bgPaint = new Paint();
		Random rand = new Random();
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
