package ru.digitalhabbits.homework1.service;
import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



import javax.annotation.Nonnull;
import java.io.IOException;


import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) throws IOException {
        final URI uri = prepareSearchUrl(searchString);

        int charString;
        StringBuilder stringBuilder= new StringBuilder();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        HttpResponse httpResponse = client.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();

        while((charString = inputStream.read()) != -1)
        {   stringBuilder.append((char)charString); }

        return resultString(stringBuilder.toString());
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String resultString(String preString){

        JsonParser parser = new JsonParser();
        JsonObject element = parser.parse(preString).getAsJsonObject();
        JsonObject obj = element.getAsJsonObject("query").getAsJsonObject("pages");

        Set<String> pages = obj.keySet();
        String preKey = pages.toArray(new String[1])[0];
        String resultString = obj.getAsJsonObject(preKey).get("extract").getAsString();

        return resultString;
    }


}
