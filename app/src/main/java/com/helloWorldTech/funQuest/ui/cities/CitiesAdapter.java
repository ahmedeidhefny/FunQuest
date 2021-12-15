package com.helloWorldTech.funQuest.ui.cities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemCityBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private Context context;
    private CitiesListener listener;
    private List<HomeApiResponse.Cities> cities ;

    public CitiesAdapter(Context context,
                         CitiesListener listener,
                         List<HomeApiResponse.Cities> cities) {
        this.context = context;
        this.listener = listener;
        this.cities = cities;
    }

    public void replace(List<HomeApiResponse.Cities> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemCityBinding binding = ItemCityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CitiesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CitiesViewHolder holder, int position) {
        HomeApiResponse.Cities city = cities.get(position);

        holder.binding.tvPlacesNumber.setText(city.getPlacesCount()+" Places");
        holder.binding.tvCityName.setText(city.getName());

//        Glide.with(context)
//                .load(Config.BASE_IMAGE_URl + city.getImage())
//                .placeholder(R.drawable.bg_help)
//                .error(R.drawable.bg_help)
//                .into(holder.binding.ivImage);

        holder.binding.itemTypeCard.setOnClickListener(view->{
            listener.onCityClicked(city);
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder {
        private final ItemCityBinding binding;
        public CitiesViewHolder(ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CitiesListener {
        void onCityClicked(HomeApiResponse.Cities mCity);
    }

}
