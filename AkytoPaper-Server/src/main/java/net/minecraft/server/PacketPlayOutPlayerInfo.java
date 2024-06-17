package net.minecraft.server;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.WorldSettings.EnumGamemode;
import org.bukkit.ChatColor;

public class PacketPlayOutPlayerInfo implements Packet<PacketListenerPlayOut> {

    public PacketPlayOutPlayerInfo.EnumPlayerInfoAction a;
    public final List<PacketPlayOutPlayerInfo.PlayerInfoData> b = Lists.newArrayList();

    public PacketPlayOutPlayerInfo() {}

    public PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction packetplayoutplayerinfo_enumplayerinfoaction, EntityPlayer... aentityplayer) {
        this.a = packetplayoutplayerinfo_enumplayerinfoaction;
        EntityPlayer[] aentityplayer1 = aentityplayer;
        int i = aentityplayer.length;

        for (int j = 0; j < i; ++j) {
            EntityPlayer entityplayer = aentityplayer1[j];

            this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(entityplayer.getProfile(), entityplayer.ping, entityplayer.playerInteractManager.getGameMode(), entityplayer.getPlayerListName()));
        }

    }

    public PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction packetplayoutplayerinfo_enumplayerinfoaction, Iterable<EntityPlayer> iterable) {
        this.a = packetplayoutplayerinfo_enumplayerinfoaction;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();

            this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(entityplayer.getProfile(), entityplayer.ping, entityplayer.playerInteractManager.getGameMode(), entityplayer.getPlayerListName()));
        }

    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (PacketPlayOutPlayerInfo.EnumPlayerInfoAction) packetdataserializer.a(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class);
        int i = packetdataserializer.e();

        for (int j = 0; j < i; ++j) {
            GameProfile gameprofile = null;
            int k = 0;
            WorldSettings.EnumGamemode worldsettings_enumgamemode = null;
            IChatBaseComponent ichatbasecomponent = null;

            switch (PacketPlayOutPlayerInfo.SyntheticClass_1.a[this.a.ordinal()]) {
            case 1:
                gameprofile = new GameProfile(packetdataserializer.g(), packetdataserializer.c(16));
                int l = packetdataserializer.e();

                for (int i1 = 0; i1 < l; ++i1) {
                    String s = packetdataserializer.c(32767);
                    String s1 = packetdataserializer.c(32767);

                    if (packetdataserializer.readBoolean()) {
                        gameprofile.getProperties().put(s, new Property(s, s1, packetdataserializer.c(32767)));
                    } else {
                        gameprofile.getProperties().put(s, new Property(s, s1));
                    }
                }

                worldsettings_enumgamemode = WorldSettings.EnumGamemode.getById(packetdataserializer.e());
                k = packetdataserializer.e();
                if (packetdataserializer.readBoolean()) {
                    ichatbasecomponent = packetdataserializer.d();
                }
                break;

            case 2:
                gameprofile = new GameProfile(packetdataserializer.g(), (String) null);
                worldsettings_enumgamemode = WorldSettings.EnumGamemode.getById(packetdataserializer.e());
                break;

            case 3:
                gameprofile = new GameProfile(packetdataserializer.g(), (String) null);
                k = packetdataserializer.e();
                break;

            case 4:
                gameprofile = new GameProfile(packetdataserializer.g(), (String) null);
                if (packetdataserializer.readBoolean()) {
                    ichatbasecomponent = packetdataserializer.d();
                }
                break;

            case 5:
                gameprofile = new GameProfile(packetdataserializer.g(), (String) null);
            }

            this.b.add(new PacketPlayOutPlayerInfo.PlayerInfoData(gameprofile, k, worldsettings_enumgamemode, ichatbasecomponent));
        }

    }

    public PlayerInfoData constructData(GameProfile profile, int ping, EnumGamemode gamemode, IChatBaseComponent name) {
        return new PacketPlayOutPlayerInfo.PlayerInfoData(profile,
                                                          ping,
                                                          gamemode,
                                                          name);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum) this.a);
        packetdataserializer.b(this.b.size());
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            PacketPlayOutPlayerInfo.PlayerInfoData packetplayoutplayerinfo_playerinfodata = (PacketPlayOutPlayerInfo.PlayerInfoData) iterator.next();

            switch (PacketPlayOutPlayerInfo.SyntheticClass_1.a[this.a.ordinal()]) {
            case 1:
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getId());
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getName());
                packetdataserializer.b(packetplayoutplayerinfo_playerinfodata.a().getProperties().size());
                Iterator iterator1 = packetplayoutplayerinfo_playerinfodata.a().getProperties().values().iterator();

                while (iterator1.hasNext()) {
                    Property property = (Property) iterator1.next();

                    packetdataserializer.a(property.getName());
                    packetdataserializer.a(property.getValue());
                    if (property.hasSignature()) {
                        packetdataserializer.writeBoolean(true);
                        packetdataserializer.a(property.getSignature());
                    } else {
                        packetdataserializer.writeBoolean(false);
                    }
                }

                packetdataserializer.b(packetplayoutplayerinfo_playerinfodata.c().getId());
                packetdataserializer.b(packetplayoutplayerinfo_playerinfodata.b());

                // SportBukkit start
                if (packetplayoutplayerinfo_playerinfodata.displayName != null) {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(net.md_5.bungee.chat.ComponentSerializer.toString(packetplayoutplayerinfo_playerinfodata.displayName));
                    break;
                }

                if (packetplayoutplayerinfo_playerinfodata.jsonDisplayName != null) {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.jsonDisplayName);
                    break;
                }
                // SportBukkit end

                if (packetplayoutplayerinfo_playerinfodata.d() == null) {
                    packetdataserializer.writeBoolean(false);
                } else {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.d());
                }
                break;

            case 2:
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getId());
                packetdataserializer.b(packetplayoutplayerinfo_playerinfodata.c().getId());
                break;

            case 3:
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getId());
                packetdataserializer.b(packetplayoutplayerinfo_playerinfodata.b());
                break;

            case 4:
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getId());

                // SportBukkit start
                if (packetplayoutplayerinfo_playerinfodata.displayName != null) {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(net.md_5.bungee.chat.ComponentSerializer.toString(packetplayoutplayerinfo_playerinfodata.displayName));
                    break;
                }

                if (packetplayoutplayerinfo_playerinfodata.jsonDisplayName != null) {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.jsonDisplayName);
                    break;
                }
                // SportBukkit end

                if (packetplayoutplayerinfo_playerinfodata.d() == null) {
                    packetdataserializer.writeBoolean(false);
                } else {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.d());
                }
                break;

            case 5:
                packetdataserializer.a(packetplayoutplayerinfo_playerinfodata.a().getId());
            }
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.values().length];

        static {
            try {
                PacketPlayOutPlayerInfo.SyntheticClass_1.a[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                PacketPlayOutPlayerInfo.SyntheticClass_1.a[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                PacketPlayOutPlayerInfo.SyntheticClass_1.a[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                PacketPlayOutPlayerInfo.SyntheticClass_1.a[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                PacketPlayOutPlayerInfo.SyntheticClass_1.a[PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }

    public class PlayerInfoData {

        public net.md_5.bungee.api.chat.BaseComponent[] displayName; // SportBukkit
        public String jsonDisplayName; // SportPaper

        private final int b;
        private final WorldSettings.EnumGamemode c;
        private final GameProfile d;
        private final IChatBaseComponent e;

        public PlayerInfoData(GameProfile gameprofile, int i, WorldSettings.EnumGamemode worldsettings_enumgamemode, IChatBaseComponent ichatbasecomponent) {
            this.d = gameprofile;
            this.b = i;
            this.c = worldsettings_enumgamemode;
            this.e = ichatbasecomponent;
        }

        public GameProfile a() {
            return this.d;
        }

        public int b() {
            return this.b;
        }

        public WorldSettings.EnumGamemode c() {
            return this.c;
        }

        public IChatBaseComponent d() {
            return this.e;
        }

        public String toString() {
            return Objects.toStringHelper(this).add("latency", this.b).add("gameMode", this.c).add("profile", this.d).add("displayName", this.e == null ? null : IChatBaseComponent.ChatSerializer.a(this.e)).toString();
        }
    }

    public static enum EnumPlayerInfoAction {

        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER;

        private EnumPlayerInfoAction() {}
    }

    public enum ConnectionBars { // TODO Fix and move class
        EMPTY(0, ChatColor.RESET),
        FIVE(100, ChatColor.GREEN),
        FOUR(200, ChatColor.YELLOW),
        THREE(400, ChatColor.GOLD),
        TWO(800, ChatColor.RED), // Not working idk
        ONE(1600, ChatColor.DARK_RED); // Not working idk

        final int ping;
        final ChatColor color;

        ConnectionBars(int ping, ChatColor color) {
            this.ping = ping;
            this.color = color;
        }

        public int asPing() {
            return this.ping;
        }

        public ChatColor asColor() {
            return this.color;
        }
    }

    // TODO Move
    public void addFakePlayer(GameProfile profile, int ping, EnumGamemode gameMode, IChatBaseComponent playerListName) {
        this.b.add(new PlayerInfoData(profile, ping, gameMode, playerListName));
    }

    // TODO Move
    public void addFakePlayer(PlayerInfoData data) {
        this.b.add(data);
    }
}
