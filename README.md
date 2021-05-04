使用docker开启neo4j的命令是
```
docker run -it -d -p 7474:7474 -p 7687:7687 neo4j
```
默认用户名密码都为neo4j

查看容器ID并启动
```
docker container ls
docker start containerId
```


生成证书
```shell
keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650
```

前端后端两次hmac
前端密钥数据库随机生成
后端密钥为用户设置

### 更新策略:
* 上传文件后，对服务进行加锁，更新完数据库释放锁后才可以再次更新数据库。

后台启动springboot的命令
```shell
nohup java -jar graphs-0.0.1-SNAPSHOT.jar > test.log 2>&1 &
```