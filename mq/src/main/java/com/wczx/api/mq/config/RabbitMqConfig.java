package com.wczx.api.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * queue||exchange
 * @author wj
 */
@Component
public class RabbitMqConfig {

    @Bean
    public Queue testQ() {
        return new Queue("test-q", true);
    }

    @Bean
    public TopicExchange testExchange(){
        return new TopicExchange("test.topic");
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(testQ()).to(testExchange()).with("a");
    }


    @Resource
    private CachingConnectionFactory connectionFactory;

    @Bean(name = "mqConsumerListenerContainer")
    public SimpleRabbitListenerContainerFactory mqConsumerListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(1);
        return factory;
    }
}
