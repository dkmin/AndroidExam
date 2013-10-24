package andexam.ver4_1.c33_multimedia;

import java.io.File;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.provider.MediaStore.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class ImageViewer extends Activity {
	ImageView mImage;
	Cursor mCursor;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageviewer);
		
		ListView list = (ListView)findViewById(R.id.list);
		mImage = (ImageView)findViewById(R.id.image);
		
		ContentResolver cr = getContentResolver();
		mCursor = cr.query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		SimpleCursorAdapter Adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1,
				mCursor, new String[] { MediaColumns.DISPLAY_NAME }, 
				new int[] { android.R.id.text1});
		list.setAdapter(Adapter);
		list.setOnItemClickListener(mItemClickListener);
		startManagingCursor(mCursor);
		
		
		//file path 에서 content uri 로 변경하기
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/100LGDSC/CAM00001.jpg");
        if (f.exists()) {
            Uri uri = Uri.fromFile(f);
            String mimeType = getContentResolver().getType(uri);
            Log.d("ldk", "mimeType:" + mimeType);
        }
		
/*		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/100LGDSC/CAM00001.jpg";
	    Cursor cursor = getContentResolver().query(
	            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	            new String[] { MediaStore.Images.Media._ID },
	            MediaStore.Images.Media.DATA + "=? ",
	            new String[] { filePath }, null);

	    if (cursor != null && cursor.moveToFirst()) {
	        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
	        Uri baseUri = Uri.parse("content://media/external/images/media");
	        Uri.withAppendedPath(baseUri, "" + id);
	    } else {
	        if (f.exists()) {
	            ContentValues values = new ContentValues();
	            values.put(MediaStore.Images.Media.DATA, filePath);
	            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	        } else {
	            return;
	        }
	    }*/
		
		//file path를 content uri로 변환
//		String fileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/100LGDSC/CAM00001.jpg";
//
//		String filePath = fileStr;
//
//		Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data ='" + filePath + "'",null,null);
//		//Cursor c = getContentResolver().query(MediaStore.Files.getContentUri("external"), null, "_data ='" + filePath + "'",null,null);
//
//		c.moveToNext();
//
//		int id = c.getInt(0);
//
//		Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);
//
//		Log.e("URI",uri.toString());
		//10-23 19:52:23.359: E/URI(6849): content://media/external/images/media/91 결과는 동일


	}

	AdapterView.OnItemClickListener mItemClickListener = 
		new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, 
				int position, long id) {
			mCursor.moveToPosition(position);
			String path = mCursor.getString(mCursor.getColumnIndex(
					Images.ImageColumns.DATA));
			try {
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(path, opt);
				mImage.setImageBitmap(bm);
			}
			catch (OutOfMemoryError e) {
				Toast.makeText(ImageViewer.this,"이미지가 너무 큽니다.",0).show();
			}
		}
	};
}
