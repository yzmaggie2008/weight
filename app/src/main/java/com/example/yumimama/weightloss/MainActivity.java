package com.example.yumimama.weightloss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage,tx_Submit;
    private EditText mWeight,mHeight,mGender;
    private Button btn_Submit,btn_Cancel;
    private RadioButton selectedRadioButton;
    private RadioGroup radioGroup;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    Intent intent = new Intent(MainActivity.this, history.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_information:
                    Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.navigation_setting:
                    Intent intent2 = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final EditText mWeight = (EditText) findViewById(R.id.weight);
        final EditText mHeight = (EditText) findViewById(R.id.height);

        final TextView tx_Submit = findViewById(R.id.tx_submit);
        radioGroup = (RadioGroup)findViewById(R.id.rd_group);
        btn_Submit = (Button)findViewById(R.id.submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               selectedRadioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

               String yourSelect = selectedRadioButton.getText().toString();
               tx_Submit.setText("The information you entered: \n" + "Weight: " + mWeight.getText().toString() +"\n" + "Height: "  + mHeight.getText().toString() + "\n" + "Gender: " +yourSelect);
            }
        });
        btn_Cancel= (Button) findViewById(R.id.cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHeight.setText("");
                mWeight.setText("");
            }
        });

    }

}
