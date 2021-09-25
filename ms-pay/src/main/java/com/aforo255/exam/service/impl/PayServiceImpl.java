package com.aforo255.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aforo255.exam.domain.Operacion;
import com.aforo255.exam.repository.OperacionRepository;
import com.aforo255.exam.service.PayService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

	@Autowired
	private OperacionRepository operacionRepository;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public Operacion save(Operacion t) {
		log.info("save: {}", new Gson().toJson(t));
		return operacionRepository.save(t);
	}

}
