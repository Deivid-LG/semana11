package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testFindOwnerById() {
		String FIRST_NAME_EXPECTED = "George";
		Integer ID = 1;

		Owner owner = null;

		try {
			owner = this.ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
	}

	@Test
	public void testFindOwnerByFirstName() {
		String FIND_FIRST_NAME = "David";
		int SIZE_EXPECTED = 1;

		List<Owner> owners = this.ownerService.findByFirstName(FIND_FIRST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	@Test
	public void testFindOwnerByLastName() {
		String LAST_NAME = "Wonderland";
		int SIZE_EXPECTED = 2;

		List<Owner> owners = this.ownerService.findByLastName(LAST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	//Chocce

	@Test
	public void testCreateOwner() {
		String FIRST_NAME = "Alice";
		String LAST_NAME = "Wonderland";
		String ADDRESS = "123 Some St";
		String CITY = "Lima";
		String TELEPHONE = "987654321";

		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

		Owner ownerCreated = this.ownerService.create(owner);

		log.info("OWNER CREATED :" + ownerCreated.toString());

		assertNotNull(ownerCreated.getId());
		assertEquals(FIRST_NAME, ownerCreated.getFirstName());
		assertEquals(LAST_NAME, ownerCreated.getLastName());
		assertEquals(ADDRESS, ownerCreated.getAddress());
		assertEquals(CITY, ownerCreated.getCity());
		assertEquals(TELEPHONE, ownerCreated.getTelephone());
	}

	//Chocce

	@Test
	public void testDeleteOwner() {
		String FIRST_NAME = "Michael";
		String LAST_NAME = "Jackson";
		String ADDRESS = "Neverland";
		String CITY = "Los Angeles";
		String TELEPHONE = "123123123";

		// ------------ Create ---------------
		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
		owner = this.ownerService.create(owner);
		log.info("" + owner);

		// ------------ Delete ---------------
		try {
			this.ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------
		try {
			this.ownerService.findById(owner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException e) {
			assertTrue(true);
		}
	}
}
