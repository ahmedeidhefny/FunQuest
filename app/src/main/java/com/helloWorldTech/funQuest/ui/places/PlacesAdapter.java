package com.helloWorldTech.funQuest.ui.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemPlaciesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    private Context context;
    private PlacesListener listener;
    private List<PlacesApiResponse.Places> places;

    public PlacesAdapter(Context context,
                             PlacesListener listener,
                             List<PlacesApiResponse.Places> places) {
        this.context = context;
        this.listener = listener;
        this.places = places;
    }

    public void replace(List<PlacesApiResponse.Places> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemPlaciesBinding binding = ItemPlaciesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PlaceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlaceViewHolder holder, int position) {
        PlacesApiResponse.Places place = places.get(position);

        holder.binding.tvCityNumber.setText(place.getGamesCount()+" Challenge");
        holder.binding.tvCityName.setText(place.getName());

        Glide.with(context)
                .load(Config.BASE_IMAGE_URl + place.getImage())
                .placeholder(R.drawable.bg_help)
                .error(R.drawable.bg_help)
                .into(holder.binding.ivPlaceImage);

        holder.binding.itemTypeCard.setOnClickListener(view->{
            listener.onCityClicked(place);
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private final ItemPlaciesBinding binding;
        public PlaceViewHolder(ItemPlaciesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface PlacesListener {
        void onCityClicked(PlacesApiResponse.Places place);
    }

}
