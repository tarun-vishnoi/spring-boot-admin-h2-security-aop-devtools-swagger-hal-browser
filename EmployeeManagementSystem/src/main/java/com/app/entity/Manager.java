package com.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "MANAGERS")
@ApiModel(description = "Manager Details")
public class Manager {

	@ApiModelProperty(notes = "Database generated Manager Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(notes = "Manager first name")
	@Column(name = "first_name")
	private String firstName;

	@ApiModelProperty(notes = "Manager last name")
	@Column(name = "last_name")
	private String lastName;

	@ApiModelProperty(notes = "Manager email")
	@Column(name = "email", nullable = false, length = 200)
	private String email;

	@ApiModelProperty(notes = "Employee Details")
	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	private List<Employee> employees;

	public Manager() {

	}

	public Manager(Integer id, String firstName, String lastName, String email, List<Employee> employees) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.employees = employees;
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

	public List<Employee> getEmployee() {
		return employees;
	}

	public void setEmployee(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", employees=" + employees + "]";
	}

}
