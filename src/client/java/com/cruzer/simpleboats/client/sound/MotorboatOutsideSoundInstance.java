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
public class MotorboatOutsideSoundInstance extends MovingSoundInstance
{
    private final MotorboatEntity boat;

    public MotorboatOutsideSoundInstance(MotorboatEntity boat)
    {
        super(
                SimpleBoatsSounds.BOAT_MOTOR,
                SoundCategory.NEUTRAL,
                SoundInstance.createRandom()
        );

        this.boat = boat;

        this.attenuationType = AttenuationType.LINEAR;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0F;

        this.x = (float) boat.getX();
        this.y = (float) boat.getY();
        this.z = (float) boat.getZ();
    }

    @Override
    public boolean canPlay() {
        return !boat.isSilent();
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }


    @Override
    public void tick()
    {
        if (boat.isRemoved())
        {
            this.setDone();
            return;
        }

        boolean hasDriver = boat.getControllingPassenger() instanceof PlayerEntity;
        if (!hasDriver)
        {
            this.volume = 0f;
            return;
        }

        this.x = (float) boat.getX();
        this.y = (float) boat.getY();
        this.z = (float) boat.getZ();

        float throttle = boat.getThrottle();

        this.volume = MathHelper.lerp(throttle, 0.75f, 1.2f);
        this.pitch = MathHelper.lerp(throttle, 0.4f, 1.55f);
    }
}
