package com.example.atlantis.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;
import com.example.atlantis.R;
import java.util.HashMap;
import java.util.Map;

public class TranslationManager {

    private static final String PREF_NAME = "AtlantisTranslations";
    private static final String KEY_LANG_CODE = "lang_code";

    public static void setLanguage(Context context, String langCode) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(KEY_LANG_CODE, langCode).apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_LANG_CODE, "en");
    }

    public static void applyWelcomeTranslations(Activity activity) {
        String lang = getLanguage(activity);
        Map<String, String> t = getTranslationMap(lang);

        TextView tvHeading = activity.findViewById(R.id.welcomeHeading);
        if (tvHeading == null) {
            // Find by finding second/third TextView in welcome layout if ID not set
            tvHeading = findTextViewByText(activity, "Welcome");
        }
        
        TextView tvSub = activity.findViewById(R.id.welcomeSubtitle);
        if (tvSub == null) {
            tvSub = findTextViewByText(activity, "Your luxury stay begins here");
        }

        TextView tvCheckInTitle = activity.findViewById(R.id.checkInTitleText);
        if (tvCheckInTitle == null) {
            tvCheckInTitle = findTextViewByText(activity, "Check-in");
        }

        TextView tvCheckInDesc = activity.findViewById(R.id.checkInDescText);
        if (tvCheckInDesc == null) {
            tvCheckInDesc = findTextViewByText(activity, "Already have a booking?");
        }

        TextView tvDining = activity.findViewById(R.id.tvDining);
        TextView tvSpa = activity.findViewById(R.id.tvSpa);
        TextView tvServices = activity.findViewById(R.id.tvServices);
        TextView tvExperiences = activity.findViewById(R.id.tvExperiences);
        TextView tvTaglineStay = activity.findViewById(R.id.tvTaglineStay);
        TextView tvTaglineThanks = activity.findViewById(R.id.tvTaglineThanks);

        if (tvHeading != null && t.containsKey("welcome_heading")) tvHeading.setText(t.get("welcome_heading"));
        if (tvSub != null && t.containsKey("welcome_subtitle")) tvSub.setText(t.get("welcome_subtitle"));
        if (tvCheckInTitle != null && t.containsKey("checkin_title")) tvCheckInTitle.setText(t.get("checkin_title"));
        if (tvCheckInDesc != null && t.containsKey("checkin_card_desc")) tvCheckInDesc.setText(t.get("checkin_card_desc"));
        if (tvDining != null && t.containsKey("feature_dining")) tvDining.setText(t.get("feature_dining"));
        if (tvSpa != null && t.containsKey("feature_spa")) tvSpa.setText(t.get("feature_spa"));
        if (tvServices != null && t.containsKey("feature_services")) tvServices.setText(t.get("feature_services"));
        if (tvExperiences != null && t.containsKey("feature_experiences")) tvExperiences.setText(t.get("feature_experiences"));
        if (tvTaglineStay != null && t.containsKey("tagline_stay")) tvTaglineStay.setText(t.get("tagline_stay"));
        if (tvTaglineThanks != null && t.containsKey("tagline_thanks")) tvTaglineThanks.setText(t.get("tagline_thanks"));
    }

    public static void applyCheckInTranslations(Activity activity) {
        String lang = getLanguage(activity);
        Map<String, String> t = getTranslationMap(lang);

        TextView tvHeading = activity.findViewById(R.id.checkInHeadingText);
        TextView tvSub = activity.findViewById(R.id.checkInSubtitleText);
        TextView tvVerifyTitle = activity.findViewById(R.id.verifyMobileTitleText);
        Button btnSendOtp = activity.findViewById(R.id.btnSendOtp);

        if (tvHeading != null && t.containsKey("checkin_heading")) tvHeading.setText(t.get("checkin_heading"));
        if (tvSub != null && t.containsKey("checkin_subtitle")) tvSub.setText(t.get("checkin_subtitle"));
        if (tvVerifyTitle != null && t.containsKey("verify_mobile_title")) tvVerifyTitle.setText(t.get("verify_mobile_title"));
        if (btnSendOtp != null && t.containsKey("btn_send_code_whatsapp")) btnSendOtp.setText(t.get("btn_send_code_whatsapp"));
    }

    private static TextView findTextViewByText(Activity activity, String substring) {
        // Fallback helper to search view hierarchy
        return null;
    }

    public static Map<String, String> getTranslationMap(String lang) {
        Map<String, String> map = new HashMap<>();
        if ("ne".equals(lang)) {
            map.put("welcome_heading", "स्वागत छ");
            map.put("welcome_subtitle", "तपाईंको विलासी बसाइ यहाँ सुरु हुन्छ");
            map.put("checkin_title", "चेक-इन");
            map.put("checkin_card_desc", "पहिले नै बुकिंग छ?\nचेक-इन गर्नुहोस्।");
            map.put("feature_dining", "स्वादिष्ट\nभोजन");
            map.put("feature_spa", "स्पा र\nवेलनेस");
            map.put("feature_services", "विश्वस्तरीय\nसेवाहरू");
            map.put("feature_experiences", "अविस्मरणीय\nअनुभवहरू");
            map.put("tagline_stay", "हामी तपाईंको बसाइ उत्कृष्ट बनाउन यहाँ छौं।");
            map.put("tagline_thanks", "धन्यवाद!");
            map.put("checkin_heading", "चेक-इन");
            map.put("checkin_subtitle", "आउनुहोस्, चेक-इन गरौं");
            map.put("verify_mobile_title", "आफ्नो मोबाइल नम्बर प्रमाणीकरण गर्नुहोस्");
            map.put("btn_send_code_whatsapp", "व्हाट्सएपमा कोड पठाउनुहोस्");
        } else if ("ar".equals(lang)) {
            map.put("welcome_heading", "أهلاً بك");
            map.put("welcome_subtitle", "إقامتك الفاخرة تبدأ هنا");
            map.put("checkin_title", "تسجيل الوصول");
            map.put("checkin_card_desc", "لديك حجز بالفعل؟\nسجل وصولك الآن.");
            map.put("feature_dining", "عشاء\nفاخر");
            map.put("feature_spa", "سبا\nوعافية");
            map.put("feature_services", "خدمات\nعالمية");
            map.put("feature_experiences", "تجارب\nلا تُنسى");
            map.put("tagline_stay", "نحن هنا لنجعل إقامتك استثنائية.");
            map.put("tagline_thanks", "شكراً لك!");
            map.put("checkin_heading", "تسجيل الوصول");
            map.put("checkin_subtitle", "لنقم بتسجيل وصولك");
            map.put("verify_mobile_title", "تأكيد رقم هاتفك المحمول");
            map.put("btn_send_code_whatsapp", "إرسال الرمز عبر WhatsApp");
        } else if ("fr".equals(lang)) {
            map.put("welcome_heading", "Bienvenue");
            map.put("welcome_subtitle", "Votre séjour de luxe commence ici");
            map.put("checkin_title", "Enregistrement");
            map.put("checkin_card_desc", "Vous avez une réservation ?\nEnregistrez-vous.");
            map.put("feature_dining", "Gastronomie\nExquise");
            map.put("feature_spa", "Spa &\nBien-être");
            map.put("feature_services", "Services\nd'Élite");
            map.put("feature_experiences", "Expériences\nUniques");
            map.put("tagline_stay", "Nous sommes là pour rendre votre séjour exceptionnel.");
            map.put("tagline_thanks", "Merci !");
            map.put("checkin_heading", "Enregistrement");
            map.put("checkin_subtitle", "Procédons à votre enregistrement");
            map.put("verify_mobile_title", "Vérifiez votre numéro de mobile");
            map.put("btn_send_code_whatsapp", "Envoyer le code sur WhatsApp");
        } else if ("ru".equals(lang)) {
            map.put("welcome_heading", "Добро пожаловать");
            map.put("welcome_subtitle", "Ваш роскошный отдых начинается здесь");
            map.put("checkin_title", "Регистрация");
            map.put("checkin_card_desc", "Уже есть бронирование?\nЗарегистрируйтесь.");
            map.put("feature_dining", "Изысканная\nкухня");
            map.put("feature_spa", "Спа и\nвелнес");
            map.put("feature_services", "Мировой\nсервис");
            map.put("feature_experiences", "Незабываемые\nвпечатления");
            map.put("tagline_stay", "Мы здесь, чтобы сделать ваш отдых незабываемым.");
            map.put("tagline_thanks", "Спасибо!");
            map.put("checkin_heading", "Регистрация");
            map.put("checkin_subtitle", "Давайте зарегистрируем вас");
            map.put("verify_mobile_title", "Подтвердите номер мобильного");
            map.put("btn_send_code_whatsapp", "Отправить код в WhatsApp");
        } else if ("zh".equals(lang)) {
            map.put("welcome_heading", "欢迎光临");
            map.put("welcome_subtitle", "您的奢华入住之旅由此开始");
            map.put("checkin_title", "办理入住");
            map.put("checkin_card_desc", "已有预订？\n请办理入住。");
            map.put("feature_dining", "美味\n餐饮");
            map.put("feature_spa", "水疗与\n康体");
            map.put("feature_services", "世界级\n服务");
            map.put("feature_experiences", "难忘\n体验");
            map.put("tagline_stay", "我们将竭诚为您打造非凡的入住体验。");
            map.put("tagline_thanks", "谢谢！");
            map.put("checkin_heading", "办理入住");
            map.put("checkin_subtitle", "让我们为您办理入住");
            map.put("verify_mobile_title", "验证您的手机号码");
            map.put("btn_send_code_whatsapp", "在WhatsApp发送验证码");
        } else if ("de".equals(lang)) {
            map.put("welcome_heading", "Willkommen");
            map.put("welcome_subtitle", "Ihr Luxusaufenthalt beginnt hier");
            map.put("checkin_title", "Check-in");
            map.put("checkin_card_desc", "Haben Sie bereits gebucht?\nChecken Sie ein.");
            map.put("feature_dining", "Köstliche\nGastronomie");
            map.put("feature_spa", "Spa &\nWellness");
            map.put("feature_services", "Weltklasse\nService");
            map.put("feature_experiences", "Unvergessliche\nErlebnisse");
            map.put("tagline_stay", "Wir sind hier, um Ihren Aufenthalt außergewöhnlich zu machen.");
            map.put("tagline_thanks", "Vielen Dank!");
            map.put("checkin_heading", "Check-in");
            map.put("checkin_subtitle", "Lassen Sie uns Sie einchecken");
            map.put("verify_mobile_title", "Bestätigen Sie Ihre Handynummer");
            map.put("btn_send_code_whatsapp", "Code auf WhatsApp senden");
        } else if ("es".equals(lang)) {
            map.put("welcome_heading", "Bienvenido");
            map.put("welcome_subtitle", "Su estancia de lujo comienza aquí");
            map.put("checkin_title", "Check-in");
            map.put("checkin_card_desc", "¿Ya tiene reserva?\nInicie sesión.");
            map.put("feature_dining", "Gastronomía\nExquisita");
            map.put("feature_spa", "Spa y\nBienestar");
            map.put("feature_services", "Servicios de\nClase Mundial");
            map.put("feature_experiences", "Experiencias\nMemorables");
            map.put("tagline_stay", "Estamos aquí para hacer que su estancia sea excepcional.");
            map.put("tagline_thanks", "¡Gracias!");
            map.put("checkin_heading", "Check-in");
            map.put("checkin_subtitle", "Vamos a registrarle");
            map.put("verify_mobile_title", "Verifique su número de móvil");
            map.put("btn_send_code_whatsapp", "Enviar código por WhatsApp");
        } else if ("it".equals(lang)) {
            map.put("welcome_heading", "Benvenuto");
            map.put("welcome_subtitle", "Il tuo soggiorno di lusso inizia qui");
            map.put("checkin_title", "Check-in");
            map.put("checkin_card_desc", "Hai già una prenotazione?\nFai il check-in.");
            map.put("feature_dining", "Cucina\nRaffinata");
            map.put("feature_spa", "Spa &\nBenessere");
            map.put("feature_services", "Servizi di\nClasse Mondiale");
            map.put("feature_experiences", "Esperienze\nMemorabili");
            map.put("tagline_stay", "Siamo qui per rendere il tuo soggiorno eccezionale.");
            map.put("tagline_thanks", "Grazie!");
            map.put("checkin_heading", "Check-in");
            map.put("checkin_subtitle", "Effettuiamo il check-in");
            map.put("verify_mobile_title", "Verifica il tuo numero di cellulare");
            map.put("btn_send_code_whatsapp", "Invia codice su WhatsApp");
        } else if ("ja".equals(lang)) {
            map.put("welcome_heading", "ようこそ");
            map.put("welcome_subtitle", "ラグジュアリーな滞在がここから始まります");
            map.put("checkin_title", "チェックイン");
            map.put("checkin_card_desc", "ご予約はお済みですか？\nチェックインしてください。");
            map.put("feature_dining", "極上の\nダイニング");
            map.put("feature_spa", "スパ＆\nウェルネス");
            map.put("feature_services", "ワールドクラス\nサービス");
            map.put("feature_experiences", "思い出に残る\n体験");
            map.put("tagline_stay", "お客様の滞在を特別なものにするために。");
            map.put("tagline_thanks", "ありがとうございます！");
            map.put("checkin_heading", "チェックイン");
            map.put("checkin_subtitle", "チェックインを開始します");
            map.put("verify_mobile_title", "携帯電話番号の確認");
            map.put("btn_send_code_whatsapp", "WhatsAppでコードを送信");
        } else if ("ko".equals(lang)) {
            map.put("welcome_heading", "환영합니다");
            map.put("welcome_subtitle", "럭셔리한 휴식이 여기서 시작됩니다");
            map.put("checkin_title", "체크인");
            map.put("checkin_card_desc", "이미 예약하셨나요?\n체크인하세요.");
            map.put("feature_dining", "파인\n다이닝");
            map.put("feature_spa", "스파 &\n웰니스");
            map.put("feature_services", "월드 클래스\n서비스");
            map.put("feature_experiences", "잊지 못할\n경험");
            map.put("tagline_stay", "특별한 휴식을 선사합니다.");
            map.put("tagline_thanks", "감사합니다!");
            map.put("checkin_heading", "체크인");
            map.put("checkin_subtitle", "체크인을 진행합니다");
            map.put("verify_mobile_title", "휴대폰 번호 확인");
            map.put("btn_send_code_whatsapp", "WhatsApp으로 코드 전송");
        } else if ("tr".equals(lang)) {
            map.put("welcome_heading", "Hoş Geldiniz");
            map.put("welcome_subtitle", "Lüks konaklamanız burada başlamaktadır");
            map.put("checkin_title", "Giriş Yap");
            map.put("checkin_card_desc", "Rezervasyonunuz var mı?\nGiriş yapın.");
            map.put("feature_dining", "Leziz\nYemekler");
            map.put("feature_spa", "Spa &\nYaşam");
            map.put("feature_services", "Dünya Standartlarında\nHizmet");
            map.put("feature_experiences", "Unutulmaz\nDeneyimler");
            map.put("tagline_stay", "Konaklamanızı olağanüstü kılmak için buradayız.");
            map.put("tagline_thanks", "Teşekkür Ederiz!");
            map.put("checkin_heading", "Giriş Yap");
            map.put("checkin_subtitle", "Girişinizi yapalım");
            map.put("verify_mobile_title", "Cep Telefonunuzu Doğrulayın");
            map.put("btn_send_code_whatsapp", "WhatsApp ile Kod Gönder");
        } else if ("hi".equals(lang)) {
            map.put("welcome_heading", "स्वागत है");
            map.put("welcome_subtitle", "आपका लक्जरी प्रवास यहाँ शुरू होता है");
            map.put("checkin_title", "चेक-इन");
            map.put("checkin_card_desc", "क्या आपकी बुकिंग है?\nचेक-इन करें।");
            map.put("feature_dining", "स्वादिष्ट\nभोजन");
            map.put("feature_spa", "स्पा और\nवेलनेस");
            map.put("feature_services", "विश्वस्तरीय\nसेवाएं");
            map.put("feature_experiences", "अविस्मरणीय\nअनुभव");
            map.put("tagline_stay", "हम आपके प्रवास को असाधारण बनाने के लिए यहाँ हैं।");
            map.put("tagline_thanks", "धन्यवाद!");
            map.put("checkin_heading", "चेक-इन");
            map.put("checkin_subtitle", "आइए आपका चेक-इन करें");
            map.put("verify_mobile_title", "अपना मोबाइल नंबर सत्यापित करें");
            map.put("btn_send_code_whatsapp", "व्हाट्सएप पर कोड भेजें");
        } else {
            // English default
            map.put("welcome_heading", "Welcome");
            map.put("welcome_subtitle", "Your luxury stay begins here");
            map.put("checkin_title", "Check-in");
            map.put("checkin_card_desc", "Already have a booking?\nCheck-in and access your stay.");
            map.put("feature_dining", "Delicious\nDining");
            map.put("feature_spa", "Spa &\nWellness");
            map.put("feature_services", "World Class\nServices");
            map.put("feature_experiences", "Memorable\nExperiences");
            map.put("tagline_stay", "We're here to make your stay exceptional.");
            map.put("tagline_thanks", "Thank You!");
            map.put("checkin_heading", "Check-in");
            map.put("checkin_subtitle", "Let's get you checked in");
            map.put("verify_mobile_title", "Verify Your Mobile Number");
            map.put("btn_send_code_whatsapp", "Send Code on WhatsApp");
        }
        return map;
    }
}
