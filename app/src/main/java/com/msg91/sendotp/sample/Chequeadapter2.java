package com.msg91.sendotp.sample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Chequeadapter2 extends RecyclerView.Adapter<Chequeadapter2.ProductViewHolder> {

    private Context mCtx;
    private List<Cheque2> productList;

    public Chequeadapter2(Context mCtx, List<Cheque2> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_c2, null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
      final   Cheque2 cheque;   cheque = productList.get(position);

                 holder.dis.setText(cheque.getStatus());
                 Picasso.get().load(cheque.getImage()).into(holder.image);



                 holder.ab.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {



                         Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                         sharingIntent.setType("text/plain");
                         String shareBody = cheque.getImage()+cheque.getStatus();
                         sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                         mCtx.startActivity(sharingIntent);


                     }
                 });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView dis,ab;
        ImageView image;

        public ProductViewHolder(View itemView) {
            super(itemView);



           ab=itemView.findViewById(R.id.ab);
            dis=itemView.findViewById(R.id.ab2);
            image=itemView.findViewById(R.id.ab1);

        }

    }



}