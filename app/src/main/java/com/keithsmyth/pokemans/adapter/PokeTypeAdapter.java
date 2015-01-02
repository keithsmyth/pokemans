package com.keithsmyth.pokemans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private final LinearLayout superLayout;
    private final LinearLayout weakLayout;
    private final LinearLayout ineffectiveLayout;
    private final LinearLayout noEffectLayout;
    private final LinearLayout resistanceLayout;
    private final Context context;

    public PokeTypeViewHolder(View itemView) {
      super(itemView);
      nameText = (TextView) itemView.findViewById(R.id.txt_name);
      superLayout = (LinearLayout) itemView.findViewById(R.id.layout_super);
      weakLayout = (LinearLayout) itemView.findViewById(R.id.layout_weak);
      ineffectiveLayout = (LinearLayout) itemView.findViewById(R.id.layout_ineffective);
      noEffectLayout = (LinearLayout) itemView.findViewById(R.id.layout_no_effect);
      resistanceLayout = (LinearLayout) itemView.findViewById(R.id.layout_resistance);
      context = itemView.getContext();
    }

    public void bind(final Pokemon.PokeType pokeType) {
      nameText.setText(pokeType.name);

      superLayout.removeAllViews();
      weakLayout.removeAllViews();

      App.getPokemonData().getPokeType(pokeType.resource_uri, new Callback<PokeType>() {
        @Override public void onSuccess(PokeType model) {
          if (!pokeType.name.equalsIgnoreCase(model.name)) return;

          setTypeEffectiveness(superLayout, model.super_effective,
              context.getString(R.string.poketype_super_effective));
          setTypeEffectiveness(weakLayout, model.weakness, context.getString(R.string
              .poketype_weakness));
          setTypeEffectiveness(ineffectiveLayout, model.ineffective, context.getString(R.string
              .poketype_ineffective));
          setTypeEffectiveness(noEffectLayout, model.no_effect, context.getString(R.string
              .poketype_no_effect));
          setTypeEffectiveness(resistanceLayout, model.resistance, context.getString(R.string
              .poketype_resistance));
        }

        @Override public void onFail(String msg) {
          // TODO: handle gracefully without context
          Log.e(PokemonFragment.class.getSimpleName(), msg);
        }
      });
    }

    private void setTypeEffectiveness(LinearLayout linearLayout, List<Lookup> lookupList,
                                      String title) {
      LayoutInflater inflater = LayoutInflater.from(linearLayout.getContext());
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup
          .LayoutParams.WRAP_CONTENT, 1);
      linearLayout.addView(getTypeItemView(inflater, title, linearLayout), layoutParams);
      if (lookupList == null) return;
      for (Lookup l : lookupList) {
        linearLayout.addView(getTypeItemView(inflater, l.name, linearLayout), layoutParams);
      }
    }

    private View getTypeItemView(LayoutInflater inflater, String title, ViewGroup parent) {
      TextView view = (TextView) inflater.inflate(R.layout.pokemon_poketype_item, parent, false);
      view.setText(title);
      return view;
    }
  }
}
