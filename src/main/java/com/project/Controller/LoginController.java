package com.project.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.project.DAO.JobDAO;  // Ensure you have a JobDAO
import com.project.DAO.UserDAO;
import com.project.pojo.Job;   // Ensure you have a Job entity
import com.project.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    JobDAO jobDAO;  // Autowired JobDAO to manage jobs

    @GetMapping("/")
    public String landing(HttpServletRequest request, Model model) {
        return "login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String adminLogin(Model model) {
        return "login-admin";
    }

    @PostMapping("/admin-user")
    public String adminUser(@RequestParam String email, @RequestParam String password, Model model) {
        final String adminEmail = "admin@gmail.com";
        final String adminPassword = "admin@123";

        if (email.equals(adminEmail) && password.equals(adminPassword)) {
            List<Job> jobs = jobDAO.findAll();  // Fetch all jobs
            model.addAttribute("jobs", jobs);  // Add jobs to the model
            return "jobs-admin";  // Redirect to the jobs admin page if login is successful
        } else {
            model.addAttribute("error", "Invalid admin credentials. Please try again.");
            return "login-admin";
        }
    }

    @PostMapping("/login-user")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model) {
        User user = userDAO.findByEmailAndPassword(email, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);  // Storing user in session
            model.addAttribute("user", user);  // Adding user to model
            List<Job> jobs = jobDAO.findAll();
            model.addAttribute("jobs", jobs);
            return "job-list";  // Ensure this view is configured to handle 'user'
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";
        }
    }
    
    
}
