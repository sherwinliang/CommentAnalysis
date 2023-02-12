package com.ca.service;

import com.ca.bean.CategorySales;
import com.ca.bean.Comments;
import com.ca.bean.CommentsAnalysis;
import com.ca.bean.CommentsTrend;
import com.ca.mapper.CommentsAnalysisMapper;
import com.ca.mapper.CommentsMapper;
import com.ca.mapper.CommentsTrendMapper;
import com.ca.utils.ProductTypeEnum;
import com.ca.utils.Util;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    public static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    @Autowired
    private CommentsAnalysisMapper commentsAnalysisMapper;
    @Autowired
    private CommentsTrendMapper commentsTrendMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    final Calendar calendar = Calendar.getInstance();

    public List<CommentsAnalysis> selectAvgScoreByRange(Integer rangeCount) {
        List<CommentsAnalysis> data = new ArrayList<>();
        try {
            data.addAll(commentsAnalysisMapper.selectAvgScoreByRange(rangeCount));
            if(!CollectionUtils.isEmpty(data)){
                data.forEach(item -> {
                    String name = Util.productIdTypeMap.get(item.getProductId());
                    item.setProductId(item.getProductId()+"_"+(name==null?"其它":name));
                });
            }
            return data;
        }catch (Exception e){
            return Collections.EMPTY_LIST;
        }
    }

    public Map<String, Object> selectTrendByProductId(String productId) {
        Map<String, Object> map = null;
        try {
            productId = productId.split("_")[0];
            List<CommentsTrend> list = commentsTrendMapper.selectTrendByProductId(productId);
            List<String> years = new ArrayList<>();
            List<BigDecimal> scores = new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                list.forEach(commentsTrend -> {
                    years.add(commentsTrend.getYear());
                    scores.add(commentsTrend.getAvgScore());
                });
                map = new HashMap<>();
                map.put("years", years);
                map.put("avgScores", scores);
            }
            return map;
        }catch (Exception e){
            return map;
        }
    }

    public Map<String, Object> selectSaleVolumnByRange(Integer rangeCount) {
        Map<String, Object> map = null;
        try {
            List<CommentsAnalysis> data = commentsAnalysisMapper.selectSaleVolumnByRange(Util.MINIMUM_SALE_VOLUMN,rangeCount);
            List<String> productIds = new ArrayList<>();
            List<Integer> saleVolumns = new ArrayList<>();
            if(!CollectionUtils.isEmpty(data)){
                data.forEach(item -> {
                    String name = Util.productIdTypeMap.get(item.getProductId());
                    String productId = item.getProductId()+"_"+(name==null?"其它":name);
                    productIds.add(productId);
                    saleVolumns.add(item.getSaleVolumn());
                });
                map = new HashMap<>();
                map.put("productIds", productIds);
                map.put("saleVolumns", saleVolumns);
            }
            return map;
        }catch (Exception e){
            return map;
        }
    }
    public void analysis() {
        List<Comments> data = null;
        Map<String, CommentsAnalysis> analysesMap = new HashMap<>();
        try {
            commentsAnalysisMapper.deleteAll();
            Long total = commentsMapper.getTotal(new Comments(), null);
            int page = 0;
            int size = 10000;
            while(total-page>0){
                data = commentsMapper.getCommentsByPage(page,size,new Comments(),null);
                data.stream().forEach(comments -> {
                    CommentsAnalysis analysis = null;
                    if((analysis=analysesMap.get(comments.getProductId()))!=null){
                        analysis.setSaleVolumn(analysis.getSaleVolumn()+1);
                        analysis.setTotalScore(analysis.getTotalScore()+comments.getScore());
                        analysis.setMaxScore(comments.getScore()>analysis.getMaxScore()?comments.getScore():analysis.getMaxScore());
                        analysis.setMinScore(comments.getScore()<analysis.getMinScore()?comments.getScore():analysis.getMinScore());
                    }else{
                        analysis = new CommentsAnalysis();
                        analysis.setProductId(comments.getProductId());
                        analysis.setSaleVolumn(1);
                        analysis.setTotalScore(comments.getScore());
                        analysis.setMaxScore(comments.getScore());
                        analysis.setMinScore(comments.getScore());
                        analysesMap.put(comments.getProductId(),analysis);
                        Util.buildProductIdTypeCache(comments);
                    }
                });
                logger.info("正在分析数据。");
                page+=10000;
            }
            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            List<CommentsAnalysis> list = analysesMap.values().stream().filter(x -> {
                if(x.getSaleVolumn()>=Util.MINIMUM_SALE_VOLUMN && Util.productIdTypeMap.get(x.getProductId())!=null){
                    x.setAvgScore(new BigDecimal(x.getTotalScore()).divide(new BigDecimal(x.getSaleVolumn()),2,BigDecimal.ROUND_HALF_UP));
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            for(int i=0;i<list.size();i++){
                commentsAnalysisMapper.insert(list.get(i));
                if(i%1000==0){
                    logger.info("正在提交数据。");
                    sqlSession.commit();
                }
            }
            logger.info("数据分析完成。");
        }catch (Exception e){
        }
    }
    public void analysisTrend() {
        List<Comments> data = null;
        Map<String, CommentsTrend> analysesMap = new HashMap<>();
        try {
            commentsTrendMapper.deleteAll();
            Long total = commentsMapper.getTotal(new Comments(), null);
            int page = 0;
            int size = 10000;
            while(total-page>0){
                data = commentsMapper.getCommentsByPage(page,size,new Comments(),null);
                data.stream().forEach(comments -> {
                    CommentsTrend analysis = null;
                    if((analysis=analysesMap.get(comments.getProductId()+"_"+getYear(comments.getTime())))!=null){
                        analysis.setSaleVolumn(analysis.getSaleVolumn()+1);
                        analysis.setTotalScore(analysis.getTotalScore()+comments.getScore());
                    }else{
                        analysis = new CommentsTrend();
                        analysis.setProductId(comments.getProductId());
                        analysis.setYear(getYear(comments.getTime())+"");
                        analysis.setSaleVolumn(1);
                        analysis.setTotalScore(comments.getScore());
                        analysesMap.put(comments.getProductId()+"_"+analysis.getYear(),analysis);
                        Util.buildProductIdTypeCache(comments);
                    }
                });
                page+=10000;
                logger.info("正在分析数据。");
            }
            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            List<CommentsTrend> list = analysesMap.values().stream().filter(x -> {
                if(x.getSaleVolumn()>=Util.MINIMUM_SALE_VOLUMN && Util.productIdTypeMap.get(x.getProductId())!=null){
                    x.setAvgScore(new BigDecimal(x.getTotalScore()).divide(new BigDecimal(x.getSaleVolumn()),2,BigDecimal.ROUND_HALF_UP));
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            for(int i=0;i<list.size();i++){
                commentsTrendMapper.insert(list.get(i));
                if(i%1000==0){
                    logger.info("正在提交数据。");
                    sqlSession.commit();
                }
            }
            logger.info("数据分析完成。");
        }catch (Exception e){
        }
    }
    private int getYear(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public List<String> getProductList() {
        List<String> list = commentsTrendMapper.selectProductList();
        List<String> result = null;
        if(!CollectionUtils.isEmpty(list)){
            result = list.stream().filter(product -> {
                String name = Util.productIdTypeMap.get(product);
                return name!=null;
            }).map(product -> {
                String name = Util.productIdTypeMap.get(product);
                return product+"_"+name;
            }).collect(Collectors.toList());
        }
        return result;
    }

    /* @description: 删除同一商品被同一用户多次评论的多余数据，删除相同详情的评论数据，因耗时长，开两个session操作防止超时报错
     * @author: Sherwin Liang
     * @timestamp: 2022/9/19 0:11
    */
    public int filterComments() {
        SqlSession sqlSession = null;
        SqlSession sqlSession2 = null;
        try {
            sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.REUSE, false);
            int count = commentsMapper.deleteInvalidData();
            sqlSession.commit();
            sqlSession2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.REUSE, false);
            int count2 = commentsMapper.deleteInvalidData2();
            sqlSession2.commit();
            logger.info(String.format("过滤数据成功，共删除%s条无效数据", count+count2));
            return count+count2;
        }catch (Exception e){
            sqlSession.rollback();
            sqlSession2.rollback();
            logger.error("删除数据异常。", e);
            return 0;
        }
    }

    public void analysisSalesByCategory() {
        List<Comments> data = null;
        Map<ProductTypeEnum,Integer> categorySalesMap = new HashMap<>();
        try {
            Util.categorySalesCache.clear();
            Long total = commentsMapper.getTotal(new Comments(), null);
            int page = 0;
            int size = 10000;
            AtomicInteger otherCount = new AtomicInteger(0);
            while(total-page>0){
                data = commentsMapper.getCommentsByPage(page,size,new Comments(),null);
                data.stream().forEach(comments -> {
                    boolean canFind = false;
                    for(ProductTypeEnum productTypeEnum:ProductTypeEnum.values()){
                        for(String keyword:productTypeEnum.getKeyWords().split(",")){
                            if((comments.getSummary()!=null && comments.getSummary().contains(keyword)) ||
                                    (comments.getText()!=null && comments.getText().contains(keyword))){
                                int saleVolumn = categorySalesMap.get(productTypeEnum)==null?1:(categorySalesMap.get(productTypeEnum)+1);
                                categorySalesMap.put(productTypeEnum,saleVolumn);
                                canFind = true;
                                break;
                            }
                        }
                        if(canFind){
                            break;
                        }
                    }
                    //最后还是找不到，则归为其它类型的销量
                    if(!canFind){
                        otherCount.getAndIncrement();
                    }
                });
                page+=10000;
                logger.info("正在分析数据。");
            }
            categorySalesMap.forEach((k,v)->{
                CategorySales categorySales = new CategorySales();
                categorySales.setCategory(k.getName());
                categorySales.setSaleVolumn(v);
                Util.categorySalesCache.add(categorySales);
            });
            Util.categorySalesCache.sort((x1,x2) -> x1.getSaleVolumn()<=x2.getSaleVolumn()?1:-1);
            logger.info("数据分析完成。");
        }catch (Exception e){
        }
    }
}
