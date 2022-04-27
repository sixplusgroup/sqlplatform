package com.qiao.brew;

import com.qiao.brew.consumer.OutputConsumer;
import com.qiao.brew.data.Result;
import com.qiao.brew.db.ConnectionData;
import com.qiao.brew.db.QueryRunner;
import com.qiao.brew.generator.Generator;
import com.qiao.brew.generator.Output;
import com.qiao.brew.sql.vendor.PostgreSQLOptions;
import com.qiao.brew.sql.vendor.VendorOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PipelineTest {
    @Test
    @DisplayName("Methods should not be called more than once")
    void pipelineTest() {
        final QueryRunner queryRunner = mock(QueryRunner.class);
        final String sql = "Select * From any;";
        final ConnectionData connectionData = new ConnectionData("", "", "", "");
        final Result result = mock(Result.class);
        when(queryRunner.runQuery(sql, connectionData)).thenReturn(result);

        final Generator generator = mock(Generator.class);
        final VendorOptions vendorOptions = new PostgreSQLOptions();
        final List<Output> outputs = Arrays.asList(new Output("1", "one"), new Output("2", "two"));
        when(generator.generate(result, vendorOptions)).thenReturn(outputs);

        final OutputConsumer outputConsumer = mock(OutputConsumer.class);

        Pipeline p = Pipeline.builder()
                .queryRunner(queryRunner)
                .connectionData(connectionData)
                .sqlQuery(sql)
                .resultProcessor(new Pipeline.ResultProcessor(generator, vendorOptions, outputConsumer))
                .build();

        p.execute();

        verify(queryRunner, times(1)).runQuery(sql, connectionData);
        verify(generator, times(1)).generate(result, vendorOptions);
        verify(outputConsumer, times(1)).consumeOutput(outputs);
    }

    @Test
    @DisplayName("The same generator should be called once")
    void generatorCacheTest() {
        final QueryRunner queryRunner = mock(QueryRunner.class);
        final String sql = "Select * From any;";
        final ConnectionData connectionData = new ConnectionData("", "", "", "");
        final Result result = mock(Result.class);
        when(queryRunner.runQuery(sql, connectionData)).thenReturn(result);

        final Generator generator = mock(Generator.class);
        final VendorOptions vendorOptions = new PostgreSQLOptions();
        final List<Output> outputs = Arrays.asList(new Output("1", "one"), new Output("2", "two"));
        when(generator.generate(result, vendorOptions)).thenReturn(outputs);

        final OutputConsumer outputConsumer1 = mock(OutputConsumer.class);
        final OutputConsumer outputConsumer2 = mock(OutputConsumer.class);

        Pipeline p = Pipeline.builder()
                .queryRunner(queryRunner)
                .connectionData(connectionData)
                .sqlQuery(sql)
                .resultProcessor(new Pipeline.ResultProcessor(generator, vendorOptions, outputConsumer1))
                .resultProcessor(new Pipeline.ResultProcessor(generator, vendorOptions, outputConsumer2))
                .build();

        p.execute();

        verify(queryRunner, times(1)).runQuery(sql, connectionData);
        verify(generator, times(1)).generate(result, vendorOptions);
        verify(outputConsumer1, times(1)).consumeOutput(outputs);
        verify(outputConsumer2, times(1)).consumeOutput(outputs);
    }
}
