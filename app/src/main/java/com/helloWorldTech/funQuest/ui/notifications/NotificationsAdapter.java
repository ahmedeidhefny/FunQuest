package com.helloWorldTech.funQuest.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemNotifiicationsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Context context;
    private NotificationsListener listener;
    private List<NotificationsApiResponse.Notifications> notifications;

    public NotificationsAdapter(Context context,
                                NotificationsListener listener,
                                List<NotificationsApiResponse.Notifications> notifications) {
        this.context = context;
        this.listener = listener;
        this.notifications = notifications;
    }

    public void replace(List<NotificationsApiResponse.Notifications> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemNotifiicationsBinding binding = ItemNotifiicationsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationsViewHolder holder, int position) {
        NotificationsApiResponse.Notifications notification = this.notifications.get(position);

        //holder.binding.tvName.setText(notification.getTitle());
        holder.binding.tvDate.setText(notification.getCreatedAt());
        holder.binding.tvDes.setText( notification.getTitle() );

//        holder.binding.itemTypeCard.setOnClickListener(view->{
//            listener.onCityClicked(notification);
//        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotifiicationsBinding binding;
        public NotificationsViewHolder(ItemNotifiicationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public interface NotificationsListener {
        void onCityClicked(NotificationsApiResponse.Notifications notification);
    }

}
