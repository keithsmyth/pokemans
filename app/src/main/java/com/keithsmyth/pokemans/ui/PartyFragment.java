package com.keithsmyth.pokemans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.adapter.PartyAdapter;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Party;

/**
 * @author keithsmyth
 */
public class PartyFragment extends Fragment {

  public static final int PICK_REQUEST_CODE = 1;
  private View emptyView;
  private RecyclerView partyListView;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_party, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    switch (id) {
      case R.id.action_lookup:
        Intent intent = PickActivity.forLookup(getActivity());
        startActivity(intent);
        return true;
      case R.id.action_clear:
        App.getPokemonData().clearParty();
        refreshParty();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

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
        Intent intent = PickActivity.forParty(getActivity());
        startActivityForResult(intent, PICK_REQUEST_CODE);
      }
    });

    return view;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      Party.Member member = Party.Member.fromJson(data.getStringExtra(PickActivity.POKEMON_KEY));
      App.getPokemonData().addToParty(member);
      refreshParty();
    }
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
