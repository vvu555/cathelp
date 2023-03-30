package com.gsc.cathelp.web.admin;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.CatInfoService;
import com.gsc.cathelp.service.TagService;
import com.gsc.cathelp.service.TypeService;
import com.gsc.cathelp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private CatInfoService catInfoService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes,
                        @PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model ){
        User user = userService.checkUser(username, password);
        if(user == null) {
            attributes.addFlashAttribute("message","此管理员账号或密码错误，请重新输入");
            return "redirect:/admin";  //redirect为重定向，不可用model，返回login页面拿不到消息
        }
        else if (user !=null && user.getType() == 0){
            model.addAttribute("userPage",catInfoService.listCat(pageable));
            model.addAttribute("userType",typeService.listTypeTop(6));
            model.addAttribute("user",(User) session.getAttribute("user"));
            model.addAttribute("userTag",tagService.listTagTop(10));
            user.setPassword(null);
            session.setAttribute( "user",user);
            attributes.addFlashAttribute("message","管理员登陆成功");
            return "redirect:/admin/adminIndex";
        }
        else{
            attributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/admin";  //redirect为重定向，不可用model，返回login页面拿不到消息
        }


        }

    @GetMapping("/logout")// 注销
    public String loginout(HttpSession session){
        session.removeAttribute("user"); //清空用户名
        return "redirect:/";
    }
}



