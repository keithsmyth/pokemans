package com.keithsmyth.pokemans.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.List;

/**
 * @author keithsmyth
 */
public class EvolutionAdapter extends RecyclerView.Adapter<EvolutionAdapter.EvolutionViewHolder> {

  private final List<Pokemon.Evolution> evolutionList;

  public EvolutionAdapter(List<Pokemon.Evolution> evolutionList) {
    this.evolutionList = evolutionList;
  }

  @Override public EvolutionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_evolution_row,
        parent, false);
    return new EvolutionViewHolder(view);
  }

  @Override public void onBindViewHolder(EvolutionViewHolder holder, int position) {
    holder.bind(evolutionList.get(position));
  }

  @Override public int getItemCount() {
    return evolutionList.size();
  }

  public static class EvolutionViewHolder extends RecyclerView.ViewHolder {

    private TextView toText;
    private TextView methodText;
    private TextView levelText;

    public EvolutionViewHolder(View itemView) {
      super(itemView);
      toText = (TextView) itemView.findViewById(R.id.txt_to);
      methodText = (TextView) itemView.findViewById(R.id.txt_method);
      levelText = (TextView) itemView.findViewById(R.id.txt_level);
    }

    public void bind(Pokemon.Evolution evolution) {
      toText.setText(evolution.to);
      methodText.setText(evolution.method);
      levelText.setText(evolution.level != 0 ? String.valueOf(evolution.level) : null);
    }
  }
}
