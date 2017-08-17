package quickstart;

public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void clear() {
        System.out.println("清除数据");
    }
}