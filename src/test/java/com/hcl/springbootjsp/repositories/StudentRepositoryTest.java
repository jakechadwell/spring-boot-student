package com.hcl.springbootjsp.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hcl.springbootjsp.model.Student;

class StudentRepositoryTest {

	private StudentRepository studentRepository;
	
	@Before
	public void setup() {
		studentRepository = new MemoryStudentRepository();
	}

	@Test
	public void testFindAll() {
		
		int expectedSize = 2;
		List<Student> list = studentRepository.findAll();
		//Checking the size of list
		assertEquals(expectedSize, list.size());
		Student s1= list.stream().filter(x -> x.getId()== 1L).findFirst().get();
		assertEquals("mkyong", s1.getName());
		s1 = list.stream().filter(x -> x.getId()== 2L).findFirst().get();
		assertEquals("jack", s1.getName());
	}

	@Test
	public void testFindOne() {
		
		String expectedValue = "mkyong";
		
		Student student = studentRepository.findOne(1L);
		assertEquals(expectedValue, student.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneWithException() {
		
		studentRepository.findOne(3L);
	}

	@Test
	public void testIsDataFromMemory() {
		
		boolean expectedValue = true;
		assertEquals(expectedValue, studentRepository.isDataFromMemory());
	}

}
