package com.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class WordCount {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(WordCount.class); // 设置运行的主类
        job.setMapperClass(WordCountMap.class); // 设置运行的MAP类
        job.setReducerClass(WordCountReduce.class); // 设置运行的reducer类

        job.setMapOutputKeyClass(Text.class); // 设置map输出key类型
        job.setMapOutputValueClass(IntWritable.class); // 设置map输出value类型

        job.setOutputKeyClass(Text.class); //设置reducer key输出类型
        job.setOutputValueClass(IntWritable.class); //设置reducer value输出类型

//        FileInputFormat.setInputPaths(job, new Path(args[0])); //设置待处理数据的目录
//        FileOutputFormat.setOutputPath(job, new Path(args[1])); //设置输出数据的目录
        FileInputFormat.setInputPaths(job, "src/main/resources/words.txt");
        FileOutputFormat.setOutputPath(job, new Path("src/main/resources/output_wc"));//跑之前要删掉output文件，否则报错

        boolean b = job.waitForCompletion(true); //提交本次任务
    }
}