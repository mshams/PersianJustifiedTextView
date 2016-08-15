package it.tadbir.persianjustifiedtextview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.tadbir.ui.PersianJustifiedTextView;
import it.tadbir.ui.persianjustifiedtextview.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "it.tadbir.app:";
    private TextView txt2;
    private PersianJustifiedTextView txt1;
    private Button btn1, btn2;
    private EditText edit1, edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Log.d(TAG, "onPostCreate");

        txt1 = (PersianJustifiedTextView) findViewById(R.id.txtView1);
        txt2 = (TextView) findViewById(R.id.txtView2);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");

        if (v == btn1) {
            txt1.append(edit1.getText());
        } else if (v == btn2) {
            txt2.append(edit2.getText());
        }
    }
}
