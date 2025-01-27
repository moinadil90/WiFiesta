package moin.movies.views.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import moin.movies.R;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {

    private ArrayList<String> suggestions;
    private Callback callback;

    public SuggestionsAdapter(ArrayList<String> suggestions, Callback callback) {
        this.suggestions = suggestions;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuggestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        final String suggestion = suggestions.get(position);
        holder.tv_suggestion.setText(suggestion);
        holder.iv_clear.setOnClickListener(view -> callback.onSuggestionDelete(suggestion));
        holder.itemView.setOnClickListener(view -> callback.onSuggestionClick(suggestion));
    }

    @Override
    public int getItemCount() {
        return suggestions == null ? 0 : suggestions.size();
    }

    public void filter(ArrayList<String> suggestions, String query) {
        query = query.toLowerCase();
        final ArrayList<String> filteredModeList = new ArrayList<>();
        for (String suggestion : suggestions) {
            if (suggestion.startsWith(query)) {
                filteredModeList.add(suggestion);
            }
        }
        this.suggestions = new ArrayList<>();
        this.suggestions.addAll(filteredModeList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onSuggestionClick(String suggestion);

        void onSuggestionDelete(String suggestion);
    }

    class SuggestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_suggestion)
        TextView tv_suggestion;
        @BindView(R.id.iv_clear)
        ImageView iv_clear;

        SuggestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
