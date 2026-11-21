package me.levansj01.verus.data;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.util.java.JavaV;

public class Transaction {
    private final AtomicReference<Long> received = new AtomicReference<>(null);
    private short id;
    private Long sent;
    private final WeakReference<Transaction> last;
    private final Queue<Runnable> runnableQueue = new ConcurrentLinkedQueue<>();

    public boolean isReceived() {
        return this.received.get() != null;
    }

    public Transaction(Transaction transaction) {
        this(new WeakReference<>(transaction));
    }

    public short getId() {
        return this.id;
    }

    public Queue<Runnable> getRunnableQueue() {
        return this.runnableQueue;
    }

    public void queue(Runnable runnable) {
        if (this.received.get() != null) {
            runnable.run();
        } else {
            this.runnableQueue.add(runnable);
        }
    }

    public void send(short id, long sent) {
        this.id = id;
        this.sent = sent;
    }

    public AtomicReference<Long> getReceived() {
        return this.received;
    }

    public Long ping() {
        return this.isSent() && this.isReceived() ? this.received.get() - this.sent : null;
    }

    public Long getSent() {
        return this.sent;
    }

    public boolean isSent() {
        return this.sent != null;
    }

    private Transaction(WeakReference<Transaction> last) {
        this.last = last;
    }

    public void receive(long value, PlayerData playerData) {
        this.received.set(value);
        Transaction transaction = this.last.get();
        if (transaction != null && !transaction.isReceived()) {
            int totalTicks = playerData.getTotalTicks();
            Check skipTransactionCheck = playerData.getCheckData().getSkipTransactionCheck();
            int receivedTransactions = playerData.getReceivedTransactions();
            if (skipTransactionCheck != null) {
                skipTransactionCheck.handleViolation(() -> String.format("ID: %s T: %s R: %s", transaction.getId(), totalTicks, receivedTransactions));
            }
        }

        this.last.clear();
        JavaV.executeSafely(this.runnableQueue, () -> " in transaction ID " + this.id);
    }

    public WeakReference<Transaction> getLast() {
        return this.last;
    }
}
