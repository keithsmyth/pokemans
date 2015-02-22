package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.pokemans.App;
import com.keithsmyth.pokemans.R;
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
  private ViewGroup itemsLayout;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_move, container, false);
    nameText = (TextView) view.findViewById(R.id.txt_name);
    descriptionText = (TextView) view.findViewById(R.id.txt_description);
    itemsLayout = (ViewGroup) view.findViewById(R.id.layout_items);
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

    itemsLayout.removeAllViews();
    LayoutInflater inflater = LayoutInflater.from(getView().getContext());
    populateItem(inflater, itemsLayout, getString(R.string.move_category), move.category);
    populateItem(inflater, itemsLayout, getString(R.string.move_power), String.valueOf(move.power));
    populateItem(inflater, itemsLayout, getString(R.string.move_pp), String.valueOf(move.pp));
    populateItem(inflater, itemsLayout, getString(R.string.move_accuracy),
        String.valueOf(move.accuracy));
  }

  private void populateItem(LayoutInflater inflater, ViewGroup viewGroup, String title,
                            String value) {
    if (value == null) return;
    View view = inflater.inflate(R.layout.move_detail_item, viewGroup, false);
    ((TextView) view.findViewById(R.id.txt_title)).setText(title);
    ((TextView) view.findViewById(R.id.txt_value)).setText(value);
    viewGroup.addView(view);
  }
}
