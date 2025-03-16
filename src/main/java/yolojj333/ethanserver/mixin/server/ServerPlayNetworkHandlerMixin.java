package yolojj333.ethanserver.mixin.server;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Final
    @Shadow
    static Logger LOGGER;

    @Shadow
    protected abstract boolean isHost();

    @Redirect(
            method = "onVehicleMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    private boolean preventVehicleMovedQuickly(ServerPlayNetworkHandler instance, @Local Entity entity, @Local(ordinal = 6) double l, @Local(ordinal = 7) double m, @Local(ordinal = 8) double n, @Local(ordinal = 9) double o, @Local(ordinal = 10) double p) {
        if (p - o > (double) 100.0F && !isHost()) {
            LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", entity.getName().getString(), instance.player.getName().getString(), l, m, n);
        }
        return true;
    }

    @Redirect(
            method = "onVehicleMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;isSpaceEmpty(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Z",
                    ordinal = 0
            )
    )
    private boolean preventVehicleMovedWrongly(ServerWorld instance, Entity entity, Box box) {
        return false;
    }

    @Redirect(
            method = "onPlayerMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    private boolean preventPlayerMovedQuickly(ServerPlayNetworkHandler instance, @Local(ordinal = 6) double l, @Local(ordinal = 7) double m, @Local(ordinal = 8) double n, @Local(ordinal = 9) double o, @Local(ordinal = 10) double p, @Local int q, @Local(ordinal = 2) float r) {
        if (p - o > (double) (r * (float) q) && !isHost()) {
            LOGGER.warn("{} moved too quickly! {},{},{}", instance.player.getName().getString(), l, m, n);
        }
        return true;
    }
}
