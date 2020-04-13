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

import com.app.dao.ManagerDao;
import com.app.entity.Manager;
import com.app.exception.ManagerNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/managers")
@Api(value = "People Management System", description = "Operations pertaining to managers in People Management System")
public class ManagerController {

	@Autowired
	ManagerDao managerDao;

	@ApiOperation(value = "View a list of available managers", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not Authorised"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "Manager not found") })
	@GetMapping
	public ResponseEntity<List<Manager>> getAllManagers() {
		List<Manager> managers = managerDao.findAll();
		return new ResponseEntity<List<Manager>>(managers, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get the manager by Id")
	@GetMapping("/{id}")
	public ResponseEntity<Manager> getManagerById(
			@ApiParam(value = "Manager id from which manager object will retrieve", required = true) @PathVariable(value = "id") Integer managerId)
			throws ManagerNotFoundException {
		Manager manager = managerDao.findById(managerId)
				.orElseThrow(() -> new ManagerNotFoundException("Manager Not Found " + managerId));
		return ResponseEntity.ok().body(manager);
	}

	@ApiOperation(value = "Add a Manager")
	@PostMapping
	public Manager createManager(
			@ApiParam(value = "Manager object store in database table", required = true) @Valid @RequestBody Manager manager) {
		return managerDao.save(manager);
	}

	@ApiOperation(value = "Update a Manager")
	@PutMapping("/{id}")
	public ResponseEntity<Manager> updateManager(
			@ApiParam(value = "Manager Id to update manager object", required = true) @PathVariable(value = "id") Integer managerId,
			@ApiParam(value = "Update manager object", required = true) @Valid @RequestBody Manager managerDetails)
			throws ManagerNotFoundException {

		Manager manager = managerDao.findById(managerId)
				.orElseThrow(() -> new ManagerNotFoundException("Manager Not Found " + managerId));
		manager.setEmail(managerDetails.getEmail());
		manager.setLastName(managerDetails.getLastName());
		manager.setFirstName(managerDetails.getFirstName());
		final Manager updatedManager = managerDao.save(manager);
		return ResponseEntity.ok(updatedManager);
	}

	@ApiOperation(value = "Delete a Manager")
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteManager(
			@ApiParam(value = "Manager Id from which manager object will delete from database table", required = true) @PathVariable(value = "id") Integer managerId)
			throws ManagerNotFoundException {
		Manager manager = managerDao.findById(managerId)
				.orElseThrow(() -> new ManagerNotFoundException("Manager Not Found " + managerId));

		managerDao.delete(manager);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
}
