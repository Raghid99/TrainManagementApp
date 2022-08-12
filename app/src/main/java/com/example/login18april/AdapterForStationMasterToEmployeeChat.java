package com.example.login18april;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForStationMasterToEmployeeChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String StationMasterName;
    List<Chat> chatList;

    public AdapterForStationMasterToEmployeeChat(String stationMasterName, List<Chat> chatList) {
        StationMasterName = stationMasterName;
        this.chatList = chatList;
    }

    @Override
    public int getItemViewType(int position) {

        if (chatList.get(position).getFrom().equals(StationMasterName)) {
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
            return new AdapterForStationMasterToEmployeeChat.SendView(view);

        } else {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_recieved, viewGroup, false);
            return new AdapterForStationMasterToEmployeeChat.RecieveView(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        Chat chat = chatList.get(position);

        if (chat.getFrom().equals(StationMasterName)){

                SendView sendView = (SendView) viewHolder;
                sendView.setSendData(chat.getChatData()+"");

        }
        else {
            RecieveView recieveView = (RecieveView) viewHolder;
            recieveView.setRecieveData(chat.getChatData()+"");

        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class SendView extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView senderText;

        public SendView(@NonNull View itemView) {
            super(itemView);

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
