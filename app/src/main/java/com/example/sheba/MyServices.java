package com.example.sheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyServices extends AppCompatActivity {

    private DatabaseReference dRef;
    private List<ModelOfIndividualServices> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyServicesAdapter adapter;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);
        dRef = FirebaseDatabase.getInstance().getReference();

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerViewMyServices);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new MyServicesAdapter(list,this);
        recyclerView.setAdapter(adapter);

        dRef.child("MyServices").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
            if(snapshot.exists()){
                for (DataSnapshot s:snapshot.getChildren()) {
                    ModelOfIndividualServices model=s.getValue(ModelOfIndividualServices.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                list.add(new ModelOfIndividualServices("No Service yet","--"));
                adapter.notifyDataSetChanged();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        dRef.child("MyServices").child(SplashScreen.trimmedMail).child("pestControlServices")
//                .setValue(new ModelOfIndividualServices("pestControlServices","7000"));


    }
    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }


}