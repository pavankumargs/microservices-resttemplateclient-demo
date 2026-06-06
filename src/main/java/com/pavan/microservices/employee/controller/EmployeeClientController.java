package com.pavan.microservices.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pavan.microservices.employee.dto.Employee;

@RestController
@RequestMapping("/employeeclient")
public class EmployeeClientController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{id}")
	public Employee getEmployeeByID(@PathVariable Long id) {
		Employee employee = restTemplate.getForObject("http://localhost:8080/employee/"+id, Employee.class);
		return employee;
	}
	
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		Employee employe = restTemplate.postForObject("http://localhost:8080/employee", employee, Employee.class);
		return employe;
	}
}
