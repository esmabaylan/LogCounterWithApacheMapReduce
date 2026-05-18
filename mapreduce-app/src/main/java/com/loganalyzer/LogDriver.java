package com.loganalyzer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("usage: LogDriver <input_path> <output_path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();

        // hadoop environment variables for docker
        conf.set("yarn.app.mapreduce.am.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");
        conf.set("mapreduce.map.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");
        conf.set("mapreduce.reduce.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");


        Job job = Job.getInstance(conf, "Web Log Analyzer");

        // test by windows
        //conf.set("mapreduce.framework.name", "local");
        // conf.set("fs.defaultFS", "file:///");


        job.setJarByClass(LogDriver.class);
        job.setMapperClass(LogMapper.class);
        job.setReducerClass(LogReducer.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}