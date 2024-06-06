package com.project.Controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.DAO.JobDAO;
import com.project.DAO.UserDAO;
import com.project.pojo.Job;
import com.project.pojo.User;
import com.project.utility.SessionFactoryUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class UserJobController {
	
	
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private JobDAO jobDAO;

    @PostMapping("/apply-job")
    public String applyToJob(@RequestParam Long jobId, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired or invalid. Please log in again.");
            return "redirect:/login";
        }
        
        try {
            boolean applied = jobDAO.applyToJob(user.getId(), jobId);
            if (applied) {
                redirectAttributes.addFlashAttribute("success", "Applied successfully!");
            } else {
                redirectAttributes.addFlashAttribute("info", "You have already applied for this job.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error applying for job.");
        }
        
        return "redirect:/job-list";
    }


    
    @GetMapping("/job-list")
    public String showJobsList(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login";  // Redirect to login if the user is not in the session
        }
        List<Job> jobs = jobDAO.findAll();
        model.addAttribute("jobs", jobs);
        model.addAttribute("user", user);  // Ensure the user is added to the model
        return "job-list";
    }
    
    
    @GetMapping("/applied-jobs")
    @Transactional // Ensure this is a read-only transaction if you're not modifying any data
    public String showAppliedJobs(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            try {
                // Assuming UserDAO is a Spring bean and properly integrated with transaction management
                user = userDAO.getById(user.getId());  // Fetch the user with managed transactions
                Hibernate.initialize(user.getJobs());  // Initialize lazy-loaded collection
                List<Job> appliedJobs = new ArrayList<>(user.getJobs());
                model.addAttribute("appliedJobs", appliedJobs);
            } catch (Exception e) {
                model.addAttribute("error", "An error occurred while fetching jobs.");
                return "error-page";
            }
        } else {
            model.addAttribute("error", "User not found. Please log in again.");
        }
        return "applied-jobs";
    }






}
