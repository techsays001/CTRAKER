package com.msg91.sendotp.sample;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {


    View root;
ImageView swipe;
    String a,b,c,d,e,f;
SharedPreferences shh,sho;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        swipe = root.findViewById(R.id.re);
        shh=getActivity().getSharedPreferences("data11",MODE_PRIVATE);
       // Toast.makeText(getActivity(), shh.getString("cph",null), Toast.LENGTH_LONG).show();
        sho=getActivity().getSharedPreferences("data117",MODE_PRIVATE);
     swipe.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {


             StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/C_traker/findloc.php",
                     new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {


                             try {
                                 JSONArray jsonArray = new JSONArray(response);
                                 for (int i = 0; i < jsonArray.length(); i++) {
                                     JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");
                                     a=json_obj.getString("la");
                                     b=json_obj.getString("lo");
                                     c=json_obj.getString("name");
                                     d=json_obj.getString("phone");
                                     e=json_obj.getString("date");
                                     f=json_obj.getString("id");





                                 }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
//                                Toast.makeText(Signin.this, response, Toast.LENGTH_LONG).show();
                             if (response.contains("success")) {


                                 SharedPreferences shh=getActivity().getSharedPreferences("data117",MODE_PRIVATE);
                                 SharedPreferences.Editor ee=shh.edit();
                                 ee.putString("la",a);
                                 ee.putString("lo",b);
                                 ee.putString("name",c);
                                 ee.putString("phone",d);
                                 ee.putString("id",e);


                                 ee.apply();
                                 Toast.makeText(getActivity(), sho.getString("la",null), Toast.LENGTH_LONG).show();

                                 String maplLabel = "ABC Label";
                                 final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                         Uri.parse("geo:0,0?q="+sho.getString("la",null)+","+sho.getString("lo",null)+"&z=16 (" + maplLabel + ")"));
                                 startActivity(intent);


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
                     params.put("phone",shh.getString("cph",null));

//returning parameter
                     return params;
                 }

             };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
             RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
             requestQueue.add(stringRequest);

         }
     });



return root;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



}



