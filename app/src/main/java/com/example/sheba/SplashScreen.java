package com.example.sheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    public static String  trimmedMail="",name="",phoneNumber="",profilePicture="",Location="",Occupation="",locationLink="",nid="";
    private DatabaseReference dRef;

    private ProgressBar progressBar;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        dRef = FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.progressBarSplashScreen);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(252,78,78), android.graphics.PorterDuff.Mode.SRC_ATOP);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String s= firebaseUser.getEmail();
        trimmedMail =  s.replace("@","");
        trimmedMail =  trimmedMail.replace(".","");

//        Toast.makeText(this, trimmedMail, Toast.LENGTH_SHORT).show();

        dRef.child("Users").child(trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ModelUser modelUser=snapshot.getValue(ModelUser.class);
                    name=modelUser.getName();
                    phoneNumber=modelUser.getPhoneNumber();
                    profilePicture=modelUser.getProfilePicture();
                    Location=modelUser.getLocation();
                    Occupation=modelUser.getOccupation();
                    locationLink=modelUser.getLocationLink();
                    nid=modelUser.getNid();
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(SplashScreen.this);

        dialogExit.setTitle("EXIT!!");
        dialogExit.setMessage("Are you sure?");

        dialogExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                finish();
            }
        });

        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogExit.show();
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
