package com;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;


public class DataMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    FlowBean bean = new FlowBean();
    Text k = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString(); // 获取第一行
        String[] fields = line.split("\t"); // 数据按\t分割
        String phonenum = fields[1];
        long unflow = Long.parseLong(fields[fields.length - 3]);
        long downflow = Long.parseLong(fields[fields.length - 2]);
        bean.set(unflow, downflow);
//        System.out.println(bean + "      --------bean--------");
        k.set(phonenum);
//        System.out.println(k + "      --------key--------");
        context.write(k, bean);
//        System.out.println(context + "      --------context--------");


    }
}
