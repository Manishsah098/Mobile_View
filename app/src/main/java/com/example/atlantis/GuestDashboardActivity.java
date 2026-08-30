package com.example.atlantis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atlantis.adapter.ServiceAdapter;
import com.example.atlantis.model.Guest;
import com.example.atlantis.model.Service;
import com.example.atlantis.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class GuestDashboardActivity extends AppCompatActivity {

    private TextView guestNameTextView;
    private TextView roomNumberTextView;
    private TextView roomTypeTextView;
    private TextView checkInDateTextView;
    private RecyclerView servicesRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private AppCompatButton btnOrderSpecial;
    private AppCompatButton btnAskAi;
    private CardView cardAiAssistant;
    private FrameLayout notificationLayout;
    private CardView profileAvatarCard;

    private SessionManager sessionManager;
    private Guest currentGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_dashboard);

        sessionManager = SessionManager.getInstance(this);

        initViews();
        loadGuestData();
        setupServicesGrid();
        setupListeners();
    }

    private void initViews() {
        guestNameTextView = findViewById(R.id.guestNameTextView);
        roomNumberTextView = findViewById(R.id.roomNumberTextView);
        roomTypeTextView = findViewById(R.id.roomTypeTextView);
        checkInDateTextView = findViewById(R.id.checkInDateTextView);
        servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnOrderSpecial = findViewById(R.id.btnOrderSpecial);
        btnAskAi = findViewById(R.id.btnAskAi);
        cardAiAssistant = findViewById(R.id.cardAiAssistant);
        notificationLayout = findViewById(R.id.notificationLayout);
        profileAvatarCard = findViewById(R.id.profileAvatarCard);
    }

    private void loadGuestData() {
        // Attempt to load from Intent extras first, then fallback to SessionManager
        if (getIntent().hasExtra("guest_data")) {
            currentGuest = (Guest) getIntent().getSerializableExtra("guest_data");
        }

        if (currentGuest == null) {
            currentGuest = sessionManager.getGuest();
        }

        // Fallback default guest if null
        if (currentGuest == null) {
            currentGuest = new Guest(
                "GST-98421",
                "Mr. Aman Singh",
                "+971 50 123 4567",
                "305",
                "Deluxe Ocean View",
                "Jul 26, 2026",
                "Jul 31, 2026",
                "guest_avatar",
                "ATR-305-2026"
            );
        }

        // Dynamically bind guest values to UI views
        guestNameTextView.setText(currentGuest.getName() + " 👋");
        roomNumberTextView.setText("Room " + currentGuest.getRoomNumber());
        roomTypeTextView.setText(currentGuest.getRoomType());
        checkInDateTextView.setText(currentGuest.getCheckInDate());
    }

    private void setupServicesGrid() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("srv_1", getString(R.string.service_order_food), R.drawable.ic_dining, "Explore 24/7 gourmet in-room dining prepared by Michelin star culinary masters.", "Dining"));
        serviceList.add(new Service("srv_2", getString(R.string.service_hotel_services), R.drawable.ic_services, "Concierge assistance, Royal butler bookings, and custom reservations.", "Concierge"));
        serviceList.add(new Service("srv_3", getString(R.string.service_spa_wellness), R.drawable.ic_spa, "Awaken Wellness: Hammam, luxury facial treatments, and private yoga sessions.", "Wellness"));
        serviceList.add(new Service("srv_4", getString(R.string.service_housekeeping), R.drawable.ic_housekeeping, "Request fresh linens, pillow menu, amenities, and evening turndown service.", "Housekeeping"));
        serviceList.add(new Service("srv_5", getString(R.string.service_wifi), R.drawable.ic_wifi, "Connect your devices seamlessly to high-speed Atlantis Royal Ultra-Wi-Fi.", "Connectivity"));
        serviceList.add(new Service("srv_6", getString(R.string.service_transport), R.drawable.ic_transport, "Book Rolls-Royce airport transfers, luxury yachts, and chauffeur cars.", "Transport"));
        serviceList.add(new Service("srv_7", getString(R.string.service_explore_atlantis), R.drawable.ic_explore, "Discover Cloud 22 infinity sky pool, Aquaventure world, and private beach.", "Experience"));
        serviceList.add(new Service("srv_8", getString(R.string.service_rate_us), R.drawable.ic_star, "Share your experience with our General Manager and let us know how we did.", "Feedback"));

        servicesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ServiceAdapter adapter = new ServiceAdapter(serviceList, new ServiceAdapter.OnServiceClickListener() {
            @Override
            public void onServiceClick(Service service) {
                showServiceDetailDialog(service);
            }
        });
        servicesRecyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        // Special Offer Button
        btnOrderSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GuestDashboardActivity.this, "Special 20% OFF offer applied! Opening In-Room Dining Menu...", Toast.LENGTH_SHORT).show();
                openAiChatWithQuery("I would like to order from the Today's Special 20% OFF dining menu.");
            }
        });

        // AI Assistant Card & Button
        btnAskAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAiChat();
            }
        });

        cardAiAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAiChat();
            }
        });

        // Notification Bell
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationsDialog();
            }
        });

        // Profile Avatar
        profileAvatarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileDialog();
            }
        });

        // Bottom Navigation
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_requests) {
                    showActiveRequestsDialog();
                    return true;
                } else if (itemId == R.id.nav_ai) {
                    openAiChat();
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    showOrdersDialog();
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    showProfileDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private void showServiceDetailDialog(final Service service) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_service_action, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        ImageView dialogIcon = dialogView.findViewById(R.id.dialogIcon);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        TextView dialogRoomTag = dialogView.findViewById(R.id.dialogRoomTag);
        ImageView dialogCloseBtn = dialogView.findViewById(R.id.dialogCloseBtn);
        AppCompatButton dialogActionBtn = dialogView.findViewById(R.id.dialogActionBtn);

        dialogIcon.setImageResource(service.getIconResId());
        dialogTitle.setText(service.getTitle());
        dialogDescription.setText(service.getDescription());
        dialogRoomTag.setText("Room " + currentGuest.getRoomNumber() + " • " + currentGuest.getRoomType());

        if (service.getId().equals("srv_1")) {
            dialogActionBtn.setText("View Gourmet Menu");
        } else if (service.getId().equals("srv_5")) {
            dialogActionBtn.setText("Connect Device (1Gbps)");
        } else if (service.getId().equals("srv_8")) {
            dialogActionBtn.setText("Submit Rating ⭐⭐⭐⭐⭐");
        } else {
            dialogActionBtn.setText("Request " + service.getTitle());
        }

        dialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (service.getId().equals("srv_5")) {
                    Toast.makeText(GuestDashboardActivity.this, "Connected to Atlantis Royal High-Speed WiFi!", Toast.LENGTH_LONG).show();
                } else if (service.getId().equals("srv_8")) {
                    Toast.makeText(GuestDashboardActivity.this, "Thank you for your 5-star rating, " + currentGuest.getName() + "!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(GuestDashboardActivity.this, "Request for " + service.getTitle() + " dispatched to your Butler!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    private void openAiChat() {
        Intent intent = new Intent(GuestDashboardActivity.this, AIChatActivity.class);
        intent.putExtra("guest_data", currentGuest);
        startActivity(intent);
    }

    private void openAiChatWithQuery(String initialQuery) {
        Intent intent = new Intent(GuestDashboardActivity.this, AIChatActivity.class);
        intent.putExtra("guest_data", currentGuest);
        intent.putExtra("initial_query", initialQuery);
        startActivity(intent);
    }

    private void showNotificationsDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Royal Notifications (3)")
            .setItems(new String[]{
                "🛎️ Welcome Gift: Complimentary Laurent-Perrier Champagne delivered to Room 305.",
                "🍽️ Dinner Reservation confirmed at Dinner by Heston Blumenthal for 8:30 PM.",
                "🏊 Cloud 22 Infinity Pool access passes are active for your stay."
            }, null)
            .setPositiveButton("Close", null)
            .show();
    }

    private void showActiveRequestsDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Your Active Requests")
            .setMessage("• Extra hypoallergenic feather pillows (Status: In Progress - Butler on the way)\n• Afternoon Tea booking at The Royal Tea Lounge (Status: Confirmed)")
            .setPositiveButton("OK", null)
            .show();
    }

    private void showOrdersDialog() {
        new AlertDialog.Builder(this)
            .setTitle("In-Room Dining & Orders")
            .setMessage("Order #ATR-8921\n• Wagyu Beef Tenderloin\n• Truffle Mashed Potatoes\n• San Pellegrino Sparkling (750ml)\n\nEstimated delivery: 25 mins to Room " + currentGuest.getRoomNumber())
            .setPositiveButton("Track Order", null)
            .show();
    }

    private void showProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guest Profile")
            .setMessage("Name: " + currentGuest.getName() +
                        "\nRoom: " + currentGuest.getRoomNumber() + " (" + currentGuest.getRoomType() + ")" +
                        "\nCheck-in: " + currentGuest.getCheckInDate() +
                        "\nCheck-out: " + currentGuest.getCheckOutDate() +
                        "\nBooking ID: " + currentGuest.getBookingId() +
                        "\nPhone: " + currentGuest.getPhone())
            .setPositiveButton("OK", null)
            .setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sessionManager.clearSession();
                    Toast.makeText(GuestDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuestDashboardActivity.this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            })
            .show();
    }
}
