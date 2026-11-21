package me.levansj01.verus.data.transaction.world;

import java.util.Iterator;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.util.java.JavaV;
import me.levansj01.verus.util.java.PrefixIterator;
import me.levansj01.verus.util.java.WrappingIterator;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public interface IVerusBlock extends Iterable<LazyData> {
    default Stream<LazyData> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    default boolean is(Material material) {
        return this.isType(material::equals);
    }

    default boolean is(Predicate<LazyData> predicate) {
        return this.getData() != null
            && predicate.test(this.getData().get())
            && JavaV.allMatch(entry -> predicate.test(entry.get()), this.getDataQueue().get());
    }

    @Override
    default Iterator<LazyData> iterator() {
        return new WrappingIterator<>(new PrefixIterator<>(this.getData(), this.getDataQueue().get().iterator()), Supplier::get);
    }

    State<Queue<State<LazyData>>> getDataQueue();

    State<LazyData> getData();

    default boolean isType(Predicate<Material> predicate) {
        return this.is(thi -> predicate.test(thi.getType().get()));
    }

    default boolean isData(Predicate<MaterialData> predicate) {
        return this.is(thi -> predicate.test(thi.getData().get()));
    }
}
