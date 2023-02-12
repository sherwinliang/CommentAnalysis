package com.ca.utils;

import com.ca.bean.CategorySales;
import com.ca.bean.Comments;
import com.ca.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created on 2017/12/20.
 */
public class Util {
    public static Integer MINIMUM_SALE_VOLUMN = 30;
    public static final Map<String, String> productIdTypeMap = new ConcurrentHashMap();
    public static final List<CategorySales> categorySalesCache = new CopyOnWriteArrayList<>();
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public static void buildProductIdTypeCache(Comments comments){
        for(ProductTypeEnum productTypeEnum:ProductTypeEnum.values()){
            for(String keyword:productTypeEnum.getKeyWords().split(",")){
                if((comments.getSummary()!=null && comments.getSummary().contains(keyword)) ||
                        (comments.getText()!=null && comments.getText().contains(keyword))){
                    productIdTypeMap.put(comments.getProductId(),productTypeEnum.getName());
                    break;
                }
            }
        }
    }
}
