package com.example.demo.message.consumer;

import com.example.demo.config.RedisConfig;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class Consumer1 implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer serializer = RedisConfig.getRedisJsonSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //接收的topic
        String channel = stringRedisSerializer.deserialize(message.getChannel());
        //消息的POJO
        Object o = serializer.deserialize(message.getBody());
        System.out.println("Consumer1 channel[" + channel + "] " + o);
    }
}
