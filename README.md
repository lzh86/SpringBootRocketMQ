# SpringBootRocketMQ

#### 介绍
Demo project for Spring Boot RocketMQ

### windows下安装RocketMQ  
1.下载地址：https://rocketmq.apache.org/release-notes/
2.下载后解压
3.配置系统环境变量
变量名：ROCKETMQ_HOME
变量值：MQ解压路径\MQ文件夹名
eg：D:\rocketmq-all-5.0.0-bin-release
4.启动
CMD进入 D:\rocketmq-all-5.0.0-bin-release\bin 目录下
执行 'start mqnamesrv.cmd' , 启动 NAMESERVER。成功后会弹出提示框，此框勿关闭。
执行 'start mqbroker.cmd -n 10.51.130.155:9876 autoCreateTopicEnable=true' , 启动 BROKER。成功后会弹出提示框，此框勿关闭。
5.安装RocketMQ监控面板
git clone  https://github.com/apache/rocketmq-dashboard.git 
克隆项目后打成jar(注意修改成自己RocketMQ所在IP地址以及端口)
mvn clean package -Dmaven.test.skip=true
java -jar target/rocketmq-dashboard-1.0.1-SNAPSHOT.jar

### linux下安装RocketMQ






