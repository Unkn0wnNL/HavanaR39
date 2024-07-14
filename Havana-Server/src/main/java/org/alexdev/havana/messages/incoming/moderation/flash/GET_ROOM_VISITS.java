package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.outgoing.moderation.flash.ROOM_VISITS_RESPONSE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_ROOM_VISITS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }
        int userId = reader.readInt();

        player.send(new ROOM_VISITS_RESPONSE(userId));
    }
}
