package yolojj333.ethanserver.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract boolean isHoldingOntoLadder();

    @Unique
    double prevYVel;
    @Unique
    double prevYVel2;
    @ModifyArg(
            method = "applyClimbingSpeed",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;max(DD)D"
            ),
            index = 0
    )
    private double increaseClimbSpeed(double a){
        prevYVel2 = prevYVel;
        prevYVel = a;
        return a <= prevYVel2 ? a * 3 : this.isHoldingOntoLadder() ? 0.0 : a * 1.1;
    }
}
