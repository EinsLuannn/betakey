package com.luan.betakey.commands;

import com.luan.betakey.manager.BetaKeyManager;
import com.luan.betakey.manager.KeyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class CreateKeyCommand extends Command {
    public CreateKeyCommand() {
        super("createKey");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BetaKeyManager betakeyManager = new BetaKeyManager();
        KeyManager keyManager = new KeyManager();
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (player.hasPermission("beta.createKey")) {
                if (args.length == 1) {
                    if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                        String key = keyManager.create();
                        betakeyManager.create(key, target.getUniqueId());
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eKey wurde erfolgreich erstellt."));
                    } else {
                        player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDer Spieler muss Online sein."));
                    }
                } else {
                    String key = keyManager.create();
                    TextComponent textComponent = new TextComponent("§7[§bBetakey§7] §eDer key §f§l");
                    TextComponent keyComp = new TextComponent(key);
                    keyComp.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, key));
                    TextComponent end = new TextComponent(" §ewurde erfolgreich erstellt.");
                    textComponent.addExtra(keyComp);
                    textComponent.addExtra(end);
                    player.sendMessage(textComponent);
                }
            } else {
                player.sendMessage(new TextComponent("§7[§bBetakey§7] §eDazu hast du keine Rechte."));
            }
        }
    }
}