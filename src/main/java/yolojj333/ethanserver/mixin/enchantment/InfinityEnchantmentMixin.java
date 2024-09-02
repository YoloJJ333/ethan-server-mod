package yolojj333.ethanserver.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export=true)
@Mixin(InfinityEnchantment.class)
public class InfinityEnchantmentMixin extends Enchantment {
    protected InfinityEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(
            method = "canAccept",
            at = @At("HEAD"),
            cancellable = true
    )
    private void allowMending(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canAccept(other));
    }
}
