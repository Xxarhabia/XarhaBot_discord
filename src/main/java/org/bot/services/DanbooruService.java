package org.bot.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bot.config.PropertiesConfig;

import java.io.IOException;
import java.util.*;

public class DanbooruService {

    public String getRandomImage(String tag, boolean nsfw, boolean gif) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Random random = new Random();

        StringBuilder tags = new StringBuilder(tag);

        if (nsfw) {
            tags.append(" rating:e");
        } else {
            tags.append(" rating:s");
        }

        if (gif && !nsfw) {
            tags.append(" filetype:gif");
        }

        PropertiesConfig config = new PropertiesConfig();
        String url = "https://danbooru.donmai.us/posts.json" +
                "?limit=10" +
                "&tags=" + tags +
                "&login=" + config.getDanbooruUser() +
                "&api_key=" + config.getDanbooruApiKey();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        System.out.println(url);
        System.out.println(request);

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Error en la API: " + response.code());
            }

            String json = response.body().string();
            JsonArray posts = JsonParser.parseString(json).getAsJsonArray();

            if (posts.size() == 0) {
                return null;
            }

            Set<String> bannedTags = Set.of(
                    "loli","shota","gore","yaoi",
                    "futanari","futa","no_testicles",
                    "autofellatio", "horse", "horse_penis"
            );

            for (int i = 0; i < posts.size(); i++) {
                // mezclamos aleatoriedad
                JsonObject post = posts.get(random.nextInt(posts.size())).getAsJsonObject();

                String tagGeneral = post.get("tag_string_general").getAsString();
                Set<String> tagsSet = new HashSet<>(List.of(tagGeneral.split(" ")));

                boolean hasBannedTag = tagsSet.stream().anyMatch(bannedTags::contains);
                if (hasBannedTag) continue;

                if (post.has("file_url") && !post.get("file_url").isJsonNull()) {
                    return post.get("file_url").getAsString();
                }
            }

            return null;
        }
    }
}
