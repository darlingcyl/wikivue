package com.frank.wiki.service;

import com.frank.wiki.domain.Category;
import com.frank.wiki.domain.CategoryExample;
import com.frank.wiki.mapper.CategoryMapper;
import com.frank.wiki.req.CategoryQueryReq;
import com.frank.wiki.req.CategorySaveReq;
import com.frank.wiki.resp.CategoryResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.util.CopyUtil;
import com.frank.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 查询所有
     * @return
     */
    public List<CategoryResp> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");//按照sort字段进行排序
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryResp> respList = CopyUtil.copyList(categoryList,CategoryResp.class);
        return respList;
    }
    /**
     * 带条件查询
     * @param categoryReq
     * @return
     */
//criteria条件器
    public PageResp<CategoryResp> getCategory(CategoryQueryReq categoryReq){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        criteria.andNameLike("%"+categoryReq.getName()+"%");
//        if (!ObjectUtils.isEmpty(categoryReq.getName())){
//            criteria.andNameLike("%"+categoryReq.getName()+"%");
//        }
        // 这里查出来的是Category，里面字段和表字段是一样的，但是我们返回出去的是响应封装类，因此要转化一下
        PageHelper.startPage(categoryReq.getPage(),categoryReq.getSize());// 1代码从第几页查，3代表每页显示3条数据.
// 需要注意的是，这行代码只对遇到的第一个select查询有用，因此在使用时，想对那个select分页就写在它的上一行
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        // 把List<Category>转化成List<CategoryResp>
//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);//使用spring提供的工具类实现对象拷贝
//            respList.add(categoryResp);
//        }

        // 使用转换工具类
        List<CategoryResp> respList=CopyUtil.copyList(categoryList,CategoryResp.class);
        PageInfo<Object> pageInfo = new PageInfo<>();
        PageResp pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

//        return categoryMapper.selectByExample(categoryExample);
    }

    /**
     * 更新和添加方法
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            long id = snowFlake.nextId();
            category.setId(id);
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
