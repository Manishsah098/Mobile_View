package com.example.atlantis;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import com.example.atlantis.model.Guest;
import com.example.atlantis.network.ApiCallback;
import com.example.atlantis.network.GuestCallback;
import com.example.atlantis.network.MockApiService;
import com.example.atlantis.utils.SessionManager;
import com.example.atlantis.utils.ValidationUtils;

public class CheckInActivity extends AppCompatActivity {

    private FrameLayout btnBack;
    private CardView cardPhoneInput;
    private CardView cardOtpVerification;
    private TextView countryCodeTextView;
    private LinearLayout checkInLanguageLayout;
    private TextView checkInLanguageTextView;
    private EditText phoneEditText;
    private TextView phoneErrorTextView;
    private AppCompatButton btnSendOtp;

    private TextView otpPhoneDisplayTextView;
    private TextView btnChangeNumber;
    private TextView otpErrorTextView;
    private TextView resendCodeTextView;
    private AppCompatButton btnVerifyOtp;
    private LinearLayout loadingLayout;
    private TextView loadingStatusTextView;

    private final String[] countryCodes = {
        "🇳🇵 +977 (Nepal)",
        "🇦🇪 +971 (UAE)",
        "🇸🇦 +966 (Saudi Arabia)",
        "🇬🇧 +44 (UK)",
        "🇺🇸 +1 (USA)",
        "🇮🇳 +91 (India)",
        "🇷🇺 +7 (Russia)",
        "🇨🇳 +86 (China)",
        "🇩🇪 +49 (Germany)",
        "🇫🇷 +33 (France)",
        "🇪🇸 +34 (Spain)",
        "🇮🇹 +39 (Italy)",
        "🇯🇵 +81 (Japan)",
        "🇰🇷 +82 (South Korea)",
        "🇹🇷 +90 (Turkey)",
        "🇳🇱 +31 (Netherlands)",
        "🇵🇹 +351 (Portugal)",
        "🇧🇷 +55 (Brazil)",
        "🇪🇬 +20 (Egypt)",
        "🇶🇦 +974 (Qatar)",
        "🇰🇼 +965 (Kuwait)",
        "🇴🇲 +968 (Oman)",
        "🇧🇭 +973 (Bahrain)",
        "🇨🇭 +41 (Switzerland)",
        "🇦🇺 +61 (Australia)",
        "🇸🇬 +65 (Singapore)",
        "🇵🇰 +92 (Pakistan)",
        "🇧🇩 +880 (Bangladesh)",
        "🇵🇭 +63 (Philippines)",
        "🇮🇩 +62 (Indonesia)",
        "🇻🇳 +84 (Vietnam)",
        "🇹🇭 +66 (Thailand)"
    };
    private int selectedCountryIndex = 0;
    private String selectedFlag = "🇳🇵";
    private String selectedDialCode = "+977";

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

    private EditText[] otpDigits;
    private String currentPhoneNumber = "";
    private CountDownTimer resendTimer;
    private boolean canResend = false;

    private MockApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        apiService = MockApiService.getInstance();
        sessionManager = SessionManager.getInstance(this);

        initViews();
        setupOtpInputs();
        setupListeners();

        com.example.atlantis.utils.TranslationManager.applyCheckInTranslations(this);
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardPhoneInput = findViewById(R.id.cardPhoneInput);
        cardOtpVerification = findViewById(R.id.cardOtpVerification);
        countryCodeTextView = findViewById(R.id.countryCodeTextView);
        checkInLanguageLayout = findViewById(R.id.checkInLanguageLayout);
        // Get the second child TextView (index 1) of the language layout which shows current language
        checkInLanguageTextView = (checkInLanguageLayout != null && checkInLanguageLayout.getChildCount() > 1)
                ? (TextView) checkInLanguageLayout.getChildAt(1)
                : null;
        phoneEditText = findViewById(R.id.phoneEditText);
        phoneErrorTextView = findViewById(R.id.phoneErrorTextView);
        btnSendOtp = findViewById(R.id.btnSendOtp);

        otpPhoneDisplayTextView = findViewById(R.id.otpPhoneDisplayTextView);
        btnChangeNumber = findViewById(R.id.btnChangeNumber);
        otpErrorTextView = findViewById(R.id.otpErrorTextView);
        resendCodeTextView = findViewById(R.id.resendCodeTextView);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        loadingLayout = findViewById(R.id.loadingLayout);
        loadingStatusTextView = findViewById(R.id.loadingStatusTextView);

