# Spark Pipeline 与 Spark训练xgboost

#### 1. Spark Pipeline
https://spark.apache.org/docs/2.0.1/ml-pipeline.html<br>

#### 2. Spark训练xgboost
###### 2.1 maven settings.xml
http://git.dev.sh.ctripcorp.com/best_htlRanking/spark-intro/blob/master/chap03/settings.xml<br>
    
    将此文件下载到本地，替换此路径下的settings.xml文件
    D:\Users\yu_wei\.m2
###### 2.2 maven 依赖
添加xgboost依赖jar<br>

    <dependencies>
        <dependency>
            <groupId>ml.dmlc</groupId>
            <artifactId>xgboost4j</artifactId>
            <version>0.7</version>
        </dependency>
        <dependency>
            <groupId>ml.dmlc</groupId>
            <artifactId>xgboost4j-spark</artifactId>
            <version>0.7</version>
        </dependency>    
    </dependencies>
###### 2.3 xgboost0.7 jar 下载
http://git.dev.sh.ctripcorp.com/best_htlRanking/spark-intro/tree/master/resources/xgboost0.7<br>
##### 3 Xgboost Parameters
http://xgboost.readthedocs.io/en/latest/parameter.html<br>