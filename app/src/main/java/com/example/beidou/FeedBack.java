package com.example.beidou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity {
    private CheckBox che1;
    private CheckBox che2;
    private CheckBox che3;
    private CheckBox che4;
    private Button mbun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        che1=findViewById(R.id.c1);
        che2=findViewById(R.id.c2);
        che3=findViewById(R.id.c3);
        che4=findViewById(R.id.c4);
        mbun=findViewById(R.id.give);
        mbun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(che1.isChecked()||che2.isChecked()||che3.isChecked()||che4.isChecked())
                {
                    Toast.makeText(FeedBack.this, "提交成功，谢谢反馈", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeedBack.this,SearchActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(FeedBack.this, "请至少选择一个选项", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeedBack.this,FeedBack.class);
                    startActivity(intent);
                }
            }
        });

    }
}
