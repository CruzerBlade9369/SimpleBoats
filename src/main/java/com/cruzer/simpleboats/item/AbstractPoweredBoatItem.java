package com.cruzer.simpleboats.item;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractPoweredBoatItem extends Item
{
    protected final EntityType<? extends AbstractBoat> boatEntityType;

    public AbstractPoweredBoatItem(EntityType<? extends AbstractBoat> type, Properties settings) {
        super(settings);
        this.boatEntityType = type;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand)
    {
        ItemStack stack = user.getItemInHand(hand);

        HitResult hit = getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);

        if (hit.getType() == HitResult.Type.MISS) return InteractionResult.PASS;

        Vec3 dir = user.getViewVector(1.0f);
        List<Entity> list = world.getEntities(
                user,
                user.getBoundingBox().expandTowards(dir.scale(5.0)).inflate(1.0),
                EntitySelector.CAN_BE_PICKED
        );

        Vec3 eyePos = user.getEyePosition();

        for (Entity e : list)
        {
            AABB box = e.getBoundingBox().inflate(e.getPickRadius());
            if (box.contains(eyePos)) return InteractionResult.PASS;
        }

        if (hit.getType() == HitResult.Type.BLOCK)
        {
            AbstractBoat boat = createBoat(world, hit, stack, user);
            if (boat == null) return InteractionResult.FAIL;

            boat.setYRot(user.getYRot());

            if(!world.noCollision(boat, boat.getBoundingBox())) return InteractionResult.FAIL;

            if(!world.isClientSide())
            {
                world.addFreshEntity(boat);
                world.gameEvent(user, GameEvent.ENTITY_PLACE, boat.blockPosition());
                stack.consume(1, user);
            }

            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private AbstractBoat createBoat(Level world, HitResult hit, ItemStack stack, Player player) {

        AbstractBoat boat = this.boatEntityType.create(world, EntitySpawnReason.SPAWN_ITEM_USE);

        if (boat != null) {
            Vec3 pos = hit.getLocation();
            boat.setInitialPos(pos.x, pos.y, pos.z);

            if (world instanceof ServerLevel server) {
                EntityType.createDefaultStackConfig(server, stack, player).accept(boat);
            }
        }

        return boat;
    }
}
