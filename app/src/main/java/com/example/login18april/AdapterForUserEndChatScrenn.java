package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForUserEndChatScrenn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Chat> chatsList;


    @Override
    public int getItemViewType(int position) {

        if (chatsList.get(position).from.equals("AdminAccount")) {

            return 2;


        } else {
            return 1;


        }


    }


    public AdapterForUserEndChatScrenn(List<Chat> chatsList) {
        this.chatsList = chatsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if (viewType == 1) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_send, viewGroup, false);
            return new AdapterForUserEndChatScrenn.SendView(view);

        } else {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_recieved, viewGroup, false);
            return new AdapterForUserEndChatScrenn.RecieveView(view);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Chat chat = chatsList.get(position);
        if (chat.getFrom().equals("AdminAccount")) {

            AdapterForUserEndChatScrenn.RecieveView recieveView = (AdapterForUserEndChatScrenn.RecieveView) viewHolder;
            recieveView.setRecieveData(chat.getChatData());


        } else {

            AdapterForUserEndChatScrenn.SendView sendView = (AdapterForUserEndChatScrenn.SendView) viewHolder;
            sendView.setSendData(chat.getChatData() + "");




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

            senderImage = itemView.findViewById(R.id.IVTextImageOfSender);
            senderText = itemView.findViewById(R.id.TVTextSender);

        }

        void setSendData(String chatData) {

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

        void setRecieveData(String chatData) {

            recieverText.setText(chatData + "");
            recieverImage.setImageResource(R.drawable.male1);


        }
    }


}


//public class AdapterForUserEndChatScrenn extends RecyclerView.Adapter<AdapterForUserEndChatScrenn.viewHolder> {
//    List<Chat> chatsList;
//
//    public AdapterForUserEndChatScrenn(List<Chat> chatsList) {
//        this.chatsList = chatsList;
//    }
//
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_for_userend_chat, viewGroup, false);
//        return new AdapterForUserEndChatScrenn.viewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
//        String from = chatsList.get(position).getFrom();
//        String to = chatsList.get(position).getTo();
//        String chatData = chatsList.get(position).getChatData();
//        viewHolder.setData(from, to, chatData);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return chatsList.size();
//    }
//
//    class viewHolder extends RecyclerView.ViewHolder {
//        ImageView senderImage, recieverImage;
//        TextView senderText, recieverText;
//
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            senderImage = itemView.findViewById(R.id.IVUserEndSender);
//            recieverImage = itemView.findViewById(R.id.IVUserEndReciever);
//            senderText = itemView.findViewById(R.id.TVUserEndSender);
//            recieverText = itemView.findViewById(R.id.TVUserEndReciever);
//        }
//
//        public void setData(String from, String to, String chatData) {
//            if (from.equals("AdminAccount")) {
//                recieverText.setText(chatData);
//                recieverText.setBackgroundResource(R.drawable.incoming);
//                recieverImage.setImageResource(R.drawable.userphoto);
//
//
//                senderImage.setImageResource(R.drawable.whitebackground);
//                senderText.setHeight(0);
//                senderImage.setMaxHeight(0);
//               // senderText.setText("");
//                //senderImage.setVisibility(View.INVISIBLE);
//
//
//            } else {
//                senderImage.setImageResource(R.drawable.userphoto);
//                senderText.setText(chatData);
//                senderText.setBackgroundResource(R.drawable.going);
//
//                recieverText.setHeight(0);
//                recieverImage.setMaxHeight(0);
//                //recieverImage.setImageResource(R.drawable.whitebackground);
//                //recieverText.setText("");
//                //recieverImage.setVisibility(View.INVISIBLE);
//
//
//
//            }
//
//        }
//    }
//
//
//
//}
