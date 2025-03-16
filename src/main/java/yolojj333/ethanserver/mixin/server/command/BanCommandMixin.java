package yolojj333.ethanserver.mixin.server.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.dedicated.command.BanCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Environment(EnvType.SERVER)
@Mixin(BanCommand.class)
public abstract class BanCommandMixin {
    @Redirect(
            method = "register",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;requires(Ljava/util/function/Predicate;)Lcom/mojang/brigadier/builder/ArgumentBuilder;"
            )
    )
    private static ArgumentBuilder modifyBanCommandPermissionLevel(LiteralArgumentBuilder instance, Predicate predicate) {
        return CommandManager.literal("ban").requires((source) -> {
            return source.hasPermissionLevel(1);
        });
    }
}
