package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class INIT_MOD_TOOL_ROOM extends MessageComposer {
    private final Player player;

    public INIT_MOD_TOOL_ROOM(Player player) {
        this.player = player;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(-1);
        response.writeInt(1); // number of message templates (from ticket browser)
        response.writeString("dit is een alert");
        response.writeInt(1); // numbers of categories
        response.writeString("categorie 1");
        response.writeInt(1); // number of kv's for in the categories
        response.writeString("item 1 key");
        response.writeString("item 1 value");

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
}
