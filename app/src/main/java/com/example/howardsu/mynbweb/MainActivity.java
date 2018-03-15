package com.example.howardsu.mynbweb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String a1,showUrl1;
    EditText editText1,editText2;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);
        showUrl1 = "http://61.63.47.211/login_app.php";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                }
        });
    }
    private void getData() {
        StringRequest request = new StringRequest( Request.Method.POST, showUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",editText1.getText().toString());
                params.put("password",editText2.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void showJSON(String response) {
      //  String a1 = new String();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("data");
            //這邊要和上面json的名稱一樣
            //下邊是把全部資料都印出來
            for (int i = 0; i < data.length(); i++) {
                JSONObject jasondata = data.getJSONObject(i);
                a1 = jasondata.getString("CUSTOMER_NUMBER");
            }
            Intent it =new Intent( Intent.ACTION_VIEW );
            if(a1.toString().equals( "test1" )) {
                it.setClass( this,Main1Activity.class );
                startActivity( it );
            } else if(a1.toString().equals( "test2" )) {
                it.setClass( this, Main3Activity.class );
                startActivity( it );
            } else
                it.setClass( this, Main4Activity.class );
                startActivity( it );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void hidekb() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
