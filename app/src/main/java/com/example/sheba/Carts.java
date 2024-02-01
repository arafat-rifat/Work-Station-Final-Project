package com.example.sheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Carts extends AppCompatActivity {
    private DatabaseReference dRef;
    private List<ModelOfIndividualCarts> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private IndividualCartAdapter adapter;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        dRef = FirebaseDatabase.getInstance().getReference();

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.cartsRecyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new IndividualCartAdapter(list,this);
        recyclerView.setAdapter(adapter);

        dRef.child("MyCarts").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:snapshot.getChildren()) {
                        ModelOfIndividualCarts model=x.getValue(ModelOfIndividualCarts.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
 list.add(new ModelOfIndividualCarts("--","--","Nothing in cart yet !","--","--","--","https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0",""));
 adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}