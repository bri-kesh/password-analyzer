import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class PasswordAnalyzer {

    // List of most common passwords
    private static final List<String> COMMON_PASSWORDS = Arrays.asList(
            "password", "123456", "password123", "qwerty", "abc123",
            "letmein", "monkey", "1234567890", "iloveyou", "admin",
            "welcome", "login", "passw0rd", "master", "hello",
            "shadow", "dragon", "654321", "superman", "michael"
    );

    public int getLengthScore(String password) {
        int length = password.length();
        if (length < 6) return 0;
        else if (length <= 9) return 1;
        else if (length <= 15) return 2;
        else return 3;
    }

    public int getVarietyScore(String password) {
        int score = 0;
        if (password.matches(".*[a-z].*")) score++; // has lowercase
        if (password.matches(".*[A-Z].*")) score++; // has uppercase
        if (password.matches(".*[0-9].*")) score++; // has numbers
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}].*")) score++; // has special chars
        return score;
    }

    public boolean isCommonPassword(String password) {
        return COMMON_PASSWORDS.contains(password.toLowerCase());
    }

    // Converts password to SHA-1 hash
    public String toSHA1(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = md.digest(password.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Checks HaveIBeenPwned API
    public int checkBreach(String password) throws Exception {
        String hash = toSHA1(password);
        String prefix = hash.substring(0, 5);
        String suffix = hash.substring(5);

        String url = "https://api.pwnedpasswords.com/range/" + prefix;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", "PasswordAnalyzer-Student-Project");

            return client.execute(request, response -> {
                String body = EntityUtils.toString(response.getEntity());
                for (String line : body.split("\n")) {
                    String[] parts = line.split(":");
                    if (parts[0].equalsIgnoreCase(suffix)) {
                        return Integer.parseInt(parts[1].trim());
                    }
                }
                return 0;
            });
        }
    }

    public String analyze(String password) {
        // Immediately fail common passwords
        if (isCommonPassword(password)) {
            return "\nPassword: " + password +
                    "\nThis is a very common password!" +
                    "\nStrength: Extremely Weak 🔴";
        }

        int lengthScore = getLengthScore(password);
        int varietyScore = getVarietyScore(password);
        int totalScore = lengthScore + varietyScore;

        String rating;
        if (totalScore <= 1) rating = "Weak 🔴";
        else if (totalScore <= 3) rating = "Fair 🟡";
        else if (totalScore <= 5) rating = "Strong 🟢";
        else rating = "Very Strong 💪";

        // Check breach
        String breachResult;
        try {
            int count = checkBreach(password);
            if (count > 0) {
                breachResult = "⚠️  Found in " + count + " data breaches!";
            } else {
                breachResult = "✅ Not found in any known data breaches";
            }
        } catch (Exception e) {
            breachResult = "⚠️  Could not check breach database (no internet?)";
        }

        return "\nPassword: " + password +
                "\nLength Score: " + lengthScore + "/3" +
                "\nVariety Score: " + varietyScore + "/4" +
                "\nTotal Score: " + totalScore + "/7" +
                "\nStrength: " + rating +
                "\nBreach Check: " + breachResult;
    }
}