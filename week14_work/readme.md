## Flink作业：

```
作业
改写flink example代码，初步理解flink api 使用流程和原理，
代码位置：https://github.com/apache/flink-playgrounds.git
flink文档：https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/try-flink/table_api/
具体要求：
report(transactions).executeInsert(“spend_report”);
将 transactions 表经过 report 函数处理后写入到 spend_report 表
每分钟（或小时）计算在五分钟（或小时）内每个账号的平均交易金额（滑动窗口）？使用分钟还是小时作为单位均可
```
运行环境
```text
电脑上需要安装如下环境：Java 8 或者 Java 11、Maven、Git、Docker
```
具体步骤：

1.配置运行环境：
```
    电脑上需要安装如下环境：Java 8 或者 Java 11、Maven、Git、Docker
    踩坑：严格按照环境要求来进行配置，必须安装Java 8 或者 Java 11环境，安装java18会出错～～
```
2.配置代码环境：
```text
    git clone https://github.com/apache/flink-playgrounds.git
    cd flink-playgrounds/table-walkthrough
```
3.代码修改
位置：/flink-playgrounds/table-walkthrough/src/main/java/org/apache/flink/playgrounds/spendreport/SpendReport.java
```java
public static Table report(Table transactions) {
        return transactions
        .window(Tumble.over(lit(1).hour()).on($("transaction_time")).as("log_ts"))
        .groupBy($("account_id"), $("log_ts"))
        .select(
        $("account_id"),
        $("log_ts").start().as("log_ts"),
        $("amount").sum().as("amount"));
        }
```
4.编译并执行
```text
在开始运行之前先在 Docker 主机上创建检查点和保存点目录（如果不做这一步有可能报错）:
mkdir -p /tmp/flink-checkpoints-directory
mkdir -p /tmp/flink-savepoints-directory

cd flink-playgrounds/table-walkthrough
docker-compose build
docker-compose up -d
```
5.查看并验证
```text
1.Flink WebUI 界面
打开浏览器并访问 http://localhost:8082

2.查看mysql数据情况
docker-compose exec mysql mysql -Dsql-demo -usql-demo -pdemo-sql
mysql> use sql-demo;
mysql> select count(*) from spend_report;
会看到count数据一直在增加

3.在 Grafana 查看最终结果
http://localhost:3000/d/FOe0PbmGk/walkthrough?viewPanel=2&orgId=1&refresh=10s
```
![](/Users/yangshengqin/Desktop/截屏2022-09-16 15.56.59.png)

