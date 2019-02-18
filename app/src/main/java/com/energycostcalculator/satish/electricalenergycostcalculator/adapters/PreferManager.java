package com.energycostcalculator.satish.electricalenergycostcalculator.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import com.energycostcalculator.satish.electricalenergycostcalculator.models.BillModel;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.StabModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 4/2/16.
 */
public class PreferManager {

    // Shared preferences file name
    private static final String PREF_NAME = "CalPrice";


    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;


    public PreferManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }



    public void storeStab(ArrayList<StabModel> addtoCartArrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(addtoCartArrayList);
        editor.putString("stablist", json);
        editor.commit();
    }

    public ArrayList<StabModel> getStabs() {
        ArrayList<StabModel> getCartList;
        Gson gson = new Gson();
        String json = pref.getString("stablist", "");
        if (json.isEmpty()) {
            getCartList = new ArrayList<StabModel>();
        } else {
            Type type = new TypeToken<List<StabModel>>() {
            }.getType();
            getCartList = gson.fromJson(json, type);
        }
        return getCartList;
    }

    public void storeBills(ArrayList<BillModel> addtoCartArrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(addtoCartArrayList);
        editor.putString("storeBills", json);
        editor.commit();
    }

    public ArrayList<BillModel> getBills() {
        ArrayList<BillModel> getCartList;
        Gson gson = new Gson();
        String json = pref.getString("storeBills", "");
        if (json.isEmpty()) {
            getCartList = new ArrayList<BillModel>();
        } else {
            Type type = new TypeToken<List<BillModel>>() {
            }.getType();
            getCartList = gson.fromJson(json, type);
        }
        return getCartList;
    }
}
