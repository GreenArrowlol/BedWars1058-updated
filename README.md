![Logo](./.github/assets/logo_open_source.png)

# BedWars1058 (Updated Fork)

This is a fork of the original **BedWars1058** by Andrei Dascălu. The original project is
here: [andrei1058/BedWars1058](https://github.com/andrei1058/BedWars1058).

I made this fork mainly to keep the plugin building and running on the newer Minecraft
server versions, so that it can be used for the setup shown in
[Bedwars Setup - Modern Minigame Series](https://builtbybit.com/resources/bedwars-setup-modern-minigame-series.74829/).

BedWars1058 is open source under the **GNU GPL 3.0** license, and this fork stays under the
same license.

## What is changed in this fork

Only maintenance and build changes are done here. Nothing of the core gameplay is removed.
In short, this is what all is done:

- **Building with Java 21** - the project now compiles and packages properly using JDK 21.
- **Fixed the compilation** - some code was not compiling on the newer setup, that is fixed
  so the plugin builds cleanly and runs on modern Spigot/Paper servers.
- **Bumped the version to 25.3.1**.
- **Expanded `/bw reload`** - the reload command is improved to cover more things.
- **Fixed the scoreboard, tab list and name tags** - after the SidebarLib update the sidebar
  provider was not getting set, so a `NullPointerException` was thrown when a player joined and
  the scoreboard, tab and name tags were coming empty. The plugin now picks and sets the correct
  version provider on startup, so everything shows up properly again. (Tested on Paper 1.20.4.)
- **Vendored the SidebarLib inside the repo** - earlier the build was depending on Andrei's
  own Maven repository for the sidebar library. That repo dependency is removed and the
  library is kept inside the project itself (`vendor-repo/`), so anybody can build the
  project without needing the external repo.
- **Removed the dependency on the offline flow-nbt repository** - the SlimeWorldManager and
  flow-nbt libraries used to come from an external repo (`repo.glaremasters.me`) which went
  down. Those libraries are now vendored inside `vendor-repo/` and the dead repository is
  removed, so the build keeps working on a clean machine.
- **Made the build fork-friendly** - the old deploy pipelines and the `andrei1058` releases
  repository are dropped from the CI and from the dependency resolution, so that the build
  resolves and passes on a clean/fresh runner without any private access.

If you are looking for the full original features, documentation and wiki, please refer to
the [original repository](https://github.com/andrei1058/BedWars1058).

## Description
BedWars is a mini-game where you have to defend your bed and destroy the others.  
Once your bed is destroyed, you cannot respawn.

## System requirements
This software runs on [Spigot](https://www.spigotmc.org/) and NMS.
Spigot forks without compiled NMS code are not supported.
Officially supported servers are [Spigot](https://www.spigotmc.org/) and [Paper](https://papermc.io/).

For building from source you need **Java 21** (JDK 21). The plugin itself runs on Java 11 or
newer at runtime.

The internal world restore system is based on zipping and unzipping maps which can become
heavy if you are still making use of HDDs and you do not have a decent CPU. For a better
and faster restore system it is recommended to use one of the following solutions:
- [SlimeWorldManager](https://www.spigotmc.org/resources/slimeworldmanager.69974/) plug-in (v2.2.1 **only**)
- [AdvancedWorldManager](https://www.spigotmc.org/resources/advanced-slimeworldmanager.87209/) plug-in (v2.8.0 **only**)
- [AdvancedSlimePaper](https://github.com/InfernalSuite/AdvancedSlimePaper) server jar (**1.20 or newer**)

BedWars1058 will hook into it and do everything for you, no additional configuration is needed.

## Building
This project uses Maven. To build it yourself:

```
./mvnw clean package
```

The compiled jar will be placed inside the `release/` folder.

## Main features

###### Flexible | Ways you can run the plugin:
- **SHARED**: can run among other mini-games on the same spigot instance. Games will only be accessible via commands.
- **MULTIARENA**: will require an entire server instance for hosting the mini-game. It will protect the lobby world and games can be joined via commands, NPCs, signs and GUIs.
- **BUNGEE-LEGACY**: the old classic bungee mode where a game means an entire server instance. You'll be added to the game when joining the server. Arena status will be displayed as MOTD.
- **BUNGEE**: a scalable bungee mode. It can host multiple arenas on the same server instance, clone and start new arenas when needed so other players can join.

###### Language | Per player language system:
- each player can receive messages, holograms, GUIs etc. in their desired language. `/bw lang`.
- you can either remove or add new languages.
- team names, group names, shop contents and a lot more can be translated.

###### Shop | Customization:
- you may configure quick-buy default items.
- you may add or remove categories.
- you may add new shop items or execute commands when bought.
- permanent items are given after you re-spawn, and can be downgradable.
- special items available: BedBug, Dream Defender, Egg Bridge, TNT Jump and Straight Fireball.
- quick buy feature is available and is synced between nodes as well in bungee mode.

###### Team Upgrades | Customization:
- you may have different team upgrades per arena group.
- you may add and remove categories and contents.
- traps that: disenchant items, give potion effects, remove effects or trigger commands.

###### Ways to join an arena:
- arena selector, which can be configured.
- join via NPCs by installing Citizens.
- join-signs with status block.
- commands like `/bw join random`, `/bw join mapName`, `/bw join groupName`.

###### Arena Settings | Customization:
- custom display name, min/max players and team size.
- toggle spectators, generators for empty teams, NPCs for empty teams, drops management.
- protection range for team-spawn and team NPCs.
- instant kill on void based on Y coordinate.
- allow map breaking, generator split, custom game rules per map.

###### Other features:
- **Player Statistics** via internal stats GUI (`/bw stats`) and placeholders.
- **Party System** (internal), plus support for Parties by AlessioDP and Party and Friends.
- **Anti AFK System**, **Custom Join Items**, **Re-Join**, **TNT Jump**, and seasonal events.

## Contributing
Contributions are welcome. Feel free to open a pull request with your changes.

## 3rd party libraries
- [bStats](https://bstats.org/getting-started/include-metrics)
- SidebarLib (vendored in this repo)
- [Commons IO](https://mvnrepository.com/artifact/commons-io/commons-io)
- [HikariCP](https://mvnrepository.com/artifact/com.zaxxer/HikariCP)
- [SLF4J](http://www.slf4j.org/)

## License
This project is licensed under the **GNU GPL 3.0**, same as the original BedWars1058.
See the [LICENSE](LICENSE) file for details.
