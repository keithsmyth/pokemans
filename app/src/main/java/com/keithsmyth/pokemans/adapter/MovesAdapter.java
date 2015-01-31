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
public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.MoveViewHolder> {

  private final List<Pokemon.Move> moveList;

  public MovesAdapter(List<Pokemon.Move> moveList) {
    this.moveList = moveList;
  }

  @Override public MoveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_move_row,
        parent, false);
    return new MoveViewHolder(view);
  }

  @Override public void onBindViewHolder(MoveViewHolder holder, int position) {
    holder.bind(moveList.get(position));
  }

  @Override public int getItemCount() {
    return moveList.size();
  }

  public static class MoveViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTextView;
    private final TextView metaTextView;

    public MoveViewHolder(View itemView) {
      super(itemView);
      nameTextView = (TextView) itemView.findViewById(R.id.txt_name);
      metaTextView = (TextView) itemView.findViewById(R.id.txt_meta);
    }

    public void bind(Pokemon.Move move) {
      nameTextView.setText(move.name);
      metaTextView.setText(String.format("%1$s %2$s", move.learn_type, move.level));
    }
  }
}
