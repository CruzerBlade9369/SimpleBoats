package com.cruzer.simpleboats.neoforge;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.SimpleBoatsPlatform;
import com.cruzer.simpleboats.SimpleBoatsClient;
import com.cruzer.simpleboats.config.SimpleBoatsConfigManagerServer;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.item.MotorboatItem;
import com.cruzer.simpleboats.item.SailboatItem;
import com.cruzer.simpleboats.network.SimpleBoatsConfigEditPacket;
import com.cruzer.simpleboats.network.SimpleBoatsConfigRequestPacket;
import com.cruzer.simpleboats.network.SimpleBoatsConfigSyncPacket;
import com.cruzer.simpleboats.network.SimpleBoatsControlPacket;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.server.permissions.Permissions;
import java.util.function.Supplier;

@Mod(SimpleBoats.MOD_ID)
public class SimpleBoatsNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimpleBoats.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, SimpleBoats.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, SimpleBoats.MOD_ID);

    public SimpleBoatsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        SimpleBoatsPlatform.setConfigDir(FMLPaths.CONFIGDIR.get());

        // Default Server Sender setup
        SimpleBoatsPlatform.setPacketSender(new SimpleBoatsPlatform.PacketSender() {
            @Override
            public void sendToServer(CustomPacketPayload payload) {
                // Set on client physical side
            }

            @Override
            public void sendToPlayer(Player player, CustomPacketPayload payload) {
                if (player instanceof ServerPlayer sp) {
                    PacketDistributor.sendToPlayer(sp, payload);
                }
            }
        });

        // Register Sounds
        SimpleBoatsSounds.BOAT_MOTOR = SOUNDS.register("boat.motor", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "boat.motor")));
        SimpleBoatsSounds.BOAT_MOTOR_START = SOUNDS.register("boat.motor.start", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "boat.motor.start")));
        SimpleBoatsSounds.BOAT_SAIL = SOUNDS.register("boat.sail.ambient", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "boat.sail.ambient")));

        // Register Items and Entities
        registerEntitiesAndItems();

        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        SOUNDS.register(modEventBus);

        modEventBus.addListener(this::registerNetworking);
        modEventBus.addListener(this::addCreativeTabContents);
        modEventBus.addListener(this::onClientSetup);

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            ClientSetup.register(modEventBus);
        }
        
        SimpleBoatsConfigManagerServer.load();
    }

    private static Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, name)));
    }

    private void registerEntitiesAndItems() {
        SimpleBoatsItems.BOAT_PROPELLER = ITEMS.register("boat_propeller", () -> new Item(itemProperties("boat_propeller")));
        SimpleBoatsItems.OUTBOARD_MOTOR = ITEMS.register("outboard_motor", () -> new Item(itemProperties("outboard_motor")));
        SimpleBoatsItems.BOAT_SAIL = ITEMS.register("boat_sail", () -> new Item(itemProperties("boat_sail")));

        for (SimpleBoatsTypes type : SimpleBoatsTypes.values()) {
            String name = type.getName();

            // Entity Type
            Supplier<Item> mbItemSupplier = () -> SimpleBoatsItems.MOTORBOAT_ITEMS.get(type).get();
            DeferredHolder<EntityType<?>, EntityType<MotorboatEntity>> mbType = ENTITIES.register(name + "_motorboat", (key) ->
                    EntityType.Builder.of(getFactory(MotorboatEntity::new, mbItemSupplier), MobCategory.MISC)
                            .sized(4.4f, 0.675f)
                            .clientTrackingRange(80)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, key))
            );
            SimpleBoatsEntities.MOTORBOAT_TYPES.put(type, mbType::get);

            Supplier<Item> sbItemSupplier = () -> SimpleBoatsItems.SAILBOAT_ITEMS.get(type).get();
            DeferredHolder<EntityType<?>, EntityType<SailboatEntity>> sbType = ENTITIES.register(name + "_sailboat", (key) ->
                    EntityType.Builder.of(getFactory(SailboatEntity::new, sbItemSupplier), MobCategory.MISC)
                            .sized(4.4f, 0.675f)
                            .clientTrackingRange(80)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, key))
            );
            SimpleBoatsEntities.SAILBOAT_TYPES.put(type, sbType::get);

            // Item
            DeferredHolder<Item, MotorboatItem> mbItem = ITEMS.register(name + "_motorboat", () -> new MotorboatItem(mbType, itemProperties(name + "_motorboat").stacksTo(1)));
            SimpleBoatsItems.MOTORBOAT_ITEMS.put(type, mbItem::get);

            DeferredHolder<Item, SailboatItem> sbItem = ITEMS.register(name + "_sailboat", () -> new SailboatItem(sbType, itemProperties(name + "_sailboat").stacksTo(1)));
            SimpleBoatsItems.SAILBOAT_ITEMS.put(type, sbItem::get);
        }
    }

    private void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SimpleBoatsItems.BOAT_PROPELLER.get());
            event.accept(SimpleBoatsItems.OUTBOARD_MOTOR.get());
            event.accept(SimpleBoatsItems.BOAT_SAIL.get());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            for (SimpleBoatsTypes type : SimpleBoatsTypes.values()) {
                event.accept(SimpleBoatsItems.MOTORBOAT_ITEMS.get(type).get());
                event.accept(SimpleBoatsItems.SAILBOAT_ITEMS.get(type).get());
            }
        }
    }

    private void registerNetworking(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(SimpleBoats.MOD_ID).optional();

        registrar.playToServer(SimpleBoatsControlPacket.ID, SimpleBoatsControlPacket.CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer sp && sp.getVehicle() instanceof AbstractPoweredBoatEntity pb) {
                    pb.applyThrottleIntent(sp, payload.throttleUp(), payload.throttleDown());
                }
            });
        });

        registrar.playToServer(SimpleBoatsConfigEditPacket.ID, SimpleBoatsConfigEditPacket.CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer sp && sp.level() instanceof ServerLevel sl && sl.getServer() != null && sp.permissions().hasPermission(Permissions.COMMANDS_ADMIN)) {
                    SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor = payload.motorboatThrustFactor();
                    SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor = payload.sailboatThrustFactor();
                    SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate = payload.motorboatTurnRate();
                    SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate = payload.sailboatMaxTurnRate();
                    SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate = payload.sailboatMinTurnRate();
                    SimpleBoatsConfigManagerServer.save();

                    MotorboatEntity.updateThrustValues(payload.motorboatThrustFactor());
                    MotorboatEntity.updateTurnRate(payload.motorboatTurnRate());
                    SailboatEntity.updateThrustValues(payload.sailboatThrustFactor());
                    SailboatEntity.updateTurnRate(payload.sailboatMaxTurnRate(), payload.sailboatMinTurnRate());

                    for (ServerPlayer player : sl.getServer().getPlayerList().getPlayers()) {
                        PacketDistributor.sendToPlayer(player, new SimpleBoatsConfigSyncPacket(
                                SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor,
                                SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor,
                                SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate,
                                SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate,
                                SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate,
                                player.permissions().hasPermission(Permissions.COMMANDS_ADMIN)
                        ));
                    }
                }
            });
        });

        registrar.playToServer(SimpleBoatsConfigRequestPacket.ID, SimpleBoatsConfigRequestPacket.CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer sp && sp.level() instanceof ServerLevel sl && sl.getServer() != null) {
                    PacketDistributor.sendToPlayer(sp, new SimpleBoatsConfigSyncPacket(
                            SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor,
                            SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor,
                            SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate,
                            SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate,
                            SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate,
                            sp.permissions().hasPermission(Permissions.COMMANDS_ADMIN)
                    ));
                }
            });
        });

        registrar.playToClient(SimpleBoatsConfigSyncPacket.ID, SimpleBoatsConfigSyncPacket.CODEC, (payload, context) -> {
            context.enqueueWork(() -> SimpleBoatsClient.handleConfigSync(payload));
        });
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        SimpleBoatsClient.initialize();
    }

    private static <E extends AbstractPoweredBoatEntity> EntityType.EntityFactory<E> getFactory(
            SimpleBoatsEntities.BoatConstructor<E> constructor,
            Supplier<Item> itemSupplier
    ) {
        return (entityType, world) -> constructor.create(entityType, world, itemSupplier);
    }

    @EventBusSubscriber(modid = SimpleBoats.MOD_ID)
    public static class ServerEvents {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer sp && sp.level() instanceof ServerLevel sl && sl.getServer() != null) {
                PacketDistributor.sendToPlayer(sp, new SimpleBoatsConfigSyncPacket(
                        SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor,
                        SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate,
                        sp.permissions().hasPermission(Permissions.COMMANDS_ADMIN)
                ));
            }
        }
    }

    public static class ClientSetup {
        public static void register(IEventBus modEventBus) {
            modEventBus.addListener(com.cruzer.simpleboats.neoforge.client.SimpleBoatsNeoForgeClient::onClientSetup);
            modEventBus.addListener(com.cruzer.simpleboats.neoforge.client.SimpleBoatsNeoForgeClient::onRegisterLayers);
            modEventBus.addListener(com.cruzer.simpleboats.neoforge.client.SimpleBoatsNeoForgeClient::onRegisterRenderers);
        }
    }
}
