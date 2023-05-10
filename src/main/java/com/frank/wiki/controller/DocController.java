package com.frank.wiki.controller;

import com.frank.wiki.req.DocQueryReq;
import com.frank.wiki.req.DocSaveReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.DocResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对doc表的操作都在此接口下执行
 */
@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    DocService docService;
    /**
     * 查询所有文档接口
     */
    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<DocResp>> resp = new CommonResp<>();
        List<DocResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

    /**
     * 根据电子书id查询文档的方法,结果已经排好序
     */
    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
        CommonResp<List<DocResp>> resp = new CommonResp<List<DocResp>>();
        List<DocResp> list = docService.all(ebookId);
        resp.setMessage("查询成功");
        resp.setContent(list);
        return  resp;
    }
    /**
     * 查询文档内容
     */
    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id){
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    /**
     * 带查询条件的查询（因为不能确定具体多少个参数，所以封装请求参数）
     * 封装请求参数的模拟方法
     */
    @GetMapping("/getDoc")
    public CommonResp<PageResp<DocResp>> selectDoc(@Valid DocQueryReq docReq){
        PageResp<DocResp> doclist = docService.getDoc(docReq);
        CommonResp<PageResp<DocResp>> resp = new CommonResp<PageResp<DocResp>>();
//        if (doclist.isEmpty()){
//            resp.setSuccess(false);
//            resp.setMessage("查询失败！返回数据为空");
//            resp.setContent(list);
//
//        }else {
//            resp.setSuccess(true);
//            resp.setMessage("查询成功");
//            resp.setContent(list);
//        }
        resp.setContent(doclist);
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
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        List idList = Arrays.stream(idsStr.split(",")).map(Long::valueOf).collect(Collectors.toList());
//        List<String> stringList = Arrays.asList(idsStr.split(","));
        CommonResp resp = new CommonResp<>();
        docService.delete(idList);
        return resp;
    }

    @DeleteMapping("/del/{id}")
    public CommonResp del(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        docService.del(id);
        return resp;
    }
}
