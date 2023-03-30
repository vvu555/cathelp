package com.gsc.cathelp.web.user;

import com.gsc.cathelp.po.Type;
import com.gsc.cathelp.service.CatInfoService;
import com.gsc.cathelp.service.TypeService;
import com.gsc.cathelp.vo.CatQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserTypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private CatInfoService catInfoService;

    @GetMapping("/typesShow/{id}")
    public String types(@PageableDefault(size = 12, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(100);
        if (id == -1) {
            id = types.get(0).getId();
        }
        CatQuery blogQuery = new CatQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", catInfoService.listTypeCat(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "user/userTypes";
    }
}
