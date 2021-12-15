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
import com.helloWorldTech.funQuest.databinding.ItemPopuarBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MostPlacesAdapter extends RecyclerView.Adapter<MostPlacesAdapter.MostPlaceViewHolder> {

    private Context context;
    private MostPlacesListener listener;
    private List<PlacesApiResponse.MostPlaces> mostPlaces;

    public MostPlacesAdapter(Context context,
                             MostPlacesListener listener,
                             List<PlacesApiResponse.MostPlaces> mostPlaces) {
        this.context = context;
        this.listener = listener;
        this.mostPlaces = mostPlaces;
    }

    public void replace(List<PlacesApiResponse.MostPlaces> mostPlaces) {
        this.mostPlaces = mostPlaces;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MostPlaceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemPopuarBinding binding = ItemPopuarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MostPlaceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MostPlaceViewHolder holder, int position) {
        PlacesApiResponse.MostPlaces city = mostPlaces.get(position);

        holder.binding.tvCityNumber.setText(city.getGamesCount()+" Challenge");
        holder.binding.tvCityName.setText(city.getName());

        Glide.with(context)
                .load(Config.BASE_IMAGE_URl + city.getImage())
                .placeholder(R.drawable.bg_help)
                .error(R.drawable.bg_help)
                .into(holder.binding.ivCityImage);

        holder.binding.itemTypeCard.setOnClickListener(view->{
            listener.onCityClicked(city);
        });
    }

    @Override
    public int getItemCount() {
        return mostPlaces.size();
    }

    public class MostPlaceViewHolder extends RecyclerView.ViewHolder {
        private final ItemPopuarBinding binding;
        public MostPlaceViewHolder(ItemPopuarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface MostPlacesListener {
        void onCityClicked(PlacesApiResponse.MostPlaces mostCity);
    }

}
