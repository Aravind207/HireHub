package com.project.Controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.DAO.UserDAO;
import com.project.pojo.User;

import jakarta.persistence.PersistenceException;

@Controller
public class AdminController {

    @Autowired
    private UserDAO userDAO;
    
    
    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-edit-user";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user, Model model) {
        userDAO.save(user);
        return "redirect:/demo";  // Redirect to the listing page after creating the user
    }
    
    
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable("id") int id, Model model) {
        User user = userDAO.getById(id);
        model.addAttribute("user", user);
        return "create-edit-user";
    }

    

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        User existingUser = userDAO.getById(id);
        if (existingUser != null) {
            try {
                existingUser.setFirstname(user.getFirstname());
                existingUser.setLastname(user.getLastname());
                existingUser.setEmail(user.getEmail());
                // Assume password is handled correctly elsewhere or not changed
                userDAO.save(existingUser);
                redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
            } catch (PersistenceException e) {
                if (e.getCause() instanceof ConstraintViolationException) {
                    redirectAttributes.addFlashAttribute("errorMessage", "This email is already registered with another user.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "EmailID already registered with different user.");
                }
                return "redirect:/users/edit/" + id;
            }
            return "redirect:/demo";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/users/edit/" + id;
        }
    }



    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userDAO.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        return "redirect:/demo";
    }



    @GetMapping("/demo")
    public String showDemoPage(Model model) {
        List<User> users = userDAO.findAll();
        model.addAttribute("users", users);
        return "demo-admin";  // Assuming 'demo-admin' is the correct template that lists all users
    }


    
}
