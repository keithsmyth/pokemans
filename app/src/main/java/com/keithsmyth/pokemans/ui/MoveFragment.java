package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.data.Callback;
import com.keithsmyth.pokemans.model.Move;

/**
 * @author keithsmyth
 */
public class MoveFragment extends BaseDataFragment<Move> {

  private static final String EXTRA_API_PATH = "extra-api-path";

  public static MoveFragment create(String apiPath) {
    Bundle args = new Bundle();
    args.putString(EXTRA_API_PATH, apiPath);
    MoveFragment fragment = new MoveFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private TextView nameText;
  private TextView descriptionText;
  private TextView categoryText;
  private TextView powerText;
  private TextView ppText;
  private TextView accuracyText;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_move, container, false);
    nameText = (TextView) view.findViewById(R.id.txt_name);
    descriptionText = (TextView) view.findViewById(R.id.txt_description);
    categoryText = (TextView) view.findViewById(R.id.txt_category);
    powerText = (TextView) view.findViewById(R.id.txt_power);
    ppText = (TextView) view.findViewById(R.id.txt_pp);
    accuracyText = (TextView) view.findViewById(R.id.txt_accuracy);
    return view;
  }

  @Override protected Class<Move> getModelType() {
    return Move.class;
  }

  @Override protected void requestData() {
    String apiPath = getArguments().getString(EXTRA_API_PATH);
    App.getPokemonData().getMove(apiPath, this);
  }

  @Override protected void populate(Move move) {
    if (getView() == null) return;
    nameText.setText(move.name);
    descriptionText.setText(move.description);
    categoryText.setText(move.category);
    powerText.setText(String.valueOf(move.power));
    ppText.setText(String.valueOf(move.pp));
    accuracyText.setText(String.valueOf(move.accuracy));
  }
}
