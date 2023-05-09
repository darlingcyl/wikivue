package com.frank.wiki.controller;


import com.frank.wiki.req.DocQueryReq;
import com.frank.wiki.req.DocSaveReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.DocQueryResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 对Doc表的操作都在此接口下执行
 */
@RestController
@RequestMapping("/Doc")
public class DocController {
    @Resource
    DocService DocService;
    /**
     * 查询所有文档接口
     */
    @GetMapping("/getAllDoc")
    public CommonResp<PageResp<DocQueryResp>> selectAllDoc(@Valid DocQueryReq DocReq){

        PageResp<DocQueryResp> Doclist = DocService.getAll(DocReq);
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<PageResp<DocQueryResp>>();
        resp.setContent(Doclist);
        resp.setMessage("获取所有文档信息成功，并且分页");
        resp.setSuccess(true);
//
//        if (Doclist.isEmpty()){
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
    @GetMapping("/getDoc")
    public CommonResp<PageResp<DocQueryResp>> selectDoc(@Valid DocQueryReq DocReq){
        PageResp<DocQueryResp> Doclist = DocService.getDoc(DocReq);
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<PageResp<DocQueryResp>>();
//        if (Doclist.isEmpty()){
//            resp.setSuccess(false);
//            resp.setMessage("查询失败！返回数据为空");
//            resp.setContent(list);
//
//        }else {
//            resp.setSuccess(true);
//            resp.setMessage("查询成功");
//            resp.setContent(list);
//        }
        resp.setContent(Doclist);
        resp.setMessage("获取所有文档信息成功，并且分页");
        resp.setSuccess(true);
        return resp;
    }

    /**
     * 保存方法
     * @param req
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        DocService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        DocService.delete(id);
        return resp;
    }
}
