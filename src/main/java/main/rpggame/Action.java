package main.rpggame;

import java.util.HashMap;
import java.util.Map;

public enum Action {
    ATTACK, CHARGE, BLOCK, ABILITY;

    private static final Map<Action, Action> winMap = new HashMap<>();

    static {
        winMap.put(ATTACK, CHARGE);
        winMap.put(CHARGE, BLOCK);
        winMap.put(BLOCK, ATTACK);
    }

    ActionResult checkAgainst(Action other) {
        if (this == other)
            return ActionResult.DRAW;

        if (this == ABILITY)
            return ActionResult.LOSE;
        else if (other == ABILITY)
            return ActionResult.WIN;


        return winMap.get(this) == other ? ActionResult.WIN : ActionResult.LOSE;
    }

}
