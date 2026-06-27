package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

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

        Vec3 forward = boat.getForward();
        float offset = 3.5f;
        Vec3 enginePos = boat.position().subtract(forward.scale(offset));

        this.x = (float) enginePos.x;
        this.y = (float) enginePos.y;
        this.z = (float) enginePos.z;

        float throttle = boat.getPowerLevel();

        this.volume = Mth.lerp(throttle, 0.8f, 1.55f);
        this.pitch = Mth.lerp(throttle, 0.4f, 1.55f);
    }
}
