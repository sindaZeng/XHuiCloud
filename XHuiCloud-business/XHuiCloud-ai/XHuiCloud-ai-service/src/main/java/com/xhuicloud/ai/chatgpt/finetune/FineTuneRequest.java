package com.xhuicloud.ai.chatgpt.finetune;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @Desc 创建一个作业，用于微调给定数据集中的指定模型。
 * @Url https://platform.openai.com/docs/api-reference/fine-tunes/create
 * @Author Sinda
 * @Date 2023/2/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineTuneRequest {

    /**
     * 上传的培训数据文件ID。
     */
    @NonNull
    String trainingFile;

    /**
     * 包含验证数据的上传文件的ID。
     */
    String validationFile;

    /**
     * 基础模型的名称进行微调。您可以从“ada”、“babbage”、“curie”或“davinci”中任选其一。
     * 要了解更多关于这些模型的信息，请参阅引擎文档。
     */
    String model;

    /**
     * 训练模型的epoch数。epoch指的是训练数据集的一个完整周期。
     */
    Integer nEpochs;

    /**
     * 用于培训的批次大小。
     * 批大小是用于训练单个向前和向后传递的训练示例的数量。
     * <p>
     * 默认情况下，批量大小将动态配置为训练中示例数量的~0.2%
     * set，上限为256 -一般来说，我们发现更大的批处理大小往往更适合于更大的数据集。
     */
    Integer batchSize;

    /**
     * 用于训练的学习率乘数。
     * 微调学习率是用于预训练的原始学习率乘以此值。
     * <p>
     * 默认情况下，学习率乘数是0.05,0.1或0.2，这取决于最终的batch_size
     * (较大的学习率往往在较大的批处理规模下表现更好)。
     * 我们建议在0.02到0.2的范围内进行实验，看看什么会产生最好的结果。
     */
    Double learningRateMultiplier;

    /**
     * 用于提示令牌损失的权重。
     * 这控制了模型尝试学习生成提示的程度
     * (与权重始终为1.0的完井相比)，
     * 当完成时间较短时，可以为训练增加稳定效果。
     * <p>
     * 如果提示符非常长(相对于补全而言)，减少这个权重是有意义的，以便
     * 避免过度优先学习提示。
     */
    Double promptLossWeight;

    /**
     * 如果设置了，我们将使用验证集计算特定于分类的指标，如准确性和F-1分数
     * 在每个时代的结束。这些指标可以在结果文件中查看。
     * <p>
     * 为了计算分类指标，你必须提供一个validation_file。
     * 另外，你必须为多类指定{@link FineTuneRequest#classificationNClasses}
     * 分类或{@link FineTuneRequest#classificationPositiveClass}用于二进制分类。
     */
    Boolean computeClassificationMetrics;

    /**
     * 分类任务中的类数。
     * <p>
     * 多类分类时需要此参数。
     */
    @JsonProperty("classification_n_classes")
    Integer classificationNClasses;

    /**
     * 二元分类中的正类。
     * <p>
     * 在进行二进制分类时，需要该参数来生成精度、召回率和F1指标。
     */
    String classificationPositiveClass;

    /**
     * 如果提供了这个，我们会在指定的beta值上计算F-beta分数。
     * F-beta分数是F-1分数的泛化。这只用于二进制分类。
     * <p>
     * 如果beta值为1(即F-1分)，精确度和回忆率的权重相同。
     * beta测试分数越大，对回忆的重视程度越高，对准确性的重视程度越低。
     * beta测试分数越小，精确度就越重要，回忆就越少。
     */
    List<Double> classificationBetas;

    /**
     * 一个最多40个字符的字符串，将添加到您的微调模型名称。
     */
    String suffix;
}
