import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

public class FlinkApp {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setString("s3.endpoint", "http://localhost:4566");
        conf.setString("s3.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider");
        conf.setString("s3.access.key", "harvester");
        conf.setString("s3.secret.key", "harvester");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(conf);
        env.enableCheckpointing(1000);
        env.setNumberOfExecutionRetries(0);
        env.setParallelism(1);

        DataStream<String> ds = env.addSource(new TickingSource());

        Path path = new Path("s3a://harvester/test.txt");
        StreamingFileSink<String> sink = StreamingFileSink
                .forRowFormat(path, new SimpleStringEncoder<String>("UTF-8"))
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                .withRolloverInterval(1000)
                                .build()
                ).build();

        ds.addSink(sink);

        env.execute();
    }
}