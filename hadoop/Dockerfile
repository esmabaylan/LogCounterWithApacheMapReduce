FROM ubuntu:22.04

WORKDIR /root



RUN apt-get update && \
    apt-get install -y wget openjdk-17-jdk ssh nano sudo && \
    wget https://dlcdn.apache.org/hadoop/common/hadoop-3.5.0/hadoop-3.5.0.tar.gz && \
    tar -xzf hadoop-3.5.0.tar.gz && \
    mv hadoop-3.5.0 /usr/local/hadoop && \
    rm hadoop-3.5.0.tar.gz

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV HADOOP_HOME=/usr/local/hadoop
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

RUN ssh-keygen -t rsa -f /root/.ssh/id_rsa -P '' && \
    cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys

RUN mkdir -p /hadoop/dfs/name && \
    mkdir -p /hadoop/dfs/data && \
    mkdir -p /tmp/hadoop && \
    mkdir -p $HADOOP_HOME/logs

COPY config/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml
COPY config/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml
COPY config/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml
COPY config/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml
COPY config/hadoop-env.sh $HADOOP_HOME/etc/hadoop/hadoop-env.sh
COPY config/start-hadoop.sh /root/start-hadoop.sh

RUN chmod +x /root/start-hadoop.sh

RUN $HADOOP_HOME/bin/hdfs namenode -format

CMD ["/root/start-hadoop.sh"]