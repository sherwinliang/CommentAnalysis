package com.ca.mapper;

import com.ca.bean.CommentsTrend;
import java.util.List;

public interface CommentsTrendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentsTrend record);

    CommentsTrend selectByPrimaryKey(Integer id);

    List<CommentsTrend> selectAll();

    int updateByPrimaryKey(CommentsTrend record);

    List<CommentsTrend> selectTrendByProductId(String productId);

    List<String> selectProductList();

    void deleteAll();
}