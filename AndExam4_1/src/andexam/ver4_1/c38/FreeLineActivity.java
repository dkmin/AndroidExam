package andexam.ver4_1.c38;

import java.util.*;

import andexam.ver4_1.R;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FreeLineActivity extends Activity {
	private MyView vw;
	// 정점 하나에 대한 정보를 가지는 클래스
	private TextView tvNowPenSize;
	
	private int penSize = 3;
	
	public class Vertex {
		Vertex(float ax, float ay, boolean ad) {
			x = ax;
			y = ay;
			Draw = ad;
		}
		float x;
		float y;
		boolean Draw;
	}

	ArrayList<Vertex> arVertex;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.freeline);
		
		tvNowPenSize = (TextView) findViewById(R.id.tvNowPenSize);
		
		LinearLayout linear = (LinearLayout) findViewById(R.id.linearFreeline);
		vw = new MyView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		linear.addView(vw, params);
		
		Button btnPenSize = (Button) findViewById(R.id.btnPenSize);
		btnPenSize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectPenSize();
				
			}
		});

		arVertex = new ArrayList<Vertex>();
	}
	
	private void selectPenSize() {
		LinearLayout linear = (LinearLayout) View.inflate(this, R.layout.penselect, null);
		SeekBar seekbarPenSize = (SeekBar) linear.findViewById(R.id.seekbarPenSize);
		final TextView tvPenSize = (TextView) linear.findViewById(R.id.tvPenSize); 
		
		seekbarPenSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tvPenSize.setText("Now Pen Size : " + progress);
			}
		});
		
		new AlertDialog.Builder(this)
		.setTitle("Select Pen Size.")
		.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String temp = (String) tvPenSize.getText();
				penSize = Integer.parseInt( temp.replace("Now Pen Size : ", "") );
				vw.setStrokeWidth(penSize);
				tvNowPenSize.setText("" + penSize);
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		})
		.show();
	}

	protected class MyView extends View {
		Paint mPaint;

		public MyView(Context context) {
			super(context);

			// Paint 객체 미리 초기화
			mPaint = new Paint();
			mPaint.setColor(Color.BLACK);
			mPaint.setStrokeWidth(penSize);
			mPaint.setAntiAlias(true);
		}
		
		public void setStrokeWidth(int size) {
			mPaint.setStrokeWidth(size);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);

			// 정점을 순회하면서 선분으로 잇는다.
			for (int i=0;i<arVertex.size();i++) {
				if (arVertex.get(i).Draw) {
					canvas.drawLine(arVertex.get(i-1).x, arVertex.get(i-1).y, 
							arVertex.get(i).x, arVertex.get(i).y, mPaint);
				}
			}
		}

		// 터치 이동 시마다 정점을 추가한다.
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				arVertex.add(new Vertex(event.getX(), event.getY(), false));
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				arVertex.add(new Vertex(event.getX(), event.getY(), true));
				invalidate();
				return true;
			}
			return false;
		}
	}
}
