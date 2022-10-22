package com.integration.sample.config;

import java.util.Objects;

import org.springframework.integration.aop.MessageSourceMutator;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageSourceLoggingAdvice implements MessageSourceMutator {
	
	@Override
	public boolean beforeReceive(Object source) {

		log.info(">>>>>>>>> POLLER RUN <<<<<<<<<<<<<<");
		
		return true;
	}
	
	@Override
	public Message<?> afterReceive(Message<?> result, MessageSource<?> source) {

		if (Objects.nonNull(result))
			log.info("________________ RECEIVED _______________ {}", result.getPayload());

		return result;
	}

}
