package com.doctory;

import com.doctory.infra.entity.*;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback(value = true)
class AdminServiceApplicationTests {


	@Autowired
	private EntityManager entityManager;

	@Test
	@Transactional
	void testAddNewAddress() {

		Address address=new Address();
		address.setAddressLine1("medan sirsiyan");
		address.setAddressLine2("mehshi east Champaran");
		address.setState("BR");
		address.setCountry("IN");
		address.setPinCode("845426");
		entityManager.persist(address);
		Assertions.assertThat(address.getId()).isNotNull();
	}

	@Test
	@Transactional
	void testAddNewPersonWithAddress() {

		Address address=new Address();
		address.setAddressLine1("medan sirsiyan");
		address.setAddressLine2("mehshi east Champaran");
		address.setState("BR");
		address.setCountry("IN");
		address.setPinCode("845426");

		Person person=new Person();
		person.setEmail("abc@gmail.com");
		person.setMobileNo("9052708146");
		person.setAlternateNo("8788978998");
		person.setFirstName("md");
		person.setLastName("sagir");
		person.setSurname("ansari");

		person.setAddress(address);
		entityManager.persist(person);

		Assertions.assertThat(person.getId()).isNotNull();
	}

	@Test
	@Transactional
	void testAddNewHospital() {

		Address address=new Address();
		address.setAddressLine1("medan sirsiyan");
		address.setAddressLine2("mehshi east Champaran");
		address.setState("BR");
		address.setCountry("IN");
		address.setPinCode("845426");

		Hospital hospital=new Hospital();
		hospital.setHospitalName("KK Hospital");
		hospital.setAddress(address);
		entityManager.persist(hospital);

		Assertions.assertThat(hospital.getId()).isNotNull();
	}
	@Test
	@Transactional
	void testAddNewBranch() {

		Address address=new Address();
		address.setAddressLine1("line1");
		address.setAddressLine2("line2");
		address.setState("BR");
		address.setCountry("IN");
		address.setPinCode("845426");

		Hospital hospital=entityManager.getReference(Hospital.class,1L);

		Branch branch=new Branch();
		branch.setAddress(address);
		branch.setBranchName("Old- branch");
		branch.setHospital(hospital);
		entityManager.persist(branch);

		Assertions.assertThat(branch.getId()).isNotNull();
	}

	@Test
	@Transactional
	void testAddNewDoctor() {

		Address address=new Address();
		address.setAddressLine1("medan sirsiyan");
		address.setAddressLine2("mehshi east Champaran");
		address.setState("BR");
		address.setCountry("IN");
		address.setPinCode("845426");

		Person person=new Person();
		person.setEmail("abc@gmail.com");
		person.setMobileNo("9052708146");
		person.setAlternateNo("8788978998");
		person.setFirstName("md");
		person.setLastName("sagir");
		person.setSurname("ansari");

		person.setAddress(address);

		Hospital hospital=entityManager.getReference(Hospital.class,1L);

		Branch branch=new Branch();
		branch.setAddress(address);
		branch.setBranchName("Old- branch");
		branch.setHospital(hospital);
		entityManager.persist(branch);

		Doctor doctor=new Doctor();
		doctor.setDegree("MBBS");
		doctor.setSpecialty("Surgon");
		doctor.setPracticeName("Medico");
		doctor.setSpecialty("N/A");
		doctor.setPerson(person);
		doctor.setBranch(branch);
		entityManager.persist(doctor);

		Assertions.assertThat(doctor.getId()).isNotNull();
	}

}
