package com.rupam.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity {
    public static final String key="com.rupam.tictactoe.sendingkey";

    public void gotoplaymode(View view){
        EditText editText1= findViewById(R.id.editTextTextPersonName1);
        EditText editText2= findViewById(R.id.editTextTextPersonName2);
        Intent intent = new Intent(this,MainActivity.class);
        String[] value={editText1.getText().toString(),editText2.getText().toString()};
        String[]newValue={"Player_0","Player_1"};
        if(value[0].length()==0) value[0]=newValue[0];
        if(value[1].length()==0) value[1]=newValue[1];

        intent.putExtra(key,value);
        startActivity(intent);
        System.out.println("Intent SENT");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
}