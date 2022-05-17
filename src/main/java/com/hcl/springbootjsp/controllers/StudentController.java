package com.hcl.springbootjsp.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.hcl.springbootjsp.model.Student;
import com.hcl.springbootjsp.model.StudentUser;

@Controller
@RequestMapping("/")
public class StudentController {

	@PersistenceContext
	EntityManager entityManager;

	public StudentController() {

	}
	
	//Render the form for the user
		@GetMapping("/register")
		public String registerNewUserView(Model model) {
			model.addAttribute("newUser", new StudentUser());
			return "register-new-user";
		}

	//called when you post from a tag
		@Transactional
		@PostMapping("/register")
		public RedirectView registerNewUser(@ModelAttribute("user") StudentUser newUser,
				RedirectAttributes redirectAttributes) {
			final RedirectView redirectView = new RedirectView("/student/login", true);
			final RedirectView redirectView1 = new RedirectView("/student/register", true);

			Query readAll = entityManager.createQuery("select u from NewUser u where u.username=?0 and u.password=?1");
			readAll.setParameter(0, newUser.getUsername());
			readAll.setParameter(1, newUser.getPassword());
			
			List<StudentUser> resultListAll = readAll.getResultList();

			//if it exists, add the user name to the session
			if(resultListAll.size() == 0) {
				entityManager.persist(newUser);
		
				redirectAttributes.addFlashAttribute("savedNewUser", newUser);
				redirectAttributes.addFlashAttribute("registerNewUserSuccess", true);
				return redirectView;
			} else {
				//redirectAttributes.addFlashAttribute("registerNewUserSuccess", false);
				return redirectView1;
			}
		}
		

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////// END REGISTER /////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// LOGIN ///////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

		@GetMapping("/login")
		public String login(Model model) {
			model.addAttribute("newUser", new StudentUser());
			return "login-new-user";
		}

	//called when you post from a tag
		@PostMapping("/login")
		public RedirectView loginSubmit(@ModelAttribute("user") StudentUser user, 
				HttpSession session,
				RedirectAttributes redirectAttributes) {
			final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
			final RedirectView redirectView1 = new RedirectView("/student/login", true);
			
			//check if the user name and password exist in the database,
			Query readAll = entityManager.createQuery("select u from NewUser u where u.username=?0 and u.password=?1");
			readAll.setParameter(0, user.getUsername());
			readAll.setParameter(1, user.getPassword());
			
			List<StudentUser> resultListAll = readAll.getResultList();

			if(resultListAll.size() == 1) {
				redirectAttributes.addFlashAttribute("savedNewUser", user);
				redirectAttributes.addFlashAttribute("registerNewUserSuccess", true);
				System.out.println("before session setting");
				session.setAttribute("loggedInUser", resultListAll.get(0));
				return redirectView;
				
			} else {
				redirectAttributes.addFlashAttribute("savedNewUser", user);
				redirectAttributes.addFlashAttribute("loginFailed", true);
				return redirectView1;
			}

		}	


		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/student/login";
		}

		
		@GetMapping("/addStudent")
		public String addStudentView(Model model, HttpSession session) {
			if(checkAuthetncate(session)) {		
				model.addAttribute("student", new Student());
				return "add-student";
			} else {
				return "redirect:/student/login";
			}
		}


		@PostMapping("/addStudent")
		@Transactional
		public RedirectView addStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
			final RedirectView redirectView = new RedirectView("/student/addStudent", true);

			entityManager.persist(student);

			redirectAttributes.addFlashAttribute("savedStudent", student);
			redirectAttributes.addFlashAttribute("addStudentSuccess", true);
			return redirectView;
		}
		

		@GetMapping("/updateStudent/{id}")
		public String updateStudent(Model m, HttpSession session, @PathVariable int id) {
			//already logged in
			if(checkAuthetncate(session)) {		
				System.out.println("inside update student b4 anything happens");
				Student updateS = entityManager.find(Student.class, id);
				m.addAttribute("student", updateS);
				System.out.println(updateS.getId() + " == " + id);
				return "update-student";
			} else {
				return "redirect:/student/login";
			}
			
		}

		@PostMapping("/updateStudent/{id}")
		@Transactional
		@ResponseBody
		public RedirectView updateStudent(@ModelAttribute("student") Student student,
				RedirectAttributes redirectAttributes) {
			final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
			entityManager.merge(student);
			return redirectView;
		}
		

		@GetMapping("/viewStudents")
		public String viewStudent(Model model, HttpSession session) {
			//already logged in
			if(checkAuthetncate(session)) {
				Query readAll = entityManager.createQuery("select s from Student s");
				List<Student> resultListAll = readAll.getResultList();
				resultListAll.forEach(System.out::println);
				model.addAttribute("student", resultListAll);
				return "view-student";
			} else {
				return "redirect:/student/login";
			}
		}


		@GetMapping("/deleteStudent/{id}")
		@Transactional
		@ResponseBody
		public RedirectView deleteStudent(HttpSession session, @PathVariable int id) {
			final RedirectView redirectView = new RedirectView("/student/viewStudents", true);
			final RedirectView redirectView1 = new RedirectView("/student/login", true);
			if(checkAuthetncate(session)) {
				Query delete = entityManager.createQuery("delete from Student s where s.id=?0");
				delete.setParameter(0, id);
				delete.executeUpdate();
				return redirectView;
			} else {
				return redirectView1;
			}
		}		


		private boolean checkAuthetncate(HttpSession session) {
			StudentUser user = (StudentUser) session.getAttribute("loggedInUser");
			System.out.println("user****=" + user);
			return user != null;
		}
}