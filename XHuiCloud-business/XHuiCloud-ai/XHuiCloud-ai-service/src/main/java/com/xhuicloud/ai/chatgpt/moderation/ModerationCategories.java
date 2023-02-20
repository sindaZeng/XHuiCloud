package com.xhuicloud.ai.chatgpt.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
public class ModerationCategories {


    public boolean hate;

    @JsonProperty("hate/threatening")
    public boolean hateThreatening;

    @JsonProperty("self-harm")
    public boolean selfHarm;

    public boolean sexual;

    @JsonProperty("sexual/minors")
    public boolean sexualMinors;

    public boolean violence;

    @JsonProperty("violence/graphic")
    public boolean violenceGraphic;

}
