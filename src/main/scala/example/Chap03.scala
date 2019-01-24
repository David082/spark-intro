package example

import org.apache.spark.sql.{Row, _}
import example.Features._
import ml.dmlc.xgboost4j.scala.spark.XGBoost
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._

/**
  * Created by yu_wei on 2018/7/26.
  */
object Chap03 {

   def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder()
        .master("yarn")
        .appName("spark xgboost yxrank v2")
        .enableHiveSupport()
        .getOrCreate()

      // ------ Train
      val label = "booking_bool"
      val features = featuresList

      val table = "dw_htlbizdb.tmp_yw_spark_xgb_dataset"
      val trainAndTest = getTrainAndTest(spark, table)

      /* Pipeline */
      val vectorAssembler = new VectorAssembler().setInputCols(features.toArray).setOutputCol("features")

      val pipeline = new Pipeline().setStages(Array(vectorAssembler))
      val dataAssembler = pipeline.fit(trainAndTest).transform(trainAndTest)
      dataAssembler.printSchema()
      dataAssembler.select("features", label).show()

      val testSetDate = "2018-07-05"
      val trainDF = dataAssembler.where(s"d != '$testSetDate'")
      val testDF = dataAssembler.where(s"d = '$testSetDate'")

      val params = List(
         "booster" -> "gbtree",
         "objective" -> "binary:logistic",
         "max_depth" -> 5,
         "eval_metric" -> "auc",
         "silent" -> 0, // 0：输出迭代信息
         "subsample" -> 0.8, // 样本随机采样
         "colsample_bytree" -> 0.8, // 列采样
         "eta" -> 0.25, // 学习率
         "min_child_weight" -> 1, // 最小叶子节点样本权重和
         "alpha" -> 0.05, // 权重的L1正则化项
         "gamma" -> 3 // 后剪枝参数
      ).toMap

      val model = XGBoost.trainWithDataFrame(trainDF, params,
         round = 10, nWorkers = 200,
         featureCol = "features", labelCol = label)
      // 将模型保存到自己的hdfs路径下
      model.saveModelAsHadoopFile("hdfs:///filepath/xgb.model")(spark.sparkContext) /* 修改为自己的hdfs路径 */

      // ------ Test
      val predict = model.transform(testDF)
      predict.printSchema()
      predict.select(label, "probabilities", "prediction").show(false)
      val probs = predict.withColumn("probs", colprob(col("probabilities")))

      /* AUC */
      val auc = AUC(probs, "probs", labelCol = label)

      spark.stop()

   }

   def getTrainAndTest(spark: SparkSession, table: String): DataFrame = {
      val sql = s"select * from $table"
      spark.sql(sql)
   }

   def colprob = udf((probs: Vector) => {
      probs(1)
   })

   def AUC(predictedDF: DataFrame, scoreCol: String, labelCol: String) = {
      val scoreAndLabels: RDD[(Double, Double)] = predictedDF.select(scoreCol, labelCol).rdd.map {
         case Row(score: Double, label: Int) => (score.toDouble, label.toDouble)
      }
      val metric = new BinaryClassificationMetrics(scoreAndLabels)
      val auc = metric.areaUnderROC()
      auc
   }

}
