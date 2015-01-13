package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.net.Uri;
import android.os.*;
import android.widget.*;

public class VideoViewer extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoviewer);
		
		VideoView video = (VideoView)findViewById(R.id.videoview);
		String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
		//video.setVideoPath(sd + "/testvideo.mp4");
		video.setVideoURI(Uri.parse("http://192.168.0.101/cgi-bin/faststream.jpg?stream=MxPEG"));
		
		final MediaController mc = new MediaController(VideoViewer.this);
		video.setMediaController(mc);
		video.postDelayed(new Runnable() {
			public void run() {
				mc.show(0);
			}
		},100);
		//video.start();
	}
}

