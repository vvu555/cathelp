package com.gsc.cathelp.web.user;

import com.gsc.cathelp.dao.UserRepository;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String joinPage(){
        return "user/userregister";
    }

    @PostMapping("/doRegister")
    public String UserRegister(@RequestParam String username,User user,RedirectAttributes attributes){
        User SqlUser = userService.checkUsername(username);
        if(user != null && SqlUser == null){
            attributes.addFlashAttribute("successMessage","注册成功！");
            userService.insertUser(user);
            return "redirect:/user";
        }
        else{
            attributes.addFlashAttribute("Message","用户名以存在");
            return "redirect:/user/register";
        }
    }
}
