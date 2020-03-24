package com.example.ap02storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername,edtPassword;
    private Button btnSaveData,btnGetData;
    private static final String INTERNAL_PATH = Environment.getDataDirectory().getPath() + "/data/com.example.ap02storage/";
    private static final String ACCOUNT_FILE = "acc.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnSaveData = findViewById(R.id.btn_save_data);
        btnGetData = findViewById(R.id.btn_get_data);
        btnSaveData.setOnClickListener(this);
        btnGetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_data:
                    saveData();
                break;
            case R.id.btn_get_data:
                showData();
                break;
                default:
                    break;
        }
    }

    private void showData() {
        String result = getData();
        if (result.isEmpty()){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
            return;
        }

        String listInfo[] = result.split("\n");

        for (int i =0;i<= listInfo.length;i++){
            String item[] = listInfo[i].split(":");
            if (edtUsername.getText().toString().equals(item[0]) && edtPassword.getText().toString().equals(item[1])){
                Toast.makeText(this,"Login success",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this,"Login false",Toast.LENGTH_SHORT).show();

    }

    private String getData() {
        try {
            String path = INTERNAL_PATH + ACCOUNT_FILE;
            File file = new File(path);
            if (!file.exists()){
                Toast.makeText(this,"No file exits",Toast.LENGTH_SHORT).show();
                return null;
            }

            FileInputStream inputStream = new FileInputStream(path);
            String data = "";
            int len;
            byte buff[] = new byte[1024];
            while ((len = inputStream.read(buff)) > 0){
                data += new String(buff,0,len);
            }
            inputStream.close();
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    private void saveData() {
        try {
            String path = INTERNAL_PATH + ACCOUNT_FILE;
            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file,true);

            String data = edtUsername.getText().toString() + ":" + edtPassword.getText().toString() + "\n";

            byte buff[] = data.getBytes();
            outputStream.write(buff,0,buff.length);
            outputStream.close();
            Toast.makeText(this,"Save success",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
