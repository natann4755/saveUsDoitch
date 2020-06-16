package com.example.saveus.user_manager;

import android.content.Context;


public class UserManager {

    public static void setToken(String token, Context context) {

        ConfigFile.getInstance(context).setProperty(Properties.KEY_TOKEN, token);

    }

    public static String getToken(Context context) {

        return ConfigFile.getInstance(context)
                .getProperty(Properties.KEY_TOKEN, Properties.DEFAULT_VALUE);

    }

    public static void setUser(String user, Context context) {

        ConfigFile.getInstance(context).setProperty(Properties.KEY_USER, user);

    }

    public static String getUser(Context context) {

        return ConfigFile.getInstance(context)
                .getProperty(Properties.KEY_USER, Properties.DEFAULT_VALUE);

    }


    public static void setFirstTimeInsideTheApp(boolean isFirstTime, Context context) {

        ConfigFile.getInstance(context).setProperty(Properties.FIRST_TIME_INSIDE_APP, isFirstTime);

    }


    public static boolean getFirstTimeInsideTheApp(Context context) {

        return ConfigFile.getInstance(context)
                .getProperty(Properties.FIRST_TIME_INSIDE_APP, Properties.DEFAULT_FIRST_TIME_INSIDE_APP);
    }



}
