package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.TypeEffectAdapter;
import com.keithsmyth.pokemans.model.Pokemon;

import java.util.List;

/**
 * @author keithsmyth
 */
public class TypesListFragment extends Fragment {

  private TextView typeText;
  private RecyclerView typeRecycleView;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_poketype_list, container, false);
    typeText = (TextView) view.findViewById(R.id.txt_types);
    typeRecycleView = (RecyclerView) view.findViewById(R.id.lst_types);
    typeRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3)); //todo: dimens
    return view;
  }

  public void loadTypes(List<Pokemon.PokeType> typeList) {
    // list names
    StringBuilder types = new StringBuilder();
    for (Pokemon.PokeType pokeType : typeList) {
      if (types.length() > 0) types.append(", ");
      types.append(pokeType.name);
    }
    typeText.setText(types.toString());
    // load type attackEffect
    final TypeEffectAdapter adapter = new TypeEffectAdapter(typeList);
    adapter.init(new TypeEffectAdapter.TypeEffectInitListener() {
      @Override public void onReady() {
        typeRecycleView.setAdapter(adapter);
      }
    });
  }
}
