package com.gsc.cathelp.web.user;

import com.gsc.cathelp.service.CatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserArchiveShowController {

    @Autowired
    private CatInfoService catInfoService;

    @GetMapping("/archive")
    public String archives(Model model){
        model.addAttribute("userArchiveMap",catInfoService.archiveCat());
        model.addAttribute("userCatCount",catInfoService.countCat());
        return "user/userarchive";
    }
}
