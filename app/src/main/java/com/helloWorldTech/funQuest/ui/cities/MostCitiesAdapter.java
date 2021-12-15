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
import com.helloWorldTech.funQuest.databinding.ItemPopuarBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MostCitiesAdapter extends RecyclerView.Adapter<MostCitiesAdapter.MostCityViewHolder> {

    private Context context;
    private MostCitiesListener listener;
    private List<HomeApiResponse.MostCities> mostCities ;

    public MostCitiesAdapter(Context context,
                             MostCitiesListener listener,
                             List<HomeApiResponse.MostCities> mostCities) {
        this.context = context;
        this.listener = listener;
        this.mostCities = mostCities;
    }

    public void replace(List<HomeApiResponse.MostCities> mostCities) {
        this.mostCities = mostCities;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MostCityViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemPopuarBinding binding = ItemPopuarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MostCityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MostCityViewHolder holder, int position) {
        HomeApiResponse.MostCities city = mostCities.get(position);

        holder.binding.tvCityNumber.setText(city.getPlacesCount()+" City");
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
        return mostCities.size();
    }

    public class MostCityViewHolder extends RecyclerView.ViewHolder {
        private final ItemPopuarBinding binding;
        public MostCityViewHolder(ItemPopuarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface MostCitiesListener{
        void onCityClicked(HomeApiResponse.MostCities mostCity);
    }

}
