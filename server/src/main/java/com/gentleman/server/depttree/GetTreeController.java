package com.gentleman.server.depttree;

import com.gentleman.server.global.ErrorEnum;
import com.gentleman.server.global.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetTreeController {

    @RequestMapping(value = "department/getTree",method = RequestMethod.GET)
    public Result getDepartmentTree(){
        List<Department> trees = GetDepartmentTree.getTree(
                GetDepartmentTree.departments,0);
        return new Result(true, ErrorEnum.SUCCESS.getCode(),
                ErrorEnum.SUCCESS.getMsg(),trees);
    }
}
