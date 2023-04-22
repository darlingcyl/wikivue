package com.frank.wiki.service.impl;

import com.frank.wiki.domain.Ebook;
import com.frank.wiki.domain.EbookExample;
import com.frank.wiki.mapper.EbookMapper;
import com.frank.wiki.req.EbookReq;
import com.frank.wiki.resp.EbookResp;
import com.frank.wiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    /**
     * 查询所有
     * @return
     */
    public List<Ebook> getAll(){
        return ebookMapper.selectByExample(null);
    }

    /**
     * 带条件查询
     * @param ebookReq
     * @return
     */
//criteria条件器
    public List<EbookResp> getEbook(EbookReq ebookReq){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+ebookReq.getName()+"%");
        // 这里查出来的是Ebook，里面字段和表字段是一样的，但是我们返回出去的是响应封装类，因此要转化一下
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        // 把List<Ebook>转化成List<EbookResp>
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);//使用spring提供的工具类实现对象拷贝
//            respList.add(ebookResp);
//        }

        // 使用转换工具类
        List<EbookResp> respList=CopyUtil.copyList(ebookList,EbookResp.class);
        return respList;

//        return ebookMapper.selectByExample(ebookExample);
    }
}
