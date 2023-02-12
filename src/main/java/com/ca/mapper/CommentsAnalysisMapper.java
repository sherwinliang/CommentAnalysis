package com.ca.mapper;

import com.ca.bean.CommentsAnalysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentsAnalysisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentsAnalysis record);

    CommentsAnalysis selectByPrimaryKey(Integer id);

    List<CommentsAnalysis> selectAll();

    int updateByPrimaryKey(CommentsAnalysis record);

    List<CommentsAnalysis> selectAvgScoreByRange(@Param("rangeCount") Integer rangeCount);

    List<CommentsAnalysis> selectSaleVolumnByRange(@Param("minVolumn") Integer minVolumn, @Param("rangeCount") Integer rangeCount);

    void deleteAll();
}