package me.levansj01.verus.data;

public interface PingHandler {
    void handleTransaction(long value, long value2);

    void handleKeepAlive(long value, long value2);
}
