package com.bambumods.bambushop.rank;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class RankAttributeEvents {

    private static final Identifier ID_HEALTH = Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "rank_health");
    private static final Identifier ID_DAMAGE = Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "rank_damage");
    private static final Identifier ID_ARMOR  = Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "rank_armor");
    private static final Identifier ID_KB     = Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "rank_knockback");

    public static void applyRankAttributes(ServerPlayer player) {
        int rank = ModAttachments.getRank(player);

        applyModifier(player, Attributes.MAX_HEALTH, ID_HEALTH,
                RankManager.getBonusHealth(rank), AttributeModifier.Operation.ADD_VALUE);

        applyModifier(player, Attributes.ATTACK_DAMAGE, ID_DAMAGE,
                RankManager.getBonusDamage(rank), AttributeModifier.Operation.ADD_VALUE);

        applyModifier(player, Attributes.ARMOR, ID_ARMOR,
                RankManager.getBonusArmor(rank), AttributeModifier.Operation.ADD_VALUE);

        applyModifier(player, Attributes.KNOCKBACK_RESISTANCE, ID_KB,
                RankManager.getBonusKnockbackResistance(rank), AttributeModifier.Operation.ADD_VALUE);

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    private static void applyModifier(ServerPlayer player, Holder<Attribute> attribute, Identifier id, double value, AttributeModifier.Operation op) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;

        if (instance.getModifier(id) != null) {
            instance.removeModifier(id);
        }

        if (value == 0) return;

        AttributeModifier modifier = new AttributeModifier(id, value, op);
        instance.addPermanentModifier(modifier);
    }

    // ===== AL ENTRAR AL MUNDO =====
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        applyRankAttributes(player);
    }

    // ===== AL RESPAWNEAR (después de morir) =====
    // Este evento se dispara DESPUÉS de que el attachment RANK se ha copiado.
    // PlayerEvent.Clone se dispara ANTES y por eso getRank() devolvía 0.
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        applyRankAttributes(player);
    }

    // ===== AL CAMBIAR DE DIMENSIÓN =====
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        applyRankAttributes(player);
    }
}