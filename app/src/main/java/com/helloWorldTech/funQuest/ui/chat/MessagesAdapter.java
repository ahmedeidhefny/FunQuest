package com.helloWorldTech.funQuest.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_STAFF = 1;
    private final int VIEW_USER = 2;

    private Context context;
    private List<Message> messageList;
    private PreferencesHelper pref;

    public MessagesAdapter(Context context,
                           List<Message> messageList,
                           PreferencesHelper pref) {
        this.context = context;
        this.messageList = messageList;
        this.pref = pref;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_USER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_receive, parent, false);
            vh = new MessagesUserViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_send, parent, false);
            vh = new MessagesStaffViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (holder instanceof MessagesUserViewHolder) {
            MessagesUserViewHolder viewHolder = (MessagesUserViewHolder) holder;

            String image = pref.getUserImage();
            Glide.with(context)
                    .load(Config.BASE_IMAGE_URl + image)
                    .placeholder(R.drawable.unknown_user)
                    .error(R.drawable.unknown_user)
                    .into(viewHolder.image);

            viewHolder.message.setText(message.getContent());

            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM HH:mm a", Locale.getDefault());
            Date timeStamp = message.getCreated();
            if (timeStamp != null) {
                String timeOfMassage = sfd.format(timeStamp);
                viewHolder.date.setText(timeOfMassage);
            }

        } else {
            MessagesStaffViewHolder viewHolder = (MessagesStaffViewHolder) holder;
            viewHolder.message.setText(message.getContent());


            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM HH:mm a", Locale.getDefault());
            Date timeStamp = message.getCreated();
            if (timeStamp != null) {
                String timeOfMassage = sfd.format(timeStamp);
                viewHolder.date.setText(timeOfMassage);
            }
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getFrom().equals("user") ? VIEW_USER : VIEW_STAFF;
    }

    public class MessagesUserViewHolder extends RecyclerView.ViewHolder {
        TextView message, date;
        CircleImageView image;

        public MessagesUserViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_message);
            date = itemView.findViewById(R.id.tv_time);
            image = itemView.findViewById(R.id.iv_image);
        }
    }

    public class MessagesStaffViewHolder extends RecyclerView.ViewHolder {
        TextView message, date;

        public MessagesStaffViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_message);
            date = itemView.findViewById(R.id.tv_time);
        }
    }
}
