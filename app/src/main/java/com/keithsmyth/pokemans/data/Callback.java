package com.keithsmyth.pokemans.data;

/**
 * @author keithsmyth
 */
public interface Callback<T> {
  public void onSuccess(T model);

  public void onFail(String msg);
}
