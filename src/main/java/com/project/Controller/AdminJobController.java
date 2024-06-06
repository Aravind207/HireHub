package com.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.DAO.JobDAO;
import com.project.pojo.Job;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminJobController {

    @Autowired
    private JobDAO jobDAO;

    @GetMapping("/jobs/create")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "create-edit-job";
    }

    @PostMapping("/jobs/create")
    public String createJob(@ModelAttribute Job job, RedirectAttributes redirectAttributes) {
        jobDAO.save(job);
        redirectAttributes.addFlashAttribute("successMessage", "Job created successfully!");
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/edit/{id}")
    public String editJobForm(@PathVariable("id") Long id, Model model) {
        Job job = jobDAO.getByJobId(id);
        if (job != null) {
            model.addAttribute("job", job);
            return "create-edit-job";
        } else {
            model.addAttribute("errorMessage", "Job not found");
            return "redirect:/jobs";
        }
    }

    @PostMapping("/jobs/update/{id}")
    public String updateJob(@PathVariable("id") Long id, @ModelAttribute Job job, RedirectAttributes redirectAttributes) {
        Job existingJob = jobDAO.getByJobId(id);
        if (existingJob != null) {
            existingJob.setOrganization(job.getOrganization());
            existingJob.setJobDesignation(job.getJobDesignation());
            existingJob.setJobDescription(job.getJobDescription());
            jobDAO.save(existingJob);
            redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
            return "redirect:/jobs";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Job not found");
            return "redirect:/jobs/edit/{id}";
        }
    }

    @GetMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        jobDAO.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Job deleted successfully!");
        return "redirect:/jobs";
    }


    @GetMapping("/jobs")
    public String showJobsPage(Model model, HttpServletRequest request) {
        List<Job> jobs = jobDAO.findAll();
        model.addAttribute("jobs", jobs);

        // Retrieve user role from session
        String userRole = (String) request.getSession().getAttribute("userRole"); 
        model.addAttribute("userRole", userRole);

        return "jobs-admin";  // This is the page that lists all jobs
    }



}
