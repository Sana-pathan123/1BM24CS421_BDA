import java.io.IOException;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WCDriver extends Configured implements Tool {

    public int run(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Usage: WCDriver <input> <output>");
            return -1;
        }

        JobConf conf = new JobConf(WCDriver.class);
        conf.setJobName("Word Count");

        // Input and Output paths
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        // Set Mapper and Reducer
        conf.setMapperClass(WCMapper.class);
        conf.setReducerClass(WCReducer.class);

        // Set Output Types
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        JobClient.runJob(conf);
        return 0;
    }

    // Main method
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new WCDriver(), args);
        System.exit(res);
    }
}