package com.durian.demo.presentation.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.durian.demo.GitDataInjection;
import com.durian.demo.R;
import com.durian.demo.base.utils.ConfigUtil;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.main.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author zhangyb
 * @description 登录模块
 * @date 2017/11/2
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final String TAG = LoginActivity.class.getCanonicalName();
    private LoginContract.Presenter presenter;
    private UserInfo userInfo;
    AutoCompleteTextView usernameView;
    EditText passwordView;
    Button signInButton;
    LinearLayout loginFormPane;

    public AlertDialog loadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        initViews();
        new LoginPresenter(this, this, GitDataInjection.provideGetUserInfo());
        this.presenter.start();
    }

    @Override
    public void initViews() {
        usernameView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        loginFormPane = (LinearLayout) findViewById(R.id.login_form_pane);
        initSignView();
    }

    private void initSignView() {
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> doLogin());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] content = new String[2];
        if (loadData().contains(";")) {
            content = loadData().split(";");
        }
        usernameView.setText(content[0]);
        passwordView.setText(content[1]);
    }

    @Override
    public void showLoginDialog() {

        if (loadDialog == null) {
            loadDialog = ConfigUtil.getAndShowLoadingDialog(this, getString(R.string.login_load));
        }
        loadDialog.show();
    }

    @Override
    public void dismissLoginDialog() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void doLogin() {
        String userName = usernameView.getText().toString().trim();
        String passWord = passwordView.getText().toString().trim();
        if (checkUserNamePasswordVaildate(userName, passWord)) {
            showLoginDialog();
            presenter.fetchUserInfoByUserName(userName, passWord);
        }
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
        this.userInfo = userInfo;
        presenter.saveData(userInfo);
        jumpToMain();
    }

    @Override
    public void onLoginFailed(String error) {
        dismissLoginDialog();
        Toast.makeText(this, error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private boolean checkUserNamePasswordVaildate(String userNameOnUI, String passwordOnUI) {
        if (TextUtils.isEmpty(userNameOnUI) || TextUtils.isEmpty(passwordOnUI)) {
            Log.w(TAG, "username or password wrong...");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private void saveData() {
        String saveUserName = usernameView.getText().toString();
        String savePassWord = passwordView.getText().toString();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(saveUserName);
        stringBuilder.append(";");
        stringBuilder.append(savePassWord);
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter = null;

        try {
            fileOutputStream = openFileOutput("userInfo", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String loadData() {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileInputStream = openFileInput("userInfo");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
