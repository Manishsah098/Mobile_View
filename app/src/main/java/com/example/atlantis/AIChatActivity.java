package com.example.atlantis;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atlantis.adapter.ChatMessageAdapter;
import com.example.atlantis.model.ChatMessage;
import com.example.atlantis.model.Guest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AIChatActivity extends AppCompatActivity {

    private FrameLayout btnChatBack;
    private RecyclerView chatRecyclerView;
    private EditText chatEditText;
    private FrameLayout btnSendChatMessage;

    private TextView chipSpaMenu;
    private TextView chipInRoomDining;
    private TextView chipButler;
    private TextView chipPoolCabana;

    private ChatMessageAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private Guest currentGuest;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        if (getIntent().hasExtra("guest_data")) {
            currentGuest = (Guest) getIntent().getSerializableExtra("guest_data");
        }
        if (currentGuest == null) {
            currentGuest = new Guest("GST-98421", "Mr. Aman Singh", "+971 50 123 4567", "305", "Deluxe Ocean View", "Jul 26, 2026", "Jul 31, 2026", "guest_avatar", "ATR-305-2026");
        }

        initViews();
        setupChatRecycler();
        setupListeners();

        // Check if opened with initial query
        if (getIntent().hasExtra("initial_query")) {
            String initialQuery = getIntent().getStringExtra("initial_query");
            if (initialQuery != null && !initialQuery.isEmpty()) {
                sendMessage(initialQuery);
            }
        }
    }

    private void initViews() {
        btnChatBack = findViewById(R.id.btnChatBack);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatEditText = findViewById(R.id.chatEditText);
        btnSendChatMessage = findViewById(R.id.btnSendChatMessage);

        chipSpaMenu = findViewById(R.id.chipSpaMenu);
        chipInRoomDining = findViewById(R.id.chipInRoomDining);
        chipButler = findViewById(R.id.chipButler);
        chipPoolCabana = findViewById(R.id.chipPoolCabana);
    }

    private void setupChatRecycler() {
        messageList = new ArrayList<>();
        
        // Initial AI welcome message
        String welcomeText = "Hello " + currentGuest.getName() + "! I am your AI Royal Concierge. How may I elevate your stay at Atlantis The Royal today?";
        messageList.add(new ChatMessage("msg_0", welcomeText, "AI Concierge", getCurrentTime(), true));

        chatAdapter = new ChatMessageAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void setupListeners() {
        btnChatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = chatEditText.getText().toString().trim();
                if (!text.isEmpty()) {
                    sendMessage(text);
                    chatEditText.setText("");
                }
            }
        });

        chipSpaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("Can you share the Awaken Spa treatment menu and availability?");
            }
        });

        chipInRoomDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("What are the signature in-room dining options tonight?");
            }
        });

        chipButler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("I would like to request my Royal Butler for garment pressing and unpacking.");
            }
        });

        chipPoolCabana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("Can I reserve a private VIP cabana at the Cloud 22 infinity pool?");
            }
        });
    }

    private void sendMessage(String userText) {
        // Add User message
        ChatMessage userMsg = new ChatMessage(
            "msg_" + System.currentTimeMillis(),
            userText,
            currentGuest.getName(),
            getCurrentTime(),
            false
        );
        messageList.add(userMsg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);

        // Simulate AI response after delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateAiResponse(userText);
            }
        }, 800);
    }

    private void generateAiResponse(String query) {
        String lower = query.toLowerCase(Locale.ROOT);
        String response;

        if (lower.contains("spa") || lower.contains("massage") || lower.contains("wellness")) {
            response = "The Awaken Spa is located on the 4th level. Our signature 90-minute 24K Gold Royal Hammam and Marine Collagen Facial have openings today at 3:00 PM and 5:30 PM. Would you like me to reserve a session for Room " + currentGuest.getRoomNumber() + "?";
        } else if (lower.contains("dining") || lower.contains("food") || lower.contains("dinner") || lower.contains("lunch") || lower.contains("special") || lower.contains("menu")) {
            response = "Delighted to assist, " + currentGuest.getName() + ". Our Today's Special offers 20% OFF across in-room dining. Michelin star recommendations tonight include the Caviar Tartlet, Wagyu A5 Ribeye, and Royal Valrhona Chocolate Soufflé. Shall I place an order for Room " + currentGuest.getRoomNumber() + "?";
        } else if (lower.contains("butler") || lower.contains("room service") || lower.contains("housekeeping") || lower.contains("unpacking")) {
            response = "Your dedicated Royal Butler has been notified and will arrive at Room " + currentGuest.getRoomNumber() + " within 10 minutes to assist you with garment pressing, bespoke requests, and tea service.";
        } else if (lower.contains("cabana") || lower.contains("pool") || lower.contains("cloud 22")) {
            response = "Cloud 22 Infinity Pool VIP Cabanas feature submerged sunbeds, panoramic Palm Island views, and dedicated cocktail mixologists. I have held Cabana #7 for you until 6:00 PM today.";
        } else {
            response = "Certainly, " + currentGuest.getName() + "! I have noted your request for Room " + currentGuest.getRoomNumber() + ". Our Royal Concierge team is attending to this immediately to ensure your stay is flawless.";
        }

        ChatMessage aiMsg = new ChatMessage(
            "msg_" + System.currentTimeMillis(),
            response,
            "AI Concierge",
            getCurrentTime(),
            true
        );
        messageList.add(aiMsg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messageList.size() - 1);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }
}
