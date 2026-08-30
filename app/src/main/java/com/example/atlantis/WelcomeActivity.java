package com.example.atlantis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.atlantis.utils.SessionManager;

public class WelcomeActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView languageTextView;
    private final String[] languages = {
        "English", 
        "नेपाली (Nepali)",
        "العربية (Arabic)", 
        "Français (French)", 
        "Русский (Russian)", 
        "中文 (Mandarin)", 
        "Deutsch (German)", 
        "Español (Spanish)", 
        "Italiano (Italian)", 
        "日本語 (Japanese)",
        "한국어 (Korean)",
        "Türkçe (Turkish)",
        "हिन्दी (Hindi)",
        "Português (Portuguese)",
        "Nederlands (Dutch)",
        "Tagalog (Filipino)",
        "Tiếng Việt (Vietnamese)",
        "ไทย (Thai)",
        "اردو (Urdu)",
        "বাংলা (Bengali)"
    };

    private final String[] langCodes = {
        "en", "ne", "ar", "fr", "ru", "zh", "de", "es", "it", "ja",
        "ko", "tr", "hi", "pt", "nl", "tl", "vi", "th", "ur", "bn"
    };

    private int selectedLanguageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sessionManager = SessionManager.getInstance(this);

        initViews();
        com.example.atlantis.utils.TranslationManager.applyWelcomeTranslations(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        com.example.atlantis.utils.TranslationManager.applyWelcomeTranslations(this);
    }

    private void initViews() {
        languageTextView = findViewById(R.id.languageTextView);
        LinearLayout languageSelectorLayout = findViewById(R.id.languageSelectorLayout);
        CardView checkInCard = findViewById(R.id.checkInCard);

        // Set initial language text from stored language
        String currentLangCode = com.example.atlantis.utils.TranslationManager.getLanguage(this);
        for (int i = 0; i < langCodes.length; i++) {
            if (langCodes[i].equalsIgnoreCase(currentLangCode)) {
                selectedLanguageIndex = i;
                languageTextView.setText(languages[i].split(" ")[0] + " ▼");
                break;
            }
        }

        // Language selection dialog
        languageSelectorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        // Check-in card navigation
        checkInCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CheckInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_language);
        builder.setSingleChoiceItems(languages, selectedLanguageIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedLanguageIndex = which;
                String selectedLangName = languages[which].split(" ")[0];
                languageTextView.setText(selectedLangName + " ▼");
                
                // 1. Save and apply dynamic text translations
                com.example.atlantis.utils.TranslationManager.setLanguage(WelcomeActivity.this, langCodes[which]);
                com.example.atlantis.utils.TranslationManager.applyWelcomeTranslations(WelcomeActivity.this);

                // 2. Apply Locale change
                com.example.atlantis.utils.LocaleHelper.setLocale(WelcomeActivity.this, langCodes[which]);
                
                Toast.makeText(WelcomeActivity.this, getString(R.string.toast_language_set) + " " + languages[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
