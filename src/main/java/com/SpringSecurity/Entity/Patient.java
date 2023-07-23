package com.SpringSecurity.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient")
public class Patient {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(nullable = false, length = 128)
	@NotNull @Length(min = 5, max = 128)
	private String name;

	@Column(nullable = false)
	@NotNull @Length(min = 10, max = 10)
	private String contactDetails;

	@Column(nullable = false)
	@NotNull @Length(min = 2, max = 128)
	private String address;

	@Column(nullable = false)
	@NotNull
	private int pincode;


}
