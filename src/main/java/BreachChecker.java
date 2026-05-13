import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class BreachChecker {
    // Checks HaveIBeenPwned API
    public static int check(String password) throws Exception {
        String hash = HashUtil.toSHA1(password);
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
}