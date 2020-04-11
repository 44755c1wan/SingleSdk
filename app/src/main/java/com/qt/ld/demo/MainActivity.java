package com.qt.ld.demo;

import android.app.Activity;
import android.app.role.RoleManager;
import android.os.Bundle;
import android.view.View;

import com.ledi.util.Operateed;
import com.qt.ld.LediDialogd;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.qt.ld.R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mInitButton:
                LediDialogd.quick(MainActivity.this, "3726", "12345");
                break;
            case R.id.mLoginButton:
                Operateed.dologin(MainActivity.this, "123", "dyh");
                break;
            case R.id.mSubmitUserButton:
                Operateed.roleInfo1(MainActivity.this, "serverid", "serverName"
                        , "roleName", "roleId", "1"
                        , "1", "0");
                break;
            case R.id.mPaySuccessUserButton:
                Operateed.Payment(MainActivity.this, "serverid", "serverName"
                        , "roleName", "roleId", "orderId", "goodsName"
                        , 1, 1, "10001", "extrasParams", "1");
                break;
        }
    }
}
