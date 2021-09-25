package com.aforo255.exam.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "operacion")
public class Operacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_operation")
	private Integer operacionId;
	
	@Column(name = "id_invoice")
	private Integer invoiceId;

	@Column(name = "amount")
	private double amount;
	
	@Column(name = "date")
	private LocalDateTime date;

}
