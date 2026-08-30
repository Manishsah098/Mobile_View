package com.example.atlantis.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atlantis.R;
import com.example.atlantis.model.ChatMessage;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {

    private final List<ChatMessage> messageList;

    public ChatMessageAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);

        if (message.isAi()) {
            holder.aiMessageContainer.setVisibility(View.VISIBLE);
            holder.userMessageContainer.setVisibility(View.GONE);
            holder.aiMessageTextView.setText(message.getText());
            holder.aiTimeTextView.setText(message.getTimestamp());
        } else {
            holder.aiMessageContainer.setVisibility(View.GONE);
            holder.userMessageContainer.setVisibility(View.VISIBLE);
            holder.userMessageTextView.setText(message.getText());
            holder.userTimeTextView.setText(message.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout aiMessageContainer;
        public final LinearLayout userMessageContainer;
        public final TextView aiMessageTextView;
        public final TextView aiTimeTextView;
        public final TextView userMessageTextView;
        public final TextView userTimeTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            aiMessageContainer = itemView.findViewById(R.id.aiMessageContainer);
            userMessageContainer = itemView.findViewById(R.id.userMessageContainer);
            aiMessageTextView = itemView.findViewById(R.id.aiMessageTextView);
            aiTimeTextView = itemView.findViewById(R.id.aiTimeTextView);
            userMessageTextView = itemView.findViewById(R.id.userMessageTextView);
            userTimeTextView = itemView.findViewById(R.id.userTimeTextView);
        }
    }
}
