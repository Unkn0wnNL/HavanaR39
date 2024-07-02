package org.alexdev.havana.messages.incoming.moderation.flash;

import org.alexdev.havana.game.events.Event;
import org.alexdev.havana.game.events.EventsManager;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomManager;
import org.alexdev.havana.messages.outgoing.moderation.MODERATOR_ALERT;
import org.alexdev.havana.messages.outgoing.rooms.user.HOTEL_VIEW;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

import java.util.List;

public class PERFORM_ROOM_ACTION implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {

        if (!player.hasFuse(Fuseright.RECEIVE_CALLS_FOR_HELP)) {
            return;
        }

        int roomId = reader.readInt();
        boolean lockRoom = reader.readBoolean();
        boolean inappropriateName = reader.readBoolean();
        boolean kickAllUsers = reader.readBoolean();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }
        if (lockRoom) {
            room.getData().setAccessType(1);
        }

        if (inappropriateName) {
            room.getData().setName("Inappropriate to hotel management");
            room.getData().setDescription("");
            room.getData().removeTags();
            Event event = EventsManager.getInstance().getEventByRoomId(roomId);
            if (event != null) {
                EventsManager.getInstance().removeEvent(event);
            }
        }

        if (kickAllUsers) {
            List<Player> players = player.getRoomUser().getRoom().getEntityManager().getPlayers();
            for (Player target : players) {
                // Don't kick other moderators
                if (target.hasFuse(Fuseright.ROOM_KICK)) {
                    continue;
                }
                target.getRoomUser().kick(false, true);
                target.send(new HOTEL_VIEW());
                target.send(new MODERATOR_ALERT("ROom is closed"));
            }
        }
    }
}