        otpDigits = new EditText[]{
            findViewById(R.id.otpDigit1),
            findViewById(R.id.otpDigit2),
            findViewById(R.id.otpDigit3),
            findViewById(R.id.otpDigit4),
            findViewById(R.id.otpDigit5),
            findViewById(R.id.otpDigit6)
        };
    }

    private void setupOtpInputs() {
        for (int i = 0; i < otpDigits.length; i++) {
            final int index = i;

            otpDigits[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        if (index < otpDigits.length - 1) {
                            otpDigits[index + 1].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            otpDigits[i].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (otpDigits[index].getText().toString().isEmpty() && index > 0) {
                            otpDigits[index - 1].requestFocus();
                            otpDigits[index - 1].setText("");
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardOtpVerification.getVisibility() == View.VISIBLE) {
                    showPhoneInputState();
                } else {
                    finish();
                }
            }
        });

        countryCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryCodeDialog();
            }
        });

        checkInLanguageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendOtp();
            }
        });

        btnChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneInputState();
            }
        });

        resendCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canResend) {
                    handleSendOtp();
                }
            }
        });

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVerifyOtp();
            }
        });
    }

    private void handleSendOtp() {
        String phone = phoneEditText.getText().toString().trim();
        String validationError = ValidationUtils.validatePhoneNumber(phone);

        if (validationError != null) {
            phoneErrorTextView.setText(validationError);
            phoneErrorTextView.setVisibility(View.VISIBLE);
            return;
        }

        phoneErrorTextView.setVisibility(View.GONE);
        currentPhoneNumber = selectedFlag + " " + selectedDialCode + " " + phone;

        // Show loading state
        setLoadingState(true, "Sending WhatsApp verification code...");

        apiService.sendOtp(currentPhoneNumber, new ApiCallback() {
            @Override
            public void onSuccess(String message) {
                setLoadingState(false, null);
                Toast.makeText(CheckInActivity.this, message, Toast.LENGTH_LONG).show();
                showOtpVerificationState();
                startResendTimer();
            }

            @Override
            public void onError(String error) {
                setLoadingState(false, null);
                phoneErrorTextView.setText(error);
                phoneErrorTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleVerifyOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        for (EditText editText : otpDigits) {
            otpBuilder.append(editText.getText().toString().trim());
        }

        String otp = otpBuilder.toString();
        String validationError = ValidationUtils.validateOtp(otp);

        if (validationError != null) {
            otpErrorTextView.setText(validationError);
            otpErrorTextView.setVisibility(View.VISIBLE);
            return;
        }

        otpErrorTextView.setVisibility(View.GONE);
        setLoadingState(true, "Verifying code...");

        apiService.verifyOtp(currentPhoneNumber, otp, new ApiCallback() {
            @Override
            public void onSuccess(String message) {
                loadingStatusTextView.setText(R.string.fetching_profile);

                // Fetch guest profile
                String mockToken = "ATR-TOKEN-AUTHENTICATED";
                apiService.getGuestProfile(mockToken, new GuestCallback() {
                    @Override
                    public void onSuccess(Guest guest) {
                        setLoadingState(false, null);
                        Toast.makeText(CheckInActivity.this, R.string.verification_successful, Toast.LENGTH_SHORT).show();

                        // Save session
                        sessionManager.saveGuestSession(mockToken, guest);

                        // Navigate to Guest Dashboard
                        Intent intent = new Intent(CheckInActivity.this, GuestDashboardActivity.class);
                        intent.putExtra("guest_data", guest);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        setLoadingState(false, null);
                        otpErrorTextView.setText(error);
                        otpErrorTextView.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(String error) {
                setLoadingState(false, null);
                otpErrorTextView.setText(error);
                otpErrorTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showPhoneInputState() {
        cardPhoneInput.setVisibility(View.VISIBLE);
        cardOtpVerification.setVisibility(View.GONE);
        if (resendTimer != null) {
            resendTimer.cancel();
        }
    }

    private void showOtpVerificationState() {
        cardPhoneInput.setVisibility(View.GONE);
        cardOtpVerification.setVisibility(View.VISIBLE);
        otpPhoneDisplayTextView.setText(currentPhoneNumber);

        // Clear OTP inputs
        for (EditText editText : otpDigits) {
            editText.setText("");
        }
        otpDigits[0].requestFocus();
        otpErrorTextView.setVisibility(View.GONE);
    }

    private void startResendTimer() {
        canResend = false;
        if (resendTimer != null) {
            resendTimer.cancel();
        }

        resendTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                resendCodeTextView.setText(getString(R.string.btn_resend_code, seconds));
                resendCodeTextView.setTextColor(getResources().getColor(R.color.text_secondary));
            }

            @Override
            public void onFinish() {
                canResend = true;
                resendCodeTextView.setText(R.string.btn_resend_now);
                resendCodeTextView.setTextColor(getResources().getColor(R.color.bright_gold));
            }
        }.start();
    }

    private void setLoadingState(boolean isLoading, String message) {
        if (isLoading) {
            loadingLayout.setVisibility(View.VISIBLE);
            if (message != null) {
                loadingStatusTextView.setText(message);
            }
            btnSendOtp.setEnabled(false);
            btnVerifyOtp.setEnabled(false);
        } else {
            loadingLayout.setVisibility(View.GONE);
            btnSendOtp.setEnabled(true);
            btnVerifyOtp.setEnabled(true);
        }
    }

    private void showCountryCodeDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Select Country Code");
        builder.setSingleChoiceItems(countryCodes, selectedCountryIndex, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                selectedCountryIndex = which;
                String selected = countryCodes[which];
                // Extract flag and dial code e.g. "🇦🇪 +971"
                String[] parts = selected.split(" ");
                if (parts.length >= 2) {
                    selectedFlag = parts[0];
                    selectedDialCode = parts[1];
                    countryCodeTextView.setText(selectedFlag + " " + selectedDialCode + " ▼");
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showLanguageDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.select_language);
        builder.setSingleChoiceItems(languages, selectedLanguageIndex, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                selectedLanguageIndex = which;
                String shortName = languages[which].split(" ")[0];
                // Update checkIn language display
                if (checkInLanguageTextView != null) {
                    checkInLanguageTextView.setText(shortName + " ▼");
                }
                
                // 1. Save and apply dynamic text translations
                com.example.atlantis.utils.TranslationManager.setLanguage(CheckInActivity.this, langCodes[which]);
                com.example.atlantis.utils.TranslationManager.applyCheckInTranslations(CheckInActivity.this);

                // 2. Apply Locale change
                com.example.atlantis.utils.LocaleHelper.setLocale(CheckInActivity.this, langCodes[which]);
                
                Toast.makeText(CheckInActivity.this, getString(R.string.toast_language_set) + " " + languages[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) {
            resendTimer.cancel();
        }
    }
}
