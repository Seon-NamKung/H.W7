package com.example.mskir.hw7;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mskir.hw6.R;

public class Main3Activity extends AppCompatActivity {
    TextView txtname,etmenu1,etmenu2,etmenu3,tvTel,tvURL,tvRegdate;
    ImageView imgno,imageView2,imageView3;
    Data container;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        init();
        receiveData();

        setInfo(container);
    }

    public void init(){
        txtname = (TextView)findViewById(R.id.txtname);
        etmenu1 = (TextView)findViewById(R.id.etmenu1);
        etmenu2 = (TextView)findViewById(R.id.etmenu2);
        etmenu3 = (TextView)findViewById(R.id.etmenu3);
        tvTel = (TextView)findViewById(R.id.tvTel);
        tvURL = (TextView)findViewById(R.id.tvURL);
        tvRegdate = (TextView)findViewById(R.id.tvRegdate);

        imgno = (ImageView)findViewById(R.id.imgno);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
    }

    void sendData(Intent intent){

    }

    void receiveData(){
        intent = getIntent();
        container = intent.getParcelableExtra("data");
    }

    void setInfo(Data data){
        txtname.setText(data.getName());
        etmenu1.setText(data.getMenu()[0]);
        etmenu2.setText(data.getMenu()[1]);
        etmenu3.setText(data.getMenu()[2]);
        tvTel.setText(data.getCall());
        tvURL.setText(data.getHp());
        tvRegdate.setText(data.getRegistered());

        switch (data.getCategory()){
            case R.drawable.chicken:
                imgno.setImageResource(R.drawable.chicken);
                break;
            case R.drawable.pizza:
                imgno.setImageResource(R.drawable.pizza);
                break;
            case R.drawable.hamnurger:
                imgno.setImageResource(R.drawable.hamnurger);
                break;
        }
    }

    public void goBack(View v){
        finish();
    }

    //CHROME으로 안될 경우 Open with WebView Browser Tester로 open
    public void browsing(View v){
        Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+container.getHp()));
        startActivity(browse);
    }

    //Marshmellow 이후부터는 권한 획득
    public void calling(View v){

        // 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            /*
                사용자 단말기의 구너한 중 "전화걸기" 권한이 허용되어 있는지 확인한다.
               Android는 c언어 기반이기 때문에 Boolean보다 int타입을 사용한다.
             */
            int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

            /*
                패키지는안드로이드 어플리케이션의 아이디이다.
                현재 어플리케이션이 CALL_PHONE에 대해 거부되어 있는지 확인한다.
             */

            if(permissionResult == PackageManager.PERMISSION_DENIED){
                /*
                    사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                    거부한적이 있으면 True를 리턴하고
                    거분한적이 없으면 False를 리턴한다.
                 */
                if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                    AlertDialog.Builder dalog = new AlertDialog.Builder(Main3Activity.this);
                    dalog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*
                                        새로운 인스턴스를 생성했기 때문에 버전체크를 다시 하여준다.
                                     */
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        //CALL_PHONE 권한을 Android OS에 요청한다.
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
                                    }
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Main3Activity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                }
                //최초로 권한을 요청할 때
                else {
                    //CALL_PHONE 권한을 Android OS에 요청한다.
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
                }
            }
            //CALL_PHONE 권한이 있을 때
            else{
                //즉시 실행
                Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:/"+container.getCall()));
                startActivity(callIntent);
            }
        }
        // 마시멜로우 미만의 버전일 때
        else {
            //즉시 실행
            Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:/"+container.getCall()));
            startActivity(callIntent);
        }
    }
}
