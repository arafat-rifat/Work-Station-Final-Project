package com.example.sheba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.ViewHolder> {
    List<ModelOfIndividualServices> list;
    Context context;

    public MyServicesAdapter(List<ModelOfIndividualServices> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_service_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.size()==0){
            holder.serviceName.setText("No Service Yet");
            holder.pricing.setText("--");


        }
        else{
            String serviceName;
            String s=list.get(position).getServiceName();
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


                default:
                    serviceName="No Service Yet";
            }
            holder.serviceName.setText(serviceName+"");
            holder.pricing.setText(list.get(position).getPriceRange());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("MyServices").child(SplashScreen.trimmedMail).
                            child(list.get(position).getServiceName()).removeValue();

                    databaseReference.child("ParticularService").child(list.get(position).getServiceName())
                            .child(SplashScreen.trimmedMail).removeValue();
                    Toast.makeText(context, "Deleted your ad !", Toast.LENGTH_SHORT).show();
                }
            });
        }



//    holder.serviceName.setOnLongClickListener(new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View v) {
//
//                CharSequence[] items = {"Delete"};
//                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//
//                dialog.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if(i == 0){
//
//
//                        }
//                    }
//                });
//                dialog.show();
//
//            return false;
//        }
//    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName,pricing;
        private ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName=itemView.findViewById(R.id.myServiceModelName);
            pricing=itemView.findViewById(R.id.myServiceModelPrice);
            delete=itemView.findViewById(R.id.myServiceModelDelete);
        }
    }
}
