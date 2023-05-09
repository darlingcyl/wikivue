package com.frank.wiki.controller;

import com.frank.wiki.req.CategoryQueryReq;
import com.frank.wiki.req.CategorySaveReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.CategoryResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 对category表的操作都在此接口下执行
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;
    /**
     * 查询所有分类接口
     */
    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<CategoryResp>> resp = new CommonResp<>();
        List<CategoryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    /**
     * 带查询条件的查询（因为不能确定具体多少个参数，所以封装请求参数）
     * 封装请求参数的模拟方法
     */
    @GetMapping("/getCategory")
    public CommonResp<PageResp<CategoryResp>> selectCategory(@Valid CategoryQueryReq categoryReq){
        PageResp<CategoryResp> categorylist = categoryService.getCategory(categoryReq);
        CommonResp<PageResp<CategoryResp>> resp = new CommonResp<PageResp<CategoryResp>>();
//        if (categorylist.isEmpty()){
//            resp.setSuccess(false);
//            resp.setMessage("查询失败！返回数据为空");
//            resp.setContent(list);
//
//        }else {
//            resp.setSuccess(true);
//            resp.setMessage("查询成功");
//            resp.setContent(list);
//        }
        resp.setContent(categorylist);
        resp.setMessage("获取所有分类信息成功，并且分页");
        resp.setSuccess(true);
        return resp;
    }

    /**
     * 保存方法
     * @param req
     * @return
     */
    @PostMapping("/save")
    public CommonResp save(@RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
