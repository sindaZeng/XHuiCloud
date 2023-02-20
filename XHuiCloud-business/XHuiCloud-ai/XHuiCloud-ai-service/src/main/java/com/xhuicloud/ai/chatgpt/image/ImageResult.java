package com.xhuicloud.ai.chatgpt.image;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class ImageResult {

    Long createdAt;

    List<Image> data;
}
