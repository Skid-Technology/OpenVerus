package me.levansj01.verus.data.reach;

import java.util.Arrays;
import java.util.Objects;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.Intersection;

public class IntersectionData {
    private final Intersection[] intersections;
    private final PacketLocation move;
    private Double minReach;
    private boolean fetched;
    private final Cuboid hitbox;

    public PacketLocation getMove() {
        return this.move;
    }

    public void setMinReach(Double minReach) {
        this.minReach = minReach;
    }

    public Cuboid getHitbox() {
        return this.hitbox;
    }

    public void setFetched(boolean fetched) {
        this.fetched = fetched;
    }

    @Override
    public String toString() {
        return "IntersectionData(move="
            + this.getMove()
            + ", hitbox="
            + this.getHitbox()
            + ", intersections="
            + Arrays.deepToString(this.getIntersections())
            + ", fetched="
            + this.isFetched()
            + ", minReach="
            + this.getMinReach()
            + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof IntersectionData)) {
            return false;
        }

        IntersectionData intersectionData = (IntersectionData)obj;
        if (!intersectionData.canEqual(this)) {
            return false;
        }

        if (this.isFetched() != intersectionData.isFetched()) {
            return false;
        }

        Double minReach = this.getMinReach();
        Double minReach2 = intersectionData.getMinReach();
        if (minReach == null ? minReach2 == null : minReach.equals(minReach2)) {
            PacketLocation move = this.getMove();
            PacketLocation move2 = intersectionData.getMove();
            if (move == null ? move2 == null : move.equals(move2)) {
                Cuboid hitbox = this.getHitbox();
                Cuboid hitbox2 = intersectionData.getHitbox();
                return (hitbox == null ? hitbox2 == null : hitbox.equals(hitbox2)) ? Arrays.deepEquals(this.getIntersections(), intersectionData.getIntersections()) : false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Double getMinReach() {
        if (!this.fetched) {
            Arrays.stream(this.intersections).filter(Objects::nonNull).mapToDouble(Intersection::getDistance).min().ifPresent(entry -> this.minReach = entry);
            this.fetched = true;
        }

        return this.minReach;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof IntersectionData;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + (this.isFetched() ? 79 : 97);
        Double minReach = this.getMinReach();
        value = value * 59 + (minReach == null ? 43 : minReach.hashCode());
        PacketLocation move = this.getMove();
        value = value * 59 + (move == null ? 43 : move.hashCode());
        Cuboid hitbox = this.getHitbox();
        value = value * 59 + (hitbox == null ? 43 : hitbox.hashCode());
        return value * 59 + Arrays.deepHashCode(this.getIntersections());
    }

    public boolean isFetched() {
        return this.fetched;
    }

    public IntersectionData(PacketLocation move, Cuboid hitbox, Intersection[] intersections) {
        this.move = move;
        this.hitbox = hitbox;
        this.intersections = intersections;
    }

    public Intersection[] getIntersections() {
        return this.intersections;
    }
}
