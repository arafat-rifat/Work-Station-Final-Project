package com.example.sheba;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ParticularServiceWithPersonsAdapter extends RecyclerView.Adapter<ParticularServiceWithPersonsAdapter.ViewHolder> {

    private List<ModelOfParticularServiceWithPersons> list;
    private Context context;

    public ParticularServiceWithPersonsAdapter(List<ModelOfParticularServiceWithPersons> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ParticularServiceWithPersonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.particular_service_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularServiceWithPersonsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.size()==1 &&list.get(position).getMail().equals(SplashScreen.trimmedMail) ){
            Glide.with(context).load("https://www.pngitem.com/pimgs/m/22-220721_circled-user-male-type-user-colorful-icon-png.png?fbclid=IwAR3VIKtfsa7rYCRsLWYKwJ6WS0Ga54kQtLxQAowOiK55zPgD8h7QeMXwr-0").into(holder.profilePic);
            holder.location.setText("");
            holder.name.setText("You are the only provider here");
            holder.price.setText("");
            holder.add.setEnabled(false);
            holder.add.setAlpha(0f);
            holder.locationLink.setEnabled(false);
            holder.locationLink.setAlpha(0f);
            holder.icon.setAlpha(0f);
            return;
        }
        Glide.with(context).load(list.get(position).getProfilePic()).into(holder.profilePic);
        holder.location.setText(list.get(position).getLocation());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPriceRange());


        holder.locationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getLocation().equals("--")){
                    return;
                }
                Uri uri = Uri.parse(list.get(position).getLocationLink()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View layout_dialog = LayoutInflater.from(context).inflate(R.layout.particular_service_dialogue_box, null);
                builder.setView(layout_dialog);

                AppCompatButton exit = layout_dialog.findViewById(R.id.serviceDialogueBoxExitButton);
                TextView nameD=layout_dialog.findViewById(R.id.serviceDialogueBoxName);
                TextView locationD=layout_dialog.findViewById(R.id.serviceDialogueBoxLocation);
                TextView priceD=layout_dialog.findViewById(R.id.serviceDialogueBoxPrice);
                ImageView imageD=layout_dialog.findViewById(R.id.serviceDialogueBoxImage);

                Glide.with(context).load(list.get(position).getProfilePic()).into(imageD);
                nameD.setText(list.get(position).getName());
                priceD.setText(list.get(position).getPriceRange());
                locationD.setText(list.get(position).getLocation());

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCancelable(false);
                dialog.getWindow().setGravity(Gravity.CENTER);

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                       // onReceive(context, intent);
                    }
                });
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(list.get(position).getLocation().equals("--")){
                    Toast.makeText(context, "Nothing to add !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String serviceName;
                switch (ListOfAParticularService.SN){
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
                        throw new IllegalStateException("Unexpected value: " + ListOfAParticularService.SN);
                }
      DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
      databaseReference.child("MyCarts").child(SplashScreen.trimmedMail).child(list.get(position).getMail()).setValue(new ModelOfIndividualCarts(list.get(position).getMail(),list.get(position).getLocation(),serviceName,list.get(position).getPriceRange(),list.get(position).getName(),list.get(position).getContactNumber(),list.get(position).getProfilePic(),list.get(position).getLocationLink())  );
      Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic,add,icon;
        private TextView name,location,price,locationLink,fullView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.particularServiceModelImage);
            icon=itemView.findViewById(R.id.iconOfLocation1);
            location=itemView.findViewById(R.id.particularServiceModelLocation);
            locationLink=itemView.findViewById(R.id.particularServiceModelLocationLink);
            name=itemView.findViewById(R.id.particularServiceModelName);
            price=itemView.findViewById(R.id.particularServiceModelPrice);
            add=itemView.findViewById(R.id.particularServiceModelAdd);
            fullView=itemView.findViewById(R.id.particularServiceModelFullView);
        }
    }
}
