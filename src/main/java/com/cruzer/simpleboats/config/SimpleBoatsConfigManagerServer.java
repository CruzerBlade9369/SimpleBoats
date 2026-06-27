package com.cruzer.simpleboats.config;

import com.cruzer.simpleboats.SimpleBoats;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleBoatsConfigManagerServer
{
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("simpleboats-server.json");

    public static SimpleBoatsConfigServer CONFIG = new SimpleBoatsConfigServer();

    public static void load()
    {
        try
        {
            if (Files.exists(CONFIG_PATH))
            {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH))
                {
                    CONFIG = GSON.fromJson(reader, SimpleBoatsConfigServer.class);
                }
            }
            else
            {
                save();
            }
        } catch (Exception e)
        {
            SimpleBoats.LOGGER.error("Failed to load server config", e);
        }
    }

    public static void save()
    {
        try
        {
            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH))
            {
                GSON.toJson(CONFIG, writer);
            }
        }
        catch (Exception e)
        {
            SimpleBoats.LOGGER.error("Failed to save server config", e);
        }
    }
}
