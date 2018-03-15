package com.example.howardsu.mynbweb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main1 );
        setTitle("連鎖店");
    }
    public void onClick(View v){
        Intent it =new Intent( Intent.ACTION_VIEW );
        switch (v.getId()) {
            case R.id.button1:
                it.setData( Uri.parse( "http://60.251.112.150/16/web_order/doc_start.asp" ) );
                startActivity( it );
                break;
            case R.id.button2:
                it.setClass( this,Main2Activity.class );
                startActivity( it );
                break;
            case R.id.button3:
                it.setData( Uri.parse( "http://60.251.112.150/appweb/index.html" ) );
                startActivity( it );
                break;
        }
    }

    public void onClick1(View v){
        Intent it =new Intent( this,Main2Activity.class );
        startActivity( it );
    }
}
