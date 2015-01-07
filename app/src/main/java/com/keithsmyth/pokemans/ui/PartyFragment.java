package com.keithsmyth.pokemans.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.PartyAdapter;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Party;

import java.util.Random;

/**
 * @author keithsmyth
 */
public class PartyFragment extends Fragment {

  private View emptyView;
  private RecyclerView partyListView;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                               Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_party, container,
        false);

    emptyView = view.findViewById(R.id.txt_empty);

    partyListView = (RecyclerView) view.findViewById(R.id.lst_party);
    partyListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    partyListView.setHasFixedSize(true);

    view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        // add a random id
        // todo: implement add to party
        Party.Member member = new Party.Member();
        member.id = new Random().nextLong();
        member.name = String.valueOf(member.id);
        App.getPokemonData().addToParty(member);
        refreshParty();
      }
    });

    return view;
  }

  @Override public void onStart() {
    super.onStart();
    refreshParty();
  }

  private void refreshParty() {
    App.getPokemonData().getParty(new Callback<Party>() {
      @Override public void onSuccess(Party model) {
        setupParty(model);
      }

      @Override public void onFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void setupParty(Party party) {
    partyListView.setAdapter(new PartyAdapter(party));
    emptyView.setVisibility(party.memberList.isEmpty() ? View.VISIBLE : View.GONE);
  }

}
