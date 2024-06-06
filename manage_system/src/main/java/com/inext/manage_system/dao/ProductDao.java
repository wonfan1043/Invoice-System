package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inext.manage_system.model.Product;

@Mapper
public interface ProductDao {

    // 商品リスト取得
    public List<Product> selectProduct();

}
