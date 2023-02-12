package com.ca.controller;

import com.ca.bean.RespBean;
import com.ca.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/range")
    public RespBean selectAvgScoreByRange(@RequestParam("rangeCount") Integer rangeCount) {
        return new RespBean("success", "查询成功!", analysisService.selectAvgScoreByRange(rangeCount));
    }

    @GetMapping("/score-years")
    public Map<String,Object> getAverageScoreByYears(@RequestParam("productId")String productId) {
        Map<String, Object> map = analysisService.selectTrendByProductId(productId);
        return map;
    }

    @GetMapping("/sale-volumn")
    public Map<String,Object> selectSaleVolumnByRange(@RequestParam("rangeCount") Integer rangeCount) {
        Map<String, Object> map = analysisService.selectSaleVolumnByRange(rangeCount);
        return map;
    }

    @GetMapping("/sales")
    public RespBean analysisSales() {
        try {
            analysisService.analysis();
            return new RespBean("success", "成功!");
        }catch (Exception e){
            return new RespBean("error", "失败!");
        }
    }

    @GetMapping("/product/list")
    public RespBean getProductList() {
        try {
            List<String> list = analysisService.getProductList();
            return new RespBean("success", "成功!", list);
        }catch (Exception e){
            return new RespBean("error", "失败!");
        }
    }

    @GetMapping("/trend")
    public RespBean analysisTrend() {
        try {
            analysisService.analysisTrend();
            return new RespBean("success", "成功!");
        }catch (Exception e){
            return new RespBean("error", "失败!");
        }
    }

    @DeleteMapping("/filter")
    public RespBean filterComments() {
        try {
            return new RespBean("success", String.format("过滤数据成功，共删除%s条无效数据.",analysisService.filterComments()));
        }catch (Exception e){
            return new RespBean("error", "失败!");
        }
    }

    @GetMapping("/category-sales")
    public RespBean analysisSalesByCategory() {
        try {
            analysisService.analysisSalesByCategory();
            return new RespBean("success", "成功!");
        }catch (Exception e){
            return new RespBean("error", "失败!");
        }
    }
}
