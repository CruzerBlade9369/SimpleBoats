package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class AbstractPoweredBoatSoundInstance extends MovingSoundInstance
{
    protected final AbstractPoweredBoatEntity boat;
    protected boolean hasDriver;

    public AbstractPoweredBoatSoundInstance(AbstractPoweredBoatEntity boat, SoundEvent sound)
    {
        super(
                sound,
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

        hasDriver = boat.getControllingPassenger() instanceof PlayerEntity;

        this.x = (float) boat.getX();
        this.y = (float) boat.getY();
        this.z = (float) boat.getZ();

        // sound conditions implementation on subclass
    }
}
