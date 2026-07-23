package com.cruzer.simpleboats.client.config;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.SimpleBoatsPlatform;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleBoatsConfigManagerClient
{
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH =
            SimpleBoatsPlatform.getConfigDir()
                    .resolve("simpleboats-client.json");

    public static SimpleBoatsConfigClient CONFIG = new SimpleBoatsConfigClient();

    public static void load()
    {
        try
        {
            if (Files.exists(CONFIG_PATH))
            {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH))
                {
                    CONFIG = GSON.fromJson(reader, SimpleBoatsConfigClient.class);
                }
            }
            else
            {
                save();
            }
        } catch (Exception e)
        {
            SimpleBoats.LOGGER.error("Failed to load client config", e);
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
            SimpleBoats.LOGGER.error("Failed to save client config", e);
        }
    }
}
