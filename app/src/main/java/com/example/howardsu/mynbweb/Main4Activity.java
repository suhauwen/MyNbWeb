package com.example.howardsu.mynbweb;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main4Activity extends AppCompatActivity {
    String showUrl1;
    public String [][] myarray;
    EditText editText1, editText2;
    Button buttonm1;
    SimpleAdapter adapter;
    ListView listview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main4 );
        setTitle("BOSS");
        listview1=findViewById(R.id.listview1);
        listview1.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String a=myarray[position][3];
                new AlertDialog.Builder(Main4Activity.this)
                        .setTitle("刪除")
                        .setMessage("刪除任務編號: " + myarray[position][3])
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletedata(a);
                                delay();
                                getData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return false;
            }

        } );
        editText1=findViewById( R.id.editText1 );
        editText2=findViewById( R.id.editText2 );
        buttonm1=findViewById( R.id.buttonm1 );
        buttonm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                delay();
                getData();
                listview1.setAdapter(adapter);
                editText2.setText( "" );
                hidekb();
            }
        });
        getData();
        //hidekb();
    }
    private void getData() {
        showUrl1 = "http://61.63.47.211/boss_select_app.php";
        StringRequest request = new StringRequest( Request.Method.POST, showUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void updateData() {
        if("".equals(editText1.getText().toString().trim())) {
            AlertDialog.Builder bdr = new AlertDialog.Builder( this );
            bdr.setMessage( "請輸入姓名" );
            bdr.setCancelable( true );
            bdr.show();
        } else if("".equals(editText2.getText().toString().trim())) {
                   AlertDialog.Builder bdr = new AlertDialog.Builder( this );
                   bdr.setMessage( "請輸入內容" );
                   bdr.setCancelable( true );
                   bdr.show();
        } else
        showUrl1 = "http://61.63.47.211/boss_update_app.php";
        StringRequest request = new StringRequest( Request.Method.POST, showUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MM_NAME",editText1.getText().toString());
                params.put("MM_MESSAGE",editText2.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void deletedata(final String seq){
        showUrl1 = "http://61.63.47.211/boss_delete_app.php";
        StringRequest request = new StringRequest( Request.Method.POST, showUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MM_SEQ",seq);
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
            myarray = new String[data.length()][4];
            for (int i = 0; i < data.length(); i++) {
                JSONObject jasondata = data.getJSONObject(i);
                myarray[i][0] = jasondata.getString("MM_NAME");
                myarray[i][1] = jasondata.getString("MM_MESSAGE");
                myarray[i][2] = jasondata.getString("MM_DATE");
                myarray[i][3] = jasondata.getString("MM_SEQ");
            }
            List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
            for (int i=0;i <myarray.length;i++){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("MM_NAME", myarray[i][0].toString());
                item.put("MM_MESSAGE", myarray[i][1].toString());
                item.put("MM_DATE", myarray[i][2]);
                item.put("MM_SEQ", myarray[i][3]);
                items.add(item);
            }
             adapter = new SimpleAdapter(
                    this,
                    items,
                    R.layout.message_list,
                    new String[]{"MM_NAME", "MM_MESSAGE", "MM_DATE","MM_SEQ"},
                    new int[]{R.id.textViewm1,R.id.textViewm4,R.id.textViewm2,R.id.textViewm3}
            );
           // ListView listview1 = findViewById(R.id.listview1);
            listview1.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void hidekb() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Main4Activity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void delay(){
        try{
            // delay 1 second
            Thread.sleep(1000);

        } catch(InterruptedException e){
            e.printStackTrace();

        }
    }
}
