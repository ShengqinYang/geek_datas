import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

object InvertedIndex {
  def main(args: Array[String]): Unit = {
    val input = "src/main/source"
    //  配置spark appname以及master
    val sparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local")
    //  val sparkConf = new SparkConf().setAppName("InvertedIndex").setMaster("local")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    //  获取hadoop操作文件的api
    val fs = FileSystem.get(sc.hadoopConfiguration)
    //  读取文件并生成列表
    val filelist = fs.listFiles(new Path(input), true)
    //  遍历文件，读取文件内容生成rdd，结构为（文件名,单词）
    var unionrdd = sc.emptyRDD[(String, String)] // var 声明一个空的rdd变量
    while (filelist.hasNext) {
      val abs_path = new Path(filelist.next().getPath.toString) // file:/D:/IdeaProjects/geek_datas/week7_work/src/main/source/file_0
      val file_name = abs_path.getName // file_0
      val rdd1 = sc.textFile(abs_path.toString).flatMap(_.split(" ").map((file_name, _)))
      //  将遍历的多个rdd拼接成一个rdd
      unionrdd = unionrdd.union(rdd1)
    }
    unionrdd.foreach(println)
    //  构建词频（（文件名，单词），词频）
    val rdd2 = unionrdd.map(word => (word, 1)).reduceByKey(_ + _)
    //    rdd2.foreach(println)
    //  调整输出格式,将（文件名，单词），词频）==》 （单词，（文件名，词频）） ==》 （单词，（文件名，词频））汇总
    val rdd3 = rdd2.map(word => (word._1._2, String.format("(%s,%s)", word._1._1, word._2.toString))).reduceByKey(_ + "," + _)
    //    rdd3.foreach(println)

    val rdd4 = rdd3.map(word => String.format("\"%s\",{%s}", word._1, word._2)).sortBy(word => word)
    rdd4.foreach(println)
  }
}

//"is",{(file_0,2),(file_2,1),(file_1,1)}
//"a",{(file_2,1)}
//"what",{(file_1,1),(file_0,1)}
//"banana",{(file_2,1)}
//"it",{(file_0,2),(file_2,1),(file_1,1)}