package com.msg91.sendotp.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter1 extends RecyclerView.Adapter<Chequeadapter1.ProductViewHolder> {

    Intent abc;
    SharedPreferences sh;

    private Context mCtx;
    private List<Cheque1> productList1;

    public Chequeadapter1(Context mCtx, List<Cheque1> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList1 = productList;
        // sh=mCtx.getSharedPreferences("Official1",MODE_PRIVATE);
        //Toast.makeText(mCtx,,Toast.LENGTH_LONG).show();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_c1, null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Cheque1 cheque = productList1.get(position);



        holder.date.setText(cheque.getUser1());
        holder.la.setText(cheque.getUser2());
        holder.lo.setText(cheque.getUser3());
        holder.address.setText(cheque.getUser4());

holder.map.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String maplLabel = "ABC Label";
        final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="+cheque.getUser2()+","+cheque.getUser3()+"&z=16 (" + maplLabel + ")"));
        mCtx.startActivity(intent);

    }
});




        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+cheque.getUser2()+","+cheque.getUser3()));
                mCtx.startActivity(intent);







            }
        });


    }


    @Override
    public int getItemCount() {
        return productList1.size();
    }

    public void filterList1(ArrayList<Cheque1> filteredList1) {

        productList1 = filteredList1;
        notifyDataSetChanged();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView date,la,lo,address,map,direction;


        public ProductViewHolder(View itemView) {
            super(itemView);


         date= itemView.findViewById(R.id.chdate);
           la = itemView.findViewById(R.id.chla);
          lo = itemView.findViewById(R.id.chlo);
          address = itemView.findViewById(R.id.chaddress);
            map = itemView.findViewById(R.id.chmap);
           direction = itemView.findViewById(R.id.chdir);
//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    class ProductViewHolder extends RecyclerView.ViewHolder {
//
//

//        public ProductViewHolder(View itemView) {
//            super(itemView);
//
//

////            review=itemView.findViewById(R.id.re);
////            viewreview=itemView.findViewById(R.id.ve);
////          //  pid=itemView.findViewById(R.id.productidd);
//
//        }
//
//    }

        }



    }
}