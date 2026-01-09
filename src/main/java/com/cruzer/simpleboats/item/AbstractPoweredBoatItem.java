package com.cruzer.simpleboats.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public abstract class AbstractPoweredBoatItem extends Item
{
    protected final EntityType<? extends AbstractBoatEntity> boatEntityType;

    public AbstractPoweredBoatItem(EntityType<? extends AbstractBoatEntity> type, Settings settings) {
        super(settings);
        this.boatEntityType = type;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);

        HitResult hit = raycast(world, user, RaycastContext.FluidHandling.ANY);

        if (hit.getType() == HitResult.Type.MISS) return ActionResult.PASS;

        Vec3d dir = user.getRotationVec(1.0f);
        List<Entity> list = world.getOtherEntities(
                user,
                user.getBoundingBox().stretch(dir.multiply(5.0)).expand(1.0),
                EntityPredicates.CAN_HIT
        );

        Vec3d eyePos = user.getEyePos();

        for (Entity e : list)
        {
            Box box = e.getBoundingBox().expand(e.getTargetingMargin());
            if (box.contains(eyePos)) return ActionResult.PASS;
        }

        if (hit.getType() == HitResult.Type.BLOCK)
        {
            AbstractBoatEntity boat = createBoat(world, hit, stack, user);
            if (boat == null) return ActionResult.FAIL;

            boat.setYaw(user.getYaw());

            if(!world.isSpaceEmpty(boat, boat.getBoundingBox())) return ActionResult.FAIL;

            if(!world.isClient())
            {
                world.spawnEntity(boat);
                world.emitGameEvent(user, GameEvent.ENTITY_PLACE, boat.getBlockPos());
                stack.decrementUnlessCreative(1, user);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private AbstractBoatEntity createBoat(World world, HitResult hit, ItemStack stack, PlayerEntity player) {

        AbstractBoatEntity boat = this.boatEntityType.create(world, SpawnReason.SPAWN_ITEM_USE);

        if (boat != null) {
            Vec3d pos = hit.getPos();
            boat.initPosition(pos.x, pos.y, pos.z);

            if (world instanceof ServerWorld server) {
                EntityType.copier(server, stack, player).accept(boat);
            }
        }

        return boat;
    }
}
