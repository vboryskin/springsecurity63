package com.alexcompany.springsecurity62.controller;

import com.alexcompany.springsecurity62.dto.UserDTO;
import com.alexcompany.springsecurity62.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", defaultValue = "false") String hasError,
                        Model model){
        if(Boolean.valueOf(hasError)){
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }
    @GetMapping("/registration")
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserDTO user, BindingResult result,
                           WebRequest webRequest, Errors errors){
        if(result.hasErrors()){
            return "/register";
        }
        userService.registerNewUser(user);
        return "redirect:/index";
    }

}
