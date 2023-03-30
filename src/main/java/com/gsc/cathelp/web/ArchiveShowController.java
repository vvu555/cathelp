package com.gsc.cathelp.web;

import com.gsc.cathelp.service.CatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ArchiveShowController {

    @Autowired
    private CatInfoService catInfoService;

    @GetMapping("/archive")
    public String archives(Model model){
        model.addAttribute("archiveMap",catInfoService.archiveCat());
        model.addAttribute("catCount",catInfoService.countCat());
        return "archive";
    }
}
