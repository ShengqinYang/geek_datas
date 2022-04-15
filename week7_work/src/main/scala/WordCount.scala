import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}


object WordCount {
  def main(arg: Array[String]): Unit = {
    val input = "src/main/source"
    val sparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    val fs = FileSystem.get(sc.hadoopConfiguration)
    val filelist = fs.listFiles(new Path(input), true)
    var unrdd = sc.emptyRDD[(String, Int)]
    while (filelist.hasNext) {
      val abs_path = new Path(filelist.next().getPath.toString)
      val rdd1 = sc.textFile(abs_path.toString).flatMap(_.split(" ")).map((_, 1))
      unrdd = unrdd.union(rdd1).reduceByKey(_ + _)
    }
    unrdd.foreach(println)
  }
}
