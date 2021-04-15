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
