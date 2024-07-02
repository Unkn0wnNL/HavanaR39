package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.moderation.flash.ROOM_CHAT_LOG_RESPONSE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_ROOM_CHAT_LOGS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.CHAT_LOG)) {
            return;
        }

        int unknown = reader.readInt();
        int roomId = reader.readInt();
        Room room = RoomManager.getInstance().getRoomById(roomId);
        if (room == null) {
            return;
        }
        player.send(new ROOM_CHAT_LOG_RESPONSE(room));
    }
}
