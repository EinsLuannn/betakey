package com.luan.betakey.commands;

import com.luan.betakey.manager.BetaKeyManager;
import com.luan.betakey.manager.KeyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ActivateCommand extends Command {
    public ActivateCommand() {
        super("activateKey");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BetaKeyManager betakeyManager = new BetaKeyManager();
        KeyManager keyManager = new KeyManager();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 1) {
                String key = args[0];
                if (keyManager.isExist(key)) {
                    if (!betakeyManager.isExist(player.getUniqueId().toString())) {
                        betakeyManager.create(key, player.getUniqueId());
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDu hast deinen Key erfolgreich aktiviert."));
                    } else {
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDer Key ist bereits belegt."));
                    }
                } else {
                    player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDer Key ist nicht gültig."));
                }
            } else {
                player.sendMessage(new TextComponent("§7[§bBetakey§7] §eBitte gebe einen Key an."));
            }
        }
    }
}