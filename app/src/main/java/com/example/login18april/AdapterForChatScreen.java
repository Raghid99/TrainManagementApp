package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForChatScreen extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Chat> chatsList;
    public static final String TAG = "Daza";

    public AdapterForChatScreen(List<Chat> chatsList) {
        this.chatsList = chatsList;
    }


    @Override
    public int getItemViewType(int position) {

        if (chatsList.get(position).from.equals("AdminAccount")) {
            Log.d(TAG, "Sending it first");

            return 1;


        } else {
            return 2;


        }


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if (viewType == 1) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_send, viewGroup, false);
            return new SendView(view);

        } else {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_recieved, viewGroup, false);
            return new RecieveView(view);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Chat chat = chatsList.get(position);
        if (chat.getFrom().equals("AdminAccount")){

            SendView sendView = (SendView) viewHolder;
            sendView.setSendData(chat.getChatData()+"");

        }else{

        RecieveView recieveView = (RecieveView) viewHolder;
        recieveView.setRecieveData(chat.getChatData());


          }

    }


    @Override
    public int getItemCount() {
        return chatsList.size();
    }


    class SendView extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView senderText;

        public SendView(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "Invoking at third");

            senderImage = itemView.findViewById(R.id.IVTextImageOfSender);
            senderText = itemView.findViewById(R.id.TVTextSender);

        }

        public void setSendData(String chatData) {

            senderText.setText(chatData + "");
            senderImage.setImageResource(R.drawable.male1);


        }
    }

    class RecieveView extends RecyclerView.ViewHolder {
        ImageView recieverImage;
        TextView recieverText;

        public RecieveView(@NonNull View itemView) {
            super(itemView);
            recieverImage = itemView.findViewById(R.id.IVTextImageOfReciever);
            recieverText = itemView.findViewById(R.id.TVTextRecieved);

        }

        private void setRecieveData(String chatData) {

            recieverText.setText(chatData + "");
            recieverImage.setImageResource(R.drawable.male1);


        }
    }


}


////////////////////////////////////////
//package com.example.login18april;
//
//        import android.support.annotation.NonNull;
//        import android.support.v7.widget.RecyclerView;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import java.util.Date;
//        import java.util.List;
//
//public class AdapterForChatScreen extends RecyclerView.Adapter<AdapterForChatScreen.viewHolder> {
//    List<Chat> chatsList;
//
//    public AdapterForChatScreen(List<Chat> chatsList) {
//        this.chatsList = chatsList;
//    }
//
//
//
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_for_chat_sxreen,viewGroup,false);
//        return new viewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
//        String from = chatsList.get(position).getFrom();
//        String to = chatsList.get(position).getTo();
//        String chatData = chatsList.get(position).getChatData();
//        //long chatID = chatsList.get(position).getChatID();
//        viewHolder.setData(from,to,chatData);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return chatsList.size();
//    }
//
//    class viewHolder extends RecyclerView.ViewHolder{
//        ImageView senderImage,recieverImage;
//        TextView senderText,recieverText;
//
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            senderImage = itemView.findViewById(R.id.IVChatScreenSender);
//            recieverImage = itemView.findViewById(R.id.IVChatScreenReciever);
//            senderText = itemView.findViewById(R.id.TVChatScreenSender);
//            recieverText = itemView.findViewById(R.id.TVChatScreenReciever);
//        }
//
//        public void setData(String from,String to,String chatData){
//            if (from.equals("AdminAccount")){
//                senderImage.setImageResource(R.drawable.userphoto);
//                senderText.setText(chatData);
//                senderText.setBackgroundResource(R.drawable.going);
//
//
//                recieverImage.setImageResource(R.drawable.whitebackground);
//                recieverImage.setMaxHeight(0);
//                recieverImage.setVisibility(View.INVISIBLE);
//
//                recieverText.setHeight(0);
//                recieverText.setVisibility(View.INVISIBLE);
//
//
//            }else {
//                recieverText.setText(chatData);
//                recieverText.setBackgroundResource(R.drawable.incoming);
//                recieverImage.setImageResource(R.drawable.userphoto);
//
//
//                senderImage.setImageResource(R.drawable.whitebackground);
//                senderImage.setMaxHeight(0);
//                senderImage.setVisibility(View.INVISIBLE);
//
//                senderText.setHeight(0);
//                senderText.setVisibility(View.INVISIBLE);
//
//
//            }
//
//        }
//    }
//
//
//}

