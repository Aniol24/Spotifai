package persistance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import persistance.exceptions.ApiServerException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Helper class with the responsibility of reading and posting Strings to an HTTPS API. Due to a misconfiguration
 * in the SimpleRPG API, it's set up to ignore SSL certificates, connecting to any server.
 * Be aware that this should NOT be used in real production environments, as verifying certificates is a
 * key part of ensuring security in the context of Internet communications
 */
public final class APIAccess {

    public static void main(String[] args) {
        // Obetener la letra de una canción
        String artist = "pentakill";
        String song = "lightbringer";
        String apiUrl = "https://balandrau.salle.url.edu/dpoo/lyrics/";

        APIAccess apiAccess;
        try {
            apiAccess = new APIAccess();
            String url = apiUrl + artist + "/" + song;
            String jsonResponse = apiAccess.getFromUrl(url);

            // Parse JSON response to get lyrics
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();
            if (jsonObject.has("lyrics")) {
                String lyrics = jsonObject.get("lyrics").getAsString();
                System.out.println("Lyrics:\n" + lyrics);
            } else if (jsonObject.has("error")) {
                String error = jsonObject.get("error").getAsString();
                System.out.println("Error: " + error);
            }

            // Realizar una solicitud POST a la API (ejemplo)
            String newArtist = "exampleArtist";
            String newSong = "exampleSong";
            String newLyrics = "This is an example lyrics.";

            JsonObject postData = new JsonObject();
            postData.addProperty("artist", newArtist);
            postData.addProperty("song", newSong);
            postData.addProperty("lyrics", newLyrics);

            String postResponse = apiAccess.postToUrl(postData.toString());
            System.out.println("POST Response: " + postResponse);

        } catch (ApiServerException e) {
            System.err.println("Error al acceder a la API.");
        }
    }

    private final HttpClient client;

    /**
     * Default constructor, where the client used for HTTPS communication is set up
     *
     * @throws ApiServerException If your computer doesn't support SSL at all. If you get this exception when calling the
     *                            constructor, contact the OOPD teachers.
     */
    public APIAccess() throws ApiServerException {
        // We set up the HTTPClient we will (re)use across requests, with a custom *INSECURE* SSL context
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new ApiServerException();
        }
    }

    /**
     * Method that reads the contents from a URL using the HTTPS protocol. Specifically, a GET request is sent.
     * Any parameters should be included in the URL.
     *
     * @param url A String representation of the URL to read from, which will be assumed to use HTTP/HTTPS.
     * @return The contents of the URL represented as text.
     * @throws ApiServerException If the URL is malformed or the server can't be reached.
     */
    public String getFromUrl(String url) throws ApiServerException {
        try {
            // Define the request
            // The default method is GET, so we don't need to specify it (but we could do so by calling .GET() before .build()
            // The HttpRequest.Builder pattern offers a ton of customization for the request (headers, body, HTTP version...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

            // We use the default BodyHandler for Strings (so we can get the body of the response as a String)
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Just return the body
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new ApiServerException();
        }
    }

    /**
     * Method that posts contents to a URL using the HTTPS protocol. Specifically, a POST request is sent.
     * The request body is set to the corresponding parameter, and the response body is returned just in case.
     *
     * @param body The content to post, which will be sent to the server in the request body.
     * @return The contents of the response, in case the server sends anything back after posting the content.
     * @throws ApiServerException If the URL is malformed or the server can't be reached.
     */
    public String postToUrl(String body) throws ApiServerException {
        try {
            // Define the request
            // In this case, we have to use the .POST() and .headers() methods to define what we want (to send a string containing JSON data)
            String API_URL = "https://balandrau.salle.url.edu/dpoo/lyrics/";
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(API_URL)).headers("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build();

            // We could use a BodyHandler that discards the response body, but here we return the API's response
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new ApiServerException();
        }
    }


    /**
     * Method that removes the contents from a URL using the HTTPS protocol. Specifically, a DELETE request is sent.
     * Any parameters should be included in the URL.
     *
     * @param url A String representation of the URL to delete from, which will be assumed to use HTTP/HTTPS.
     * @return The contents of the response, in case the server sends anything back after deleting the content.
     * @throws ApiServerException If the URL is malformed or the server can't be reached.
     */
    public String deleteFromUrl(String url) throws ApiServerException {
        try {
            // Define the request
            // The default method is GET, so we don't need to specify it (but we could do so by calling .GET() before .build()
            // The HttpRequest.Builder pattern offers a ton of customization for the request (headers, body, HTTP version...)
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).DELETE().build();

            // We use the default BodyHandler for Strings (so we can get the body of the response as a String)
            // Note we could also send the request asynchronously, but things would escalate in terms of coding complexity
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            throw new ApiServerException();
        }
    }

    /**
     * Helper function that sets up a SSLContext designed to ignore certificates, accepting anything by default
     * NOT TO BE USED IN REAL PRODUCTION ENVIRONMENTS
     *
     * @return An instance of the SSLContext class, which manages SSL verifications, configured to accept even misconfigured certificates
     */
    private SSLContext insecureContext() throws NoSuchAlgorithmException, KeyManagementException {
        // We set up a TrustManager that accepts every certificate by default
        TrustManager[] insecureTrustManager = new TrustManager[]{new X509TrustManager() {
            // By not throwing any exceptions in these methods we're accepting everything
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            // This doesn't affect our use case, so we just return an empty array
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // We set up the SSLContext with the over-accepting TrustManager
        SSLContext sc = SSLContext.getInstance("ssl");
        sc.init(null, insecureTrustManager, null);
        return sc;
    }
}
