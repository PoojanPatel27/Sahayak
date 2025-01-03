package com.example.myapplication.quickaid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NgoListAdapter extends RecyclerView.Adapter<NgoListAdapter.MyViewHolder> {

    Context context;
    ArrayList<OrgDetailsModel> listOrg;

    public NgoListAdapter(Context context, ArrayList<OrgDetailsModel> List) {
        this.context = context;
        this.listOrg = List;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ngolist_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OrgDetailsModel orgDetails = listOrg.get(position);
        holder.ngoName.setText(orgDetails.getName());
        holder.ngoCategory.setText(orgDetails.getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrgDetailActivity.class);
                 intent.putExtra("name",orgDetails.getName());
                 intent.putExtra("category",orgDetails.getCategory());
                 intent.putExtra("contact",orgDetails.getNumber());
                 intent.putExtra("address",orgDetails.getAddress());
                 context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrg.size();
    }



    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView ngoName,ngoCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ngoName = itemView.findViewById(R.id.ngoNameTv);
            ngoCategory = itemView.findViewById(R.id.ngoCategoryTv);
        }
    }

    public void filterList(ArrayList<OrgDetailsModel> filteredList){
        listOrg = filteredList;
        notifyDataSetChanged();
    }
}
