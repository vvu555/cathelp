package com.gsc.cathelp.web.user;

import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CatInfoService catInfoService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping
    public String userLoginPage(){
        return "user/login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes,
                            @PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model ){
        User user = userService.checkUser(username, password);
        if(user == null){
            attributes.addFlashAttribute("message","账号或密码错误，请重新输入");
            return "redirect:/user";  //redirect为重定向，不可用model，返回login页面拿不到消息
        }
        else if(user != null && user.getType() == 0){
            model.addAttribute("userPage",catInfoService.listCat(pageable));
            model.addAttribute("userType",typeService.listTypeTop(6));
            model.addAttribute("user",(User) session.getAttribute("user"));
            model.addAttribute("userTag",tagService.listTagTop(10));
            user.setPassword(null);
            attributes.addFlashAttribute("message","管理员登陆成功");
            session.setAttribute( "user",user);
            return "redirect:/admin/adminIndex";
        }
        else if(user !=null && user.getType() == 1){
            model.addAttribute("userPage",catInfoService.listCat(pageable));
            model.addAttribute("userType",typeService.listTypeTop(6));
            model.addAttribute("user",(User) session.getAttribute("user"));
            model.addAttribute("userTag",tagService.listTagTop(10));
            user.setPassword(null);
            attributes.addFlashAttribute("message","登陆成功");
            session.setAttribute( "user",user);
            return "redirect:/user/userindex";
        }
        else{
            attributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/user";  //redirect为重定向，不可用model，返回login页面拿不到消息
        }
    }
    @GetMapping("/logout")// 注销
    public String userLoginOut(HttpSession session){
        session.removeAttribute("user"); //清空用户名
        return "redirect:/";
    }


    @PostMapping("/postEdit")
    public String post(User user, RedirectAttributes attributes) {
        User u;
        if (user.getId() == null) {
            attributes.addFlashAttribute("message", "发生错误");
            return "redirect:/user/userInfoAlter";
        } else {
            user.setUpdateTime(new Date());
            u = userInfoService.updateUser(user.getId(), user);
        }
        if (u == null) {
            attributes.addFlashAttribute("message", "失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功，请重新登陆");
        }
        return "redirect:/";
    }

    @GetMapping("/editPassword/{id}")
    public String edit(@PathVariable Long id, Model model){
        User user = userInfoService.getUser(id);
        model.addAttribute("user",user);
        return "user/userInfoAlter";
    }
}
