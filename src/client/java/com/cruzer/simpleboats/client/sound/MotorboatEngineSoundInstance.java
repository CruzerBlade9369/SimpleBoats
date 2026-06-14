package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;

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

        this.volume = Mth.lerp(throttle, 0.8f, 1.55f);
        this.pitch = Mth.lerp(throttle, 0.4f, 1.55f);
    }
}
