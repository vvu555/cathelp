package com.gsc.cathelp.web.user;

import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.CatAdoptService;
import com.gsc.cathelp.service.CatInfoService;
import com.gsc.cathelp.service.TagService;
import com.gsc.cathelp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserIndexController {
    @Autowired
    private CatInfoService catInfoService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CatAdoptService catAdoptService;

    @GetMapping("/userindex")
    public String UserIndex(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model , HttpSession session){
        model.addAttribute("userPage",catInfoService.listCat(pageable));
        model.addAttribute("userType",typeService.listTypeTop(6));
        model.addAttribute("user",(User) session.getAttribute("user"));
        model.addAttribute("userTag",tagService.listTagTop(10));
        return "user/userindex";
    }

    @GetMapping("/userCatInfo/{id}")
    public String catInfo(@PathVariable Long id, Model model){
        model.addAttribute("userCat",catInfoService.getCatInfo(id));
        return "user/userIndexInfo";
    }


    //测试
    @GetMapping("/tt")
    public String UserTset(@PageableDefault(size = 12,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("userPage",catAdoptService.listUnAdoptCat(pageable));
        model.addAttribute("userType", typeService.listTypeTop(6));
        model.addAttribute("userTag", tagService.listTagTop(10));
        return "user/userTest";
    }

}
