package me.levansj01.verus.compat.api.wrapper;

import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import org.bukkit.GameMode;

public class PlayerAbilities {
    private final boolean allowEdit;
    private final boolean isCreativeMode;
    private final boolean allowFlying;
    private final boolean isFlying;
    private final float walkSpeed;
    private final boolean disableDamage;
    private final float flySpeed;

    protected boolean canEqual(Object obj) {
        return obj instanceof PlayerAbilities;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public PlayerAbilities then(VPacketPlayOutAbilities<?> packet) {
        return new PlayerAbilities(
            packet.isInvulnerable(), packet.isFlying(), packet.isCanFly(), packet.isCanInstantlyBuild(), this.allowEdit, packet.getFlySpeed(), packet.getWalkSpeed()
        );
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    @Deprecated
    public PlayerAbilities(boolean flag, boolean flag2, boolean flag3, boolean flag4, float value, float value2) {
        this(flag, flag2, flag3, flag4, true, value, value2);
    }

    public boolean isCreativeMode() {
        return this.isCreativeMode;
    }

    public boolean isAllowEdit() {
        return this.allowEdit;
    }

    public PlayerAbilities then(GameMode gameMode) {
        boolean isFlying = this.isFlying;
        boolean flag = gameMode != GameMode.ADVENTURE;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        if (gameMode == GameMode.CREATIVE) {
            flag3 = true;
            flag4 = true;
            flag2 = true;
        } else if (gameMode.getValue() == 3) {
            flag3 = true;
            flag4 = false;
            flag2 = true;
            isFlying = true;
        } else {
            flag3 = false;
            flag4 = false;
            flag2 = false;
            isFlying = false;
        }

        return new PlayerAbilities(flag2, isFlying, flag3, flag4, flag, this.flySpeed, this.walkSpeed);
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + (this.isDisableDamage() ? 79 : 97);
        value = value * 59 + (this.isFlying() ? 79 : 97);
        value = value * 59 + (this.isAllowFlying() ? 79 : 97);
        value = value * 59 + (this.isCreativeMode() ? 79 : 97);
        value = value * 59 + (this.isAllowEdit() ? 79 : 97);
        value = value * 59 + Float.floatToIntBits(this.getFlySpeed());
        return value * 59 + Float.floatToIntBits(this.getWalkSpeed());
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    public boolean isDisableDamage() {
        return this.disableDamage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof PlayerAbilities)) {
            return false;
        } else {
            PlayerAbilities abilities = (PlayerAbilities)obj;
            if (!abilities.canEqual(this)) {
                return false;
            } else if (this.isDisableDamage() != abilities.isDisableDamage()) {
                return false;
            } else if (this.isFlying() != abilities.isFlying()) {
                return false;
            } else if (this.isAllowFlying() != abilities.isAllowFlying()) {
                return false;
            } else if (this.isCreativeMode() != abilities.isCreativeMode()) {
                return false;
            } else if (this.isAllowEdit() != abilities.isAllowEdit()) {
                return false;
            } else {
                return Float.compare(this.getFlySpeed(), abilities.getFlySpeed()) != 0 ? false : Float.compare(this.getWalkSpeed(), abilities.getWalkSpeed()) == 0;
            }
        }
    }

    public PlayerAbilities(boolean disableDamage, boolean isFlying, boolean allowFlying, boolean isCreativeMode, boolean allowEdit, float flySpeed, float walkSpeed) {
        this.disableDamage = disableDamage;
        this.isFlying = isFlying;
        this.allowFlying = allowFlying;
        this.isCreativeMode = isCreativeMode;
        this.allowEdit = allowEdit;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    public boolean fly() {
        return this.allowFlying || this.isFlying;
    }

    public boolean isAllowFlying() {
        return this.allowFlying;
    }
}
