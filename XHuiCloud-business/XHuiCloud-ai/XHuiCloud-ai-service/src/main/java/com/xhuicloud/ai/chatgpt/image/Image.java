package com.xhuicloud.ai.chatgpt.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class Image {

    String url;

    @JsonProperty("b64_json")
    String b64Json;
}
