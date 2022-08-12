package com.example.login18april;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForShowingUsers extends RecyclerView.Adapter<AdapterForShowingUsers.ViewHolder>{
    List<ClientUser> clientUsers;

    public AdapterForShowingUsers(List<ClientUser> clientUsers) {
        this.clientUsers = clientUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_adminscreen_showing_clientusers,viewGroup,false);
    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        String username = clientUsers.get(position).getUsername();

        viewHolder.setData(username,"Male");

        viewHolder.Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                String username = clientUsers.get(position).getUsername();
                Intent it = new Intent(context,ClientUserDetailsForAdmin.class);
                it.putExtra("username",username);
                context.startActivity(it);

            }
        });


    }

    @Override
    public int getItemCount() {
        return clientUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView Username;
            ImageView UserImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Username = itemView.findViewById(R.id.TVAdminScreenShowingNameRecycler);
                UserImage =  itemView.findViewById(R.id.IVAdminScreenShowingImage);
            }


            public void setData(String username,String gender){
                Username.setText(username);
                if (gender.equals("Male")){
                    UserImage.setImageResource(R.drawable.male1);
                }else{
                    UserImage.setImageResource(R.drawable.googleg_standard_color_18);
                }


            }
        }
}









///////////////////////////
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.List;

//public class AdapterForShowingUsers extends RecyclerView.Adapter<AdapterForShowingUsers.ViewHolder> {
//    private List<ClientUser> clientUsers;
//
//    public AdapterForShowingUsers(List<ClientUser> clientUsers) {
//        this.clientUsers = clientUsers;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_adminscreen_showing_clientusers,viewGroup,false);
//        return new ViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        String username = clientUsers.get(position).getUsername();
//        viewHolder.setData(username);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return clientUsers.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView Username;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            Username = itemView.findViewById(R.id.TVAdminScreenShowingNameRecycler);
//
//        }
//
//        private void setData(String username){
//            Username.setText(username);
//
//        }
//    }
//}
