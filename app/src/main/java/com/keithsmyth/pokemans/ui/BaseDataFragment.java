package com.keithsmyth.pokemans.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.keithsmyth.pokemans.R;
import com.keithsmyth.pokemans.data.Callback;

/**
 * @author keithsmyth
 */
public abstract class BaseDataFragment<T> extends Fragment
    implements Callback<T> {

  private static final String EXTRA_MODEL = "extra-model";
  protected ProgressBar progressBar;
  protected T model;

  protected abstract Class<T> getModelType();

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (savedInstanceState != null) {
      String json = savedInstanceState.getString(EXTRA_MODEL);
      if (!TextUtils.isEmpty(json)) {
        model = new Gson().fromJson(json, getModelType());
      }
    }

    if (model == null) {
      setLoading(true);
      requestData();
    } else {
      populate(model);
    }
  }

  protected abstract void requestData();

  @Override public void onSuccess(T model) {
    if (getView() == null) return;
    setLoading(false);
    this.model = model;
    populate(model);
  }

  protected abstract void populate(T model);

  @Override public void onFail(String msg) {
    if (getView() == null) return;
    setLoading(false);
  }

  protected void setLoading(boolean isLoading) {
    if (getView() == null) return;
    if (progressBar == null) {
      progressBar = (ProgressBar) getView().findViewById(R.id.progress);
    }
    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (model != null) {
      outState.putString(EXTRA_MODEL, new Gson().toJson(model));
    }
  }
}
