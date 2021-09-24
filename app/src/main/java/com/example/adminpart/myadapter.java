package com.example.adminpart;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>{


    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final  myviewholder holder,final int position, @NonNull final  model model)
    {
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getFurl()).into(holder.img);

       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                       .setContentHolder(new ViewHolder(R.layout.dialogcontent))

                       .create();


               View myview = dialogPlus.getHolderView();
               EditText name = myview.findViewById(R.id.uname);
               EditText description = myview.findViewById(R.id.udescription);
               EditText price = myview.findViewById(R.id.uprice);
               EditText furl = myview.findViewById(R.id.uimgurl);
               Button submit= myview.findViewById(R.id.usubmit);


               name.setText(model.getName());
               description.setText(model.getDescription());
               price.setText(model.getPrice());
               furl.setText(model.getFurl());

                dialogPlus.show();






                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String,Object> map= new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("furl",furl.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("foods")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        dialogPlus.dismiss();






                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


           }
       });
//delete method

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
               builder.setTitle(" Delete Food Item");

               builder.setMessage("Are you Sure Delete Food Item ");

               builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       FirebaseDatabase.getInstance().getReference().child("foods")
                               .child(getRef(position).getKey()).removeValue();

                   }
               });

               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               builder.show();




           }
       });






    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);


    }



    class myviewholder  extends RecyclerView.ViewHolder
    {

        CircleImageView img;
        TextView name,description,price;
        ImageView edit,delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            name=(TextView)itemView.findViewById(R.id.nametext);
            description=(TextView)itemView.findViewById(R.id.descriptiontext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
            img=(CircleImageView)itemView.findViewById(R.id.img1);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);


        }

    }
}
