package com.ca.controller;

import com.ca.bean.Comments;
import com.ca.bean.RespBean;
import com.ca.bean.RespPageBean;
import com.ca.service.CommentsService;
import com.ca.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/list")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Comments comments, Date[] beginDateScope) {
        return commentsService.getCommentsByPage(page, size, comments, beginDateScope);
    }

    @PostMapping("/add")
    public RespBean addComments(Comments comments) {
        if (commentsService.addComments(comments)) {
            return new RespBean("success", "新增成功!");
        }
        return new RespBean("error", "新增失败!");
    }

    @DeleteMapping("/delete/{id}")
    public RespBean deleteCommentById(@PathVariable Integer id) {
        if (commentsService.deleteComments(id)) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @PutMapping("/update")
    public RespBean updateComments(Comments comments) {
        if (commentsService.updateComments(comments)) {
            return new RespBean("success", "更新成功!");
        }
        return new RespBean("error", "更新失败!");
    }

    @GetMapping("/category-sales")
    public RespBean querySalesByCategory() {
        Util.categorySalesCache.forEach(x -> System.out.println(String.format("数据类型：%s, 销量：%s",x.getCategory(),x.getSaleVolumn())));
        return new RespBean("success", "查询成功!", Util.categorySalesCache);
    }
}
