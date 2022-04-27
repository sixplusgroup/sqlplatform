package com.qiao.brew.generator;

import com.qiao.brew.data.Result;
import com.qiao.brew.sql.vendor.VendorOptions;

import java.util.List;

public interface Generator {

    /**
     * Generate data based on the result.
     *
     * @param result result to generate data from
     * @param vendorOptions VendorOptions of database
     * @return String representation of generated data.
     */
    List<Output> generate(Result result, VendorOptions vendorOptions);

}
