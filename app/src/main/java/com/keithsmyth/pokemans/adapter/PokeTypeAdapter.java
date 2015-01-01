package com.keithsmyth.pokemans.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.PokemonFragment;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Lookup;
import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.List;

/**
 * @author keithsmyth
 */
public class PokeTypeAdapter extends RecyclerView.Adapter<PokeTypeAdapter.PokeTypeViewHolder> {

  private final List<Pokemon.PokeType> typeList;

  public PokeTypeAdapter(List<Pokemon.PokeType> typeList) {
    this.typeList = typeList;
  }

  @Override public PokeTypeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
        .pokemon_poketype_row, viewGroup, false);
    return new PokeTypeViewHolder(view);
  }

  @Override public void onBindViewHolder(PokeTypeViewHolder pokeTypeViewHolder, int i) {
    pokeTypeViewHolder.bind(typeList.get(i));
  }

  @Override public int getItemCount() {
    return typeList.size();
  }

  public static class PokeTypeViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameText;
    private final TextView superText;
    private final TextView weakText;

    public PokeTypeViewHolder(View itemView) {
      super(itemView);
      nameText = (TextView) itemView.findViewById(R.id.txt_name);
      superText = (TextView) itemView.findViewById(R.id.txt_super);
      weakText = (TextView) itemView.findViewById(R.id.txt_weak);
    }

    public void bind(final Pokemon.PokeType pokeType) {
      nameText.setText(pokeType.name);
      superText.setText(null);
      weakText.setText(null);

      App.getPokemonData().getPokeType(pokeType.resource_uri, new Callback<PokeType>() {
        @Override public void onSuccess(PokeType model) {
          if (!pokeType.name.equalsIgnoreCase(model.name)) return;

          setTypeEffectiveness(superText, model.super_effective);
          setTypeEffectiveness(weakText, model.weakness);
        }

        @Override public void onFail(String msg) {
          // TODO: handle gracefully without context
          Log.e(PokemonFragment.class.getSimpleName(), msg);
        }
      });
    }

    private void setTypeEffectiveness(TextView textView, List<Lookup> lookupList) {
      if (lookupList == null) return;
      StringBuilder output = new StringBuilder();
      for (Lookup l : lookupList) {
        if (output.length() > 0) output.append(", ");
        output.append(l.name);
      }
      textView.setText(output.toString());
    }
  }
}
