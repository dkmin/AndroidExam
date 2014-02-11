package andexam.ver4_1.c26_cp;

import andexam.ver4_1.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

public class ContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        
        CursorLoaderListFragment contact = new CursorLoaderListFragment();
        getFragmentManager().beginTransaction().add(R.id.frameContact, contact).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

}
