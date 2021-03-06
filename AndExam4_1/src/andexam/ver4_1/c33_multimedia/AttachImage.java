package andexam.ver4_1.c33_multimedia;

import java.io.*;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class AttachImage extends Activity {
	static final int CALL_CAMERA = 0;
	static final int CALL_CAMERA2 = 1;
	static final int CALL_GALLERY = 2;
	ImageView mImage;
	String mPath;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attachimage);

		mImage = (ImageView)findViewById(R.id.attachimage);
		mPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
				"/attachimage.jpg";
	}

	public void mOnClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btncamera:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CALL_CAMERA);
			break;
		case R.id.btncamera2:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
			startActivityForResult(intent, CALL_CAMERA2);
			break;
		case R.id.btngallery:
			intent = new Intent(Intent.ACTION_PICK, 
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
			startActivityForResult(intent, CALL_GALLERY);
			break;
		}
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CALL_CAMERA:
				mImage.setImageBitmap((Bitmap)data.getExtras().get("data"));
				break;
			case CALL_CAMERA2:
				mImage.setImageBitmap(BitmapFactory.decodeFile(mPath));
				break;
			case CALL_GALLERY:
				Uri uri = data.getData();
				try {
					mImage.setImageBitmap(MediaStore.Images.Media.getBitmap(
							getContentResolver(), data.getData()));
				} catch (Exception e) { ; }
				
				getWidthFromUri(uri);
				Log.d("ldk", "width:" + getWidth(uri));
				
				break;
			}
		}
	}
	
	private void getWidthFromUri(Uri uri){
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();

		Log.d("ldk", "Uri width:" + cursor.getString(cursor.getColumnIndex(MediaColumns.WIDTH)));
	}
	
	private int getWidth(Uri uri){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		int width = 0;
		
		try {
			Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
			width = options.outWidth;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return width;
	}
}

