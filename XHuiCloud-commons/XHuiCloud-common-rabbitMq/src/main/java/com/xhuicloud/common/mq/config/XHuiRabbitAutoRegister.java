/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.mq.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.mq.constant.XHuiRabbitMqConstant;
import com.xhuicloud.common.mq.properties.XHuiRabbitMqProperties;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class XHuiRabbitAutoRegister {

    private final XHuiRabbitMqProperties xHuiRabbitMqProperties;

    private final ConfigurableApplicationContext applicationContext;

    private final CustomExchange delayedExchange;

    private final DirectExchange directExchange;

    @PostConstruct
    private void init() {
        registryQueues();
        registryBindings();
    }

    /**
     * 注册消费队列，包括错误队列
     */
    private void registryQueues() {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        List<String> rawQueues = xHuiRabbitMqProperties.getQueues();
        if (CollectionUtil.isEmpty(rawQueues)) {
            return;
        }
        List<String> queues = new ArrayList<>(rawQueues);
        rawQueues.stream().forEach(s -> queues.add(getErrorQueueName(s)));
        for (String queue : queues) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Queue.class);
            beanDefinitionBuilder.addConstructorArgValue(queue).addConstructorArgValue(true);
            registry.registerBeanDefinition(getQueueBeanName(queue), beanDefinitionBuilder.getBeanDefinition());
        }
    }

    /**
     * 注册 Bindings
     */
    private void registryBindings() {
        List<String> queues = xHuiRabbitMqProperties.getQueues();
        if (CollectionUtil.isEmpty(queues)) {
            return;
        }
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        for (String queue : queues) {
            /**
             * direct 队列绑定
             */
            String queueBeanName = getQueueBeanName(queue);
            BeanDefinitionBuilder directBindingBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class, () -> {
                Queue queueBean = factory.getBean(queueBeanName, Queue.class);
                return BindingBuilder.bind(queueBean).to(directExchange).with(queue);
            });
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(queueBeanName + XHuiRabbitMqConstant.DIRECT_BINDING_SUFFIX, directBindingBuilder.getRawBeanDefinition());

            /**
             * 延时 队列绑定
             */
            BeanDefinitionBuilder delayedBindingBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class, () -> {
                Queue queueBean = factory.getBean(queueBeanName, Queue.class);
                return BindingBuilder.bind(queueBean).to(delayedExchange).with(queue).noargs();
            });
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(queueBeanName + XHuiRabbitMqConstant.DELAYED_BINDING_SUFFIX, delayedBindingBuilder.getRawBeanDefinition());

            /**
             * 错误队列绑定
             */
            String errorQueueName = getErrorQueueName(queue);
            BeanDefinitionBuilder errorBindingBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class, () -> {
                Queue queueBean = factory.getBean(getQueueBeanName(errorQueueName), Queue.class);
                return BindingBuilder.bind(queueBean).to(directExchange).with(errorQueueName);
            });
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(errorQueueName + XHuiRabbitMqConstant.BINDING_SUFFIX, errorBindingBuilder.getRawBeanDefinition());

        }
    }

    /**
     * 获取错误队列的名字
     *
     * @param queue
     * @return
     */
    private String getErrorQueueName(String queue) {
        return XHuiRabbitMqConstant.ERROR_QUEUE_PREFIX + queue;
    }

    /**
     * 获取队列bean的名字
     *
     * @param queue
     * @return
     */
    private String getQueueBeanName(String queue) {
        return StrUtil.toCamelCase(queue.replace("\\.", "_"));
    }
}
