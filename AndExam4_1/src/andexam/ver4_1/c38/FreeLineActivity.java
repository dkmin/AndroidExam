package andexam.ver4_1.c38;

import java.util.*;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
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
	private TextView tvNowPenColor;
	
	private int penSize = 5;
	private int penColor = Color.BLACK;
	
	public class Vertex {
		float x;
		float y;
		boolean Draw;
		int eventType;
		int penSize;
		int penColor;
		
		Vertex(float ax, float ay, boolean ad, int eventType, int penSize, int penColor) {
			x = ax;
			y = ay;
			Draw = ad;
			this.eventType = eventType;
			this.penSize = penSize;
			this.penColor = penColor;
		}

	}

	ArrayList<Vertex> arVertex;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.freeline);
		
		tvNowPenSize = (TextView) findViewById(R.id.tvNowPenSize);
		tvNowPenSize.setText(String.valueOf(penSize));
		tvNowPenColor = (TextView) findViewById(R.id.tvNowPenColor);
		tvNowPenSize.setBackgroundColor(penColor);
		
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
		
		Button btnPenColor = (Button) findViewById(R.id.btnPenColor);
		btnPenColor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectColor();
				
			}
		});

		arVertex = new ArrayList<Vertex>();
	}
	
	private void selectPenSize() {
		LinearLayout linear = (LinearLayout) View.inflate(this, R.layout.penselect, null);
		SeekBar seekbarPenSize = (SeekBar) linear.findViewById(R.id.seekbarPenSize);
		final TextView tvPenSize = (TextView) linear.findViewById(R.id.tvPenSize); 
		
		seekbarPenSize.setProgress(penSize);
		tvPenSize.setText(String.valueOf(penSize));
		
		seekbarPenSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tvPenSize.setText( String.valueOf(progress) );
			}
		});
		
		new AlertDialog.Builder(this)
		.setTitle("Select Pen Size.")
		.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				penSize = Integer.parseInt( (String) tvPenSize.getText() );
				vw.setStrokeWidth(penSize);
				tvNowPenSize.setText( String.valueOf(penSize) );
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		})
		.show();
	}
	
	private void selectColor() {
		//https://github.com/brk3/android-color-picker.git
		
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, penColor, new OnAmbilWarnaListener() {
			
			@Override
			public void onOk(AmbilWarnaDialog arg0, int color) {
				penColor = color;
				tvNowPenColor.setBackgroundColor(color);
				vw.setColor(color);
			}
			
			@Override
			public void onCancel(AmbilWarnaDialog arg0) {
				
			}
		});
		dialog.show();
	}

	protected class MyView extends View {
		Paint mPaint;

		public MyView(Context context) {
			super(context);

			// Paint 객체 미리 초기화
			mPaint = new Paint();
			mPaint.setColor(penColor);
			mPaint.setStrokeWidth(penSize);
			mPaint.setAntiAlias(true);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
		}
		
		public void setStrokeWidth(int size) {
			mPaint.setStrokeWidth(size);
		}
		
		public void setColor(int color) {
			mPaint.setColor(color);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);

			// 정점을 순회하면서 선분으로 잇는다.
			for (int i=0;i<arVertex.size();i++) {
				if(arVertex.get(i).eventType == MotionEvent.ACTION_DOWN) {
					mPaint.setColor(arVertex.get(i).penColor);
					mPaint.setStrokeWidth(arVertex.get(i).penSize);
				}
				if (arVertex.get(i).Draw) {
					canvas.drawLine(arVertex.get(i-1).x, arVertex.get(i-1).y, 
							arVertex.get(i).x, arVertex.get(i).y, mPaint);
				}
			}
		}

		// 터치 이동 시마다 정점을 추가한다.
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				arVertex.add(new Vertex(event.getX(), event.getY(), false, MotionEvent.ACTION_DOWN, penSize, penColor));
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				arVertex.add(new Vertex(event.getX(), event.getY(), true, MotionEvent.ACTION_MOVE, penSize, penColor));
				invalidate();
				return true;
			}
			return false;
		}
	}
}
