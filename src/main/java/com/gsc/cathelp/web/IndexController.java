package com.gsc.cathelp.web;

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

@Controller
public class IndexController {

    @Autowired
    private CatInfoService catInfoService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable,Model model){
        model.addAttribute("page",catInfoService.listCat(pageable));
        model.addAttribute("Types",typeService.listTypeTop(6));
        model.addAttribute("Tags",tagService.listTagTop(10));
        return "index";
    }

    @GetMapping("/catinfo/{id}")
    public String catinfo(@PathVariable Long id,Model model){
        model.addAttribute("cat",catInfoService.getCatInfo(id));
        return "catinfo";
    }
}
