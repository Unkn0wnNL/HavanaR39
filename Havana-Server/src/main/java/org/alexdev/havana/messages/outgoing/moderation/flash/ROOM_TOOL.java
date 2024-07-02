package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ROOM_TOOL extends MessageComposer {
    private final Room room;

    public ROOM_TOOL(Room room) {
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(room.getId());
        response.writeInt(room.getData().getVisitorsNow()); // user count
        response.writeBool(room.getEntityManager().getPlayers().stream().anyMatch((player -> player.getDetails().getId() == room.getData().getId())));
        response.writeInt(room.getData().getOwnerId());
        response.writeString(room.getData().getOwnerName());
        response.writeInt(room.getData().getId());
        response.writeString(room.getData().getName());
        response.writeString(room.getData().getDescription());
        response.writeInt(room.getData().getTags().size());
        for (String tag : room.getData().getTags()) {
            response.writeString(tag);
        }
        if (EventsManager.getInstance().hasEvent(room.getId())) {
            response.writeBool(true);
            Event event = EventsManager.getInstance().getEventByRoomId(room.getId());
            response.writeString(event.getName());
            response.writeString(event.getDescription());
            response.writeString(event.getTags().size());
            event.getTags().forEach(response::writeString);
        }
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return 538; //
    }
}
