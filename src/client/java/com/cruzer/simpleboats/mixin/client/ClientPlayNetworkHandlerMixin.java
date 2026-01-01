package com.cruzer.simpleboats.mixin.client;

import com.cruzer.simpleboats.client.sound.MotorboatOutsideSoundInstance;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin
{
    @Inject(
            method = "playSpawnSound",
            at = @At("TAIL")
    )
    private void simpleboats$playMotorboatSpawnSound(
            Entity entity,
            CallbackInfo ci
    )
    {
        if (entity instanceof MotorboatEntity boat)
        {
            MinecraftClient.getInstance().getSoundManager().play(
                    new MotorboatOutsideSoundInstance(
                            boat
                    )
            );
        }
    }
}
