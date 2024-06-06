package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.DAO.UserDAO;
import com.project.pojo.User;

@Controller
public class RegistrationController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  
    }

    @PostMapping("/register-user")
    public String registerUser(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        User newUser = new User();
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        newUser.setEmail(email);
        newUser.setPassword(password);
        
        userDAO.save(newUser);

        redirectAttributes.addFlashAttribute("message", "User registered successfully! You can now log in.");
        return "redirect:/register";  // Redirect to the registration page to display the message
    }

}
