package com.aforo255.exam.producer;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.aforo255.exam.domain.Operacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PayEventProducer {
	private static final String TOPIC = "transaction-events";
	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	public ListenableFuture<SendResult<Integer, String>> sendPayEvent(Operacion operacionEvent) throws JsonProcessingException {
		log.info("INICIO::sendPayEvent::");
		Integer key = operacionEvent.getOperacionId();
		String value = objectMapper.writeValueAsString(operacionEvent);

		ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, TOPIC);
		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				log.info("ON SUCCESS::sendPayEvent::");
				try {
					handleSuccess(key, value, result);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);

			}
		});
		return listenableFuture;
	}

	private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
		log.info("::buildProducerRecord::");
		List<Header> recordHeaders = List.of(new RecordHeader("deposit-event-source", "scanner".getBytes()));
		return new ProducerRecord<>(topic, null, key, value, recordHeaders);
	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("::handleFailure::{} ", ex.getMessage());
		try {

		} catch (Throwable throwable) {
			log.error("Error en OnFailure {}", throwable.getMessage());
		}
	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) throws JsonMappingException, JsonProcessingException {
		log.error("::handleSuccess::{} ", value);
//		TransactionRedis trxRedis = objectMapper.readValue(value, TransactionRedis.class);
//		transactionService.save(trxRedis);
//		log.info("Message Sent Successfully for the key :{} and the value is {},partition is {}", key, value,result.getRecordMetadata().partition());

	}
}
