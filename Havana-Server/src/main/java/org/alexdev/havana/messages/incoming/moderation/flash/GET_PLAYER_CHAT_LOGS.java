package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.moderation.flash.PLAYER_CHAT_LOG_RESPONSE;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class GET_PLAYER_CHAT_LOGS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.CHAT_LOG)) {
            return;
        }

        int userId = reader.readInt();
        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(userId);
        if (playerDetails == null) {
            return;
        }
        player.send(new PLAYER_CHAT_LOG_RESPONSE(playerDetails));
    }
}
