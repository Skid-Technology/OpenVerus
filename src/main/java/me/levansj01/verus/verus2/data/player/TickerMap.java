package me.levansj01.verus.verus2.data.player;

public class TickerMap {
    private final int[] timers = new int[TickerType.values().length];

    public void incrementAll() {
        for (TickerType tickerType : TickerType.getAutos()) {
            this.increment(tickerType);
        }
    }

    public void set(TickerType tickerType, int value) {
        this.timers[tickerType.ordinal()] = value;
    }

    public int get(TickerType tickerType) {
        return this.timers[tickerType.ordinal()];
    }

    public void reset(TickerType type) {
        this.set(type, 0);
    }

    public void set(TickerType tickerType, TickerType tickerType2) {
        this.timers[tickerType.ordinal()] = this.get(tickerType2);
    }

    public int increment(TickerType tickerType) {
        return ++this.timers[tickerType.ordinal()];
    }

    public TickerMap() {
        int value = 0;

        for (TickerType tickerType : TickerType.values()) {
            this.timers[value] = tickerType.getStarting();
            value++;
        }
    }
}
