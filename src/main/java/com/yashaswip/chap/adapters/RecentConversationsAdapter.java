package com.yashaswip.chap.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yashaswip.chap.databinding.ItemContainerRecentConversionBinding;
import com.yashaswip.chap.listeners.ConversionListner;
import com.yashaswip.chap.models.ChatMessage;
import com.yashaswip.chap.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversationsViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversionListner conversionListner;
    @NonNull
    @Override
    public ConversationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationsViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationsViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public RecentConversationsAdapter(List<ChatMessage> chatMessages,ConversionListner conversionListner){
        this.chatMessages = chatMessages;
        this.conversionListner = conversionListner;
    }

    class ConversationsViewHolder extends RecyclerView.ViewHolder{
        ItemContainerRecentConversionBinding binding;
        ConversationsViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding){
            super(itemContainerRecentConversionBinding.getRoot());
            binding =  itemContainerRecentConversionBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.ConversionName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v-> {
                User user = new User();
                user.id = chatMessage.conversionId;
                user.name = chatMessage.ConversionName;
                user.image = chatMessage.conversionImage;
                conversionListner.onConversationClicked(user);
            });
        }
    }

    private Bitmap getConversionImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
