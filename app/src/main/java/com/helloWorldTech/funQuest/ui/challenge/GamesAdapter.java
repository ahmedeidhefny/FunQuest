package com.helloWorldTech.funQuest.ui.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GamesApiResponse;
import com.helloWorldTech.funQuest.databinding.ItemGameChallengesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ChallengesViewHolder> {

    private Context context;
    private GameListener listener;
    private List<GamesApiResponse.Items> games;

    public GamesAdapter(Context context,
                        GameListener listener,
                        List<GamesApiResponse.Items> games) {
        this.context = context;
        this.listener = listener;
        this.games = games;
    }

    public void replace(List<GamesApiResponse.Items> gameList) {
        this.games = gameList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ChallengesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemGameChallengesBinding binding = ItemGameChallengesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChallengesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChallengesViewHolder holder, int position) {
        GamesApiResponse.Items game = games.get(position);

        holder.binding.tvName.setText(game.getName());
        holder.binding.tvDate.setText(game.getStartDateTime());
        holder.binding.tvTeamNumber.setText("0" + game.getTeamsCount() );

        Glide.with(context)
                .load(Config.BASE_IMAGE_URl + game.getImage())
                .placeholder(R.drawable.bg_help)
                .error(R.drawable.bg_help)
                .into(holder.binding.ivImage);

        holder.binding.itemTypeCard.setOnClickListener(view->{
            listener.onCityClicked(game);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ChallengesViewHolder extends RecyclerView.ViewHolder {
        private final ItemGameChallengesBinding binding;
        public ChallengesViewHolder(ItemGameChallengesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface GameListener {
        void onCityClicked(GamesApiResponse.Items game);
    }

}
