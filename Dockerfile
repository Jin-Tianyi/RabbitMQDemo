FROM  openjdk:11
MAINTAINER 240214191@qq.com
LABEL  by=jty name=MqDemo description="测试idea整合docker" version="1.0"
RUN mkdir -p /mq/demo
RUN cd /mq/demo
WORKDIR /mq/demo
ADD ./target/rabbitmqDemo.jar ./app.jar
EXPOSE  9009
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "app.jar"]