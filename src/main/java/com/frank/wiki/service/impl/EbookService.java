package com.frank.wiki.service.impl;

import com.frank.wiki.domain.Ebook;
import com.frank.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;
    public List<Ebook> getAll(){
        return ebookMapper.selectByExample(null);
    }
}
