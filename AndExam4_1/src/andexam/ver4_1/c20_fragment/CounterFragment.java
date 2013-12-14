package andexam.ver4_1.c20_fragment;

import andexam.ver4_1.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CounterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class CounterFragment extends Fragment {

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.counterfragment, container, false);

        Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
        final TextView textCounter=(TextView)root.findViewById(R.id.txtcounter);
        btnIncrease.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(textCounter.getText().toString());
                textCounter.setText(Integer.toString(count + 1));
            }
        });

        return root;
    }


}
