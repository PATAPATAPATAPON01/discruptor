package demo3;

import com.lmax.disruptor.EventTranslator;
import demo1.TradeTransaction;

import java.util.Random;

class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random=new Random();
    @Override  
    public void translateTo(TradeTransaction event, long sequence) {  
        this.generateTradeTransaction(event);  
    }  
    private TradeTransaction generateTradeTransaction(TradeTransaction trade){  
        trade.setPrice(random.nextDouble()*9999);  
        return trade;  
    }  
}  