package com.helloWorldTech.funQuest.ui.award;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemAwardsBinding;
import com.helloWorldTech.funQuest.databinding.ItemNotifiicationsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AwardsAdapter extends RecyclerView.Adapter<AwardsAdapter.AwardViewHolder> {

    private Context context;
    private AwardListener listener;
    private List<AwardsApiResponse.Award> awards;

    public AwardsAdapter(Context context,
                         AwardListener listener,
                         List<AwardsApiResponse.Award> awards) {
        this.context = context;
        this.listener = listener;
        this.awards = awards;
    }

    public void replace(List<AwardsApiResponse.Award> awards) {
        this.awards = awards;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public AwardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemAwardsBinding binding = ItemAwardsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AwardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AwardViewHolder holder, int position) {
        AwardsApiResponse.Award notification = this.awards.get(position);

        holder.binding.tvDate.setText(notification.getCreatedAt());
        holder.binding.tvDes.setText( notification.getTitle() );

//        holder.binding.itemTypeCard.setOnClickListener(view->{
//            listener.onCityClicked(notification);
//        });
    }

    @Override
    public int getItemCount() {
        return awards.size();
    }

    public class AwardViewHolder extends RecyclerView.ViewHolder {
        private final ItemAwardsBinding binding;
        public AwardViewHolder(ItemAwardsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public interface AwardListener {
        void onCityClicked(AwardsApiResponse.Award award);
    }

}
