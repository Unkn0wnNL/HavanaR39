package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.moderation.cfh.CallForHelp;
import org.alexdev.havana.game.moderation.cfh.CallForHelpManager;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.messages.outgoing.moderation.CRY_REPLY;
import org.alexdev.havana.messages.outgoing.moderation.flash.CFH_RESOLVED;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class CLOSE_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {

        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }
        int result = reader.readInt(); // 1 = useless, 2 = abusive, 3 = resolved

        int unknown = reader.readInt();

        int ticketId = reader.readInt();

        CallForHelp call = CallForHelpManager.getInstance().getCall(ticketId);

        if (call != null) {
            call.setDeleted(true);
            Player caller = PlayerManager.getInstance().getPlayerById(call.getCaller());
            if (caller != null) {
                if (result == 1) {
                    if (caller.flash) {
                        caller.send(new CFH_RESOLVED(CFH_RESOLVED.StatusCode.INVALID));
                    } else {
                        caller.send(new CRY_REPLY("INVALID"));
                    }
                }
                if (result == 2) {
                    if (caller.flash) {
                        caller.send(new CFH_RESOLVED(CFH_RESOLVED.StatusCode.ABUSIVE));
                    } else {
                        caller.send(new CRY_REPLY("ABUSIVE"));
                    }
                }
                if (result == 3) {
                    if (caller.flash) {
                        caller.send(new CFH_RESOLVED(CFH_RESOLVED.StatusCode.RESOLVED));
                    } else {
                        caller.send(new CRY_REPLY("OPGELOST"));
                    }
                }
            }
            CallForHelpManager.getInstance().sendCfhsToMods();
        }
    }
}
