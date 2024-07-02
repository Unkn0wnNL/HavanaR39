package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CFH_PICK_ERROR extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
    }

    @Override
    public short getHeader() {
        return 532;
    }
}
