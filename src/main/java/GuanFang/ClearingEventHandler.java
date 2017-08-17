package GuanFang;

import com.lmax.disruptor.EventHandler;
import quickstart.LongEvent;

/**
 * Created by PataPon on 2017/8/17.
 */
public class ClearingEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        longEvent.clear();
    }
}
