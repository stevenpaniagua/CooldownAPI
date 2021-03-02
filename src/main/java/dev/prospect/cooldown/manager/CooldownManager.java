package dev.prospect.cooldown.manager;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.prospect.cooldown.Cooldown;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    @Getter
    private final Multimap<UUID, Cooldown> map = ArrayListMultimap.create();

    public void activate(UUID uuid, Cooldown cooldown) {
        map.put(uuid, cooldown);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> map.remove(uuid, cooldown), cooldown.getDuration(), TimeUnit.SECONDS);
    }

    public Cooldown getCooldownByIdentifier(UUID uuid, String identifier) {
        return map.get(uuid).stream().filter(r -> r.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    public boolean isOnCooldown(UUID uuid, String cooldown) {
        return getCooldownByIdentifier(uuid, cooldown) != null && getCooldownByIdentifier(uuid, cooldown).isActive();
    }

    public long getExpirationMillis(UUID uuid, String cooldown) {
        return map.entries().stream().filter(uuidCooldownEntry -> uuidCooldownEntry.getKey().equals(uuid) && uuidCooldownEntry.getValue().equals(getCooldownByIdentifier(uuid, cooldown))).map(uuidCooldownEntry -> uuidCooldownEntry.getValue().getExpirationMillis()).findFirst().orElse(0L) - System.currentTimeMillis();
    }
}