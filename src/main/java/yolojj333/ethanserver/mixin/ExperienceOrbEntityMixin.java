package yolojj333.ethanserver.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {
    @Shadow
    protected abstract int repairPlayerGears(PlayerEntity player, int amount);

    @Redirect(
            method = "repairPlayerGears",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;amount:I"
            )
    )
    private int mendingRepairInput(ExperienceOrbEntity instance, @Local(argsOnly = true) int amount) {
        return amount;
    }

    @Inject(
            method = "getMendingRepairAmount",
            at = @At("TAIL"),
            cancellable = true
    )
    private void lowerMendingRepairAmount(int experienceAmount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Math.random() < 0.5 ? experienceAmount : 0);
    }

    @Inject(
            method = "getMendingRepairCost",
            at = @At("TAIL"),
            cancellable = true
    )
    private void lowerMendingRepairCost(int repairAmount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(repairAmount);
    }

    @Redirect(
            method = "repairPlayerGears",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/entity/player/PlayerEntity;I)I"
            )
    )
    private int gainXpIfMendingFail(ExperienceOrbEntity instance, PlayerEntity player, int amount, @Local(ordinal = 1) int i) {
        return i == 0 ? amount : repairPlayerGears(player, amount);
    }
}
