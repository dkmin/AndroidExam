package andexam.ver4_1.c38;

import andexam.ver4_1.c18_process.AndExam_Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSTTSReceiver extends BroadcastReceiver {
    public SMSTTSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        
        
        String result = "";
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[])bundle.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage msg = SmsMessage.createFromPdu((byte [])pdus[i]);
                result += msg.getOriginatingAddress() + "에게서 온 문자입니다.     " +
                msg.getMessageBody() + "\n";
            }
            //화면이 꺼져 있으면 토스트가 안뜨므로 화면을 깨우는 부분을 액티비티에 구현함.
            //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, SMSUnLockActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("sms", result);
            context.startActivity(i);
        }
       
    }
}
