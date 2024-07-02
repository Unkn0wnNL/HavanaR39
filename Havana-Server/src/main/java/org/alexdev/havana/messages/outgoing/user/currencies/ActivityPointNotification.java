package org.alexdev.havana.messages.outgoing.user.currencies;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ActivityPointNotification extends MessageComposer {
    private final int pixels;
    private final int receivedPixels;

    public ActivityPointNotification(int pixels, int receivedPixels) {
        this.pixels = pixels;
        this.receivedPixels = receivedPixels;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.pixels);
        response.writeInt(this.receivedPixels);

    }

    @Override
    public short getHeader() {
        return 438; // "Fv"
    }
}
