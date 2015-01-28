package andexam.ver4_1.c38;

import java.io.IOException;
import java.io.InputStream;

import andexam.ver4_1.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Coding101_2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coding101_2);
		
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		container.addView(new MyView(this));
	}

	class MyView extends View {
		private Paint mPaint;
		Bitmap mBitmap;

		public MyView(Context context) {
			super(context);
			mPaint = new Paint();
			mPaint.setColor(Color.BLUE);
			
			AssetManager assetManager = getAssets();
		    InputStream is = null;
		    try {
		        is = assetManager.open("coffee1.png");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    mBitmap = BitmapFactory.decodeStream(is);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			
			canvas.drawCircle(490, 200, 100, mPaint);
			
			canvas.drawBitmap(mBitmap, 440, 400, null);
			
			super.onDraw(canvas);
		}
		
		
	}
}
