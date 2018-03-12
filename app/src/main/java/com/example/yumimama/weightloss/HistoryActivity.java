package com.example.yumimama.weightloss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by yumimama on 2/5/18.
 */

public class HistoryActivity extends Activity implements View.OnClickListener {
    private EditText etDate,etWeight;

    private Button btnChange,btnDelete,btnAdd;
    private int id;
    private DatabaseHandler handler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        etDate= (EditText) findViewById(R.id.history_date);
        etWeight= (EditText) findViewById(R.id.history_weight);
        btnChange= (Button) findViewById(R.id.btn_change);
        btnDelete= (Button) findViewById(R.id.btn_delete);
        btnAdd= (Button) findViewById(R.id.btn_add_history);

        handler=new DatabaseHandler(this);

        intent=getIntent();


        String request=intent.getStringExtra("request");
        switch (request){

            case "Add":
                btnChange.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
                break;

            case "Look":
                id=intent.getExtras().getInt("id");
                etDate.setText(intent.getStringExtra("date"));
                etWeight.setText(intent.getStringExtra("weight"));


        }
        btnAdd.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_history:
                historyData newHistory=new historyData(id,etDate.getText().toString(),etWeight.getText().toString());
                handler.addHistory(newHistory);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_change:
                historyData history=new historyData(id,etDate.getText().toString(),etWeight.getText().toString());
                handler.updateHitory(history);
                //这里设置resultCode是为了区分是修改后返回主界面的还是删除后返回主界面的。
                setResult(2,intent);
                finish();
                break;
            case R.id.btn_delete:
                historyData h=new historyData(id,etDate.getText().toString(),etWeight.getText().toString());
                handler.deleteHistory(h);
                setResult(3, intent);
                finish();
                break;
        }
    }
}
