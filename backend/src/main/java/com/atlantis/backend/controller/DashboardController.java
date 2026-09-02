package com.atlantis.backend.controller;

import com.atlantis.backend.model.FoodOrder;
import com.atlantis.backend.model.GuestRequest;
import com.atlantis.backend.model.UserGuest;
import com.atlantis.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    private final List<GuestRequest> requestsList = new ArrayList<>();
    private final List<FoodOrder> ordersList = new ArrayList<>();

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getDashboardData(@RequestParam(required = false, defaultValue = "1") Long userId) {
        UserGuest guest = userRepository.findById(userId).orElse(null);
        if (guest == null) {
            List<UserGuest> all = userRepository.findAll();
            if (!all.isEmpty()) {
                guest = all.get(0);
            } else {
                guest = new UserGuest("Mr.", "Aman Singh", "501234567", "+971", "305", "Deluxe Ocean View", "Jul 26, 2026", "Jul 30, 2026", "app/src/main/res/drawable/guest_avatar.jpg");
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("user", guest);

        // Header & Greetings
        data.put("greetingTitle", "Welcome,");
        data.put("guestDisplayName", guest.getTitle() + " " + guest.getFullName() + " 👋");
        data.put("subText", "Welcome to Atlantis The Royal. We're delighted to have you with us.");

        // Booking Cards
        Map<String, Object> roomCard = new HashMap<>();
        roomCard.put("title", "Room " + guest.getRoomNumber());
        roomCard.put("sub", guest.getRoomType());
        data.put("roomCard", roomCard);

        Map<String, Object> checkInCard = new HashMap<>();
        checkInCard.put("title", "Check-in");
        checkInCard.put("sub", guest.getCheckInDate());
        data.put("checkInCard", checkInCard);

        // Today's Special Offer
        Map<String, Object> specialOffer = new HashMap<>();
        specialOffer.put("badge", "TODAY'S SPECIAL");
        specialOffer.put("discount", "20% OFF");
        specialOffer.put("title", "On Lunch & Dinner");
        specialOffer.put("validity", "Valid till 11:00 PM");
        specialOffer.put("bgImage", "steak_dish.jpg");
        specialOffer.put("buttonText", "Order Now");
        data.put("specialOffer", specialOffer);

        // Explore Services List
        List<Map<String, String>> services = new ArrayList<>();
        services.add(Map.of("id", "food", "name", "Order Food", "icon", "cloche"));
        services.add(Map.of("id", "services", "name", "Hotel Services", "icon", "bell"));
        services.add(Map.of("id", "spa", "name", "Spa & Wellness", "icon", "lotus"));
        services.add(Map.of("id", "housekeeping", "name", "Housekeeping", "icon", "spray"));
        services.add(Map.of("id", "wifi", "name", "WiFi", "icon", "wifi"));
        services.add(Map.of("id", "transport", "name", "Transport", "icon", "car"));
        services.add(Map.of("id", "explore", "name", "Explore Atlantis The Royal", "icon", "pin"));
        services.add(Map.of("id", "rate", "name", "Rate Us", "icon", "star"));
        data.put("services", services);

        // AI Assistant section
        Map<String, Object> aiSection = new HashMap<>();
        aiSection.put("title", "Ask Atlantis The Royal AI");
        aiSection.put("subtitle", "Your personal hotel assistant. Ask anything or make a request.");
        aiSection.put("buttonText", "Ask Now");
        data.put("aiAssistant", aiSection);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/requests")
    public ResponseEntity<GuestRequest> createRequest(@RequestBody GuestRequest req) {
        req.setStatus("In Progress");
        requestsList.add(req);
        return ResponseEntity.ok(req);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<GuestRequest>> getRequests(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(requestsList);
    }

    @PostMapping("/orders")
    public ResponseEntity<FoodOrder> createOrder(@RequestBody FoodOrder order) {
        order.setStatus("Preparing & Delivering to Room " + order.getRoomNumber());
        ordersList.add(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<FoodOrder>> getOrders(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(ordersList);
    }

    @PostMapping("/ai/chat")
    public ResponseEntity<Map<String, String>> chatAi(@RequestBody Map<String, String> body) {
        String prompt = body.getOrDefault("message", "").toLowerCase();
        String reply;

        if (prompt.contains("food") || prompt.contains("menu") || prompt.contains("eat") || prompt.contains("order")) {
            reply = "I can assist you with dining! You have an active 20% discount on Lunch & Dinner today. Would you like me to send our In-Room Dining Menu?";
        } else if (prompt.contains("spa") || prompt.contains("massage")) {
            reply = "Awaken Spa is open until 10:00 PM today. I can reserve a relaxing Swedish massage or Holism therapy slot for you.";
        } else if (prompt.contains("towel") || prompt.contains("clean") || prompt.contains("housekeeping")) {
            reply = "Housekeeping dispatch request sent for your room! Extra towels and amenities will arrive in 10 minutes.";
        } else if (prompt.contains("pool") || prompt.contains("beach")) {
            reply = "Cloud 22 Sky Pool & Beach Club pass is included with your Deluxe Ocean View stay. Temperature is 28°C with clear skies!";
        } else {
            reply = "Hello! I am your Atlantis The Royal AI Assistant. I can help with room service, housekeeping, transport, spa reservations, or local Dubai recommendations.";
        }

        return ResponseEntity.ok(Map.of("reply", reply, "status", "success"));
    }
}
