package com.miqiang;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public class ParseJsonUtilTest {

    @Test
    public void parse() {
        String path = "/Users/miqiang/Desktop/燕郊交通 2.xml";
        String writeFile = "/Users/miqiang/Desktop/jxbfdsksj.sql";
        String dbType = "oracle";
        String schema = "jiaowu";
        ParseJsonUtil util = new ParseJsonUtil();
        util.excute(path, writeFile, dbType, schema);
    }
}