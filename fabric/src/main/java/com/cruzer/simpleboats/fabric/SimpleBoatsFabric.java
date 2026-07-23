package com.cruzer.simpleboats.fabric;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.SimpleBoatsPlatform;
import com.cruzer.simpleboats.config.SimpleBoatsConfigManagerServer;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.item.MotorboatItem;
import com.cruzer.simpleboats.item.SailboatItem;
import com.cruzer.simpleboats.network.SimpleBoatsNetworking;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import java.util.function.Supplier;

public class SimpleBoatsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SimpleBoatsPlatform.setConfigDir(FabricLoader.getInstance().getConfigDir());
        
        SimpleBoatsPlatform.setPacketSender(new SimpleBoatsPlatform.PacketSender() {
            @Override
            public void sendToServer(CustomPacketPayload payload) {
                // No-op on server
            }

            @Override
            public void sendToPlayer(Player player, CustomPacketPayload payload) {
                if (player instanceof ServerPlayer sp) {
                    ServerPlayNetworking.send(sp, payload);
                }
            }
        });

        // Register Sounds
        SimpleBoatsSounds.BOAT_MOTOR = registerSound("boat.motor");
        SimpleBoatsSounds.BOAT_MOTOR_START = registerSound("boat.motor.start");
        SimpleBoatsSounds.BOAT_SAIL = registerSound("boat.sail.ambient");

        // Register Entities & Items
        registerEntitiesAndItems();

        // Networking Registry
        SimpleBoatsNetworking.register();

        // Load Config
        SimpleBoatsConfigManagerServer.load();
    }

    private Supplier<SoundEvent> registerSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name);
        SoundEvent sound = Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
        return () -> sound;
    }

    private static Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name)));
    }

    private void registerEntitiesAndItems() {
        // Ingredients
        SimpleBoatsItems.BOAT_PROPELLER = registerItem("boat_propeller", new Item(itemProperties("boat_propeller")));
        SimpleBoatsItems.OUTBOARD_MOTOR = registerItem("outboard_motor", new Item(itemProperties("outboard_motor")));
        SimpleBoatsItems.BOAT_SAIL = registerItem("boat_sail", new Item(itemProperties("boat_sail")));

        addToItemGroup(SimpleBoatsItems.BOAT_PROPELLER.get(), CreativeModeTabs.INGREDIENTS);
        addToItemGroup(SimpleBoatsItems.OUTBOARD_MOTOR.get(), CreativeModeTabs.INGREDIENTS);
        addToItemGroup(SimpleBoatsItems.BOAT_SAIL.get(), CreativeModeTabs.INGREDIENTS);

        // Motorboats & Sailboats
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values()) {
            String name = type.getName();

            // Entity Type
            ResourceKey<EntityType<?>> mbKey = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name + "_motorboat"));
            Supplier<Item> mbItemSupplier = () -> SimpleBoatsItems.MOTORBOAT_ITEMS.get(type).get();
            EntityType<MotorboatEntity> mbType = Registry.register(BuiltInRegistries.ENTITY_TYPE, mbKey,
                    EntityType.Builder.of(getFactory(MotorboatEntity::new, mbItemSupplier), MobCategory.MISC)
                            .sized(4.4f, 0.675f)
                            .clientTrackingRange(80)
                            .build(mbKey)
            );
            SimpleBoatsEntities.MOTORBOAT_TYPES.put(type, () -> mbType);

            ResourceKey<EntityType<?>> sbKey = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name + "_sailboat"));
            Supplier<Item> sbItemSupplier = () -> SimpleBoatsItems.SAILBOAT_ITEMS.get(type).get();
            EntityType<SailboatEntity> sbType = Registry.register(BuiltInRegistries.ENTITY_TYPE, sbKey,
                    EntityType.Builder.of(getFactory(SailboatEntity::new, sbItemSupplier), MobCategory.MISC)
                            .sized(4.4f, 0.675f)
                            .clientTrackingRange(80)
                            .build(sbKey)
            );
            SimpleBoatsEntities.SAILBOAT_TYPES.put(type, () -> sbType);

            // Item
            Item mbItem = registerItemDirect(name + "_motorboat", new MotorboatItem(() -> mbType, itemProperties(name + "_motorboat").stacksTo(1)));
            SimpleBoatsItems.MOTORBOAT_ITEMS.put(type, () -> mbItem);
            addToItemGroup(mbItem, CreativeModeTabs.TOOLS_AND_UTILITIES);

            Item sbItem = registerItemDirect(name + "_sailboat", new SailboatItem(() -> sbType, itemProperties(name + "_sailboat").stacksTo(1)));
            SimpleBoatsItems.SAILBOAT_ITEMS.put(type, () -> sbItem);
            addToItemGroup(sbItem, CreativeModeTabs.TOOLS_AND_UTILITIES);
        }
    }

    private Supplier<Item> registerItem(String name, Item item) {
        Identifier id = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name);
        Item registered = Registry.register(BuiltInRegistries.ITEM, id, item);
        return () -> registered;
    }

    private Item registerItemDirect(String name, Item item) {
        Identifier id = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name);
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    private void addToItemGroup(Item item, ResourceKey<CreativeModeTab> group) {
        CreativeModeTabEvents.modifyOutputEvent(group).register((itemGroup) -> itemGroup.accept(item));
    }

    private static <E extends AbstractPoweredBoatEntity> EntityType.EntityFactory<E> getFactory(
            SimpleBoatsEntities.BoatConstructor<E> constructor,
            Supplier<Item> itemSupplier
    ) {
        return (entityType, world) -> constructor.create(entityType, world, itemSupplier);
    }
}
