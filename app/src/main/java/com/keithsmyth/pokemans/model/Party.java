package com.keithsmyth.pokemans.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keithsmyth
 */
public class Party {

  public static Party fromJson(String json) {
    return json == null ? new Party() : new Gson().fromJson(json, Party.class);
  }

  public final List<PartyMember> memberList;

  public Party() {
    memberList = new ArrayList<>();
  }

  public String getAsJson() {
    return new Gson().toJson(this);
  }

}
