package com.hcl.springbootjsp.repositories;

import java.util.List;

import com.hcl.springbootjsp.model.H2Student;
import com.hcl.springbootjsp.model.Student;

public interface StudentRepository{
	
	List<Student> findAll();
	List<H2Student> h2FindAll();
	
	Student findOne(Long id);
	H2Student h2FindOne(Long id);

    boolean isDataFromMemory();
}
