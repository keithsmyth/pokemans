package com.keithsmyth.pokemans.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keithsmyth.pokemans.R;

/**
 * @author keithsmyth
 */
public class PartyFragment extends Fragment {

  private RecyclerView partyList;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_party, container,
        false);

    partyList = (RecyclerView) view.findViewById(R.id.lst_party);
    partyList.setLayoutManager(new LinearLayoutManager(getActivity()));
    partyList.setHasFixedSize(true);

    return view;
  }
}
