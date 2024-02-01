package com.example.sheba;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheba.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        private FirebaseAuth mAuth;
        private LinearLayout linearLayout;
        private Button becomeASeller;
        private RelativeLayout plumbingSanitaryServices,pestControlServices,paintingServices,houseShiftingService,homeCleaningService
                ,gasStoveRepair;
//        private String email,name,trimmedMail;
        private TextView textView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    private int flag=0;
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
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
//        becomeASeller=findViewById(R.id.myServicesOrBecomeASeller);
        textView=findViewById(R.id.textViewMainActivity);
        textView.setText("Hi, "+SplashScreen.name);

        plumbingSanitaryServices=findViewById(R.id.plumbingSanitaryServices);
        linearLayout=findViewById(R.id.dialThrough);
        pestControlServices=findViewById(R.id.pestControlServices);
        paintingServices=findViewById(R.id.paintingServices);
        houseShiftingService=findViewById(R.id. houseShiftingService);
        homeCleaningService=findViewById(R.id.homeCleaningService);
        gasStoveRepair=findViewById(R.id.gasStoveRepair);

        plumbingSanitaryServices.setOnClickListener(this);
        pestControlServices.setOnClickListener(this);
        paintingServices.setOnClickListener(this);
        houseShiftingService.setOnClickListener(this);
        homeCleaningService.setOnClickListener(this);
        gasStoveRepair.setOnClickListener(this);
        linearLayout.setOnClickListener(this);



        System.out.println(SplashScreen.name);
        if(SplashScreen.Occupation.equals("seller")){
//            becomeASeller.setText("My services");
            flag=1;
        }





    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.myServicesOrBecomeASeller);
        if (flag==1) {
            item.setTitle("My services");

        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.logoutUser:
                // do your code
                mAuth.signOut();
                startActivity(new Intent(this,LogIn.class));
                finish();
                return true;
            case R.id.myServicesOrBecomeASeller:
                if(flag==1){
//            Toast.makeText(this, "Already a seller", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MyServices.class));
                }else{
                    android.app.AlertDialog.Builder dialogExit = new android.app.AlertDialog.Builder(MainActivity.this);
                    View layout_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.pay_now_dialogue_box, null);
                    dialogExit.setView(layout_dialog);
                    dialogExit.setTitle("Payment !!");
                    dialogExit.setMessage("Please pay 499 BDT");

                    dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Intent in=new Intent(getApplicationContext(),SignUpAsServiceProvider.class);
                            in.putExtra("trimmedMail",SplashScreen.trimmedMail);
                            in.putExtra("name",SplashScreen.name);
                            in.putExtra("nid",SplashScreen.nid);
                            startActivity(in);
                            finish();
                        }
                    });

                    dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    android.app.AlertDialog dialog = dialogExit.create();
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.getWindow().setGravity(Gravity.CENTER);

                }



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(View view) {


    }

    public void becomeASeller(View view) {

    }


    @Override
    public void onClick(View v) {

        Intent intent=new Intent(this,ListOfAParticularService.class);
        switch (v.getId()){
            case R.id.dialThrough:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"123456789"));
                startActivity(callIntent);
                break;
                case R.id.plumbingSanitaryServices:
                intent.putExtra("ServiceName","plumbingSanitaryServices");
                startActivity(intent);
                break;
                case R.id.pestControlServices:
                intent.putExtra("ServiceName","pestControlServices");
                startActivity(intent);
                break;
                case R.id.paintingServices:
                intent.putExtra("ServiceName","paintingServices");
                startActivity(intent);
                break;
                case R.id.houseShiftingService:
                intent.putExtra("ServiceName","houseShiftingService");
                startActivity(intent);
                break;
                case R.id.homeCleaningService:
                intent.putExtra("ServiceName","homeCleaningService");
                startActivity(intent);
                break;
                case R.id.gasStoveRepair:
                intent.putExtra("ServiceName","gasStoveRepair");
                startActivity(intent);
                break;


        }
    }

    public void bookings(View view) {
        Intent inte=new Intent(this,Carts.class);
        startActivity(inte);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(MainActivity.this);

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
