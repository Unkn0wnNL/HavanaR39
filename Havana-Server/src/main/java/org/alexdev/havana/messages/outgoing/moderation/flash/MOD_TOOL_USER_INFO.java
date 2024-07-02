package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class MOD_TOOL_USER_INFO extends MessageComposer {
    private final PlayerDetails target;

    public MOD_TOOL_USER_INFO(PlayerDetails target) {
        this.target = target;
    }

    @Override
    public void compose(NettyResponse response) {

        if (target == null) {
            return;
        }

        response.writeInt(target.getId());
        response.writeString(target.getName());
        response.writeInt((int) (target.getJoinDate() / 1000L)); // registration date
        response.writeInt((int) (target.getLastOnline() / 1000L)); // last online
        response.writeBool(PlayerDao.isPlayerOnline(target.getId()));
        response.writeInt(0); // cfhs
        response.writeInt(1); // abusive cfhs
        response.writeInt(3); // cautions
        response.writeInt(1); // bans

    }

    @Override
    public short getHeader() {
        return 533;
    }
}
