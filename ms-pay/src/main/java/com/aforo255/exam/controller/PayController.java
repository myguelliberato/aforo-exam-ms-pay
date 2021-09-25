package com.aforo255.exam.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aforo255.exam.domain.Operacion;
import com.aforo255.exam.producer.PayEventProducer;
import com.aforo255.exam.service.PayService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@RefreshScope
@RestController
@Slf4j
public class PayController {

	@Autowired
	private PayService payService;

	@Autowired
	private PayEventProducer payEventProducer;

	@PostMapping("/v1/save")
	public ResponseEntity<Operacion> save(@RequestBody Operacion request) throws JsonProcessingException {
		log.info("INICIO:::::save");
		request.setDate(LocalDateTime.now());
		Operacion operacion = payService.save(request);
		log.info("FIN SAVE TX:::: INICIO::: KAFKA:::::");
		payEventProducer.sendPayEvent(operacion);
		log.info("::::::FIN SEND EVENTO:::::::::::");
		return ResponseEntity.status(HttpStatus.CREATED).body(operacion);
	}

}
