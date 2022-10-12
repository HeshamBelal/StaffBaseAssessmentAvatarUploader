package uploader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.RunConfig;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AvatarUploader {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final String token;
    private final String finalUri;
    public AvatarUploader() {
        this.token = new RunConfig().getToken();
        this.finalUri = new RunConfig().getFinalUri();
    }

    public void postForAvatar(String avatarPath) throws IOException {
        Map<String, String> metaDataValue = new HashMap<>();
        metaDataValue.put("type", "image");
        metaDataValue.put("filename", "Avatar");
        String content;
        try {
            content = MAPPER.writeValueAsString(metaDataValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("metadata", content)
                .addFormDataPart("file", avatarPath,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(avatarPath)))
                .build();
        Request request = new Request.Builder()
                .url(finalUri)
                .method("POST", body)
                .addHeader("Authorization", token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(request);
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("response.status " + response.code());
    }
}
