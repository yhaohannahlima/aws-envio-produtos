package com.produtos.grupo6.spring.aws.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
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

	public static Optional<?> post(Object obj) throws ClientProtocolException, IOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		
		
		// formatando string para formar o _index, usando o nome simplificado da classe
		String className = obj.getClass().getSimpleName().toLowerCase();
		String index = "/" + className + "/_doc";

		// Pegando id do obj "by reflection"
		Field idField = obj.getClass().getDeclaredField("id");
		idField.setAccessible(true);
		var idObj = idField.get(obj).toString();
		idField.setAccessible(false);

		// id para do _doc
		String id = "/" + idObj;

		// Passando para Json o obj
		String json = new Gson().toJson(obj);

		// encapsulando http entity json no charset utf-8
		StringEntity entity = new StringEntity(json, "UTF-8");

		// montando dado e cabecalho http
		HttpPost post = new HttpPost(HOST + index + id);
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Accept", "text/plain");
		post.setEntity(entity);

		HttpClientBuilder clienteBuilder = HttpClientBuilder.create();
		CloseableHttpClient client = clienteBuilder.build();

		// enviando o post
		var response = client.execute(post);
		client.close();

		return Optional.of(response);
	}
}
