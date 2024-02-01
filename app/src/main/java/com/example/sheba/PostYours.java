package com.example.sheba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostYours extends AppCompatActivity {
    private EditText price;
    private Button confirm;
    private String serviceName,s;
    private Bundle bundle;
    private DatabaseReference dRef;
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
        setContentView(R.layout.activity_post_yours);
        dRef = FirebaseDatabase.getInstance().getReference();

        price=findViewById(R.id.postYoursEditText);
        confirm=findViewById(R.id.postYoursButton);

        bundle=getIntent().getExtras();
        s=bundle.getString("ServiceName");

        switch (s){
            case "plumbingSanitaryServices":
                serviceName="Plumbing Sanitary Services";
                break;
            case "pestControlServices":
                serviceName="Pest Control Services";
                break;
            case "paintingServices":
                serviceName="Painting Services";
                break;
            case "houseShiftingService":
                serviceName="House Shifting Service";
                break;
            case "homeCleaningService":
                serviceName="Home Cleaning Service";
                break;
            case "gasStoveRepair":
                serviceName="Gas Stove Repair";
                break;


        }


    }

    public void post(View view) {
        if(price.getText().toString().isEmpty()){
            price.setError("Enter");
            price.requestFocus();
            return;
        }

        Toast.makeText(this, "Posted your Ad", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, price.getText().toString()+"\n"+serviceName, Toast.LENGTH_SHORT).show();
        dRef.child("ParticularService").child(s).child(SplashScreen.trimmedMail).setValue(new ModelOfParticularServiceWithPersons(SplashScreen.name,SplashScreen.profilePicture,SplashScreen.phoneNumber,price.getText().toString()+" TK",SplashScreen.Location,SplashScreen.trimmedMail,SplashScreen.locationLink));
                dRef.child("MyServices").child(SplashScreen.trimmedMail).child(s)
                .setValue(new ModelOfIndividualServices(s,price.getText().toString()+" TK"));

        Intent intent=new Intent(this,SplashScreen.class);
        startActivity(intent);

    }
}