package me.invis.character.listener;

import me.invis.character.Characters;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowPlayerCharacter implements Listener {

    private final List<UUID> onCooldown = new ArrayList<>();

    @EventHandler
    private void onInteract(PlayerInteractAtEntityEvent event) {
        if(onCooldown.contains(event.getPlayer().getUniqueId())) return;
        if(!(event.getRightClicked() instanceof Player)) return;
        if(!Characters.inst().getCharactersManager().hasCharacter((Player) event.getRightClicked())) return;

        event.getPlayer().performCommand("character " + event.getRightClicked().getName());
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        onCooldown.add(event.getPlayer().getUniqueId());
        new BukkitRunnable(){

            @Override
            public void run() {
                onCooldown.remove(event.getPlayer().getUniqueId());
            }

        }.runTaskLater(Characters.inst(), 60);
    }

}
