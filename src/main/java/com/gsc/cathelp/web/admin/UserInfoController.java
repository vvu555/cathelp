package com.gsc.cathelp.web.admin;

import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.UserInfoService;
import com.gsc.cathelp.vo.CatQuery;
import com.gsc.cathelp.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserInfoController {

    private static final String INPUT ="admin/userInfos-input";
    private static final String LIST ="admin/userInfos";
    private static final String REDIRECT_LIST ="redirect:/admin/userInfos";


    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/userInfos")
    public String Users(@PageableDefault(size = 10, sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, UserQuery user, Model model){
        model.addAttribute("page",userInfoService.listUser(pageable,user));
        return "admin/userInfos";
    }

    @GetMapping("/userInfos/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userInfoService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "已删除");
        return REDIRECT_LIST;
    }
    @PostMapping("/userInfos/search")
    public String search(@PageableDefault(size = 5,sort = {"username"},direction = Sort.Direction.DESC)
                                 Pageable pageable, UserQuery user, Model model) {
        model.addAttribute("page", userInfoService.listUser(pageable,user));
        return "admin/userInfos :: userList";
    }

    @GetMapping("/userInfos/input")
    public String input(Model model) {
        model.addAttribute("user", new User());
        return INPUT;
    }

    @PostMapping("/userInfos")
    public String post(User user, RedirectAttributes attributes) {
        User u;
        if (user.getId() == null) {
            u = userInfoService.saveUser(user);
        } else {
            u = userInfoService.updateUser(user.getId(), user);
        }
        if (u == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/userInfos/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        User user = userInfoService.getUser(id);
        model.addAttribute("user",user);
        return INPUT;
    }
}
