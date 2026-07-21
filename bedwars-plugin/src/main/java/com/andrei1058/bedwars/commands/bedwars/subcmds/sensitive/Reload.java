/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import com.andrei1058.bedwars.configuration.MoneyConfig;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Reload extends SubCommand {

    public Reload(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(11);
        showInList(true);
        setPermission(Permissions.PERMISSION_RELOAD);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+"       §8 - §ereload configs",
                "§fReload config files and messages.\n§7Values read live update instantly.\n§cSettings applied at startup still need a full restart!", "/"+ getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof Player) {
            if (!MainCommand.isLobbySet((Player) s)) return true;
        } else {
            if (!MainCommand.isLobbySet(null)) return true;
        }

        // Reload the main yml-backed config files. These are read live in most places,
        // so re-reading them from disk makes edited values take effect immediately.
        reloadConfig(s, "config", com.andrei1058.bedwars.BedWars.config);
        reloadConfig(s, "generators", com.andrei1058.bedwars.BedWars.generators);
        reloadConfig(s, "signs", com.andrei1058.bedwars.BedWars.signs);
        reloadConfig(s, "sounds", Sounds.getSounds());
        reloadConfig(s, "levels", LevelsConfig.levels);
        reloadConfig(s, "money", MoneyConfig.money);
        reloadConfig(s, "upgrades", UpgradesManager.getConfiguration());

        // Shop needs its index (categories/items/tiers) rebuilt, not just the yml re-read.
        if (com.andrei1058.bedwars.BedWars.shop != null) {
            com.andrei1058.bedwars.BedWars.shop.reloadConfiguration();
            s.sendMessage("§6 ▪ §7shop.yml reloaded!");
        }

        // Reload language / message files.
        for (Language l : Language.getLanguages()){
            l.reload();
            s.sendMessage("§6 ▪ §7"+l.getLangName()+" reloaded!");
        }

        s.sendMessage("§a ▪ §7Reload complete. §8Startup-only settings (server type, lobby, listeners) still require a full restart.");
        return true;
    }

    /**
     * Re-read a config file from disk if it exists. Null-safe: some configs are only
     * initialised for certain server types (e.g. signs is null in BUNGEE mode).
     */
    private void reloadConfig(CommandSender s, String displayName, ConfigManager cfg) {
        if (cfg == null) return;
        cfg.reload();
        s.sendMessage("§6 ▪ §7" + displayName + ".yml reloaded!");
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, BedWars api) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (Arena.isInArena(p)) return false;
            if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        }
        return hasPermission(s);
    }
}
