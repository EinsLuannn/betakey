package com.luan.betakey.commands;

import com.luan.betakey.manager.BetaKeyManager;
import com.luan.betakey.manager.KeyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DeleteKeyCommand extends Command {
    public DeleteKeyCommand() {
        super("deleteKey");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BetaKeyManager betakeyManager = new BetaKeyManager();
        KeyManager keyManager = new KeyManager();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (player.hasPermission("beta.delete")) {
                if (args.length == 1) {
                    if (keyManager.isExist(args[0])) {
                        keyManager.delete(args[0]);
                        betakeyManager.delete(args[0]);
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eKey wurde erflogreich gelöcht."));
                    } else {
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDer Key existiert nicht."));
                    }
                } else {
                    player.sendMessage(new TextComponent("§7[§bBetakey§7] §eNutze /deleteKey <Key>."));
                }
            } else {
                player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDazu hast du keine Rechte."));
            }
        }
    }
}