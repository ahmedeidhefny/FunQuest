package com.helloWorldTech.funQuest.ui.fag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemCityBinding;
import com.helloWorldTech.funQuest.databinding.ItemFaqBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FagAdapter extends RecyclerView.Adapter<FagAdapter.FagViewHolder> {

    private Context context;
    private FagListener listener;
    private List<FagApiResponse.Faq> faqs;

    public FagAdapter(Context context,
                      FagListener listener,
                      List<FagApiResponse.Faq> faqs) {
        this.context = context;
        this.listener = listener;
        this.faqs = faqs;
    }

    public void replace(List<FagApiResponse.Faq> faqs) {
        this.faqs = faqs;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public FagViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemFaqBinding binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FagViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FagViewHolder holder, int position) {
        FagApiResponse.Faq faq = faqs.get(position);

        holder.binding.tvTitle.setText(faq.getTitle());

        holder.binding.itemCardLyt.setOnClickListener(view->{
            listener.onCityClicked(faq);
        });
    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

    public class FagViewHolder extends RecyclerView.ViewHolder {
        private final ItemFaqBinding binding;
        public FagViewHolder(ItemFaqBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface FagListener {
        void onCityClicked(FagApiResponse.Faq faq);
    }

}
