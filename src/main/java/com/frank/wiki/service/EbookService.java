package com.frank.wiki.service;

import com.frank.wiki.domain.Ebook;
import com.frank.wiki.domain.EbookExample;
import com.frank.wiki.mapper.EbookMapper;
import com.frank.wiki.req.EbookQueryReq;
import com.frank.wiki.req.EbookSaveReq;
import com.frank.wiki.resp.CommonResp;
import com.frank.wiki.resp.EbookResp;
import com.frank.wiki.resp.PageResp;
import com.frank.wiki.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.frank.wiki.util.SnowFlake;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 查询所有
     * @return
     */
    public PageResp<EbookResp> getAll(EbookQueryReq ebookReq){
//        PageHelper.startPage(1,3);
//        return ebookMapper.selectByExample(null);
        // 这里查出来的是Ebook，里面字段和表字段是一样的，但是我们返回出去的是响应封装类，因此要转化一下
        PageHelper.startPage(ebookReq.getPage(),ebookReq.getSize());// 1代码从第几页查，3代表每页显示3条数据.
// 需要注意的是，这行代码只对遇到的第一个select查询有用，因此在使用时，想对那个select分页就写在它的上一行
        List<Ebook> ebookList = ebookMapper.selectByExample(null);

        // 把List<Ebook>转化成List<EbookResp>
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);//使用spring提供的工具类实现对象拷贝
//            respList.add(ebookResp);
//        }

        // 使用转换工具类
        List<EbookResp> respList=CopyUtil.copyList(ebookList,EbookResp.class);
        PageInfo<Object> pageInfo = new PageInfo<>();
        PageResp pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 带条件查询
     * @param ebookReq
     * @return
     */
//criteria条件器
    public PageResp<EbookResp> getEbook(EbookQueryReq ebookReq){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
//        criteria.andNameLike("%"+ebookReq.getName()+"%");
        if (!ObjectUtils.isEmpty(ebookReq.getName())){
            criteria.andNameLike("%"+ebookReq.getName()+"%");
        }
        if(!ObjectUtils.isEmpty(ebookReq.getCategoryId2())){ // 根据二级分类查询内容,带条件
            criteria.andCategory2IdEqualTo(ebookReq.getCategoryId2());
        }
        // 这里查出来的是Ebook，里面字段和表字段是一样的，但是我们返回出去的是响应封装类，因此要转化一下
        PageHelper.startPage(ebookReq.getPage(),ebookReq.getSize());// 1代码从第几页查，3代表每页显示3条数据.
// 需要注意的是，这行代码只对遇到的第一个select查询有用，因此在使用时，想对那个select分页就写在它的上一行
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
        PageInfo<Object> pageInfo = new PageInfo<>();
        PageResp pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

//        return ebookMapper.selectByExample(ebookExample);
    }

    /**
     * 更新和添加方法
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            long id = snowFlake.nextId();
            ebook.setId(id);
            ebookMapper.insert(ebook);
        } else {
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
