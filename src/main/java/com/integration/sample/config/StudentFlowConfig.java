package com.integration.sample.config;

import java.time.Duration;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaInboundChannelAdapterSpec;

import com.integration.sample.repository.dao.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class StudentFlowConfig {

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private MessageSourceLoggingAdvice loggingAdvice;

	@Bean
	public IntegrationFlow mainFlow() {

		// @formatter:off
		return IntegrationFlows.from(jpaInboundSpec(), p -> p.poller(pollerSpec()))
								.handle(m -> log.info("***** Entity Passed Down, which shouldn't be: {}", m.getPayload()))
								.get();
		// @formatter:on
	}

	@Bean
	public JpaInboundChannelAdapterSpec jpaInboundSpec() {

		// @formatter:off
		return Jpa.inboundAdapter(emf)
					.entityClass(Student.class)
					.jpaQuery("SELECT s FROM Student s WHERE s.status is NULL")
					// trick to update polled record
					.deleteAfterPoll(true)
					.maxResults(1);
		// @formatter:on
	}

	@Bean
	public PollerSpec pollerSpec() {

		// @formatter:off
		return Pollers.fixedDelay(Duration.ofMinutes(2))
					.maxMessagesPerPoll(1)
					.transactional()
					.advice(loggingAdvice);
		// @formatter:on
	}

}
