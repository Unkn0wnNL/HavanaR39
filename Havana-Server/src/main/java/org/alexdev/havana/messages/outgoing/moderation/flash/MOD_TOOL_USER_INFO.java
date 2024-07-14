package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.dao.mysql.BanDao;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;
import org.alexdev.havana.util.DateUtil;

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
        response.writeInt((DateUtil.getCurrentTimeSeconds() - (int) target.getJoinDate()) / 60); // registration date
        response.writeInt((DateUtil.getCurrentTimeSeconds() - (int) target.getLastOnline()) / 60); // last online
        response.writeBool(PlayerDao.isPlayerOnline(target.getId()));
        response.writeInt(0); // cfhs
        response.writeInt(0); // abusive cfhs
        response.writeInt(0); // cautions
        response.writeInt(BanDao.getTotalBans(target.getId())); // bans
    }

    @Override
    public short getHeader() {
        return 533;
    }
}
