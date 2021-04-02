package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.model.entity.Product;
import com.gentleman.model.mapper.ProductMapper;
import com.gentleman.server.service.ExportService;
import com.gentleman.server.service.PoiService;
import com.gentleman.server.service.WebOperationService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 一粒尘埃
 * @date 2021/1/23/16:03
 */
@Controller
public class ExcelController {

    private static final String prefix = "excel";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ExportService exportService;

    @Autowired
    private PoiService poiService;

    @Autowired
    private Environment environment;

    @Autowired
    private WebOperationService webOperationService;

    @RequestMapping(value = prefix+"/getList",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getProductList(@RequestParam String name){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            List<Product> products = productMapper.selectAll(name);
            response.setData(products);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;

    }

    /**
     * 导出
     * @param name
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = prefix+"/export",method = RequestMethod.GET)
    public @ResponseBody String export(String name,HttpServletResponse response) throws Exception {
        final String[] headers = {"名称","单位","单价","库存量","备注","采购日期"};
        List<Product> products =  productMapper.selectAll(name);
        List<Map<Integer,Object>> exports = exportService.export(products);
        if(!CollectionUtils.isEmpty(exports) && exports.size() >0){
            //Workbook workbook = poiService.fillExcelSheetData(exports,headers,environment.getProperty("export.product.sheet.name"));
            Workbook workbook = poiService.manageSheet(exports,headers,environment.getProperty("export.product.sheet.name"));
            webOperationService.downloadExcel(response, workbook,environment.getProperty("export.product.file.name"));
            return environment.getProperty("export.product.file.name");
        }
        return null;
    }
}
