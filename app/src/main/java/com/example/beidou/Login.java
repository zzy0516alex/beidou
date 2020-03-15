package com.example.beidou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private Button mbtnconfirm;
    private Button mbtnregistergo;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mbtnconfirm=findViewById(R.id.confirm);
        mbtnregistergo=findViewById(R.id.registerGo);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        SharedPreferences login=getSharedPreferences("login",MODE_PRIVATE);
        //
        final String usernameRead=login.getString("username","X");
        final String passwordRead=login.getString("password","123456");
        mbtnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!usernameRead.equals("X")) && (!passwordRead.equals("123456"))){
                    if(username.getText().toString().equals(usernameRead)&&password.getText().toString().equals(passwordRead))
                    {
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this,SearchActivity.class);
                        startActivity(intent);
                    }
                    else  Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this, "请先注册", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Register.class);
                    startActivity(intent);
                }
            }
        });
        mbtnregistergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
}
