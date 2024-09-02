package yolojj333.ethanserver.mixin.enchantment;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    private int mendingRepairAmountInput(ExperienceOrbEntity instance, @Local(argsOnly = true) int amount, @Local ItemStack itemStack) {
        return EnchantmentHelper.get(itemStack).containsKey(Enchantments.INFINITY) ? amount / 2 : amount;
    }

    @Inject(
            method = "getMendingRepairAmount",
            at = @At("TAIL"),
            cancellable = true
    )
    private void lowerMendingRepairAmount(int experienceAmount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Math.random() < 0.69 ? experienceAmount * 2 : 0);
    }

    @Inject(
            method = "getMendingRepairCost",
            at = @At("TAIL"),
            cancellable = true
    )
    private void lowerMendingRepairCost(int repairAmount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(repairAmount / 2);
    }

    @Redirect(
            method = "repairPlayerGears",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/entity/player/PlayerEntity;I)I"
            )
    )
    private int gainXpIfMendingFail(ExperienceOrbEntity instance, PlayerEntity player, int amount, @Local ItemStack itemStack, @Local(ordinal = 1) int i) {
        amount = EnchantmentHelper.get(itemStack).containsKey(Enchantments.INFINITY) ? amount - (i/2) : amount;
        return i == 0 ? amount : repairPlayerGears(player, amount);
    }
}