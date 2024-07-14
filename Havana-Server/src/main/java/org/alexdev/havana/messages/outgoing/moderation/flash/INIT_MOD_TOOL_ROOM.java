package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.*;

public class INIT_MOD_TOOL_ROOM extends MessageComposer {
    private final Player player;
    static List<CategoryPreset> categoryPresets = fillPresets();

    public INIT_MOD_TOOL_ROOM(Player player) {
        this.player = player;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(-1);
        response.writeInt(1); // number of message templates (from ticket browser)
        response.writeString("This is a standard alert");

        response.writeInt(categoryPresets.size()); // numbers of categories
        for (CategoryPreset categoryPreset : categoryPresets) {
            response.writeString(categoryPreset.name);
            response.writeInt(categoryPreset.getPresets().size()); // number of kv's for in the categories
            categoryPreset.getPresets().forEach((k, v) -> {
                response.writeString(k);
                response.writeString(v);

            });
        }
        response.writeBool(player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)); // Tickets
        response.writeBool(player.hasFuse(Fuseright.CHAT_LOG)); // chatlogs
        response.writeBool(player.hasFuse(Fuseright.ROOM_ALERT)); // message, user, action, caution
        response.writeBool(player.hasFuse(Fuseright.KICK)); // kick
        response.writeBool(player.hasFuse(Fuseright.BAN)); // ban
        response.writeBool(player.hasFuse(Fuseright.KICK)); // caution, message
        response.writeInt(1); // ?
        response.writeInt(2); // number of room message presets count
        response.writeString("hoi");
        response.writeString("test");
    }

    @Override
    public short getHeader() {
        return 531;
    }


    private static List<CategoryPreset> fillPresets() {
        List<CategoryPreset> categoryPresets = new ArrayList<>();

        CategoryPreset communication = new CategoryPreset("Communication");
        communication.addPreset("Flooding", "Flooding or spamming is against the hotel rules.");
        communication.addPreset("Swearing", "Excessive or extreme language or swearing is against the hotel rules.");
        categoryPresets.add(communication);

        CategoryPreset rooms = new CategoryPreset("Rooms");
        rooms.addPreset("Blocking", "Blocking parts of/access to a room is against hotel rules.");
        rooms.addPreset("Unacceptable Room", "Your room name and/or description is unacceptable according to hotel rules.");
        categoryPresets.add(rooms);

        return categoryPresets;
    }

    static class CategoryPreset {
        String name;
        TreeMap<String, String> presets = new TreeMap<>();

        public CategoryPreset(String name) {
            this.name = name;
        }

        public void addPreset(String name, String value) {
            presets.put(name, value);
        }

        public Map<String, String> getPresets() {
            return presets;
        }
    }
}
