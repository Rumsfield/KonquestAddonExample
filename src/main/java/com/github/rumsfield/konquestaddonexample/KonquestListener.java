package com.github.rumsfield.konquestaddonexample;

import com.github.rumsfield.konquest.api.KonquestAPI;
import com.github.rumsfield.konquest.api.event.territory.KonquestTerritoryMoveEvent;
import com.github.rumsfield.konquest.api.model.KonquestRelationshipType;
import com.github.rumsfield.konquest.api.model.KonquestKingdom;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class KonquestListener implements Listener {

    private final KonquestAPI api;

    public KonquestListener(KonquestAPI api) {
        this.api = api;
    }

    /**
     * This handler checks for players entering territories, and
     * displays a message and plays a sound depending on their
     * relationship to the kingdom.
     * @param event The movement event to listen for
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnterTerritory(KonquestTerritoryMoveEvent event) {
        // Check for non-null territory
        if (event.getTerritoryTo() == null) return;
        // Get event info
        KonquestKingdom territoryKingdom = event.getTerritoryTo().getKingdom();
        KonquestKingdom playerKingdom = event.getPlayer().getKingdom();
        Player eventPlayer = event.getPlayer().getBukkitPlayer();
        // Check kingdom relationship using API instance
        KonquestRelationshipType relation = api.getKingdomManager().getRelationRole(playerKingdom,territoryKingdom);
        if(relation.equals(KonquestRelationshipType.FRIENDLY)) {
            // Example access of API instance
            String friendlyColorFormat = api.getFriendlyPrimaryColor();
            eventPlayer.sendMessage(friendlyColorFormat+"Welcome Friend!");
            eventPlayer.playSound(eventPlayer.getLocation(), Sound.ENTITY_GENERIC_DRINK, (float)1.0, (float)1.0);
        } else {
            // Example access of Konquest directly from event
            String enemyColorFormat = event.getKonquest().getEnemyPrimaryColor();
            eventPlayer.sendMessage(enemyColorFormat+"Go Away!");
            eventPlayer.playSound(eventPlayer.getLocation(), Sound.ENTITY_GENERIC_HURT, (float)1.0, (float)1.0);
        }
    }

}
