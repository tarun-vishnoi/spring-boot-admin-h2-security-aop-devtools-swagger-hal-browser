package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.EmployeeDao;
import com.app.entity.Employee;
import com.app.exception.EmployeeNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/employees")
@Api(value = "People Management System", description = "Operations pertaining to employee in People Management System")
public class EmployeeController {

	@Autowired
	EmployeeDao employeeDao;

	@ApiOperation(value = "View a list of available employees", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "Not Authorised"),
			@ApiResponse(code = 404, message = "Employee not found") })
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeDao.findAll();
		return new ResponseEntity<List<Employee>>(employees, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get an employee by Id")
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(
			@ApiParam(value = "Employee id from which employee object will retrieve", required = true) @PathVariable(value = "id") Integer employeeId)
			throws EmployeeNotFoundException {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@ApiOperation(value = "Add an Employee")
	@PostMapping
	public Employee createEmployee(
			@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestBody Employee employee) {
		return employeeDao.save(employee);
	}

	@ApiOperation(value = "Update an Employee")
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(
			@ApiParam(value = "Employee Id to update employee object", required = true) @PathVariable(value = "id") Integer employeeId,
			@ApiParam(value = "Update employee object", required = true) @Valid @RequestBody Employee employeeDetails)
			throws EmployeeNotFoundException {

		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found " + employeeId));
		employee.setEmail(employeeDetails.getEmail());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeDao.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@ApiOperation(value = "Delete an Employee")
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteEmployee(
			@ApiParam(value = "Employee Id from which employee object will delete from database table", required = true) @PathVariable(value = "id") Integer employeeId)
			throws EmployeeNotFoundException {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found " + employeeId));

		employeeDao.delete(employee);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
