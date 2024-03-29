import dev.prospect.cooldown.Cooldown;
import dev.prospect.cooldown.api.CooldownAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getPluginManager;

public class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Optional<Cooldown> optional = Optional.ofNullable(CooldownAPI.getInstance().getCooldownManager().getCooldownByIdentifier(player.getUniqueId(), "CHAT"));

        if(optional.isPresent() && optional.get().isActive()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Please wait " + optional.get().getPrettierDuration() + " before typing again.");

        } else {
            Cooldown cooldown = new Cooldown("CHAT", player.getUniqueId(), 10L, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10L));
            CooldownAPI.getInstance().getCooldownManager().activate(player.getUniqueId(), cooldown);
        }
    }
}