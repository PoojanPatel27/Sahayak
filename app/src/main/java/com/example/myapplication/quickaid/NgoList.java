package com.example.myapplication.quickaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NgoList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    NgoListAdapter adapter;
    ArrayList<OrgDetailsModel> listOrg;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_list);

        recyclerView = findViewById(R.id.ngoRecView);
        searchView = findViewById(R.id.SearchViewNgoList);

        database = FirebaseDatabase.getInstance().getReference("Organization");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listOrg = new ArrayList<>();
        adapter = new NgoListAdapter(this, listOrg);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrgDetailsModel orgDetails = dataSnapshot.getValue(OrgDetailsModel.class);
                    listOrg.add(orgDetails);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        ArrayList<OrgDetailsModel> filteredlist = new ArrayList<>();
        for (OrgDetailsModel item : listOrg){
            Toast.makeText(this, ""+item.getName(), Toast.LENGTH_SHORT).show();
            if (item.getName().toLowerCase().contains(newText.toLowerCase()) || item.getCategory().toLowerCase().contains(newText.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }
}