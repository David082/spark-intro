package example

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.FloatType
import org.apache.spark.sql.{SaveMode, SparkSession}


/**
  * Created by yu_wei on 2018/7/12.
  */
object Chap01 {

   def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder()
        .master("yarn") // master: "local" -- 本地模式, "yarn" -- 集群模式
        .appName("spark example")
        .enableHiveSupport() // 启动hive连接 (若windows本地运行，删除此方法)
        .getOrCreate()

      val table = "dw_htlbizdb.tmp_yw_yxrank_qid_ndcg" // 请修改为自己要使用的表
      val df = spark.sql(getDF(table))  // 执行sql，获取数据
      df.show(false)

      /* 使用自定义函数UDF */
      val dfUdf = df.withColumn("hotel_score", sigmoid(col("hotel_score")))
      dfUdf.printSchema()

      /* Spark DataFrame 相关操作 */
      df.filter("")
      df.where("hotel_score >= 4.0")

      val desc = df.groupBy("d").agg(max("hotel_star").alias("max_hotel_star"), min("hotel_score").cast(FloatType))

      /* Spark DataFrame 存入hive临时表 */
      dfUdf.write.format("orc").mode(SaveMode.Overwrite).saveAsTable("dw_htlbizdb.tmp_table_name")

      spark.stop()

   }

   // 定义函数
   def getDF(tableName: String) = {
      val sql = s"select * from $tableName" // sql语句结尾勿写分号
      sql
   }

   /* 定义udf */
   def sigmoid = udf((x: Double) => {
      1 / (1 + Math.exp(-x))
   })

}