package com.flow_calc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class FlowBean implements Writable {

    private long upflow;
    private long downflow;
    private long sumflow;

    public FlowBean() {
    }

    public FlowBean(long upflow, long downflow) {
        super();
        this.upflow = upflow;
        this.downflow = downflow;
        this.sumflow = upflow + downflow;
    }

    public FlowBean(long upflow, long downflow, long sumflow) {
        this.upflow = upflow;
        this.downflow = downflow;
        this.sumflow = sumflow;

    }

    public void set(long upflow, long downflow) {
        this.upflow = upflow;
        this.downflow = downflow;
        this.sumflow = upflow + downflow;
    }

    //序列化方法
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upflow);
        out.writeLong(downflow);
        out.writeLong(sumflow);
    }

    public String toString() {
        return upflow + "\t" + downflow + "\t" + sumflow;
    }

    //这是反序列化方法
    //反序列时候  注意序列化的顺序
    //先序列化的先出来
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upflow = in.readLong();
        this.downflow = in.readLong();
        this.sumflow = in.readLong();
    }

    public long getUpFlow() {
        return upflow;
    }

    public void setUpFlow(long upflow) {
        this.upflow = upflow;
    }

    public long getDownflow() {
        return downflow;
    }

    public void setDownflow(long downflow) {
        this.downflow = downflow;
    }

    public long getSumflow() {
        return sumflow;
    }

    public void setSumflow(long sumflow) {
        this.sumflow = sumflow;
    }


}
