package com.example.sheba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IndividualCartAdapter extends RecyclerView.Adapter<IndividualCartAdapter.ViewHolder> {
    private List<ModelOfIndividualCarts> list;
    private Context context;

    public IndividualCartAdapter(List<ModelOfIndividualCarts> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_cart_model,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getProfilePic()).into(holder.profilePic);

        holder.location.setText(list.get(position).getLocation());
        holder.name.setText(list.get(position).getProviderName());
        holder.price.setText(list.get(position).getPriceRange());
        holder.phone.setText(list.get(position).getContactNumber());
        holder.serviceName.setText(list.get(position).getServiceName());

        holder.locationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(position).getContactNumber().equals("--")){
                    Toast.makeText(context, "Please go back !", Toast.LENGTH_SHORT).show();
                    return;
                }

                    Uri uri = Uri.parse(list.get(position).getLocationLink()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getContactNumber().equals("--")){
                    Toast.makeText(context, "Nothing to remove !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(list.size()==0){
                    Toast.makeText(context, "Please go back !", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("MyCarts").child(SplashScreen.trimmedMail).child(list.get(position).getMail()).removeValue();

                Toast.makeText(context, "Removed !!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView profilePic,remove;
       private TextView name,location,price,phone,serviceName,locationLink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.individualCartModelImage);
            location=itemView.findViewById(R.id.individualCartModelLocation);
            locationLink=itemView.findViewById(R.id.individualCartModelLocationLink);
            name=itemView.findViewById(R.id.individualCartModelName);
            price=itemView.findViewById(R.id.individualCartModelPrice);
            remove=itemView.findViewById(R.id.individualCartModelRemove);
            phone=itemView.findViewById(R.id.individualCartModelPhoneNumber);
            serviceName=itemView.findViewById(R.id.individualCartModelServiceName);
        }
    }
}
