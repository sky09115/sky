package com.university.demo.util.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SparkUtils {

    private final String DB_URL = "jdbc:mysql://localhost:3306/27music?serverTimezone=Asia/Shanghai";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "12345678";

    private SparkConf sparkConf;
    private JavaSparkContext javaSparkContext;
    private SQLContext sqlContext;

    public Properties getDBProperties(){
        Properties props = new Properties();
        props.put("user", DB_USERNAME);
        props.put("password", DB_PASSWORD);
        return props;
    }

    /*
     *   初始化Load
     *   创建sparkContext, sqlContext, hiveContext
     * */
    public SparkUtils() {
        // 如果没有初始化，初始化一下
        if(sparkConf==null && javaSparkContext==null){
            initSparckContext();
            initSQLContext();
        }
    }

    /*
     *   创建sparkContext
     * */
    private void initSparckContext() {
        String warehouseLocation = System.getProperty("user.dir");
        sparkConf = new SparkConf()
                .setAppName("from-to-mysql")
                .set("spark.sql.warehouse.dir", warehouseLocation)
                .setMaster("local");
        javaSparkContext = new JavaSparkContext(sparkConf);
    }

    /*
     *   创建sqlContext
     *   用于读写MySQL中的数据
     * */
    private void initSQLContext() {
        sqlContext = new SQLContext(javaSparkContext);
    }


    public Integer count(String table) {
        DataFrame rows = sqlContext.read().jdbc(DB_URL, table, getDBProperties()).where("1=1");
        JavaRDD<Row> testRdd= rows.toJavaRDD();
        return testRdd.collect().size();
    }

    public Integer countLog(String type) {
        DataFrame rows = sqlContext.read().jdbc(DB_URL, "tb_log", getDBProperties()).
                where("opt ='" + type + "'");
        JavaRDD<Row> testRdd= rows.toJavaRDD();
        return testRdd.collect().size();
    }
}
