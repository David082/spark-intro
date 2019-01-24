package example

import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

/**
  * Created by yu_wei on 2018/7/18.
  */
object Chap02 {

   def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder()
        .master("yarn")
        .appName("spark example chap02")
        .enableHiveSupport()
        .getOrCreate()

      val table = "dw_htlbizdb.tmp_yw_yxrank_qid_ndcg" /* 请修改为自己要使用的表 */
      val df = getDF(spark, table)

      val keyCol = "qid_mainkey"
      val label = "booking_bool"
      val at = 3
      val qidNdcg = qidNdcgAtK(df, keyCol, label, k = at)
      val ndcg = ndcgAtK(df, keyCol, label, k = at)

      qidNdcg.show(false)
      ndcg.show(false)

      spark.stop()

   }

   def getDF(spark: SparkSession, tableName: String) = {
      val sql = s"select * from $tableName"
      spark.sql(sql)
   }

   def reli = udf((i: Int) => {
      Math.pow(2, i) - 1
   })

   def log2 = udf((x: Int) => {
      Math.log(x + 1) / Math.log(2)
   })

   def relDivLog2AtK = udf((rank: Int, k: Int, rel: Int, logx: Double) => {
      if (rank > k) 0
      else if (logx == 0 || rel.toString == null || logx.toString == null) 0
      else rel / logx
   })

   def qidNdcgAtK(df: DataFrame, keyofRank: String, relCol: String, k: Int) = {
      val dcgAtK = df.select(keyofRank, "rank_in_qid", relCol)
        .withColumn("dcgAtK", relDivLog2AtK(col("rank_in_qid"), lit(k), reli(col(relCol)), log2(col("rank_in_qid"))))
        .withColumn("ideal_rank", row_number().over(Window.partitionBy(keyofRank).orderBy(col(relCol).desc)))
        .withColumn("idcgAtK", relDivLog2AtK(col("ideal_rank"), lit(k), reli(col(relCol)), log2(col("ideal_rank"))))
        .groupBy(keyofRank).agg((sum("dcgAtK") / sum("idcgAtK")).alias("ndcg"))
      dcgAtK
   }

   /**
     *
     * @param df        : DataFrame
     * @param keyofRank : key
     * @param relCol    : rel column
     * @param k         : at k
     * @return DataFrame of NDCG
     */
   def ndcgAtK(df: DataFrame, keyofRank: String, relCol: String, k: Int) = {
      qidNdcgAtK(df, keyofRank, relCol, k).agg(avg(col("ndcg")).alias("ndcg"))
   }

}