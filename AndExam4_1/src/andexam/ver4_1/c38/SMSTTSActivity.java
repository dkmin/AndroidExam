package andexam.ver4_1.c38;

import java.util.Locale;

import andexam.ver4_1.R;
import andexam.ver4_1.R.layout;
import andexam.ver4_1.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class SMSTTSActivity extends Activity implements TextToSpeech.OnInitListener {
    
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstts);
        
        tts = new TextToSpeech(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smstt, menu);
        return true;
    }
    
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiverBR, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiverBR);
    }

    BroadcastReceiver mReceiverBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String result = "";
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage msg = SmsMessage.createFromPdu((byte [])pdus[i]);
                    result += msg.getOriginatingAddress() + "에게서 온 문자입니다.     " +
                    msg.getMessageBody() + "\n";
                }
                Toast.makeText(SMSTTSActivity.this, result, Toast.LENGTH_SHORT).show();
                speakOut(result);
            }
        }
    };

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
