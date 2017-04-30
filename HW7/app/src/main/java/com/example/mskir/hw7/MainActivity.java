package com.example.mskir.hw7;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mskir.hw6.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<Data> instanceList = new ArrayList<>();
    Data data_container;
    Button b_add,b_name,b_kind,b_select,b_delete;
    DataAdapter dataAdapter;
    ListView listView;
    EditText editText;

    final int _ADD_QUERY = 100;

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
                                instanceList.remove(x);
                                dataAdapter.notifyDataSetChanged();
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
    public void onClick(final View v){
        if(v == b_add){
            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra("data",data_container);
            startActivityForResult(intent,_ADD_QUERY);}
        else if(v == b_name){
            Comparator<Data> nameAsc = new Comparator<Data>() {
                @Override
                public int compare(Data o1, Data o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };

            Collections.sort(instanceList, nameAsc);
            dataAdapter.notifyDataSetChanged();
        }
        else if(v == b_kind){
            Comparator<Data> nameAsc = new Comparator<Data>() {
                @Override
                public int compare(Data o1, Data o2) {
                    if (o1.getCategory() < o2.getCategory())
                        return -1;
                    else if (o1.getCategory() < o2.getCategory())
                        return 0;
                    else {
                        return 1;
                    }
                }
            };
            Collections.sort(instanceList, nameAsc);
            dataAdapter.notifyDataSetChanged();

        }else if(v == b_select){
            checkBoxHandler(true);
        }else if( v == b_delete){

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("삭제확인")
                    .setIcon(R.drawable.baemin)
                    .setMessage("선택한 맛집을 정말 삭제할꺼에요?")
                    .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            instanceList = dataAdapter.deleteCheckedItem();
                            checkBoxHandler(false);
                        }
                    })
                    .setNegativeButton("취소", null)
                    .create()
                    .show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == _ADD_QUERY){
            if(resultCode == RESULT_OK){
                data_container = data.getParcelableExtra("data");
                instanceList.add(data_container);
                dataAdapter.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initListView() {
        dataAdapter = new DataAdapter(this, instanceList);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(dataAdapter);
        b_add = (Button) findViewById(R.id.b_add);
        b_name = (Button) findViewById(R.id.b_name);
        b_kind = (Button) findViewById(R.id.b_kind);
        b_select = (Button) findViewById(R.id.b_select);
        b_delete = (Button) findViewById(R.id.b_delete);
        editText = (EditText) findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString() ;
                if (filterText.length() > 0) {
                    listView.setFilterText(filterText) ;
                } else {
                    listView.clearTextFilter() ; }
            }
        });
    }

    public void checkBoxHandler(Boolean handler){
        if(handler == true){
            dataAdapter.viewCheckBox(true);
            b_select.setVisibility(View.GONE);
            b_delete.setVisibility(View.VISIBLE);
            dataAdapter.notifyDataSetChanged();
        }else{
            dataAdapter.viewCheckBox(false);
            b_delete.setVisibility(View.GONE);
            b_select.setVisibility(View.VISIBLE);
            dataAdapter.notifyDataSetChanged();
        }
    }
}
