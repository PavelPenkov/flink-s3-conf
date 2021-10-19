import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

public class TickingSource extends RichSourceFunction<String> {
    private boolean stopped;
    private int current;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while(!stopped) {
            ctx.collect(Integer.toString(current));
            current++;
            Thread.sleep(500);
        }
    }

    @Override
    public void cancel() {
        stopped = true;

    }
}