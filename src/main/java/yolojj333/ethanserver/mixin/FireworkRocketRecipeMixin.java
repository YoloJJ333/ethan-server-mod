package yolojj333.ethanserver.mixin;

import net.minecraft.recipe.FireworkRocketRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FireworkRocketRecipe.class)
public class FireworkRocketRecipeMixin {
    @ModifyConstant(
        method = "matches(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/world/World;)Z",
        constant = @Constant(intValue = 3)
    )
    private int increaseMaxSpeed(int constant){
        return 8;
    }
}
