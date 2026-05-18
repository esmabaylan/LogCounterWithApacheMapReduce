package com.loganalyzer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable ONE = new IntWritable(1);
    private Text ipAndMethodKey = new Text();

    // 54.36.149.41 - - [22/Jan/2019:03:56:14 +0330] "GET /filter.....

    private static final Pattern LOG_PATTERN = Pattern.compile("^([\\d\\.]+)\\s+.*\\\"([A-Z]+)\\s+");

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        Matcher matcher = LOG_PATTERN.matcher(line);

        if (matcher.find()) {
            String ip = matcher.group(1);
            String method = matcher.group(2);

            String compositeKey = ip + " - " + method;

            ipAndMethodKey.set(compositeKey);
            context.write(ipAndMethodKey, ONE);
        }
    }



}