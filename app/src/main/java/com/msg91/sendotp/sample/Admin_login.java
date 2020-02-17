package com.msg91.sendotp.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Admin_login extends AppCompatActivity {
Button admin_login;
String a,b,c,d,e,f,abc;
    SharedPreferences sharedPreferences,sh;
    private boolean loggedIn = false;
EditText admin_uname,admin_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin_login=findViewById(R.id.admin_login);
        admin_uname=findViewById(R.id.admin_uname);


        sh=getSharedPreferences("Official12",MODE_PRIVATE);
        loggedIn=sh.getBoolean("ph12",false);
        sharedPreferences=getSharedPreferences("phone12",MODE_PRIVATE);
        if (loggedIn) {
           startActivity(new Intent(Admin_login.this,Mainactivity2.class));
            // Snackbar.make(v,"Enter emergency number",Snackbar.LENGTH_SHORT).show();

        }

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (admin_uname.getText().toString().isEmpty()) {

                    admin_uname.setError("enter valid  phone");
                }

                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/C_traker/chaild_login.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//Toast.makeText(Signin.this, response, Toast.LENGTH_LONG).show();


admin_uname.getText().clear();
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");
                                            a=json_obj.getString("cph");
                                            b=json_obj.getString("phone");
                                            c=json_obj.getString("name");
                                            d=json_obj.getString("empid");
                                            e=json_obj.getString("address");
                                            f=json_obj.getString("district");




                                        }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
//                                Toast.makeText(Signin.this, response, Toast.LENGTH_LONG).show();
                                    if (response.contains("success")) {

                                        Intent in = new Intent(Admin_login.this, Popup.class);
                                        in.putExtra("pho",admin_uname.getText().toString());
                                        SharedPreferences sh=getSharedPreferences("data111",MODE_PRIVATE);
                                        SharedPreferences.Editor ee=sh.edit();
                                        ee.putString("cph",a);
                                        ee.putString("phone",b);
                                        ee.putString("name",c);
                                        ee.putString("empid",d);
                                        ee.putString("address",e);



                                        ee.apply();

                                        startActivity(in);
                                    }
                                    else
                                    {
                                      admin_uname.setError("incorrect credentials");
                                        Toast.makeText(getApplicationContext(),"Invalid Employ Id or Password",Toast.LENGTH_LONG).show();
                                    }


                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                                }

                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
//Adding parameters to request
                            params.put("phone",admin_uname.getText().toString());

//returning parameter
                            return params;
                        }

                    };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Admin_login.this);
                    requestQueue.add(stringRequest);
                }


            }
        });

    }
}
