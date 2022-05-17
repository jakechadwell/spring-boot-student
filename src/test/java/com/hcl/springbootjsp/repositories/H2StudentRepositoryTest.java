package com.hcl.springbootjsp.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcl.springbootjsp.model.H2Student;

class H2StudentRepositoryTest {

	private StudentRepository sr = null;
	
	@BeforeEach
	public void setUp() throws Exception {
		sr = new H2StudentRepository();
		H2StudentRepository hr = (H2StudentRepository)sr;
		hr.add(new H2Student(1L, "mkyong", 25));
		hr.add(new H2Student(2L, "jack", 30));
	}

	@Test
	public void testFindAll() {
		System.out.println("hey");
		int expectedSize = 2;
		List<H2Student> list = sr.h2FindAll();
		//Checking the size of list
		assertEquals(expectedSize, list.size());
		H2Student s1= list.stream().filter(x -> x.getId()== 1L).findFirst().get();
		assertEquals("mkyong", s1.getName());
		s1 = list.stream().filter(x -> x.getId()== 2L).findFirst().get();
		assertEquals("jack", s1.getName());
	}

	@Test
	public void testFindOne() {
		String expectedValue = "mkyong";
		
		H2Student student = sr.h2FindOne(1L);
		assertEquals(expectedValue, student.getName());
	}

	@Test
	public void testIsDataFromMemory() {
		fail("Not yet implemented");
	}

}
