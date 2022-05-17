package com.produtos.grupo6.spring.aws.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;

/*
 * Abordagem por acesso 
 * Rest no elastiSearch
 */
public class RestElasticService {

	final static String HOST = "http://localhost:9200";
	final static String INDEX_PRODUTOS = "/produtos/_doc";
	final static String INDEX_CLIENTES = "/clientes/_doc";	
	final static String INDEX_PEDIDOS = "/pedidos/_doc";

	public static Optional<?> postProduto(Object obj, Integer idProduto) throws ClientProtocolException, IOException{
		String id = "/"+Integer.toString(idProduto);		
		String json = new Gson().toJson(obj); 		
		
		StringEntity entity = new StringEntity(json,"UTF-8"); 
		System.out.println(entity);
		
		HttpPost post = new HttpPost(HOST+INDEX_PRODUTOS+id);  //http://localhost:9200/_doc/1
		HttpClientBuilder clienteBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clienteBuilder.build();
		
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Accept","text/plain");
		post.setEntity(entity);
		
		var response = client.execute(post);		
		client.close();		
		
		return Optional.of(response);		
	}
}
