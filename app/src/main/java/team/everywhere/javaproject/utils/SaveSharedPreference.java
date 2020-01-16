package team.everywhere.javaproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SaveSharedPreference {
    private static final String TAG = "SaveSharedPreference";

    private static final String PREF_USER_EMAIL = "userEmail";
    private static final String PREF_USER_PASSWORD = "userPassword";
    private static final String PREF_AUTO_LOGIN = "autoLogin";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserInfo(Context ctx, String userEmail, String userPassword) {
//    public static void setUserInfo(Context ctx, String userPhoneNumber, String userPassword) {
        Log.d(TAG, "setUserInfo: userEmail : " + userEmail + " has saved");
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, userEmail);
//        editor.putString(PREF_USER_PHONENUMBER, userPhoneNumber);
        editor.putString(PREF_USER_PASSWORD, userPassword);
        editor.apply();
    }

    public static void setAutoLogin(Context ctx, String isAuto) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_AUTO_LOGIN, isAuto);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static String getUserPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD, "");
    }

    public static String getAutoLogin(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_AUTO_LOGIN, "false");
    }


    // 저장된 모든 value 삭제
    public static void clearAll(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    //아이디 비밀번호 저장 기능만 해제
    public static void clearUserInfo(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_EMAIL);
        editor.remove(PREF_USER_PASSWORD);
        editor.apply();
    }

}
