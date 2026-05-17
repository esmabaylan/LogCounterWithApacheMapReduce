# [TR] Hadoop'un çalışması için gereken temel çevresel değişkenler (Environment Variables).
# [EN] Core environment variables required for Hadoop to run.


export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64


export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop

export HDFS_NAMENODE_USER=root
export HDFS_DATANODE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root


export HADOOP_OPTS="$HADOOP_OPTS -Djava.net.preferIPv4Stack=true"


export HDFS_NAMENODE_OPTS="-Dhadoop.security.logger=${HADOOP_SECURITY_LOGGER:-INFO,RFAS} -Dhdfs.audit.logger=${HDFS_AUDIT_LOGGER:-INFO,NullAppender} $HDFS_NAMENODE_OPTS"