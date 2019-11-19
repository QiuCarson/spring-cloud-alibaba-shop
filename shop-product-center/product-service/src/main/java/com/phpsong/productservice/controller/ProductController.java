package com.phpsong.productservice.controller;

import io.swagger.annotations.Api;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: qiuyisong
 * @Date: 2019/11/19 14:59
 */
@RestController
@RequestMapping("/product/")
@Api("商品API")
public class ProductController {

    @GetMapping("list.do")
    public RequestEntity<Map<String, String>> list() {
        return null;
    }
}
