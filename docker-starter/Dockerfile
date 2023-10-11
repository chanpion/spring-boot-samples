FROM eclipse/centos_jdk8

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV APPNAME=docker-starter
RUN mkdir -p /data

VOLUME /data
VOLUME /tmp/output


COPY ${APPNAME}.jar /data/
COPY application.properties /data/

WORKDIR /data

ENV JAVA_OPTS=" -Xms512m -Xmx1024m -Dspring.config.location=/data/application.properties "

CMD java ${JAVA_OPTS} -jar ${APPNAME}.jar

EXPOSE 8088