package org.alexdev.havana.messages.outgoing.moderation.flash;

import org.alexdev.havana.dao.mysql.RoomDao;
import org.alexdev.havana.game.moderation.ChatMessage;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.Calendar;
import java.util.List;

public class ROOM_CHAT_LOG_RESPONSE extends MessageComposer {

    private final Room room;

    public ROOM_CHAT_LOG_RESPONSE(Room room) {
        this.room = room;
    }

    @Override
    public void compose(NettyResponse response) {
        List<ChatMessage> chats = RoomDao.getModChatlog(room.getId());

        response.writeBool(room.isPublicRoom());
        response.writeInt(room.getId());
        response.writeString(room.getData().getName());
        response.writeInt(chats.size());
        chats.forEach(chatMessage -> writeChatResponse(response, chatMessage));
    }

    private static void writeChatResponse(NettyResponse response, ChatMessage chatMessage) {
        response.writeInt(chatMessage.getCalendar().get(Calendar.HOUR_OF_DAY));
        response.writeInt(chatMessage.getCalendar().get(Calendar.MINUTE));
        response.writeInt(chatMessage.getPlayerId());
        response.writeString(chatMessage.getPlayerName());
        response.writeString(chatMessage.getMessage());
    }

    @Override
    public short getHeader() {
        return 535;
    }
}
