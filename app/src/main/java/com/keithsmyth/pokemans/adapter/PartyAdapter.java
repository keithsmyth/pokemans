package com.keithsmyth.pokemans.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Party;

/**
* @author keithsmyth
*/
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.MemberViewHolder> {

  private final Party party;

  public PartyAdapter(Party party) {
    this.party = party;
  }

  @Override public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedex_pokemon_row, parent, false);
    return new MemberViewHolder(view);
  }

  @Override public void onBindViewHolder(MemberViewHolder holder, int position) {
    holder.bind(party.memberList.get(position));
  }

  @Override public int getItemCount() {
    return party.memberList.size();
  }

  public static class MemberViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public MemberViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView;
    }

    public void bind(Party.Member pokemon) {
      textView.setText(pokemon.name);
    }
  }
}
