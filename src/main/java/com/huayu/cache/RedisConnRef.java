package com.huayu.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisConnRef {


    @Autowired
    private JedisConnectionFactory connectionFactory;
    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        RedisCache.setJedisConnectionFactory(connectionFactory);
    }


}
