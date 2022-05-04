package com.produtos.grupo6.spring.aws.util;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
public class KafkaUtil {
	
	public static void sendMessage(String key,String value) throws 
	InterruptedException, ExecutionException{
		
		KafkaProducer<String, String> producer = new KafkaProducer<String,String>(properties());
		
		ProducerRecord <String, String> record = new ProducerRecord<String,String>
		(System.getenv("KAFKA_TOPIC"),key, value);
		
		Callback callback = (data, ex) -> {
			
			if(ex != null) {
				ex.printStackTrace();
				return;
			}
			 System.out.println("Mensagem enviada com sucesso para: " + data.topic() 
			 + " | partition " + data.partition() 
			 + "| offset " + data.offset() 
			 + "| tempo " + data.timestamp());
		};
		
		producer.send(record, callback).get();
        producer.close();
	}
	
	  private static Properties properties() {
	        var properties = new Properties();
	        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_HOST"));
	        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        return properties;
	    }
	

}
