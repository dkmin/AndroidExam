package andexam.ver4_1.c38;

import java.util.Locale;

import andexam.ver4_1.R;
import andexam.ver4_1.R.id;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

public class SMSUnLockActivity extends Activity implements TextToSpeech.OnInitListener {
    
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsun_lock);
        
        tts = new TextToSpeech(this, this);
        
         // 화면이 잠겨있을 때 보여주기
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    // 키잠금 해제하기
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    // 화면 켜기
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
        String result = getIntent().getStringExtra("sms");
        TextView tv = (TextView)findViewById(R.id.tvReceivedSMS);
        tv.setText(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smsun_lock, menu);
        return true;
    }
    
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            
            int result = tts.setLanguage(Locale.KOREA);
 
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    
    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
