package com.gsc.cathelp.service;
import com.gsc.cathelp.po.Tag;
import com.gsc.cathelp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);
    Tag updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);
}
