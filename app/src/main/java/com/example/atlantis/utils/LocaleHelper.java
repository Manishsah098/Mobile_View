package com.example.atlantis.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import java.util.Locale;

public class LocaleHelper {

    private static final String PREF_LANGUAGE = "selected_language";
    private static final String PREF_LANG_CODE = "selected_lang_code";

    public static Context setLocale(Context context, String languageCode) {
        saveLanguageCode(context, languageCode);
        return updateResources(context, languageCode);
    }

    public static Context onAttach(Context context) {
        String lang = getPersistedLanguageCode(context, "en");
        return setLocale(context, lang);
    }

    public static String getPersistedLanguageCode(Context context, String defaultLanguage) {
        return context.getSharedPreferences("AtlantisLocalePref", Context.MODE_PRIVATE)
                .getString(PREF_LANG_CODE, defaultLanguage);
    }

    private static void saveLanguageCode(Context context, String languageCode) {
        context.getSharedPreferences("AtlantisLocalePref", Context.MODE_PRIVATE)
                .edit()
                .putString(PREF_LANG_CODE, languageCode)
                .apply();
    }

    private static Context updateResources(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            return context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
            return context;
        }
    }
}
