package com.ca.service;

import com.ca.bean.Comments;
import com.ca.bean.RespPageBean;
import com.ca.mapper.CommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentsService {

    public static final Logger logger = LoggerFactory.getLogger(CommentsService.class);

    @Autowired
    private CommentsMapper commentsMapper;

    public RespPageBean getCommentsByPage(Integer page, Integer size, Comments comments, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Comments> data = commentsMapper.getCommentsByPage(page, size, comments, beginDateScope);
        Long total = commentsMapper.getTotal(comments, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public boolean addComments(Comments comments) {
        try{
            commentsMapper.insert(comments);
            return true;
        }catch(Exception e){
            logger.error("新增失败", e);
            return false;
        }
    }

    public boolean updateComments(Comments comments) {
        try{
            commentsMapper.updateByPrimaryKey(comments);
            return true;
        }catch(Exception e){
            logger.error("更新失败", e);
            return false;
        }
    }

    public boolean deleteComments(Integer id) {
        try{
            commentsMapper.deleteByPrimaryKey(id);
            return true;
        }catch(Exception e){
            logger.error("删除失败", e);
            return false;
        }
    }
}
