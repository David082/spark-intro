# Spark集群

#### 1. Spark UI
##### local模式
http://10.8.92.190:4041<br>
##### yarn模式
http://svr15543hp360.hadoop.sh.ctripcorp.com:8088<br>

#### 2. Spark 集群参数
##### local模式
<pre>
/opt/app/spark-2.0.1/bin/spark-shell \
 --jars xgboost4j-0.7.jar,xgboost4j-spark-0.7.jar \
 --master local
</pre>
##### yarn模式
###### spark-shell 
<pre>
/opt/app/spark-2.0.1/bin/spark-shell \
 --jars xgboost4j-0.7.jar,xgboost4j-spark-0.7.jar \
 --master yarn \
 --num-executors 100 \
 --executor-memory 8g \
 --executor-cores 2 \
 --driver-memory 2g \
 --conf spark.default.parallelism=600
</pre>
###### spark-submit xx.jar
<pre>
/opt/app/spark-2.0.1/bin/spark-submit \
 --jars xgboost4j-0.7.jar,xgboost4j-spark-0.7.jar \
 --master yarn \
 --num-executors 100 \
 --executor-memory 8g \
 --executor-cores 2 \
 --driver-memory 2g \
 --conf spark.default.parallelism=600 \
 --class hotel.poirank.LightgbmModel spark-lightgbm-1.0.jar
</pre>
##### yarn模式架构
<img src="https://github.com/David082/spark-intro/blob/master/img/chap02/yarn-client.png"/><br>
#### 3. Spark 集群参数配置
###### refer
https://spark.apache.org/docs/2.0.1/configuration.html<br>
<pre>
1. num-executors：一般设置为50-100
2. executor-cores： 一般设置为2
3. executor-memory：4g ~ 8g
4. driver-memory: 1g or 2g
5. spark.default.parallelism = 2倍或3倍*(num-executors * executor-cores)

集群总核数： num-executors * executor-cores
集群总内存大小： (executor-memory / executor-cores) * num-executors
</pre>
