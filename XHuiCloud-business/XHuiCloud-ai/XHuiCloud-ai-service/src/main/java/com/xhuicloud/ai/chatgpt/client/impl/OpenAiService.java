package com.xhuicloud.ai.chatgpt.client.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.xhuicloud.ai.chatgpt.client.OpenAiApi;
import com.xhuicloud.ai.chatgpt.completion.CompletionRequest;
import com.xhuicloud.ai.chatgpt.completion.CompletionResult;
import com.xhuicloud.ai.chatgpt.edit.EditRequest;
import com.xhuicloud.ai.chatgpt.edit.EditResult;
import com.xhuicloud.ai.chatgpt.embedding.EmbeddingRequest;
import com.xhuicloud.ai.chatgpt.embedding.EmbeddingResult;
import com.xhuicloud.ai.chatgpt.file.File;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneEvent;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneRequest;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneResult;
import com.xhuicloud.ai.chatgpt.image.CreateImageEditRequest;
import com.xhuicloud.ai.chatgpt.image.CreateImageRequest;
import com.xhuicloud.ai.chatgpt.image.CreateImageVariationRequest;
import com.xhuicloud.ai.chatgpt.image.ImageResult;
import com.xhuicloud.ai.chatgpt.interceptor.AuthenticationInterceptor;
import com.xhuicloud.ai.chatgpt.model.DeleteResult;
import com.xhuicloud.ai.chatgpt.model.Model;
import com.xhuicloud.ai.chatgpt.moderation.ModerationRequest;
import com.xhuicloud.ai.chatgpt.moderation.ModerationResult;
import com.xhuicloud.ai.entity.Conversation;
import com.xhuicloud.ai.payload.MessagePayload;
import com.xhuicloud.ai.service.ConversationService;
import lombok.RequiredArgsConstructor;
import net.dreamlu.iot.mqtt.spring.server.MqttServerTemplate;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Component
@RequiredArgsConstructor
public class OpenAiService {

    private static final String BASE_URL = "https://api.openai.com/";

    private static OpenAiApi api;

    @Value("${xhuicloud.intelligence.token}")
    private String token;

    private final ConversationService conversationService;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(ofSeconds(500).toMillis(), TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.api = retrofit.create(OpenAiApi.class);
    }

    public List<Model> listModels() {
        return api.listModels().blockingGet().data;
    }

    public Model getModel(String modelId) {
        return api.getModel(modelId).blockingGet();
    }

    public CompletionResult createCompletion(CompletionRequest request) {
        return api.createCompletion(request).blockingGet();
    }

    public EditResult createEdit(EditRequest request) {
        return api.createEdit(request).blockingGet();
    }

    public EmbeddingResult createEmbeddings(EmbeddingRequest request) {
        return api.createEmbeddings(request).blockingGet();
    }

    public List<File> listFiles() {
        return api.listFiles().blockingGet().data;
    }

    public File uploadFile(String purpose, String filepath) {
        java.io.File file = new java.io.File(filepath);
        RequestBody purposeBody = RequestBody.create(okhttp3.MultipartBody.FORM, purpose);
        RequestBody fileBody = RequestBody.create(MediaType.parse("text"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", filepath, fileBody);

        return api.uploadFile(purposeBody, body).blockingGet();
    }

    public DeleteResult deleteFile(String fileId) {
        return api.deleteFile(fileId).blockingGet();
    }

    public File retrieveFile(String fileId) {
        return api.retrieveFile(fileId).blockingGet();
    }

    public FineTuneResult createFineTune(FineTuneRequest request) {
        return api.createFineTune(request).blockingGet();
    }

    public CompletionResult createFineTuneCompletion(CompletionRequest request) {
        return api.createFineTuneCompletion(request).blockingGet();
    }

    public List<FineTuneResult> listFineTunes() {
        return api.listFineTunes().blockingGet().data;
    }

    public FineTuneResult retrieveFineTune(String fineTuneId) {
        return api.retrieveFineTune(fineTuneId).blockingGet();
    }

    public FineTuneResult cancelFineTune(String fineTuneId) {
        return api.cancelFineTune(fineTuneId).blockingGet();
    }

    public List<FineTuneEvent> listFineTuneEvents(String fineTuneId) {
        return api.listFineTuneEvents(fineTuneId).blockingGet().data;
    }

    public DeleteResult deleteFineTune(String fineTuneId) {
        return api.deleteFineTune(fineTuneId).blockingGet();
    }

    public ImageResult createImage(CreateImageRequest request) {
        return api.createImage(request).blockingGet();
    }

    public ImageResult createImageEdit(CreateImageEditRequest request, String imagePath, String maskPath) {
        java.io.File image = new java.io.File(imagePath);
        java.io.File mask = null;
        if (maskPath != null) {
            mask = new java.io.File(maskPath);
        }
        return createImageEdit(request, image, mask);
    }

    public ImageResult createImageEdit(CreateImageEditRequest request, java.io.File image, java.io.File mask) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("image"), image);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("prompt", request.getPrompt())
                .addFormDataPart("size", request.getSize())
                .addFormDataPart("response_format", request.getResponseFormat())
                .addFormDataPart("image", "image", imageBody);

        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }

