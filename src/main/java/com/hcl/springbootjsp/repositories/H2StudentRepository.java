package com.hcl.springbootjsp.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hcl.springbootjsp.model.H2Student;
import com.hcl.springbootjsp.model.Student;

public class H2StudentRepository implements StudentRepository {

private static EntityManager entityManager = null;
	
	public H2StudentRepository() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jbd-pu");
		entityManager = emf.createEntityManager();
	}

	@Override
	public List<Student> findAll() {
		Query readAll = entityManager.createQuery("select s from Student s");
        List<Student> resultListAll = readAll.getResultList();
        return resultListAll;
	}

	@Override
	public Student findOne(Long id) {
		// TODO Auto-generated method stub
		Query read = entityManager.createQuery("select s from Student s where s.studentId=?0");
		read.setParameter(0, id);
		Student student = (Student) read.getSingleResult();
		return student;
	}

	@Override
	public boolean isDataFromMemory() {
		// TODO Auto-generated method stub
		return false;
	}

	public void add(H2Student student) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(student);
		transaction.commit();
	}

	@Override
	public List<H2Student> h2FindAll() {
		Query readAll = entityManager.createQuery("select s from H2Student s");
        List<H2Student> resultListAll = readAll.getResultList();
        return resultListAll;
	}

	@Override
	public H2Student h2FindOne(Long id) {
		Query read = entityManager.createQuery("select s from H2Student s where s.id=?0");
		read.setParameter(0, id);
		H2Student student = (H2Student) read.getSingleResult();
		return student;
	}

}
