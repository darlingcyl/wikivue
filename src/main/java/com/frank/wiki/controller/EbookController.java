package com.frank.wiki.controller;

import com.frank.wiki.domain.Ebook;
import com.frank.wiki.service.impl.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    EbookService ebookService;
    /**
     * 查询所有电子书接口
     */
    @GetMapping("/getAllEbook")
    public List<Ebook> selectAllEbook(){

        return ebookService.getAll();
    }
}
