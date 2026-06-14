package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class AbstractPoweredBoatSoundInstance extends AbstractTickableSoundInstance
{
    protected final AbstractPoweredBoatEntity boat;
    protected boolean hasDriver;

    public AbstractPoweredBoatSoundInstance(AbstractPoweredBoatEntity boat, SoundEvent sound)
    {
        super(
                sound,
                SoundSource.NEUTRAL,
                SoundInstance.createUnseededRandom()
        );

        this.boat = boat;

        this.attenuation = Attenuation.LINEAR.LINEAR;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;

        this.x = (float) boat.getX();
        this.y = (float) boat.getY();
        this.z = (float) boat.getZ();
    }

    @Override
    public boolean canPlaySound() {
        return !boat.isSilent();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick()
    {
        if (boat.isRemoved())
        {
            this.stop();
            return;
        }

        hasDriver = boat.getControllingPassenger() instanceof Player;

        this.x = (float) boat.getX();
        this.y = (float) boat.getY();
        this.z = (float) boat.getZ();

        // sound conditions implementation on subclass
    }
}
