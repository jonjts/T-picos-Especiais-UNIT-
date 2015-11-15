package br.com.unit.tec.unitplus.util;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.unit.tec.unitplus.constants.Constants;

/**
 * Created by jon_j on 24/10/2015.
 */
public class Util {

    public static void saveUserName(String userName, Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Constants.PREFERENCE_USER_NAME, userName);
        edit.commit();
    }

    public static String getUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return pref.getString(Constants.PREFERENCE_USER_NAME, "");
    }
}
