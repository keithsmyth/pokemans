package com.keithsmyth.pokemans.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.model.Pokedex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author keithsmyth
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

  private final List<Pokedex.Pokemon> origPokemonList;
  private final List<Pokedex.Pokemon> pokemonList;
  private final PokemonItemClickListener itemClickListener;

  public PokemonAdapter(List<Pokedex.Pokemon> pokemonList, PokemonItemClickListener
      itemClickListener) {
    // orig values
    this.origPokemonList = pokemonList;
    sortPokemonList();
    // filtered values
    this.pokemonList = new ArrayList<>();
    setFilter(null);

    this.itemClickListener = itemClickListener;
  }

  private void sortPokemonList() {
    Collections.sort(origPokemonList, new Comparator<Pokedex.Pokemon>() {
      @Override public int compare(Pokedex.Pokemon lhs, Pokedex.Pokemon rhs) {
        return lhs.name.compareTo(rhs.name);
      }
    });
  }

  @Override public PokemonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
        .pokedex_pokemon_row, viewGroup, false);
    return new PokemonViewHolder(view);
  }

  @Override public void onBindViewHolder(PokemonViewHolder pokemonViewHolder, int i) {
    pokemonViewHolder.bind(pokemonList.get(i), itemClickListener);
  }

  @Override public int getItemCount() {
    return pokemonList.size();
  }

  public void setFilter(String filterString) {
    pokemonList.clear();
    if (TextUtils.isEmpty(filterString)) {
      pokemonList.addAll(origPokemonList);
    } else {
      String lowerFilter = filterString.toLowerCase();
      for (Pokedex.Pokemon pokemon : origPokemonList) {
        if (pokemon.name.toLowerCase().contains(lowerFilter)) {
          pokemonList.add(pokemon);
        }
      }
    }
    notifyDataSetChanged();
  }

  public static interface PokemonItemClickListener {
    public void onClick(Pokedex.Pokemon pokemon);
  }

  public static class PokemonViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameText;

    public PokemonViewHolder(View itemView) {
      super(itemView);
      nameText = (TextView) itemView;
    }

    public void bind(final Pokedex.Pokemon pokemon, final PokemonItemClickListener listener) {
      nameText.setText(pokemon.name);
      nameText.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onClick(pokemon);
        }
      });
    }
  }
}
