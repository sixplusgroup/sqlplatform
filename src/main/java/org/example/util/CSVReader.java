package org.example.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2022/3/18
 **/
public class CSVReader {
    public static List<String> readCsv(String path) {
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(FileUtil.file(path));
        List<CsvRow> rows = data.getRows();
        List<String> res = new ArrayList<>();
        for (CsvRow csvRow : rows) {
            res.add(csvRow.get(0).replaceAll("\"\"","\""));//getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> res = readCsv("../../src/main/resources/org/example/sqls.csv");
        System.out.println(res.size());
    }

}