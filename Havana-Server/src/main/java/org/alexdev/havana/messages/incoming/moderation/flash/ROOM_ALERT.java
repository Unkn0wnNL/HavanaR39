package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class ROOM_ALERT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.ROOM_ALERT)) {
            return;
        }

        Room room = player.getRoomUser().getRoom();
        int unknown1 = reader.readInt();
        int alertMode = reader.readInt();
        String msg = reader.readString();
        boolean isCaution = alertMode != 3;

        if (!isCaution) {
            room.send(new ALERT(msg));
        }
    }
}
