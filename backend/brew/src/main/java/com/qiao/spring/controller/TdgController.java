package com.qiao.spring.controller;

import com.qiao.spring.pojo.Table;
import com.qiao.spring.service.TdgService;
import com.qiao.spring.util.BodyStructure;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class TdgController {
    @PostMapping(value="/tdg")
    public List<String> getTestData(@RequestBody BodyStructure body) {
        String query = body.getQuery();
        System.out.println("收到 tdg 请求，query 是: " + query);
        Table[]  tables = body.getTables();
        String xml = body.getCoverageRequestXML();
        TdgService service = new TdgService();
        return service.getTestData(query, tables, xml);
    }
}
