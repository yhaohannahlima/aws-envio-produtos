package com.produtos.grupo6.spring.aws.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class RedisService {
    
    private Jedis jedis;
    
    
    public RedisService(){
        // Nesta etapa vamos deixar aqui, depois mudar para env. - confirmar porta do regis de sua maquina.
        jedis = new Jedis("http://localhost:6379");
    }

    public void write(String key, String value, long secondsToExpire){
        SetParams params = new SetParams();
        params.ex(secondsToExpire);
        jedis.set(key,value,params);
    }


    public String read(String key){
        return jedis.get(key);
    }

    public void close(){
        jedis.close();
    }
}
