package org.alexdev.havana.messages.incoming.effects;

import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;

public class USE_AVATAR_EFFECT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int effectId = reader.readInt();
        doAction(player, effectId, false);
    }

    public static void doAction(Player player, int effectId) {
        doAction(player, effectId, false);
    }

    public static void doAction(Player player, int effectId, boolean overrideByFuse) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (effectId > 0) {
            Effect toActivate = null;
            if (player.hasFuse(Fuseright.ADMINISTRATOR_ACCESS) && overrideByFuse) {
                player.getRoomUser().useEffect(effectId);
                return;
            }
            var activatedEffectCheck = player.getEffects().stream().filter(effect -> effect.getEffectId() == effectId && effect.isActivated()).findFirst();

            if (activatedEffectCheck.isPresent()) {
                toActivate = activatedEffectCheck.get();
            }

            if (toActivate == null) {
                return;
            }

            player.getRoomUser().useEffect(effectId);
        } else {
            player.getRoomUser().useEffect(0);
        }
    }
}
