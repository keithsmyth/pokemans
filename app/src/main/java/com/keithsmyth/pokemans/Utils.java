package com.keithsmyth.pokemans;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author keithsmyth
 */
public final class Utils {

  public static void closeKeyboard(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
        .INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
