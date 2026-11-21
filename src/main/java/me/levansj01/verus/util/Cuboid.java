package me.levansj01.verus.util;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.world.ChunkRef;
import me.levansj01.verus.data.transaction.world.IVerusBlock;
import me.levansj01.verus.data.transaction.world.IVerusChunk;
import me.levansj01.verus.data.transaction.world.IVerusWorld;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.ILocation;
import me.levansj01.verus.util.location.IVector3d;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.util.location.Vector3d;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Cuboid {
    private static final Integer MIN_Y_LEVEL = NMSManager.getInstance().getMinYLevel();
    private double y1;
    private double z2;
    private double x2;
    private double z1;
    private double y2;
    private double x1;

    public boolean containsXY(double value, double value2) {
        return this.x1 <= value && this.x2 >= value && this.y1 <= value2 && this.y2 >= value2;
    }

    public boolean contains(Vector3d vector3d) {
        return vector3d == null ? false : this.contains(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }

    public boolean checkLoaded(Supplier<String> supplier, IVerusWorld verusWorld) {
        return this.checkBlocksInternal(supplier, 256, (first, second) -> true, thi -> {
            IVerusBlock verusBlock = verusWorld.get(thi);
            return verusBlock != null && verusBlock.getData() != null;
        });
    }

    public double getY2() {
        return this.y2;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public Cuboid hitbox() {
        return this.add(-0.3, 0.3, 0.0, 1.8, -0.3, 0.3).expand(0.1, 0.1, 0.1);
    }

    public Cuboid(double value, double value2, double value3) {
        this(value, value, value2, value2, value3, value3);
    }

    public boolean checkBlocksInternal(Supplier<String> supplier, IVerusWorld verusWorld, Predicate<IVerusBlock> predicate) {
        ChunkRef chunkRef = new ChunkRef();
        return this.checkBlocksInternal(supplier, 256, (first, second) -> {
            int value = first >> 4;
            int value2 = second >> 4;
            return chunkRef.same(value, value2) ? (IVerusChunk)chunkRef.getChunk() : (IVerusChunk)chunkRef.set(verusWorld.getChunk(value, value2), value, value2);
        }, thi -> true, (first, second) -> predicate.test(first == null ? null : first.getType(second)));
    }

    public boolean containsXY(Vector3d vector3d) {
        return vector3d == null ? false : this.containsXY(vector3d.getX(), vector3d.getY());
    }

    public boolean containsXZ(double value, double value2) {
        return this.x1 <= value && this.x2 >= value && this.z1 <= value2 && this.z2 >= value2;
    }

    public boolean contains(ILocation location) {
        return this.contains(location.getX(), location.getY(), location.getZ());
    }

    public double getX2() {
        return this.x2;
    }

    public boolean checkBlocks(Supplier<String> supplier, IVerusWorld verusWorld, Predicate<Material> predicate) {
        return this.checkBlocksInternal(supplier, verusWorld, thi -> {
            for (LazyData lazyData : thi) {
                if (!predicate.test(lazyData.getType().get())) {
                    return false;
                }
            }

            return true;
        });
    }

    @Override
    public String toString() {
        return "Cuboid(x1="
            + this.getX1()
            + ", x2="
            + this.getX2()
            + ", y1="
            + this.getY1()
            + ", y2="
            + this.getY2()
            + ", z1="
            + this.getZ1()
            + ", z2="
            + this.getZ2()
            + ")";
    }

    public double getX1() {
        return this.x1;
    }

    public Cuboid setValues(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        return this;
    }

    public Cuboid shrink(double value, double value2, double value3) {
        this.x1 += value;
        this.x2 -= value;
        this.y1 += value2;
        this.y2 -= value2;
        this.z1 += value3;
        this.z2 -= value3;
        return this;
    }

    public Cuboid() {
        this(0.0, 0.0, 0.0);
    }

    public boolean checkBlocks(Player player, World world, Predicate<Material> predicate) {
        return this.checkBlocksInternal(player, world, thi -> predicate.test(thi.getType(player, world)));
    }

    public Intersection calculateIntercept(Vector3d vector3d, Vector3d vector3d2) {
        Vector3d intermediateWithXValue = vector3d.getIntermediateWithXValue(vector3d2, this.x1);
        Vector3d intermediateWithXValue2 = vector3d.getIntermediateWithXValue(vector3d2, this.x2);
        Vector3d intermediateWithYValue = vector3d.getIntermediateWithYValue(vector3d2, this.y1);
        Vector3d intermediateWithYValue2 = vector3d.getIntermediateWithYValue(vector3d2, this.y2);
        Vector3d intermediateWithZValue = vector3d.getIntermediateWithZValue(vector3d2, this.z1);
        Vector3d intermediateWithZValue2 = vector3d.getIntermediateWithZValue(vector3d2, this.z2);
        if (!this.containsYZ(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }

        if (!this.containsYZ(intermediateWithXValue2)) {
            intermediateWithXValue2 = null;
        }

        if (!this.containsXZ(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }

        if (!this.containsXZ(intermediateWithYValue2)) {
            intermediateWithYValue2 = null;
        }

        if (!this.containsXY(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }

        if (!this.containsXY(intermediateWithZValue2)) {
            intermediateWithZValue2 = null;
        }

        Vector3d vector3d3 = null;
        if (intermediateWithXValue != null) {
            vector3d3 = intermediateWithXValue;
        }

        if (intermediateWithXValue2 != null && (vector3d3 == null || vector3d.distanceSquared(intermediateWithXValue2) < vector3d.distanceSquared(vector3d3))) {
            vector3d3 = intermediateWithXValue2;
        }

        if (intermediateWithYValue != null && (vector3d3 == null || vector3d.distanceSquared(intermediateWithYValue) < vector3d.distanceSquared(vector3d3))) {
            vector3d3 = intermediateWithYValue;
        }

        if (intermediateWithYValue2 != null && (vector3d3 == null || vector3d.distanceSquared(intermediateWithYValue2) < vector3d.distanceSquared(vector3d3))) {
            vector3d3 = intermediateWithYValue2;
        }

        if (intermediateWithZValue != null && (vector3d3 == null || vector3d.distanceSquared(intermediateWithZValue) < vector3d.distanceSquared(vector3d3))) {
            vector3d3 = intermediateWithZValue;
        }

        if (intermediateWithZValue2 != null && (vector3d3 == null || vector3d.distanceSquared(intermediateWithZValue2) < vector3d.distanceSquared(vector3d3))) {
            vector3d3 = intermediateWithZValue2;
        }

        if (vector3d3 == null) {
            return null;
        }

        Direction direction;
        if (vector3d3 == intermediateWithXValue) {
            direction = Direction.WEST;
        } else if (vector3d3 == intermediateWithXValue2) {
            direction = Direction.EAST;
        } else if (vector3d3 == intermediateWithYValue) {
            direction = Direction.DOWN;
        } else if (vector3d3 == intermediateWithYValue2) {
            direction = Direction.UP;
        } else if (vector3d3 == intermediateWithZValue) {
            direction = Direction.NORTH;
        } else {
            direction = Direction.SOUTH;
        }

        return new Intersection(vector3d3, vector3d3.copy().subtract(vector3d), direction);
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double cX() {
        return (this.x1 + this.x2) * 0.5;
    }

    public boolean checkBlocksInternal(Player player, World world, Predicate<IBlockPosition> predicate) {
        NMSManager nmsManager = NMSManager.getInstance();
        return this.checkBlocksInternal(player::getName, world.getMaxHeight(), (first, second) -> nmsManager.isLoaded(world, first, second), predicate);
    }

    public double cZ() {
        return (this.z1 + this.z2) * 0.5;
    }

    public boolean containsXZ(PlayerLocation location) {
        return this.containsXZ(location.getX(), location.getZ());
    }

    public Cuboid setValues(double value, double value2, double value3) {
        return this.setValues(value, value, value2, value2, value3, value3);
    }

    public Cuboid copy() {
        return new Cuboid(this.x1, this.x2, this.y1, this.y2, this.z1, this.z2);
    }

    public double distanceXZ(double value, double value2) {
        if (this.containsXZ(value, value2)) {
            return 0.0;
        }

        double value3 = Math.min(Math.pow(value - this.x1, 2.0), Math.pow(value - this.x2, 2.0));
        double value4 = Math.min(Math.pow(value2 - this.z1, 2.0), Math.pow(value2 - this.z2, 2.0));
        return MathHelper.sqrt_double(value3 + value4);
    }

    public double getWidthZ() {
        return this.z2 - this.z1;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public boolean isSquare() {
        return this.x1 == 0.0 && this.x2 == 1.0 && this.y1 == 0.0 && this.y2 == 1.0 && this.z1 == 0.0 && this.z2 == 1.0;
    }

    public boolean contains(double value, double value2, double value3) {
        return this.x1 <= value && this.x2 >= value && this.y1 <= value2 && this.y2 >= value2 && this.z1 <= value3 && this.z2 >= value3;
    }

    public boolean containsBlock(World world, int value, int value2, int value3) {
        int value4 = (int)Math.floor(this.x1);
        int value5 = (int)Math.ceil(this.x2);
        int value6 = Math.max((int)Math.floor(this.y1), MIN_Y_LEVEL);
        int value7 = Math.min((int)Math.ceil(this.y2), world.getMaxHeight());
        int value8 = (int)Math.floor(this.z1);
        int value9 = (int)Math.ceil(this.z2);
        return value4 <= value && value5 > value && value6 <= value2 && value7 > value2 && value8 <= value3 && value9 > value3;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    public boolean containsBlock(World world, BlockPosition blockPosition) {
        return this.containsBlock(world, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    public Cuboid add(Cuboid cuboid) {
        return this.add(cuboid.getX1(), cuboid.getX2(), cuboid.getY1(), cuboid.getY2(), cuboid.z1, cuboid.z2);
    }

    public double getZ1() {
        return this.z1;
    }

    public Cuboid(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public Cuboid move(double value, double value2, double value3) {
        this.x1 += value;
        this.x2 += value;
        this.y1 += value2;
        this.y2 += value2;
        this.z1 += value3;
        this.z2 += value3;
        return this;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public boolean checkBlocksInternal(Supplier<String> supplier, int value, BiFunction<Integer, Integer, Boolean> function, Predicate<IBlockPosition> predicate) {
        return this.checkBlocksInternal(supplier, value, function, Boolean::booleanValue, (first, second) -> predicate.test(second));
    }

    public Cuboid expand(double value, double value2, double value3) {
        this.x1 -= value;
        this.x2 += value;
        this.y1 -= value2;
        this.y2 += value2;
        this.z1 -= value3;
        this.z2 += value3;
        return this;
    }

    public boolean containsYZ(double value, double value2) {
        return this.y1 <= value && this.y2 >= value && this.z1 <= value2 && this.z2 >= value2;
    }

    public boolean overlaps(Cuboid cuboid) {
        return !(this.x1 > cuboid.x2) && !(cuboid.x1 > this.x2) && !(this.z1 > cuboid.z2) && !(cuboid.z1 > this.z2);
    }

    public double distanceXZ(PlayerLocation location) {
        return this.distanceXZ(location.getX(), location.getZ());
    }

    public void include(IVector3d vector3d) {
        double x = vector3d.getX();
        double y = vector3d.getY();
        double z = vector3d.getZ();
        if (x < this.x1) {
            this.x1 = x;
        }

        if (x > this.x2) {
            this.x2 = x;
        }

        if (y < this.y1) {
            this.y1 = y;
        }

        if (y > this.y2) {
            this.y2 = y;
        }

        if (z < this.z1) {
            this.z1 = z;
        }

        if (z > this.z2) {
            this.z2 = z;
        }
    }

    public void setZ1(double z1) {
        this.z1 = z1;
    }

    public static Cuboid withLimit(ILocation location, ILocation location2, int value) {
        return location.distanceSquared(location2) < value ? new Cuboid(location, location2) : new Cuboid(location2);
    }

    private Cuboid(IVector3d vector3d, IVector3d vector3d2) {
        this(
            Math.min(vector3d.getX(), vector3d2.getX()),
            Math.max(vector3d.getX(), vector3d2.getX()),
            Math.min(vector3d.getY(), vector3d2.getY()),
            Math.max(vector3d.getY(), vector3d2.getY()),
            Math.min(vector3d.getZ(), vector3d2.getZ()),
            Math.max(vector3d.getZ(), vector3d2.getZ())
        );
    }

    public Cuboid face(Direction direction) {
        switch (direction) {
            case UP:
                this.y1 = this.y2;
                break;
            case DOWN:
                this.y2 = this.y1;
                break;
            case EAST:
                this.x1 = this.x2;
                break;
            case WEST:
                this.x2 = this.x1;
                break;
            case SOUTH:
                this.z1 = this.z2;
                break;
            case NORTH:
                this.z2 = this.z1;
        }

        return this;
    }

    public boolean checkBlocksAndLoaded(Supplier<String> supplier, IVerusWorld verusWorld, Predicate<Material> predicate) {
        return this.checkBlocksInternal(supplier, verusWorld, thi -> {
            if (thi != null && thi.getData() != null) {
                for (LazyData lazyData : thi) {
                    if (!predicate.test(lazyData.getType().get())) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        });
    }

    public boolean rayTraceInternal(Player player, Predicate<MutableBlockLocation> predicate) {
        return this.rayTraceInternal(player::getName, predicate);
    }

    public Cuboid combine(Cuboid cuboid) {
        return new Cuboid(
            Math.min(this.x1, cuboid.x1),
            Math.max(this.x2, cuboid.x2),
            Math.min(this.y1, cuboid.y1),
            Math.max(this.y2, cuboid.y2),
            Math.min(this.z1, cuboid.z1),
            Math.max(this.z2, cuboid.z2)
        );
    }

    public double distanceSquared(ILocation location) {
        return this.contains(location)
            ? 0.0
            : Math.min(Math.pow(location.getX() - this.x1, 2.0), Math.pow(location.getX() - this.x2, 2.0))
                + Math.min(Math.pow(location.getY() - this.y1, 2.0), Math.pow(location.getY() - this.y2, 2.0))
                + Math.min(Math.pow(location.getZ() - this.z1, 2.0), Math.pow(location.getZ() - this.z2, 2.0));
    }

    public boolean containsBlock(World world, PacketLocation location) {
        int value = (int)Math.floor(location.getX());
        int value2 = (int)Math.floor(location.getY());
        int value3 = (int)Math.floor(location.getZ());
        return this.containsBlock(world, value, value2, value3);
    }

    public Cuboid setValues(ILocation values) {
        return this.setValues(values.getX(), values.getY(), values.getZ());
    }

    public double getWidthY() {
        return this.y2 - this.y1;
    }

    public double getY1() {
        return this.y1;
    }

    public Cuboid to(ILocation location, int value) {
        if (this.distanceSquared(location) > value) {
            return this;
        }

        this.include(location);
        return this;
    }

    public boolean rayTraceInternal(Supplier<String> supplier, Predicate<MutableBlockLocation> predicate) {
        double distance = MathUtil.hypot(this.x2 - this.x1, this.y2 - this.y1, this.z2 - this.z1);
        double value = (this.x2 - this.x1) / distance;
        double value2 = (this.y2 - this.y1) / distance;
        double value3 = (this.z2 - this.z1) / distance;
        double value4 = MathUtil.lowest(value, value2, value3);
        int value5 = (int)Math.ceil(value4 / distance);
        if (value5 > 100) {
            VerusLauncher.getPlugin()
                .getLogger()
                .severe(
                    String.format(
                        "Tried to raytrace %s blocks for %s (%s, %s, %s) -> (%s, %s, %s) ",
                        value5,
                        supplier.get(),
                        this.x1,
                        this.y1,
                        this.z1,
                        this.x2,
                        this.y2,
                        this.z2
                    )
                );
            return false;
        }

        Vector3d vector3d = new Vector3d(this.x1, this.y1, this.z1);
        MutableBlockLocation mutableBlockLocation = MutableBlockLocation.from(vector3d);

        while (value5-- >= 0) {
            if (!predicate.test(mutableBlockLocation)) {
                return false;
            }

            vector3d.add(value4);
            mutableBlockLocation.andThen(vector3d);
        }

        return true;
    }

    public <C> boolean checkBlocksInternal(
        Supplier<String> supplier, int value, BiFunction<Integer, Integer, C> function, Predicate<C> predicate, BiPredicate<C, IBlockPosition> biPredicate
    ) {
        int value2 = (int)Math.floor(this.x1);
        int value3 = (int)Math.ceil(this.x2);
        int value4 = Math.max((int)Math.floor(this.y1), MIN_Y_LEVEL);
        int value5 = Math.min((int)Math.ceil(this.y2), value);
        int value6 = (int)Math.floor(this.z1);
        int value7 = (int)Math.ceil(this.z2);
        int value8 = (1 + value3 - value2) * (1 + value7 - value6) * (1 + value5 - value4);
        if (value8 > 500) {
            VerusLauncher.getPlugin()
                .getLogger()
                .severe(
                    String.format("Tried to check %s blocks for %s in (%s, %s, %s) -> (%s, %s, %s) ", value8, supplier.get(), value2, value4, value6, value3, value5, value7)
                );
            return false;
        }

        MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(value2, value4, value6);

        while (mutableBlockLocation.getX() < value3) {
            for (; mutableBlockLocation.getZ() < value7; mutableBlockLocation.incrementZ()) {
                C obj = function.apply(mutableBlockLocation.getX(), mutableBlockLocation.getZ());
                if (predicate.test(obj)) {
                    while (mutableBlockLocation.getY() < value5) {
                        if (!biPredicate.test(obj, mutableBlockLocation)) {
                            return false;
                        }

                        mutableBlockLocation.incrementY();
                    }

                    mutableBlockLocation.setY(value4);
                }
            }

            mutableBlockLocation.setZ(value6);
            mutableBlockLocation.setY(value4);
            mutableBlockLocation.incrementX();
        }

        return true;
    }

    public boolean containsXZ(Vector3d vector3d) {
        return vector3d == null ? false : this.containsXZ(vector3d.getX(), vector3d.getZ());
    }

    public Cuboid(IBlockPosition blockPosition) {
        this(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    public double getWidthX() {
        return this.x2 - this.x1;
    }

    public boolean containsYZ(Vector3d vector3d) {
        return vector3d == null ? false : this.containsYZ(vector3d.getY(), vector3d.getZ());
    }

    public BasicLocation[] corners() {
        double[] values = this.x1 != this.x2 ? new double[]{this.x1, this.x2} : new double[]{this.x1};
        double[] values2 = this.y1 != this.y2 ? new double[]{this.y1, this.y2} : new double[]{this.y1};
        double[] values3 = this.z1 != this.z2 ? new double[]{this.z1, this.z2} : new double[]{this.z1};
        BasicLocation[] basicLocations = new BasicLocation[values.length * values2.length * values3.length];
        int value = 0;

        for (double value2 : values) {
            for (double value3 : values2) {
                for (double value4 : values3) {
                    basicLocations[value++] = new BasicLocation(value2, value3, value4);
                }
            }
        }

        return basicLocations;
    }

    public double getZ2() {
        return this.z2;
    }

    public Cuboid add(double value, double value2, double value3, double value4, double value5, double value6) {
        this.x1 += value;
        this.x2 += value2;
        this.y1 += value3;
        this.y2 += value4;
        this.z1 += value5;
        this.z2 += value6;
        return this;
    }

    public double cY() {
        return (this.y1 + this.y2) * 0.5;
    }

    public Cuboid(IVector3d vector3d) {
        this(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }
}
