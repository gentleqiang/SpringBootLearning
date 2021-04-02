package com.gentleman.server.service;

import com.gentleman.model.entity.Product;
import com.gentleman.model.mapper.ProductMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 一粒尘埃
 * @date 2021/1/25/22:04
 */
@Service
public class ExportService {

    @Autowired
    private ProductMapper productMapper;

    public List<Map<Integer,Object>> export(final List<Product> products){
        /*名称","单位","单价","库存量","备注","采购日期*/
        List<Map<Integer,Object>> rows = Lists.newArrayList();
        Map<Integer,Object> rowMap;
        for (Product product : products) {
            rowMap = Maps.newHashMap();
            rowMap.put(0,product.getName());
            rowMap.put(1,product.getUnit());
            rowMap.put(2,product.getPrice());
            rowMap.put(3,product.getStock());
            rowMap.put(4,product.getRemark());
            rowMap.put(5,product.getPurchaseDate());
            rows.add(rowMap);
        }
        return rows;
    }
}
