package yolojj333.ethanserver.mixin;

import net.minecraft.item.FireworkRocketItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {
    @Shadow
    @Final
    public static final byte[] FLIGHT_VALUES = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
}
