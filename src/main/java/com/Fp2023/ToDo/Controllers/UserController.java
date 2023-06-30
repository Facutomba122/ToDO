package com.Fp2023.ToDo.Controllers;

import com.Fp2023.ToDo.DTO.UserDTO;
import com.Fp2023.ToDo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/register")
    public String register(ModelMap modelMap){
        modelMap.addAttribute("user", new UserDTO());
        return "register.html";
    }
    
    @PostMapping("/registerData")
    public String registerData(@ModelAttribute("user") UserDTO userDTO) throws Exception{
        userService.saveUser(userDTO);
        return "redirect:/login?register=true";
    }
    
    
    @GetMapping("/login")
    public String login(ModelMap modelMap){
        modelMap.addAttribute("user", new UserDTO());
        return "login.html";
    }
    
    @PostMapping("/loginData")
    public String loginData(){
        return "home.html";
    }
    
}