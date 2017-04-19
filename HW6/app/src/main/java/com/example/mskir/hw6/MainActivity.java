package com.example.mskir.hw6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dataList = new ArrayList<>();
    ArrayList<Data> instanceList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Data data_container;

    ListView listView;
    TextView tv;

    final int _ADD_QUERY = 100;
    final int _CALL_QUERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("data",instanceList.get(position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                final int x = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("삭제확인")
                        .setIcon(R.drawable.baemin)
                        .setMessage("선택한 맛집을 정말 삭제할꺼에요?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataList.remove(x);
                                instanceList.remove(x);
                                adapter.notifyDataSetChanged();
                                tv.setText("맛집 리스트(" + dataList.size() + "개)");
                                Snackbar.make(view,"삭제되었습니다.",1000).setAction("확인",null).show();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();

                return false;
            }
        });

    }
    public void onClick(View v){
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra("data",data_container);
        startActivityForResult(intent,_ADD_QUERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == _ADD_QUERY){
            if(resultCode == RESULT_OK){
                data_container = data.getParcelableExtra("data");
                dataList.add(data_container.getName());
                instanceList.add(data_container);
                adapter.notifyDataSetChanged();
                tv.setText("맛집 리스트(" + dataList.size() + "개)");
            }
        }else{
            if(resultCode == RESULT_OK){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initListView() {
        tv = (TextView)findViewById(R.id.tv);
        listView = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
    }
}
