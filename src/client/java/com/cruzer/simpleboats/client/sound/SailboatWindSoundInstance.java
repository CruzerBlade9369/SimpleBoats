package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SailboatWindSoundInstance extends AbstractPoweredBoatSoundInstance
{
    public SailboatWindSoundInstance(SailboatEntity boat)
    {
        super(
                boat, SimpleBoatsSounds.BOAT_SAIL
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

        float fwdSpd = Math.max(0.0f, boat.getForwardSpeed());

        this.pitch = 1f;
        this.volume = MathHelper.lerp(fwdSpd / 0.4f, 0f, 0.5f);
    }
}
