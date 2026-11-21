package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.data.state.State;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class LazyData {
    public static final LazyData AIR = new LazyData(State.of(0), State.of(MaterialList.AIR_DATA), State.of(MaterialList.AIR), State.of(0.6F));
    public static final State<LazyData> STATE_AIR = State.of(AIR);
    private final State<MaterialData> data;
    private final State<Float> frictionFactor;
    private final State<Material> type;
    private final State<Integer> value;

    public State<MaterialData> getData() {
        return this.data;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        State value2 = this.getValue();
        value = value * 59 + (value2 == null ? 43 : value2.hashCode());
        State data = this.getData();
        value = value * 59 + (data == null ? 43 : data.hashCode());
        State type = this.getType();
        value = value * 59 + (type == null ? 43 : type.hashCode());
        State frictionFactor = this.getFrictionFactor();
        return value * 59 + (frictionFactor == null ? 43 : frictionFactor.hashCode());
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof LazyData;
    }

    public State<Integer> getValue() {
        return this.value;
    }

    public State<Material> getType() {
        return this.type;
    }

    public State<Float> getFrictionFactor() {
        return this.frictionFactor;
    }

    public LazyData(State<Integer> value, State<MaterialData> data, State<Material> type, State<Float> frictionFactor) {
        this.value = value;
        this.data = data;
        this.type = type;
        this.frictionFactor = frictionFactor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LazyData)) {
            return false;
        }

        LazyData lazyData = (LazyData)obj;
        if (!lazyData.canEqual(this)) {
            return false;
        }

        State value = this.getValue();
        State value2 = lazyData.getValue();
        if (value == null ? value2 == null : value.equals(value2)) {
            State data = this.getData();
            State data2 = lazyData.getData();
            if (data == null ? data2 == null : data.equals(data2)) {
                State type = this.getType();
                State type2 = lazyData.getType();
                if (type == null ? type2 == null : type.equals(type2)) {
                    State frictionFactor = this.getFrictionFactor();
                    State frictionFactor2 = lazyData.getFrictionFactor();
                    return frictionFactor == null ? frictionFactor2 == null : frictionFactor.equals(frictionFactor2);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "LazyData(value="
            + this.getValue()
            + ", data="
            + this.getData()
            + ", type="
            + this.getType()
            + ", frictionFactor="
            + this.getFrictionFactor()
            + ")";
    }
}
