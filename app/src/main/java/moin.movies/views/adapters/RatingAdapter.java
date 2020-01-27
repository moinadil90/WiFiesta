package moin.movies.views.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import moin.movies.R;
import moin.movies.models.MovieModel;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    private ArrayList<MovieModel.Rating> ratings;

    public RatingAdapter(ArrayList<MovieModel.Rating> ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        final MovieModel.Rating rating = ratings.get(position);
        holder.tv_source.setText(rating.getSource());
        holder.tv_rating.setText(rating.getValue());
    }

    @Override
    public int getItemCount() {
        return ratings == null ? 0 : ratings.size();
    }

    class RatingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_source)
        TextView tv_source;
        @BindView(R.id.tv_rating)
        TextView tv_rating;

        RatingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
