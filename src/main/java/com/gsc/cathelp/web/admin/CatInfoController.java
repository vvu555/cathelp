package com.gsc.cathelp.web.admin;

import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.CatInfoService;
import com.gsc.cathelp.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class CatInfoController {


    private static final String INPUT ="admin/catinfos-input";
    private static final String LIST ="admin/catinfos";
    private static final String REDIRECT_LIST ="redirect:/admin/catinfos";


    @Autowired
    private CatInfoService catInfoService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/catinfos")
    public String catinfos(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                       Pageable pageable, CatQuery cat, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",catInfoService.listCat(pageable,cat));
        return LIST;
    }

    @PostMapping("/catinfos/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                   Pageable pageable, CatQuery cat, Model model){
        model.addAttribute("page",catInfoService.listCat(pageable,cat));
        return "admin/catinfos :: catList";
    }

    @GetMapping("/catinfos/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("cat",new Cat());
        return INPUT;
    }

    @GetMapping("/catinfos/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Cat cat = catInfoService.getCatInfo(id);
        cat.init();
        model.addAttribute("cat",cat);
        return INPUT;
    }

    //上传功能
    @PostMapping("/catinfos")
    public String post(@RequestParam("file") MultipartFile file,Cat cat, RedirectAttributes attributes, HttpSession session) throws IOException {
        cat.setType(typeService.getType(cat.getType().getId()));
        cat.setTags(tagService.listTag(cat.getTagIds()));
        Cat c;
        if (cat.getId() == null){
            /**
             * 上传图片
             */
            String filePath = "你的相对路径";
            //获取原始图片的拓展名
            String originalFilename = file.getOriginalFilename();
            //新的文件名字
            String newFileName = UUID.randomUUID() + originalFilename;
            //封装上传文件位置的全路径
            File targetFile = new File(filePath, newFileName);
            //把本地文件上传到封装上传文件位置的全路径
            file.transferTo(targetFile);
            //设置猫咪的首图路径
            String picturePath = targetFile.getAbsolutePath();
            cat.setFirstPicture(picturePath);
            /**
             * 保存
             */
            c = catInfoService.saveCat(cat);
        }
        else{
            Cat catInfo = catInfoService.getCatInfo(cat.getId());
            cat.setUser(catInfo.getUser());
            c = catInfoService.updateCat(cat.getId(),cat);
        }
        if(c ==null){
            attributes.addFlashAttribute("message","失败");
        }
        else{
            attributes.addFlashAttribute("message","成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/catinfos/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        catInfoService.deleteCat(id);
        redirectAttributes.addFlashAttribute("message","已删除");
        return REDIRECT_LIST;
    }



    //主页
    @GetMapping("/adminIndex")
    public String UserIndex(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model , HttpSession session){
        model.addAttribute("userPage",catInfoService.listCat(pageable));
        model.addAttribute("userType",typeService.listTypeTop(6));
        model.addAttribute("user",(User) session.getAttribute("user"));
        model.addAttribute("userTag",tagService.listTagTop(10));
        return "admin/adminIndex";
    }

    @GetMapping("/adminCatInfo/{id}")
    public String catInfo(@PathVariable Long id, Model model){
        model.addAttribute("userCat",catInfoService.getCatInfo(id));
        return "admin/adminIndexInfo";
    }
}
