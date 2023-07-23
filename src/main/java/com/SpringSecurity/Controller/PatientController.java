package com.SpringSecurity.Controller;

import com.SpringSecurity.Entity.Patient;
import com.SpringSecurity.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
	@RequestMapping("/patients")
public class PatientController {

	@Autowired private PatientRepository repo;
	
	@PostMapping
	@ApiOperation(value = "Create Patient", authorizations = {@Authorization(value = "Bearer")})
	public ResponseEntity<Patient> createPatient(@RequestBody @Valid Patient product) {
		Patient savedProduct = repo.save(product);
		return ResponseEntity.ok().body(savedProduct);

	}
	
	@GetMapping
	public List<Patient> listOfPatient() {
		return repo.findAll();
	}


	@DeleteMapping("/delete/{patientId}")
	public ResponseEntity<String> deletePatient(@PathVariable("patientId") Integer id) {
		repo.deleteById(id);
		return new ResponseEntity<>("Data Deleted Successfully!", HttpStatus.OK);
	}

	@DeleteMapping("/update/{patientId}")
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient , @PathVariable("patientId") Integer id ) {
		try {
			Patient patientDataWithId = repo.findById(id).orElseThrow();

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(patient);
			Patient patientsData = mapper.readValue(jsonString, Patient.class);
			patientsData.setId(id);
			Patient saveLoadsData = repo.save(patientsData);
			return ResponseEntity.ok().body(saveLoadsData);

		} catch (Exception e) {
			System.out.println("PatientData not found in database " + e);
			return null;
		}
	}

}
