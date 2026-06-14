package com.cruzer.simpleboats.mixin.client;

import com.cruzer.simpleboats.client.sound.MotorboatEngineSoundInstance;
import com.cruzer.simpleboats.client.sound.SailboatWindSoundInstance;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin
{
    @Inject(
            method = "postAddEntitySoundInstance",
            at = @At("TAIL")
    )
    private void simpleboats$playMotorboatSpawnSound(
            Entity entity,
            CallbackInfo ci
    )
    {
        if (entity instanceof MotorboatEntity mb)
        {
            Minecraft.getInstance().getSoundManager().play(
                    new MotorboatEngineSoundInstance(
                            mb
                    )
            );
        }

        if (entity instanceof SailboatEntity sb)
        {
            Minecraft.getInstance().getSoundManager().play(
                    new SailboatWindSoundInstance(
                            sb
                    )
            );
        }
    }
}
