package dev.prospect.cooldown;

import dev.prospect.cooldown.api.CooldownAPI;
import dev.prospect.cooldown.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Cooldown {

    private String identifier;
    private UUID uuid;
    private long duration, expirationMillis;

    public boolean isActive() {
        return System.currentTimeMillis() < expirationMillis;
    }

    public String getPrettierDuration() {
        return Utils.getPrettierDuration(CooldownAPI.getInstance().getCooldownManager().getExpirationMillis(uuid, identifier));
    }
}