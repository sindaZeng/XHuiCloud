package com.xhuicloud.ai.chatgpt.client;

import com.xhuicloud.ai.chatgpt.completion.CompletionRequest;
import com.xhuicloud.ai.chatgpt.completion.CompletionResult;
import com.xhuicloud.ai.chatgpt.edit.EditRequest;
import com.xhuicloud.ai.chatgpt.embedding.EmbeddingRequest;
import com.xhuicloud.ai.chatgpt.embedding.EmbeddingResult;
import com.xhuicloud.ai.chatgpt.model.Model;
import com.xhuicloud.ai.chatgpt.moderation.ModerationRequest;
import com.xhuicloud.ai.chatgpt.moderation.ModerationResult;
import com.xhuicloud.ai.chatgpt.edit.EditResult;
import com.xhuicloud.ai.chatgpt.file.File;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneEvent;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneRequest;
import com.xhuicloud.ai.chatgpt.finetune.FineTuneResult;
import com.xhuicloud.ai.chatgpt.image.CreateImageRequest;
import com.xhuicloud.ai.chatgpt.image.ImageResult;
import com.xhuicloud.ai.chatgpt.model.DeleteResult;
import com.xhuicloud.ai.chatgpt.model.OpenAiResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
public interface OpenAiApi {

    @GET("v1/models")
    Single<OpenAiResponse<Model>> listModels();

    @GET("/v1/models/{model_id}")
    Single<Model> getModel(@Path("model_id") String modelId);

    @POST("/v1/completions")
    Single<CompletionResult> createCompletion(@Body CompletionRequest request);

    @POST("/v1/edits")
    Single<EditResult> createEdit(@Body EditRequest request);

    @POST("/v1/embeddings")
    Single<EmbeddingResult> createEmbeddings(@Body EmbeddingRequest request);

    @GET("/v1/files")
    Single<OpenAiResponse<File>> listFiles();

    @Multipart
    @POST("/v1/files")
    Single<File> uploadFile(@Part("purpose") RequestBody purpose, @Part MultipartBody.Part file);

    @DELETE("/v1/files/{file_id}")
    Single<DeleteResult> deleteFile(@Path("file_id") String fileId);

    @GET("/v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    @POST("/v1/fine-tunes")
    Single<FineTuneResult> createFineTune(@Body FineTuneRequest request);

    @POST("/v1/completions")
    Single<CompletionResult> createFineTuneCompletion(@Body CompletionRequest request);

    @GET("/v1/fine-tunes")
    Single<OpenAiResponse<FineTuneResult>> listFineTunes();

    @GET("/v1/fine-tunes/{fine_tune_id}")
    Single<FineTuneResult> retrieveFineTune(@Path("fine_tune_id") String fineTuneId);

    @POST("/v1/fine-tunes/{fine_tune_id}/cancel")
    Single<FineTuneResult> cancelFineTune(@Path("fine_tune_id") String fineTuneId);

    @GET("/v1/fine-tunes/{fine_tune_id}/events")
    Single<OpenAiResponse<FineTuneEvent>> listFineTuneEvents(@Path("fine_tune_id") String fineTuneId);

    @DELETE("/v1/models/{fine_tune_id}")
    Single<DeleteResult> deleteFineTune(@Path("fine_tune_id") String fineTuneId);

    @POST("/v1/images/generations")
    Single<ImageResult> createImage(@Body CreateImageRequest request);

    @POST("/v1/images/edits")
    Single<ImageResult> createImageEdit(@Body RequestBody requestBody);

    @POST("/v1/images/variations")
    Single<ImageResult> createImageVariation(@Body RequestBody requestBody);

    @POST("/v1/moderations")
    Single<ModerationResult> createModeration(@Body ModerationRequest request);

}
