package com.helloWorldTech.funQuest.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HistoryApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemChallengeHistoryBinding;
import com.helloWorldTech.funQuest.databinding.ItemPlaciesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private ItemListener listener;
    private List<HistoryApiResponse.Items> items;

    public HistoryAdapter(Context context,
                          ItemListener listener,
                          List<HistoryApiResponse.Items> items) {
        this.context = context;
        this.listener = listener;
        this.items = items;
    }

    public void replace(List<HistoryApiResponse.Items> places) {
        this.items = places;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemChallengeHistoryBinding binding = ItemChallengeHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryViewHolder holder, int position) {
        HistoryApiResponse.Items item = this.items.get(position);

        holder.binding.tvDate.setText(item.getDate());
        holder.binding.tvName.setText(item.getGameName());
        holder.binding.tvRank.setText(item.getWinnerTeamName());
        holder.binding.tvCityPlace.setText(item.getCityName() + " - " + item.getPlaceName());

        Glide.with(context)
                .load(Config.BASE_IMAGE_URl + item.getImage())
                .placeholder(R.drawable.bg_help)
                .error(R.drawable.bg_help)
                .into(holder.binding.ivImage);

        holder.binding.itemTypeCard.setOnClickListener(view->{
            listener.onCityClicked(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemChallengeHistoryBinding binding;
        public HistoryViewHolder(ItemChallengeHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ItemListener {
        void onCityClicked(HistoryApiResponse.Items item);
    }

}
