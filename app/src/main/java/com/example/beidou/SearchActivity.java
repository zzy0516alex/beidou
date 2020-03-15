package com.example.beidou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.SoftReference;

public class SearchActivity extends AppCompatActivity {
    EditText searchBox;
    Button search;
    String TextIn;
    String Bin_type="notfound";
    Elements elements;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBox=findViewById(R.id.searchBox);
        search=findViewById(R.id.search);
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bin_type= (String) msg.obj;
                if(!Bin_type.equals("未找到"))Bin_type=Bin_type.substring(Bin_type.length()-4);
                judgeType();
            }
        };
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextIn=searchBox.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0) ;
                if(!TextIn.equals(""))classificationSearch();
                //compare(TextIn);
            }
        });
    }

    private void judgeType() {
        if (Bin_type.equals("干垃圾")) {
            Toast.makeText(SearchActivity.this, "属于可回收垃圾", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this, BluetoothReceiver.class);
            intent.putExtra("Bintype","recyclable");
            startActivity(intent);
        }
        else if(Bin_type.equals("other"))
        {
            Toast.makeText(SearchActivity.this, "属于其他垃圾", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this,BluetoothReceiver.class);
            intent.putExtra("Bintype","other");
            startActivity(intent);
        }
        else if(Bin_type.equals("湿垃圾"))
        {
            Toast.makeText(SearchActivity.this, "属于厨余垃圾", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this,BluetoothReceiver.class);
            intent.putExtra("Bintype","kitchen");
            startActivity(intent);
        }
        else if(Bin_type.equals("有害垃圾"))
        {
            Toast.makeText(SearchActivity.this, "属于有害垃圾", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this,BluetoothReceiver.class);
            intent.putExtra("Bintype","hazardous");
            startActivity(intent);
        }
        else if(Bin_type.equals("未找到"))
        {
            Toast.makeText(SearchActivity.this, "没有搜到", Toast.LENGTH_SHORT).show();
            restartActivity(this);
        }
    }

    private void classificationSearch() {
        new Thread(new Runnable() {
            String outputResults=null;
            @Override
            public void run() {
                try {

                    Document doc= Jsoup.connect("https://lajifenleiapp.com/sk/"+TextIn).get();
                    elements =doc.select("div.row");
                    final String results=elements.get(3).select("h1").text();
                    if(!results.equals("")) {
                        outputResults = results.replace(" ", "");
                    }else{
                        outputResults="未找到";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message=handler.obtainMessage();
                message.obj=outputResults;
                handler.sendMessage(message);
            }
        }).start();
    }
    public static void restartActivity(Activity act){

        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }

}
