package com.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        System.out.println("--->Reducer-->" + Thread.currentThread().getName());
        int count = 0;
        for (IntWritable value : values) {
            count++;
        }
        context.write(key, new IntWritable(count));
        System.out.println("--->Reducer-->OVER--" + Thread.currentThread().getName());
    }
}