package com.gsc.cathelp.web.user;

import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.*;
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
import sun.awt.geom.AreaOp;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserAdoptController {
    @Autowired
    private CatInfoService catInfoService;

    @Autowired
    private CatAdoptService catAdoptService;


    @GetMapping("/useradopt")
    public String UserIndex(@PageableDefault(size = 12,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("userPage",catAdoptService.listUnAdoptCat(pageable));
        return "user/useradopt";
    }

    @GetMapping("/userAdopt/{id}")
    public String AdoptInfo(@PathVariable Long id, Model model){
        model.addAttribute("cat",catInfoService.getCatInfo(id));
        return "user/adoptinfo";
    }

    @PostMapping("/adoptPost")
    public String adoptPost(Adopt adopt, RedirectAttributes attributes, HttpSession session){

        adopt.setUser((User) session.getAttribute("user"));
        catAdoptService.userSave(adopt);
        attributes.addFlashAttribute("message","申请成功，等待管理员审核");
        return "redirect:/user/tt";
    }


    @GetMapping("/userGetAdoptApply")
    public String getApply(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model,HttpSession session){
        model.addAttribute("page", catAdoptService.listUserApply(session,pageable));
        return "user/applyAdoptInfo";
    }

}
