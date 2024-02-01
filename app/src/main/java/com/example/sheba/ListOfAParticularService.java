package com.example.sheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfAParticularService extends AppCompatActivity {
    private Bundle bundle;
    private Button post;
    public static String SN="";
    private DatabaseReference dRef;
    private List<ModelOfParticularServiceWithPersons> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ParticularServiceWithPersonsAdapter adapter;
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
        setContentView(R.layout.activity_list_of_aparticular_service);


        dRef = FirebaseDatabase.getInstance().getReference();
        bundle=getIntent().getExtras();
        SN=bundle.getString("ServiceName");
        post=findViewById(R.id.listOfAParticularServicePostButton);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PostYours.class);
                i.putExtra("ServiceName",SN);
                startActivity(i);
            }
        });

        if(SplashScreen.Occupation.equals("buyer")){
            post.setEnabled(false);
            post.setAlpha(0f);
        }

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.listOfAParticularServiceRecyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new ParticularServiceWithPersonsAdapter(list,this);
        recyclerView.setAdapter(adapter);
        dRef.child("ParticularService").child(SN).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:snapshot.getChildren()) {
                        ModelOfParticularServiceWithPersons model=x.getValue(ModelOfParticularServiceWithPersons.class);
//                        if(model.getMail().equals(SplashScreen.trimmedMail)){
//                            continue;
//                        }
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
  list.add(new ModelOfParticularServiceWithPersons("No Service Provider","https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0",
          "--","--","--","--",""));

  adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Toast.makeText(this, serviceName, Toast.LENGTH_SHORT).show();


    }
}