package com.hcl.springbootjsp.repositories;

import java.util.Arrays;
import java.util.List;

import com.hcl.springbootjsp.model.H2Student;
import com.hcl.springbootjsp.model.Student;

public class MemoryStudentRepository implements StudentRepository{
	
	private static List<Student> DATA = Arrays.asList(
            new Student(1L, "mkyong", 38),
            new Student(2L, "jack", 40)
    );

    @Override
    public List<Student> findAll() {
        return DATA;
    }

    @Override
    public Student findOne(Long id) {
        return DATA.stream()
        		.filter(x -> x.getId() == id)
        		.findFirst()
        		.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean isDataFromMemory() {
        return true;
    }

	@Override
	public List<H2Student> h2FindAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H2Student h2FindOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
