package com.example.petProject1.Controller.User;

import com.example.petProject1.Model.User.User;
import com.example.petProject1.Security.User_Details;
import com.example.petProject1.Service.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService operations;

    @Autowired
    public void setOperations(UserService operations) {
        this.operations = operations;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginUser") User user) {
        return "user/loginPage";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("registerUser") User user) {
        return "user/registerPage";
    }

    @PostMapping("/reg")
    public String registerInDB(@ModelAttribute("registerUser") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerUser", user);
            return "user/registerPage";
        }
        operations.saveUser(user);
        return "redirect:/user/login";
    }
    //PROFILE
    @GetMapping("/profile")
    public String profilePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// Получаем имя текущего пользователя
        Object principal = authentication.getPrincipal();
        if (principal instanceof User_Details) {
            User_Details details = (User_Details) principal;
            User user = details.getUser(); // Получаем объект Manager
            model.addAttribute("user",user);
        }
        return "user/profile"; // Возвращаем имя представления
    }
}
