package com.flow_calc;

import java.io.IOException;

import com.flow_calc.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class DataReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    FlowBean v = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context)
            throws IOException, InterruptedException {
        // 计算总流量
        long sum_upflow = 0;
        long sum_downflow = 0;
        for (FlowBean bean : values) {
            sum_upflow += bean.getUpFlow();
            sum_downflow += bean.getDownflow();
        }
        v.set(sum_upflow, sum_downflow);
        context.write(key, v);
    }
}
