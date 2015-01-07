package com.keithsmyth.pokemans.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author keithsmyth
 */
public class PreferenceWrapper {

  private static final String PARTY_LIST_KEY = "party-list-key";
  private final SharedPreferences preferences;

  public PreferenceWrapper(Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public void setPartyList(String partyList) {
    preferences.edit().putString(PARTY_LIST_KEY, partyList).apply();
  }

  public String getPartyList() {
    return preferences.getString(PARTY_LIST_KEY, null);
  }
}
