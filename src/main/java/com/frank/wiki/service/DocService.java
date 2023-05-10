package com.frank.wiki.service;

import com.frank.wiki.domain.Content;
import com.frank.wiki.domain.Doc;
import com.frank.wiki.domain.DocExample;
import com.frank.wiki.mapper.ContentMapper;
import com.frank.wiki.mapper.DocMapper;
import com.frank.wiki.req.DocQueryReq;
import com.frank.wiki.req.DocSaveReq;
import com.frank.wiki.resp.DocResp;
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
public class DocService {
    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ContentMapper contentMapper;

    /**
     * 根据id集合进行批量删除
     */
    public void delete(List<Long> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    /**
     * 查询所有
     * @return
     */
    public List<DocResp> all(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");//按照sort字段进行排序
        List<Doc> docList = docMapper.selectByExample(docExample);
        List<DocResp> respList = CopyUtil.copyList(docList,DocResp.class);
        return respList;
    }
    /**
     * 带条件查询
     * @param docReq
     * @return
     */
//criteria条件器
    public PageResp<DocResp> getDoc(DocQueryReq docReq){
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
//        criteria.andNameLike("%"+docReq.getName()+"%");
//        if (!ObjectUtils.isEmpty(docReq.getName())){
//            criteria.andNameLike("%"+docReq.getName()+"%");
//        }
        // 这里查出来的是Doc，里面字段和表字段是一样的，但是我们返回出去的是响应封装类，因此要转化一下
        PageHelper.startPage(docReq.getPage(),docReq.getSize());// 1代码从第几页查，3代表每页显示3条数据.
// 需要注意的是，这行代码只对遇到的第一个select查询有用，因此在使用时，想对那个select分页就写在它的上一行
        List<Doc> docList = docMapper.selectByExample(docExample);

        // 把List<Doc>转化成List<DocResp>
//        List<DocResp> respList = new ArrayList<>();
//        for (Doc doc : docList) {
//            DocResp docResp = new DocResp();
//            BeanUtils.copyProperties(doc,docResp);//使用spring提供的工具类实现对象拷贝
//            respList.add(docResp);
//        }

        // 使用转换工具类
        List<DocResp> respList=CopyUtil.copyList(docList,DocResp.class);
        PageInfo<Object> pageInfo = new PageInfo<>();
        PageResp pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;

//        return docMapper.selectByExample(docExample);
    }

    /**
     * 更新和添加方法
     */
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req,Content.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            long id = snowFlake.nextId();
            doc.setId(id);
            docMapper.insert(doc);
            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
            int result = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if(result==0){
                contentMapper.insert(content);
            }
//            contentMapper.updateByPrimaryKeyWithBLOBs(content);
        }
    }

    public void del(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询文档内容
     */
    public String findContent(Long id){
        Content content = contentMapper.selectByPrimaryKey(id);
        if (content != null){
            return content.getContent();
        }
        return "";
    }

    /**
     * 根据电子书id查询文档
     *
     * @return
     */
    public List<DocResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        List<DocResp> respList = CopyUtil.copyList(docList, DocResp.class);
        return respList;
    }
}
