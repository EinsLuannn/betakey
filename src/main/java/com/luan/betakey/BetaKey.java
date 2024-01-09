package com.luan.betakey;

import com.luan.betakey.commands.ActivateCommand;
import com.luan.betakey.commands.CreateKeyCommand;
import com.luan.betakey.commands.DeleteKeyCommand;
import com.luan.betakey.listener.PlayerLoginListener;
import com.luan.betakey.mysql.MySQL;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class BetaKey extends Plugin {

    @Getter
    private static BetaKey instance;

    @Getter
    private Configuration config;

    private MySQL mySQL;

    {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        createFiles();
        mySQL = new MySQL();
        mySQL.connect();
        mySQL.createTabels();

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CreateKeyCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new DeleteKeyCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ActivateCommand());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerLoginListener());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        mySQL.disconnect();
    }

    private void createFiles() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream inputStream = getResourceAsStream("config.yml")) {
                Files.copy(inputStream, file.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
