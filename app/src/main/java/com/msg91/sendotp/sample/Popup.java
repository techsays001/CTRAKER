package com.msg91.sendotp.sample;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.msg91.sendotp.sample.R.layout.activity_popup;

public class Popup extends AppCompatActivity {
    TextView name, contact;
    EditText  phonee;
Button login;
Intent in;
SharedPreferences sh;
    static public final int CONTACT = 0;
    static public final int RequestPermissionCode = 100;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_popup);
        EnableRuntimePermission();
        myDialog = new Dialog(this);
        sh=getSharedPreferences("data111",MODE_PRIVATE);


       // Toast.makeText(Popup.this,sh.getString("cph",null),Toast.LENGTH_LONG).show();
     login=findViewById(R.id.loginbt);
        phonee=findViewById(R.id.coph1);
       contact=findViewById(R.id.cobt);
       name=findViewById(R.id.coname1);

       contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 7);

            }
        });




       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             if (phonee.getText().toString().isEmpty()) {

                   phonee.setError("Chosee aphone Number");
                 Toast.makeText(Popup.this, "Chosee aphone Number", Toast.LENGTH_LONG).show();
               }




               else {


                 StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/C_traker/rphone.php",
                         new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
//If we are getting success fr
                                 if (response.contains("Successful")) {

                                     Intent in = new Intent(Popup.this, Mainactivity2.class);
                                     SharedPreferences sh=getSharedPreferences("data112",MODE_PRIVATE);
                                     SharedPreferences.Editor ee=sh.edit();



                                     ee.apply();

                                     startActivity(in);
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


                         params.put("ph", phonee.getText().toString());
                         params.put("cph", sh.getString("cph",null));

// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                         return params;
                     }

                 };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                 RequestQueue requestQueue = Volley.newRequestQueue(Popup.this);
                 requestQueue.add(stringRequest);
             }}

       });


    }
    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("M");
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(Popup.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(Popup.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Popup.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(),"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

        super.onActivityResult(RequestCode, ResultCode, ResultIntent);

        switch (RequestCode) {

            case (7):
                if (ResultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "";
                    int IDresultHolder;

                    uri = ResultIntent.getData();

                    cursor1 = getContentResolver().query(uri, null, null, null, null);

                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult);

                        if (IDresultHolder == 1) {

                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                name.setText(TempNameHolder);



                                if(TempNumberHolder.startsWith("+"))
                                {
                                    if(TempNumberHolder.length()==13)
                                    {
                                        String str_getMOBILE=TempNumberHolder.substring(3);
                                       phonee.setText(str_getMOBILE);
                                    }
                                    else if(TempNumberHolder.length()==14)
                                    {
                                        String str_getMOBILE=TempNumberHolder.substring(4);
                                       phonee.setText(str_getMOBILE);
                                    }


                                }
                                else
                                {
                                    String strArray = TempNumberHolder.replaceAll("\\s+", "");
                                    // String removeCurrency = cursor.getString(0);
                                   // String  removeCurrency = TempNumberHolder.replaceAll("99", "");
                                    phonee.setText(strArray);

                                }

                            }
                        }

                    }
                }
                break;
        }

    }
    }