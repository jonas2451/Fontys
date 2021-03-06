package dao.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A utility class that calls a webservice to assign a unique ID to an Object.
 */
public class GUID {

    private static final String URI = "https://www.uuidgenerator.net/api/guid";


    /**
     * Generates a unique GUID.
     *
     * @return 36-digit long String
     */
    public static String generate() {
        try {
            URL host = new URL(URI);

            HttpURLConnection conn = (HttpURLConnection) host.openConnection();

            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader scanner = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);

                StringBuilder builder = new StringBuilder(36);

                for (int i = 0; i < 36; i++) {
                    builder.append( (char) scanner.read());
                }

                return builder.toString();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "";
    }

}
