package example

import org.apache.spark.sql.{Row, _}
import example.Features._
import ml.dmlc.xgboost4j.scala.spark.XGBoost
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
/**
  * Created by yu_wei on 2018/8/2.
  */
object Chap04 {

   def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder()
        .master("yarn")
        .appName("spark xgboost yxrank args")
        .enableHiveSupport()
        .getOrCreate()

      // ------ Args
      var label = ""
      var table = ""
      var round = 0

      args.sliding(2, 2).toList.collect {
         case Array("--label", argLabel: String) => label = argLabel
         case Array("--table", argTable: String) => table = argTable
         case Array("--round", argRound: String) => round = argRound.toInt
      }

      // ------ Train
      val features = featuresList
      val trainAndTest = getTrainAndTest(spark, table)

      /* Pipeline */
      val vectorAssembler = new VectorAssembler().setInputCols(features.toArray).setOutputCol("features")

      val pipeline = new Pipeline().setStages(Array(vectorAssembler))
      val dataAssembler = pipeline.fit(trainAndTest).transform(trainAndTest)
      dataAssembler.printSchema()

      val testSetDate = "2018-07-05"
      val trainDF = dataAssembler.where(s"d != '$testSetDate'")
      val testDF = dataAssembler.where(s"d = '$testSetDate'")

      /* NDCG */
      val ndcg = ndcgAtK(testDF, "qid_mainkey", label, k = 3)
      println("======> NDCG:", ndcg.first().getDouble(0))

      spark.stop()

   }

   def getTrainAndTest(spark: SparkSession, table: String): DataFrame = {
      val sql = s"select * from $table"
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
