package com.example.yumimama.weightloss;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumimama on 1/17/18.
 */

public class history extends AppCompatActivity implements View.OnClickListener {
    private ListView historys;
    private HistoryAdapter adapter;
    private Button btnAdd,btnSearch;
    private DatabaseHandler dbHandler;
    private List<historyData> historyList;
    private SQLiteDatabase db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    Intent intent = new Intent(history.this, history.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_information:
                    Intent intent1 = new Intent(history.this, MainActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.navigation_setting:
                    Intent intent2 = new Intent(history.this, CameraActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        historys= (ListView) findViewById(R.id.history_list);
        btnAdd= (Button) findViewById(R.id.btn_add);
        btnSearch= (Button) findViewById(R.id.btn_search);


        btnSearch.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        dbHandler=new DatabaseHandler(this);


        historyList = dbHandler.getAllHistory();
        adapter = new HistoryAdapter(this,historyList);
        historys.setAdapter(adapter);


        historys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(history.this,HistoryActivity.class);


                intent.putExtra("request","Look");
                intent.putExtra("id",historyList.get(i).getId());
                intent.putExtra("date",historyList.get(i).getDate());
                intent.putExtra("weight",historyList.get(i).getWeight());

                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                Intent i=new Intent(history.this,HistoryActivity.class);

                i.putExtra("request","Add");
                startActivityForResult(i, 1);
                break;
            case R.id.btn_search:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);


                final ConstraintLayout searchView= (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_search,null);
                builder.setView(searchView);
                final AlertDialog dialog=builder.create();
                dialog.show();


                final EditText searchDate= (EditText) searchView.findViewById(R.id.search_date);
                Button btnDialogSearch= (Button) searchView.findViewById(R.id.btn_search_dialog);
                btnDialogSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchDate.setVisibility(View.GONE);
                        ListView list = (ListView) searchView.findViewById(R.id.search_result);
                        List<historyData> resultList = new ArrayList<historyData>();
                        final historyData searchHistory = dbHandler.getHistory(searchDate.getText().toString());
                        if (searchHistory != null) {
                            resultList.add(searchHistory);
                            HistoryAdapter resultAdapter = new HistoryAdapter(getApplicationContext(), resultList);
                            list.setAdapter(resultAdapter);
                            list.setVisibility(View.VISIBLE);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(history.this, HistoryActivity.class);
                                    intent.putExtra("request", "Look");
                                    intent.putExtra("id", searchHistory.getId());
                                    intent.putExtra("date", searchHistory.getDate());
                                    intent.putExtra("weight", searchHistory.getWeight());
                                    startActivityForResult(intent, 0);
                                }
                            });
                        } else {
                            //关闭Dialog
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "No data existed at that day", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode){
            case 0:
                if (resultCode==2)
                    Toast.makeText(this,"Saved Change",Toast.LENGTH_SHORT).show();
                if (resultCode==3)
                    Toast.makeText(this,"Already Delete",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                if (resultCode==RESULT_OK)
                    Toast.makeText(this,"Add Successful",Toast.LENGTH_SHORT).show();
                break;
        }
        historyList = dbHandler.getAllHistory();
        adapter = new HistoryAdapter(this,historyList);
        historys.setAdapter(adapter);

    }
    }

