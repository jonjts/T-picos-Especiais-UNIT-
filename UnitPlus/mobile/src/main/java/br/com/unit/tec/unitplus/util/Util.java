package br.com.unit.tec.unitplus.util;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.unit.tec.unitplus.constants.Constants;

/**
 * Created by jon_j on 24/10/2015.
 */
public class Util {

    public static void saveUserId(Long id, Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putLong(Constants.PREFERENCE_ID, id);
        edit.commit();
    }

    public static Long getUserId(Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return pref.getLong(Constants.PREFERENCE_ID, -1);
    }

    public static void clearSession(Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putLong(Constants.PREFERENCE_ID, -1);
        edit.commit();
    }
}
