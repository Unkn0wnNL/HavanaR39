package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class CFH_RESOLVED extends MessageComposer {

    public CFH_RESOLVED(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public enum StatusCode {
        RESOLVED,
        INVALID,
        ABUSIVE
    }

    private final StatusCode statusCode;

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(statusCode.ordinal());
    }

    @Override
    public short getHeader() {
        return 540;
    }
}
