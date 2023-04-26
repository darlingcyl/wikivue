package com.frank.wiki.controller;

import com.frank.wiki.domain.Ebook;
import com.frank.wiki.req.EbookReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.EbookResp;
import com.frank.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对ebook表的操作都在此接口下执行
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    EbookService ebookService;
    /**
     * 查询所有电子书接口
     */
    @GetMapping("/getAllEbook")
    public CommonResp<List<Ebook>> selectAllEbook(){

        List<Ebook> list = ebookService.getAll();
        CommonResp<List<Ebook>> resp = new CommonResp<List<Ebook>>();
        if (list.isEmpty()){
            resp.setSuccess(false);
            resp.setMessage("查询失败！返回数据为空");
            resp.setContent(list);
        }else {
            resp.setSuccess(true);
            resp.setMessage("查询成功");
            resp.setContent(list);
        }
        return resp;
    }

    /**
     * 带查询条件的查询（因为不能确定具体多少个参数，所以封装请求参数）
     * 封装请求参数的模拟方法
     */
    @GetMapping("/getEbook")
    public CommonResp<List<EbookResp>> selectEbook(EbookReq ebookReq){
        List<EbookResp> list = ebookService.getEbook(ebookReq);
        CommonResp<List<EbookResp>> resp = new CommonResp<List<EbookResp>>();
        if (list.isEmpty()){
            resp.setSuccess(false);
            resp.setMessage("查询失败！返回数据为空");
            resp.setContent(list);
        }else {
            resp.setSuccess(true);
            resp.setMessage("查询成功");
            resp.setContent(list);
        }
        return resp;
    }
}
