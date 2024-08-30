package yolojj333.ethanserver.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MultishotEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultishotEnchantment.class)
public class MultishotEnchantmentMixin extends Enchantment {
    protected MultishotEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(
            method = "canAccept",
            at = @At("TAIL")
    )
    private void allowPiercing(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canAccept(other));
    }
}
