package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MotorboatEngineSoundInstance extends AbstractPoweredBoatSoundInstance
{
    public MotorboatEngineSoundInstance(MotorboatEntity boat)
    {
        super(
                boat, SimpleBoatsSounds.BOAT_MOTOR
        );
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!hasDriver)
        {
            this.volume = 0;
            return;
        }

        float throttle = boat.getPowerLevel();

        this.volume = MathHelper.lerp(throttle, 0.8f, 1.55f);
        this.pitch = MathHelper.lerp(throttle, 0.4f, 1.55f);
    }
}
