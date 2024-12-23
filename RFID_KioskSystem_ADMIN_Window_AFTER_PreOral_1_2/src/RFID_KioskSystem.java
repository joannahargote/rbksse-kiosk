import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RFID_KioskSystem {

    public static void authenticateUser(String rfidTag) {
        try {
            String apiUrl = "http://localhost/RFID_Evaluation_System/api/authenticateRFID.php?rfid_tag=" + rfidTag;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            StringBuilder content;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            }

            // Process JSON response
            String jsonResponse = content.toString();
            if (jsonResponse.contains("\"status\":\"success\"")) {
                System.out.println("Authentication successful.");
                System.out.println("Welcome, " + parseStudentName(jsonResponse));
            } else {
                System.out.println("Authentication failed: " + parseErrorMessage(jsonResponse));
            }

        } catch (IOException e) {
        }
    }

    private static String parseStudentName(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"name\":\"") + 8;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }

    private static String parseErrorMessage(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"message\":\"") + 11;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }

    
    public static void main(String[] args) {
        String testRFID = "0670374451"; // Replace with a test RFID tag from your database
        authenticateUser(testRFID);
    }


}
