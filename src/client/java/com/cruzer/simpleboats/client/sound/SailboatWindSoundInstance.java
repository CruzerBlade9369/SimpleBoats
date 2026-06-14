package com.cruzer.simpleboats.client.sound;

import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class SailboatWindSoundInstance extends AbstractPoweredBoatSoundInstance
{
    private Vec3 lastPos;
    private float speedEstimate;

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

        float rawSpeed = calculateDisplacementVelocity();
        speedEstimate *= 0.95f;
        speedEstimate = Math.clamp(Math.max(speedEstimate, rawSpeed), 0f, 0.5f);

        this.pitch = 1f;
        this.volume = Mth.lerp(speedEstimate / 0.4f, 0f, 0.3f);
    }

    private float calculateDisplacementVelocity()
    {
        Vec3 pos = boat.position();

        if (lastPos == null)
        {
            lastPos = pos;
            return 0f;
        }

        Vec3 delta = pos.subtract(lastPos);
        lastPos = pos;

        float yawRad = boat.getYRot() * ((float)Math.PI / 180F);

        double fwdX = -Math.sin(yawRad);
        double fwdZ = Math.cos(yawRad);

        float forwardSpeed = (float)(
                delta.x * fwdX +
                        delta.z * fwdZ
        );

        return Math.abs(forwardSpeed);
    }
}
