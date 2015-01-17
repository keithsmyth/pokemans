package com.keithsmyth.pokemans.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Lookup;
import com.keithsmyth.pokemans.model.PokeType;
import com.keithsmyth.pokemans.model.Pokemon;
import com.keithsmyth.pokemans.model.TypeEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author keithsmyth
 */
public class TypeEffectAdapter extends RecyclerView.Adapter<TypeEffectAdapter
    .TypeEffectViewHolder> {

  private final List<TypeEffect> typeEffectList;
  private final HashMap<String, TypeEffect> typeEffectHashMap;
  private final List<Pokemon.PokeType> pokeTypeList;
  private int counter;

  public TypeEffectAdapter(List<Pokemon.PokeType> pokeTypeList) {
    this.pokeTypeList = pokeTypeList;
    typeEffectList = new ArrayList<>();
    typeEffectHashMap = new HashMap<>();
    initTypeEffectList();
  }

  private void initTypeEffectList() {
    typeEffectList.clear();
    typeEffectList.add(new TypeEffect("Normal"));
    typeEffectList.add(new TypeEffect("Fighting"));
    typeEffectList.add(new TypeEffect("Flying"));
    typeEffectList.add(new TypeEffect("Poison"));
    typeEffectList.add(new TypeEffect("Ground"));
    typeEffectList.add(new TypeEffect("Rock"));
    typeEffectList.add(new TypeEffect("Bug"));
    typeEffectList.add(new TypeEffect("Ghost"));
    typeEffectList.add(new TypeEffect("Steel"));
    typeEffectList.add(new TypeEffect("Fire"));
    typeEffectList.add(new TypeEffect("Water"));
    typeEffectList.add(new TypeEffect("Grass"));
    typeEffectList.add(new TypeEffect("Electric"));
    typeEffectList.add(new TypeEffect("Psychic"));
    typeEffectList.add(new TypeEffect("Ice"));
    typeEffectList.add(new TypeEffect("Dragon"));
    typeEffectList.add(new TypeEffect("Dark"));
    typeEffectList.add(new TypeEffect("Fairy"));

    typeEffectHashMap.clear();
    for (TypeEffect typeEffect : typeEffectList) {
      typeEffectHashMap.put(typeEffect.name.toLowerCase(), typeEffect);
    }
  }

  public void init(final TypeEffectInitListener listener) {
    if (counter != 0) throw new RuntimeException("Type Effectiveness load error");

    counter = pokeTypeList.size();
    for (Pokemon.PokeType pokeType : pokeTypeList) {
      App.getPokemonData().getPokeType(pokeType.resource_uri, new Callback<PokeType>() {
        @Override public void onSuccess(PokeType model) {
          updateTypeEffectiveness(model);
          if (--counter == 0) completeInit(listener);
        }

        @Override public void onFail(String msg) {
          //TODO: handle gracefully
          if (--counter == 0) completeInit(listener);
        }
      });
    }

  }

  private void updateTypeEffectiveness(PokeType pokeType) {
    for (Lookup item : pokeType.super_effective) {
      typeEffectHashMap.get(item.name).attackEffect *= 2;
    }
    for (Lookup item : pokeType.ineffective) {
      typeEffectHashMap.get(item.name).attackEffect *= 0.5;
    }
    for (Lookup item : pokeType.no_effect) {
      typeEffectHashMap.get(item.name).attackEffect = 0;
    }
    for (Lookup item : pokeType.weakness) {
      typeEffectHashMap.get(item.name).defenceEffect *= -2;
    }
    for (Lookup item : pokeType.resistance) {
      typeEffectHashMap.get(item.name).defenceEffect *= -0.5;
    }
  }

  private void completeInit(TypeEffectInitListener listener) {
    // remove boring types
    for (int i = typeEffectList.size() - 1; i >= 0; i--) {
      TypeEffect typeEffect = typeEffectList.get(i);
      if (typeEffect.attackEffect == 1 && typeEffect.defenceEffect == 1) {
        typeEffectList.remove(i);
      }
    }
    listener.onReady();
  }

  @Override public TypeEffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_poketype_item,
        parent, false);
    return new TypeEffectViewHolder(view);
  }

  @Override public void onBindViewHolder(TypeEffectViewHolder holder, int position) {
    holder.bind(typeEffectList.get(position));
  }

  @Override public int getItemCount() {
    return typeEffectList.size();
  }

  public static interface TypeEffectInitListener {
    void onReady();
  }

  public static class TypeEffectViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public TypeEffectViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView;
    }

    public void bind(TypeEffect typeEffect) {
      textView.setText(String.format("%1$s %2$s / %3$s",
          typeEffect.name,
          typeEffect.attackEffect,
          typeEffect.defenceEffect));
    }
  }
}
