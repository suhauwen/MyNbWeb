package com.example.howardsu.mynbweb;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class Main2Activity extends AppCompatActivity {
    String showUrl1;
    TextView textView5, textView6, textView7;
    EditText editText;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        setTitle("庫存查詢");
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        editText = findViewById(R.id.editText);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView5.setText(" ");
                textView6.setText(" ");
                textView7.setText(" ");
                getData();
                hidekb();
            }
        });
    }

    private void getData() {
        if("".equals(editText.getText().toString().trim())) {
            AlertDialog.Builder bdr = new AlertDialog.Builder( this );
            bdr.setMessage( "請輸入料號" );
            bdr.setCancelable( true );
            bdr.show();
        } else
            showUrl1 = "http://61.63.47.211/inventory_app2.php";
        StringRequest request = new StringRequest(Request.Method.POST, showUrl1, new Response.Listener<String>() {
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
                params.put("itemn",editText.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("data");
            //這邊要和上面json的名稱一樣
            //下邊是把全部資料都印出來
            for (int i = 0; i < data.length(); i++) {
                JSONObject jasondata = data.getJSONObject(i);
                String a1 = jasondata.getString("ITEM_NUM");
                String a2 = jasondata.getString("ITEM_NAME");
                int a3 = jasondata.getInt("QTY");
                textView7.append(a1 + " \n");
                textView5.append(a2 + " \n");
                textView6.append(a3 + " \n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void hidekb() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Main2Activity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
