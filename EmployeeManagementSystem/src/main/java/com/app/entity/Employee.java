package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "EMPLOYEES")
@ApiModel(description = "Employee Details")
public class Employee {

	@ApiModelProperty(notes = "Database generated Employee Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(notes = "Employee first name")
	@Column(name = "first_name")
	private String firstName;

	@ApiModelProperty(notes = "Employee last name")
	@Column(name = "last_name")
	private String lastName;

	@ApiModelProperty(notes = "Employee email")
	@Column(name = "email", nullable = false, length = 200)
	private String email;

	@ApiModelProperty(notes = "Employee city")
	@Column(name = "city", nullable = false, length = 200)
	private String city;

	@JsonIgnore
	@ApiModelProperty(notes = "Manager Details")
	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Manager manager;

	public Employee() {

	}

	public Employee(Integer id, String firstName, String lastName, String email, String city, Manager manager) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.manager = manager;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", city=" + city + ", manager=" + manager + "]";
	}

}
