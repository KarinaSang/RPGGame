package main.rpggame;

import java.util.HashMap;
import java.util.Map;

public enum Action {
    ATTACK, CHARGE, BLOCK;

    private static final Map<Action, Action> winMap = new HashMap<>();

    static {
        winMap.put(ATTACK, CHARGE);
        winMap.put(CHARGE, BLOCK);
        winMap.put(BLOCK, ATTACK);
    }

    ActionResult checkAgainst(Action other) {
        if (this == other)
            return ActionResult.DRAW;

        return winMap.get(this) == other ? ActionResult.WIN : ActionResult.LOSE;
    }

}
