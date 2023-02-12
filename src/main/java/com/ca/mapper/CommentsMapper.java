package com.ca.mapper;

import com.ca.bean.Comments;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CommentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comments record);

    Comments selectByPrimaryKey(Integer id);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);

    List<Comments> getCommentsByPage(@Param("page")Integer page, @Param("size")Integer size, @Param("comments")Comments comments, @Param("beginDateScope")Date[] beginDateScope);

    Long getTotal(@Param("comments")Comments comments, @Param("beginDateScope")Date[] beginDateScope);

    int deleteInvalidData();

    int deleteInvalidData2();
}