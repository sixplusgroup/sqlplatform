package com.qiao.spring.service;

import com.github.sergdelft.sqlcorgi.schema.Column.DataType;
import com.qiao.ga.EvoSQL;
import com.qiao.spring.pojo.Column.FE_DATATYPE;
import com.qiao.spring.pojo.Table;
import com.qiao.brew.Runner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
public class TdgService {

    private static final Logger log = LogManager.getLogger(TdgService.class);
    private static final String url = "https://in2test.lsi.uniovi.es/sqlfpc/api/v2/rules.xml";
    private static DataType convert(FE_DATATYPE datatype) {
        // 仿照 SchemaConverter 类里的 convertColumn 方法
        if (datatype == FE_DATATYPE.INTEGER || datatype == FE_DATATYPE.DOUBLE) {
            return DataType.NUM;
        } else {
            return DataType.STRING;
        }
    }

    /**
     * 获取覆盖路径
     *
     * @param xml 请求 body，包含 sql query 和 table schema
     * @return List<String>  格式的路径
     * @throws Exception URL 时候的 MalformedURLException
     */
    private List<String> getCoveragePaths(String xml) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

        // 添加请求头
        connection.setRequestMethod("POST");
        // 这句必须要加，我是看的 Postman 的 Content Type 是这个
        connection.setRequestProperty("Content-Type", "text/plain");

        // 发送 post 请求
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
        writer.write(xml);
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post xml : " + xml);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder responseXML = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            responseXML.append(inputLine);
        }
        in.close();

        return parseResponseXML(responseXML.toString());
    }


    private static List<String> parseResponseXML(String xml) {
        List<String> coveragePaths = new ArrayList<>();
        System.out.println(xml);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            Document document = builder.parse(input);

            NodeList nodeList = document.getElementsByTagName("sql");
            for (int i = 1; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                coveragePaths.add(node.getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Found " + coveragePaths.size() + " paths: ");
        coveragePaths.forEach(log::info);
        return coveragePaths;
    }

    public List<String> getTestData(String query, Table[] tables, String xml) {
        List<String> statements = new ArrayList<>();
        try {
            List<String> coveragePath = getCoveragePaths(xml);
            log.info("获取覆盖路径完成");
            statements = Runner.run(query, tables, coveragePath);
//            statements = Runner.run(query, tables, getCoveragePaths(xml));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            statements.add(e.toString());
        }
        return statements;
    }

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><sqlfpc><version>2.3.26</version><sql>select * from car where name='Porsche';</sql><fpcrules>  <fpcrule><id>1</id><category>S</category><type>T</type><subtype>FF</subtype><location>1.1.[WHERE name = 'Porsche']</location>    <sql>SELECT * FROM car WHERE NOT(name = 'Porsche')</sql>    <description>--Some row in the table such that:--The WHERE condition fulfills:  --(F) name = 'Porsche' is FALSE</description>  </fpcrule>  <fpcrule><id>2</id><category>S</category><type>T</type><subtype>TF</subtype><location>1.1.[WHERE name = 'Porsche']</location>    <sql>SELECT * FROM car WHERE (name = 'Porsche')</sql>    <description>--Some row in the table such that:--The WHERE condition fulfills:  --(T) name = 'Porsche' is TRUE</description>  </fpcrule>  <fpcrule><id>3</id><category>S</category><type>N</type><subtype>NF</subtype><location>1.1.[name]</location>    <sql>SELECT * FROM car WHERE (name IS NULL)</sql>    <description>--Some row in the table such that:--The WHERE condition fulfills:  --(N) name is NULL</description>  </fpcrule></fpcrules></sqlfpc>";
        List<String> res = parseResponseXML(xml);
        for (String str : res) {
            System.out.println(str);
        }
    }
}
