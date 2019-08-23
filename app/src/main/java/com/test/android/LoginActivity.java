package com.test.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;


public class LoginActivity extends AppCompatActivity{

    Toolbar toolbar;

    EditText username,password,reUsername,FirstRePassword,SecondRePassword;

    CardView register,login;

    SharedPreferences prf;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginInterface();

        //欢迎界面
        setContentView(R.layout.welcome);

        //创建数据库
        LitePal.getDatabase();

        prf=getSharedPreferences("com.test.SettingData",MODE_PRIVATE);

        editor=prf.edit();
    }

    //登录界面
    void loginInterface(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String s=prf.getString("username",null);
                //如果已登录就直接启动MainActivity
                if(s!=null){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    setContentView(R.layout.login_layout);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }

                    toolbar=findViewById(R.id.tool_bar);
                    username=findViewById(R.id.username);
                    password=findViewById(R.id.password);
                    register=findViewById(R.id.register);
                    login=findViewById(R.id.login);

                    toolbar.setTitle("登录");
                    setSupportActionBar(toolbar);
                    ActionBar actionBar=getSupportActionBar();
                    if(actionBar!=null){
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setHomeButtonEnabled(true);
                    }

                    password.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            if(count>12){
                                TextInputLayout til=findViewById(R.id.til);
                                til.setError("密码超出最大长度");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    //注册
                    register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final View registerLayout=getLayoutInflater().inflate(R.layout.register_layout,null);

                            reUsername=registerLayout.findViewById(R.id.re_username);
                            FirstRePassword=registerLayout.findViewById(R.id.first_re_password);
                            SecondRePassword=registerLayout.findViewById(R.id.second_re_password);
                            TextInputLayout fpTil=registerLayout.findViewById(R.id.fp_til);
                            final TextInputLayout spTil=registerLayout.findViewById(R.id.sp_til);


                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(LoginActivity.this);
                            alertDialog.setTitle("注 册");
                            alertDialog.setView(registerLayout);
                            alertDialog.setNegativeButton("取消",null);
                            alertDialog.setPositiveButton("注册", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String un=reUsername.getText().toString();
                                    String firstPassword=FirstRePassword.getText().toString();
                                    String seconfPassword=SecondRePassword.getText().toString();

                                    if(reUsername.length()>0 ){

                                        if(firstPassword.equals(seconfPassword)){
                                            Account account=new Account();
                                            account.setPassword(seconfPassword);
                                            account.setUsername(un);
                                            account.save();
                                            editor.putString("username",un);
                                            editor.apply();
                                            username.setText(un);
                                            password.setText(seconfPassword);
                                        }else{
                                            Toast.makeText(LoginActivity.this,"前后密码不相同",Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            alertDialog.show();
                        }
                    });

                    //登录
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(username.length()>0 && password.length()>0){

                                String u=username.getText().toString();
                                String p=password.getText().toString();

                                List<Account> list=DataSupport.where("username=?",u).find(Account.class);

                                if(list.size()>0){

                                    for(Account account:list){

                                        if(account.getPassword().equals(p)){
                                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                            //启动主界面
                                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                            intent.putExtra("username",u);
                                            startActivity(intent);

                                            editor.putString("username",account.getUsername());
                                            editor.apply();

                                            finish();
                                        }else {
                                            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }else {
                                    Toast.makeText(LoginActivity.this,"账号未注册",Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }

            },1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            default:
                break;

        }
        return true;
    }

}
