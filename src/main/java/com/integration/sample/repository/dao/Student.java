package com.integration.sample.repository.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE non_existent_entity SET status = 'PICKED' WHERE id = ?")
@DynamicUpdate
@Entity
public class Student implements Persistable<Long> {

	@Id
	private Long id;

	private String name;

	private String status;

	@Override
	public boolean isNew() {
		return false;
	}

}
