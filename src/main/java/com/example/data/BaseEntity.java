package com.example.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends PanacheEntity {

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime lastModified;

	public Long getId() {
		return id;
	}
}
