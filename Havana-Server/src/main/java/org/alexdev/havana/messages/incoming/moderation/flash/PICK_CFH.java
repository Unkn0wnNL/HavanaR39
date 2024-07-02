package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.moderation.flash.CALL_FOR_HELP_FLASH;
import org.alexdev.havana.messages.outgoing.moderation.flash.CFH_PICK_ERROR;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class PICK_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }

        int unknown = reader.readInt();

        int ticketId = reader.readInt();

        CallForHelp call = CallForHelpManager.getInstance().getCall(ticketId);

        if (call == null || !call.isOpen()) {
            player.send(new CFH_PICK_ERROR());
            return;
        }
        call.setPickedUpBy(player);

        for (Player p : PlayerManager.getInstance().getPlayers()) {
            if (p.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
                p.send(new CALL_FOR_HELP_FLASH(call));
            }
        }
    }
}
