package com.luan.betakey.listener;

import com.luan.betakey.BetaKey;
import com.luan.betakey.manager.BetaKeyManager;
import com.luan.betakey.manager.KeyManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {

    private BetaKeyManager betakeyManager;
    private KeyManager keyManager;

    public PlayerLoginListener() {
        betakeyManager = new BetaKeyManager();
        keyManager = new KeyManager();
    }

    @EventHandler
    public void onJoin(PostLoginEvent event){
        ProxiedPlayer player = event.getPlayer();
        if (!player.hasPermission("beta.ignore")) {
            if (betakeyManager.isExist(event.getPlayer().getUniqueId().toString())){
                String key = betakeyManager.getKey(event.getPlayer().getUniqueId());
                if (!keyManager.isExist(key)){
                    player.disconnect("§7▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n§4Your key is not valid.\n§7▙▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▟");
                } else {

                }
            } else {
                if (ProxyServer.getInstance().getServerInfo(BetaKey.getInstance().getConfig().getString("checkBetaKeyServer")) != null) {
                    player.connect(ProxyServer.getInstance().getServerInfo(BetaKey.getInstance().getConfig().getString("checkBetaKeyServer")));
                } else {
                    player.disconnect("§7▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n§4An unknown error has occurred\n please contact an admin.\n§7▙▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▖▟");
                }
            }
        } else {
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bBetakey System wurde aufgrund deiner Permission ignoriert."));
        }

    }
}