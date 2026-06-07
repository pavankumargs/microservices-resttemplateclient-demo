package com.pavan.microservices.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.pavan.microservices.employee.dto.Employee;

@RestController
@RequestMapping("/employeeclient")
public class EmployeeClientController {

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * below method is the basic getForObject() method used to basic get calls. if
	 * you need only response body then we can use getForObject() Method it doesn't
	 * support status code and headers
	 */

//	@GetMapping("/{id}")
//	public Employee getEmployeeByID(@PathVariable Long id) {
//		Employee employee = restTemplate.getForObject("http://localhost:8080/employee/"+id, Employee.class);
//		return employee;
//	}

	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		Employee employe = restTemplate.postForObject("http://localhost:8080/employee", employee, Employee.class);
		return employe;
	}

	/*
	 * getForEntity() Method Example
	 * 
	 * we can include status code and headers also
	 */

//	@GetMapping("/{id}")
//	public Employee getEmpById(@PathVariable Long id) {
//		ResponseEntity<Employee> response = restTemplate.getForEntity("http://localhost:8080/employee/" + id,
//				Employee.class);
//		System.out.println(response.getHeaders());
//		System.out.println(response.getStatusCode());
//		return response.getBody();
//	}
	
	/*
	 * exchange() method we can send any request with full control it supports
	 * get,post,put,delete and it gives status code, response body and headers
	 */
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeByID(@PathVariable Long id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Employee> response = restTemplate.exchange("http://localhost:8080/employee/" + id,
					HttpMethod.GET, entity, Employee.class);
			System.out.println(response.getStatusCode());
			System.out.println(response.getHeaders());
			return ResponseEntity.ok(response.getBody());
		} catch (HttpClientErrorException.NotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}
	}
}
