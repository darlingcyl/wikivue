package com.frank.wiki.controller;

import com.frank.wiki.req.EbookQueryReq;
import com.frank.wiki.req.EbookSaveReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.EbookResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public CommonResp<PageResp<EbookResp>> selectAllEbook(@Valid EbookQueryReq ebookReq){

        PageResp<EbookResp> ebooklist = ebookService.getAll(ebookReq);
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<PageResp<EbookResp>>();
        resp.setContent(ebooklist);
        resp.setMessage("获取所有电子书信息成功，并且分页");
        resp.setSuccess(true);
//
//        if (ebooklist.isEmpty()){
//            resp.setSuccess(false);
//            resp.setMessage("查询失败！返回数据为空");
//            resp.setContent(list);
//        }else {
//            resp.setSuccess(true);
//            resp.setMessage("查询成功");
//            resp.setContent(list);
//        }
        return resp;
    }

    /**
     * 带查询条件的查询（因为不能确定具体多少个参数，所以封装请求参数）
     * 封装请求参数的模拟方法
     */
    @GetMapping("/getEbook")
    public CommonResp<PageResp<EbookResp>> selectEbook(@Valid EbookQueryReq ebookReq){
        PageResp<EbookResp> ebooklist = ebookService.getEbook(ebookReq);
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<PageResp<EbookResp>>();
//        if (ebooklist.isEmpty()){
//            resp.setSuccess(false);
//            resp.setMessage("查询失败！返回数据为空");
//            resp.setContent(list);
//
//        }else {
//            resp.setSuccess(true);
//            resp.setMessage("查询成功");
//            resp.setContent(list);
//        }
        resp.setContent(ebooklist);
        resp.setMessage("获取所有电子书信息成功，并且分页");
        resp.setSuccess(true);
        return resp;
    }

    /**
     * 保存方法
     * @param req
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
