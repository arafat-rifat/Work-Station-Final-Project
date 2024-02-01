package com.example.sheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpAsServiceProvider extends AppCompatActivity {
    private Bundle bundle;
    final int code = 999;
    private ImageView imageView;
    private Button imageButton,createButton;
    private ProgressBar progressBar;
    private EditText contactNumber,location,locationLink;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String trimmedMail,name,nid;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    private String pictureLink ="https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0";

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
        setContentView(R.layout.activity_sign_up_as_service_provider);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        bundle=getIntent().getExtras();
        trimmedMail=bundle.getString("trimmedMail");
        name=bundle.getString("name");
        nid=bundle.getString("nid");

        contactNumber=findViewById(R.id.SignUpAsServiceProviderContactNumber);
        locationLink=findViewById(R.id.SignUpAsServiceProviderLocationLink);
        location=findViewById(R.id.SignUpAsServiceProviderLocation);
        imageView = findViewById(R.id.SignUpAsServiceProviderPictureIcon);
        imageButton = findViewById(R.id.SignUpAsServiceProviderSelectPicture);
        createButton = findViewById(R.id.SignUpAsServiceProviderCreateAccount);
        progressBar=findViewById(R.id.signUpDonorProgressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(252,78,78), android.graphics.PorterDuff.Mode.SRC_ATOP);


    }

    public void signUpPictureChoosingButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null) {
            progressBar.setVisibility(View.VISIBLE);
            createButton.setEnabled(false);
            Uri result = data.getData();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference("picLink"+trimmedMail);
            storageRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pictureLink=uri.toString();
                            imageView.setImageURI(result);
                            progressBar.setVisibility(View.INVISIBLE);
                            createButton.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't Upload !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void signUpDonorCreateAccount(View view) {
        String phone=contactNumber.getText().toString(),loc=location.getText().toString(),locLink=locationLink.getText().toString();


        if(phone.isEmpty()){
            contactNumber.setError("Please enter !");
            contactNumber.requestFocus();
            return;
        }
        if(loc.isEmpty()){
            location.setError("Please enter !");
            location.requestFocus();
            return;
        }
        if(locLink.isEmpty()){
            locationLink.setError("Please enter !");
            locationLink.requestFocus();
            return;
        }
        if(!locLink.startsWith("https")){
            String arr[]=locLink.split("https",2);
            locLink=arr[1];
            locLink="https"+locLink;
        }
        databaseReference.child("Users").child(trimmedMail).setValue(new ModelUser(name,phone,pictureLink,loc,"seller",locLink,nid));
        Toast.makeText(this, "Congratulations !!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,SplashScreen.class));

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(SignUpAsServiceProvider.this);

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
}