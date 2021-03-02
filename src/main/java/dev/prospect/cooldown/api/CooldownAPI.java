package dev.prospect.cooldown.api;

import dev.prospect.cooldown.manager.CooldownManager;
import lombok.Getter;

@Getter
public class CooldownAPI {

    @Getter private static CooldownAPI instance;

    public CooldownAPI() {
        instance = this;
    }

    private final CooldownManager cooldownManager = new CooldownManager();
}