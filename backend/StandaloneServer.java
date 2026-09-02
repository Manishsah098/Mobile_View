import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StandaloneServer {

    static class Guest {
        long id;
        String title;
        String fullName;
        String phoneNumber;
        String countryCode;
        String roomNumber;
        String roomType;
        String checkInDate;
        String checkOutDate;
        String avatarUrl;
        int notificationCount = 3;

        Guest(long id, String title, String fullName, String phoneNumber, String countryCode, String roomNumber, String roomType, String checkInDate, String checkOutDate, String avatarUrl) {
            this.id = id;
            this.title = title;
            this.fullName = fullName;
            this.phoneNumber = phoneNumber;
            this.countryCode = countryCode;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.avatarUrl = avatarUrl;
        }

        String toJson() {
            return String.format(
                "{\"id\":%d,\"title\":\"%s\",\"fullName\":\"%s\",\"phoneNumber\":\"%s\",\"countryCode\":\"%s\",\"roomNumber\":\"%s\",\"roomType\":\"%s\",\"checkInDate\":\"%s\",\"checkOutDate\":\"%s\",\"avatarUrl\":\"%s\",\"notificationCount\":%d}",
                id, escapeJson(title), escapeJson(fullName), escapeJson(phoneNumber), escapeJson(countryCode),
                escapeJson(roomNumber), escapeJson(roomType), escapeJson(checkInDate), escapeJson(checkOutDate),
                escapeJson(avatarUrl), notificationCount
            );
        }
    }

    private static final List<Guest> guests = new ArrayList<>();
    private static long idCounter = 1;

    static {
        guests.add(new Guest(
            idCounter++,
            "Mr.",
            "Aman Singh",
            "501234567",
            "+971",
            "305",
            "Deluxe Ocean View",
            "Jul 26, 2026",
            "Jul 30, 2026",
            "app/src/main/res/drawable/guest_avatar.jpg"
        ));
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // CORS & OPTIONS helper handler wrapper
        server.createContext("/api/dashboard/data", exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) { sendResponse(exchange, 204, ""); return; }

            Guest g = guests.get(0);
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.contains("userId=")) {
                try {
                    long qId = Long.parseLong(query.split("userId=")[1].split("&")[0]);
                    for (Guest candidate : guests) {
                        if (candidate.id == qId) { g = candidate; break; }
                    }
                } catch (Exception ignored) {}
            }

            String json = String.format(
                "{" +
                "\"user\":%s," +
                "\"greetingTitle\":\"Welcome,\"," +
                "\"guestDisplayName\":\"%s %s 👋\"," +
                "\"subText\":\"Welcome to Atlantis The Royal. We're delighted to have you with us.\"," +
                "\"roomCard\":{\"title\":\"Room %s\",\"sub\":\"%s\"}," +
                "\"checkInCard\":{\"title\":\"Check-in\",\"sub\":\"%s\"}," +
                "\"specialOffer\":{\"badge\":\"TODAY'S SPECIAL\",\"discount\":\"20%% OFF\",\"title\":\"On Lunch & Dinner\",\"validity\":\"Valid till 11:00 PM\",\"bgImage\":\"steak_dish.jpg\",\"buttonText\":\"Order Now\"}," +
                "\"services\":[" +
                "  {\"id\":\"food\",\"name\":\"Order Food\"}," +
                "  {\"id\":\"services\",\"name\":\"Hotel Services\"}," +
                "  {\"id\":\"spa\",\"name\":\"Spa & Wellness\"}," +
                "  {\"id\":\"housekeeping\",\"name\":\"Housekeeping\"}," +
                "  {\"id\":\"wifi\",\"name\":\"WiFi\"}," +
                "  {\"id\":\"transport\",\"name\":\"Transport\"}," +
                "  {\"id\":\"explore\",\"name\":\"Explore Atlantis The Royal\"}," +
                "  {\"id\":\"rate\",\"name\":\"Rate Us\"}" +
                "]," +
                "\"aiAssistant\":{\"title\":\"Ask Atlantis The Royal AI\",\"subtitle\":\"Your personal hotel assistant. Ask anything or make a request.\",\"buttonText\":\"Ask Now\"}" +
                "}",
                g.toJson(), escapeJson(g.title), escapeJson(g.fullName), escapeJson(g.roomNumber), escapeJson(g.roomType), escapeJson(g.checkInDate)
            );
            sendResponse(exchange, 200, json);
        });

        // Register API
        server.createContext("/api/auth/register", exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) { sendResponse(exchange, 204, ""); return; }

            String body = readBody(exchange);
            String title = parseJsonField(body, "title", "Mr.");
            String fullName = parseJsonField(body, "fullName", "Guest User");
            String phoneNumber = parseJsonField(body, "phoneNumber", "501234567");
            String countryCode = parseJsonField(body, "countryCode", "+971");
            String roomNumber = parseJsonField(body, "roomNumber", "305");
            String roomType = parseJsonField(body, "roomType", "Deluxe Ocean View");
            String checkInDate = parseJsonField(body, "checkInDate", "Jul 26, 2026");
            String checkOutDate = parseJsonField(body, "checkOutDate", "Jul 30, 2026");
            String avatarUrl = parseJsonField(body, "avatarUrl", "app/src/main/res/drawable/guest_avatar.jpg");

            Guest newGuest = new Guest(idCounter++, title, fullName, phoneNumber, countryCode, roomNumber, roomType, checkInDate, checkOutDate, avatarUrl);
            guests.add(0, newGuest); // Insert at front as current active guest

            sendResponse(exchange, 200, newGuest.toJson());
        });

        // Login / Profile API
        server.createContext("/api/auth/login", exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) { sendResponse(exchange, 204, ""); return; }
            Guest g = guests.get(0);
            sendResponse(exchange, 200, g.toJson());
        });

        server.createContext("/api/user/profile", exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) { sendResponse(exchange, 204, ""); return; }
            Guest g = guests.get(0);
            sendResponse(exchange, 200, g.toJson());
        });

        // AI Chat API
        server.createContext("/api/ai/chat", exchange -> {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) { sendResponse(exchange, 204, ""); return; }
            String body = readBody(exchange).toLowerCase();
            String reply;
            if (body.contains("food") || body.contains("eat") || body.contains("order")) {
                reply = "I can assist you with dining! You have an active 20% discount on Lunch & Dinner today. Would you like me to open the menu?";
            } else if (body.contains("spa") || body.contains("massage")) {
                reply = "Awaken Spa is open until 10:00 PM today. I can reserve a Swedish massage slot for your room.";
            } else if (body.contains("towel") || body.contains("housekeeping")) {
                reply = "Housekeeping request registered! Towels and fresh amenities are on the way.";
            } else {
                reply = "Hello! I am your Atlantis The Royal AI Assistant. How can I assist you with your stay today?";
            }
            sendResponse(exchange, 200, "{\"reply\":\"" + escapeJson(reply) + "\",\"status\":\"success\"}");
        });

        server.setExecutor(null);
        server.start();
        System.out.println("=================================================================");
        System.out.println("🚀 Atlantis Spring Boot REST API Backend Running on Port " + port);
        System.out.println("=================================================================");
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.set("Content-Type", "application/json; charset=UTF-8");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String readBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static String parseJsonField(String json, String field, String defaultVal) {
        try {
            String pattern = "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"";
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(json);
            if (matcher.find()) return matcher.group(1);
        } catch (Exception ignored) {}
        return defaultVal;
    }

    private static String escapeJson(String raw) {
        if (raw == null) return "";
        return raw.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
