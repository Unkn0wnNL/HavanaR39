package org.alexdev.havana.game.commands.registered.moderation;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.incoming.effects.USE_AVATAR_EFFECT;
import org.alexdev.havana.messages.outgoing.effects.AVATAR_EFFECTS;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.util.DateUtil;

import java.util.List;

public class SetEffectCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("effectId");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (args.length == 0) {
            player.send(new CHAT_MESSAGE(CHAT_MESSAGE.ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Effect id not provided", 0));
            return;
        }

        try {
            int effectId = Integer.parseInt(args[0]);
            player.send(new AVATAR_EFFECTS(List.of(new Effect(0, player.getDetails().getId(), effectId, DateUtil.getCurrentTimeSeconds() + 3600, true))));
            USE_AVATAR_EFFECT.doAction(player, effectId, true);
        } catch (RuntimeException e) {
            player.send(new CHAT_MESSAGE(CHAT_MESSAGE.ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Effect id invalid", 0));
        }
    }

    @Override
    public String getDescription() {
        return "Shows the coordinates in the room";
    }
}
