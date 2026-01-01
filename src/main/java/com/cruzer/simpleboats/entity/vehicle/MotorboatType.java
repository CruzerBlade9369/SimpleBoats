package com.cruzer.simpleboats.entity.vehicle;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.util.Identifier;

public enum MotorboatType {
    OAK("oak"),
    BIRCH("spruce"),
    SPRUCE("birch"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    MANGROVE("mangrove"),
    CHERRY("cherry"),
    PALE_OAK("pale_oak");

    private final String name;

    MotorboatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Identifier getTexture() {
        return Identifier.of(
                SimpleBoats.MOD_ID,
                "textures/entity/motorboat/" + name + "_motorboat.png"
        );
    }
}