        if (mask != null) {
            RequestBody maskBody = RequestBody.create(MediaType.parse("image"), mask);
            builder.addFormDataPart("mask", "mask", maskBody);
        }

        return api.createImageEdit(builder.build()).blockingGet();
    }

    public ImageResult createImageVariation(CreateImageVariationRequest request, String imagePath) {
        java.io.File image = new java.io.File(imagePath);
        return createImageVariation(request, image);
    }

    public ImageResult createImageVariation(CreateImageVariationRequest request, java.io.File image) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("image"), image);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("size", request.getSize())
                .addFormDataPart("response_format", request.getResponseFormat())
                .addFormDataPart("image", "image", imageBody);

        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }

        return api.createImageVariation(builder.build()).blockingGet();
    }

    public ModerationResult createModeration(ModerationRequest request) {
        return api.createModeration(request).blockingGet();
    }

    public void createCompletionForStream(MqttServerTemplate mqttServerTemplate, MessagePayload messagePayload, String topic, ChannelContext context) {
        try {
            sendForStream(mqttServerTemplate, messagePayload, topic, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendForStream(MqttServerTemplate mqttServerTemplate, MessagePayload messagePayload, String topic, ChannelContext context) throws IOException {
        URL url = new URL(BASE_URL + "v1/completions");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + this.token);
        con.setDoOutput(true);
        con.setConnectTimeout(1000 * 60 * 5);
        con.setReadTimeout(1000 * 60 * 5);
        String question = messagePayload.getText();
        byte[] postData = ("{\n" +
                "    \"prompt\": \"" + question + "\",\n" +
                "    \"model\": \"text-davinci-003\",\n" +
                "    \"stream\": true,\"max_tokens\": " + 4000 + "}").getBytes(StandardCharsets.UTF_8);
        con.setRequestProperty("Content-Length", Integer.toString(postData.length));
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }
        int responseCode = con.getResponseCode();
        StringBuffer sb = new StringBuffer();
        if (responseCode == 200) {
            // 先发送回复的问题
            messagePayload.setUserId("system");
            messagePayload.setText("@问题: [" + messagePayload.getText() + "]\n\n");
            mqttServerTemplate.publishAll(topic, JSONUtil.toJsonStr(messagePayload).getBytes(StandardCharsets.UTF_8));
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    String replace = line.replace("data: ", "");
                    if ("[DONE]".equals(replace)) {
                        messagePayload.setText("[DONE]");
                        messagePayload.setUserId("system");
                        mqttServerTemplate.publishAll(topic, JSONUtil.toJsonStr(messagePayload).getBytes(StandardCharsets.UTF_8));
                        break;
                    }
                    if (StrUtil.isNotBlank(replace)) {
                        CompletionResult completionResult = JSONUtil.toBean(replace, CompletionResult.class);
                        String text = completionResult.getChoices().get(0).getText();
                        messagePayload.setText(text);
                        sb.append(text);
                        messagePayload.setUserId("system");
                        mqttServerTemplate.publishAll(topic, JSONUtil.toJsonStr(messagePayload).getBytes(StandardCharsets.UTF_8));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Conversation conversation = new Conversation();
            conversation.setMessageId(messagePayload.getMessageId());
            conversation.setUserId(Long.valueOf(context.userid));
            conversation.setQuestion(question);
            conversation.setAnswer(sb.toString());
            conversation.setTenantId(Long.valueOf(context.getToken()));
            conversationService.save(conversation);
        }
    }

}
