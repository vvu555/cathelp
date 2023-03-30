package com.gsc.cathelp.web.admin;


import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.CatAdoptService;
import com.gsc.cathelp.vo.CatQuery;
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
public class AdoptController {

    @Autowired
     private CatAdoptService catAdoptService;

    @GetMapping("/adopt")
    public String adoptTo(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model) {
        model.addAttribute("apply",catAdoptService.listAdoptApply(pageable));
        return "admin/adopt";
    }

    @GetMapping("/adopt-input")
    public String adoptToInput() {
        return "/admin/adopt-input";
    }

    @GetMapping("/adopt/{id}/adopt-input")
    public String look(@PathVariable Long id, Model model){
        model.addAttribute("adopt",catAdoptService.getAdoptId(id));
        return "admin/adopt-input";
    }

    @PostMapping("/adoptApply")
    public String post(Adopt adopt, RedirectAttributes attributes){
        catAdoptService.save(adopt);
        attributes.addFlashAttribute("message","审核通过");
        return "redirect:/admin/adopt";
    }
}
