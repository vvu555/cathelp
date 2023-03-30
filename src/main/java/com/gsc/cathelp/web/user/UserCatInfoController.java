package com.gsc.cathelp.web.user;

import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.service.CatInfoService;
import com.gsc.cathelp.service.TagService;
import com.gsc.cathelp.service.TypeService;
import com.gsc.cathelp.service.UserCatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserCatInfoController {

    private static final String INPUT ="user/catinfos-input";
    private static final String REDIRECT_LIST ="redirect:/user/catinfos-input";


    @Autowired
    private CatInfoService catInfoService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/catinfos-input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("cat",new Cat());
        return INPUT;
    }

    @GetMapping("/{id}/catinfos-input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Cat cat = catInfoService.getCatInfo(id);
        cat.init();
        model.addAttribute("cat",cat);
        return INPUT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        catInfoService.deleteCat(id);
        redirectAttributes.addFlashAttribute("message","已删除");
        return "redirect:/user/userCatInfos";
    }

    @PostMapping("/catinfos")
    public String UserPost(@RequestParam("file") MultipartFile file, Cat cat, RedirectAttributes attributes, HttpSession session) throws IOException {
        cat.setUser((User) session.getAttribute("user"));
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
            c = catInfoService.updateCat(cat.getId(),cat);
        }
        if(c ==null){
            attributes.addFlashAttribute("message","失败");
        }
        else{
            attributes.addFlashAttribute("message","成功");
        }

        return "redirect:/user/userCatInfos";
    }




    //测试
    @Autowired
    private UserCatInfoService userCatInfoService;
    @GetMapping("/userCatInfos")
    public String catInfo(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model,HttpSession session){
        model.addAttribute("page", userCatInfoService.listUserCat(session,pageable));
        return "user/catinfos";
    }


}
