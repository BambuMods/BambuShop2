========================================
BAMBU SHOP - ESTADO DEL PROYECTO
Última actualización: 16/10/2026
========================================

--- INFO GENERAL ---
Mod ID: bambushop
Nombre: Bambu Shop
Versión: 1.0.0
Package: com.bambumods.bambushop
Autor: BambuMods
NeoForge: 21.11.45
Minecraft: 1.21.11
Java: 21
IDE: IntelliJ
Repo GitHub: https://github.com/BambuMods/BambuShop2


--- CONCEPTO DEL MOD ---
Mod de tienda + sistema de rangos con economía virtual.
- Dinero virtual (sin item físico, guardado en el jugador)
- Tienda para comprar/vender items con dólares
- 20 rangos de progresión (RPG)
- Ganas dinero minando, matando mobs, talando, pescando
- Multiplicador de dinero por rango (x1 a x250)
- Multiplicador de XP por rango (x1 a x4)
- Stats por rango (vida, daño, armadura, knockback)
- Mobs con nivel (1-20) que escalan contigo
- Drops chetados (armas/armaduras con encantamientos altos)
- Mobs OP (Épico, Legendario, Mítico)
- Auto-sell
- HUD permanente con dinero, rango y auto-sell
- Sin cosméticos, sin vuelo, sin dash, sin teletransporte
- Todo vanilla (sin items custom)


--- CONTROLES ACTUALES ---
/menu         → abre el hub principal (TIENDA / RANGOS)  [TODOS]
/rankgui      → abre directamente la GUI de rangos        [TODOS]
/autosell     → activa/desactiva auto-venta               [TODOS]
/addmoney     → +100$ (debug)                             [SOLO OPs]
/myrank       → ver rango actual (debug)                  [SOLO OPs]
/setrank <n>  → forzar rango (debug)                      [SOLO OPs]
/shopdebug    → debug de tienda                           [SOLO OPs]


--- FASES COMPLETADAS ---
[✅] FASE 1: Dinero del jugador (AttachmentType MONEY)
[✅] FASE 2: GUI hub + botones TIENDA y RANGOS
[✅] FASE 3: Dinero por minar, matar, talar, pescar
[✅] FASE 4.1-4.3: Rangos + GUI + persistencia
[✅] FASE 4.6: Stats por rango + bonus XP
[✅] FASE 4.7: Multiplicador de dinero por rango
[✅] FASE 5: Tienda completa
    - JSON de precios
    - GUI de categorías (6 de compra + 1 botón VENDER)
    - GUI de compra con lista de items
    - GUI de cantidad (1/10/64)
    - GUI de venta con slot + botones -1/-10/-64/+1/+10/+64/TODOS
    - Notificaciones flotantes
    - Sincronización de dinero al instante
[✅] FASE 6.5.1: Attachment MOB_LEVEL
[✅] FASE 6.5.2: Nombre del mob con nivel y color
[✅] FASE 6.5.3: Stats del mob (HP, daño)
[✅] FASE 6.5.4: Dinero y XP escalados por nivel
[✅] FASE 6.5.5: Nivel del mob según rango del jugador
[✅] FASE 6.5.6: Drops chetados + items de rankeo
[✅] FASE 6.5.7: Mobs OP (Épico, Legendario, Mítico)
[✅] FASE 6.5.9: Auto-sell
[✅] FASE 6.5.10: HUD permanente (dinero + rango + auto-sell)
[✅] FASE 6.5.11: Formato compacto de números (1k, 1.5M, 1B)
[✅] FASE 7.1: Limpieza de prints
[✅] FASE 7.2: Permisos de comandos


--- FASES DESCARTADAS ---
[❌] FASE 4.8: Habilidades por rango
    Motivo: se solapa con el equipo OP
[❌] FASE 6.6: Encantamientos custom
    Motivo: muy complejo, los drops chetados ya dan encantamientos altos
[❌] Prestigio / Árbol de perks
    Motivo: el sistema de rangos actual es suficiente
[❌] 10 rangos extra (21-30)
    Motivo: el rango 20 ya es un logro


--- LOS 20 RANGOS ---
0.  Sin Rango
1.  Novato
2.  Aprendiz
3.  Escudero
4.  Ballestero
5.  Cazador de Sombras
6.  Rastreador
7.  Guerrero
8.  Veterano
9.  Élite
10. Caballero
11. Campeón
12. Héroe
13. Maestro
14. Gran Maestro
15. Leyenda
16. Mítico
17. Archimago
18. Señor de las Sombras
19. Semidiós
20. Dios


--- TABLA DE RANGOS (dinero, objetos, XP) ---
1→2:   500$ | 16 carbón, 8 hierro | 5 XP
2→3:   2.500$ | 32 hierro, 16 oro, 8 cobre | 10 XP
3→4:   8.000$ | 64 hierro, 32 oro, 16 redstone | 15 XP
4→5:   25.000$ | 32 diamantes, 16 lapis, 8 esmeraldas | 20 XP
5→6:   75.000$ | 32 diamantes, 64 hierro, 16 prismarina | 25 XP
6→7:   200.000$ | 64 diamantes, 32 esmeraldas, 16 amatista | 30 XP
7→8:   500.000$ | 1 stack bloques hierro, 32 esmeraldas, 16 netherite | 40 XP
8→9:   1.250.000$ | 1 stack bloques oro, 64 esmeraldas, 32 netherite | 50 XP
9→10:  3.000.000$ | 2 stacks bloques diamante, 1 stack netherite, 64 esmeraldas | 60 XP
10→11: 6.000.000$ | 2 stacks bloques netherite, 16 estrellas nether | 75 XP
11→12: 12.000.000$ | 3 stacks bloques diamante, 32 estrellas nether, 8 cabezas wither | 100 XP
12→13: 24.000.000$ | 4 stacks bloques netherite, 32 estrellas nether, 16 corazones mar | 125 XP
13→14: 45.000.000$ | 5 stacks bloques diamante, 48 estrellas nether, 2 cabezas dragón | 150 XP
14→15: 80.000.000$ | 6 stacks bloques netherite, 48 estrellas nether, 32 cabezas wither | 175 XP
15→16: 140.000.000$ | 8 stacks bloques diamante, 64 estrellas nether, 4 cabezas dragón | 200 XP
16→17: 250.000.000$ | 10 stacks bloques netherite, 64 estrellas nether, 32 amatista | 250 XP
17→18: 400.000.000$ | 12 stacks bloques diamante, 96 estrellas nether, 32 cabezas wither | 300 XP
18→19: 700.000.000$ | 16 stacks bloques netherite, 128 estrellas nether, 8 cabezas dragón | 400 XP
19→20: 1.200.000.000$ | 24 stacks bloques netherite, 192 estrellas nether, 64 cabezas wither, 32 corazones mar | 500 XP


--- MULTIPLICADOR DE DINERO POR RANGO ---
Rango 0-1:  x1
Rango 2:    x2
Rango 3:    x3
Rango 4:    x4
Rango 5:    x5
Rango 6:    x7
Rango 7:    x9
Rango 8:    x12
Rango 9:    x15
Rango 10:   x20
Rango 11:   x25
Rango 12:   x32
Rango 13:   x40
Rango 14:   x50
Rango 15:   x65
Rango 16:   x80
Rango 17:   x100
Rango 18:   x130
Rango 19:   x170
Rango 20:   x250


--- MULTIPLICADOR DE XP POR RANGO ---
Rango 0-3:   x1
Rango 4-9:   x2
Rango 10-19: x3
Rango 20:    x4


--- STATS POR RANGO ---
| Rango | Vida | Daño | Armadura | Knockback |
|-------|------|------|----------|-----------|
| 0-1   | +0   | +0   | +0       | +0        |
| 5     | +8   | +2   | +2       | +0.20     |
| 10    | +23  | +7   | +7       | +0.70     |
| 15    | +43  | +14.5| +14.5    | +1.45     |
| 20    | +68  | +22  | +22      | +2.45     |

Rango 20 = 88 HP (44 corazones)


--- MOBS CON NIVEL ---
- Nivel 1-20 asignado al spawnear
- Nivel según rango del jugador:
  - min = max(1, rango - 3)
  - max = min(20, rango + 5)
- Nombre: "<Mob> [Nv. X] (PY)" con color según nivel
- HP = 20 + (nivel × 10)
- Daño = 2 + (nivel × 3)
- Dinero = base × nivel × rango × aleatorio(1-5)
- XP = baseXp × nivel × rango × aleatorio(1-3)


--- MOBS OP ---
| Tipo | Probabilidad | Rango mínimo |
|------|--------------|--------------|
| Épico | 1/50 | 5+ |
| Legendario | 1/250 | 10+ |
| Mítico | 1/750 | 15+ |

Multiplicadores:
- Épico: HP x3, Daño x1.5, Dinero x5, XP x3
- Legendario: HP x6, Daño x2.5, Dinero x15, XP x6
- Mítico: HP x12, Daño x4, Dinero x50, XP x12

Drops: 2 items garantizados
- Épico: encantamiento 21-23
- Legendario: encantamiento 24-26
- Mítico: encantamiento 27-30

Nombres: "Zombi Épico [Nv. X]" (color naranja/rojo/morado)


--- DROPS CHETADOS (mobs hostiles normales) ---
Probabilidad según nivel:
- Nivel 1-5: 2%
- Nivel 6-10: 5%
- Nivel 11-15: 10%
- Nivel 16-20: 15%

Items: espada, pico, hacha, armadura, arco, ballesta, tridente (1/7)

Material:
- Nivel 1-5: Hierro
- Nivel 6-10: Diamante
- Nivel 11-20: Netherite

Encantamiento: 1-5, 5-10, 10-15, 15-20 según nivel


--- DROPS DE RANKEO (mobs pasivos) ---
Probabilidad según nivel:
- Nivel 1-5: 20%
- Nivel 6-10: 40%
- Nivel 11-15: 60%
- Nivel 16-20: 80%

Items:
- Nivel 1-10: Prismarina, Amatista (1-4)
- Nivel 11-15: Estrella del Nether, Cabeza de Wither (1-2)
- Nivel 16-20: Corazón del Mar, Cabeza de Dragón (1)


--- AUTO-SELL ---
- Comando /autosell (toggle)
- Persiste al morir y viajar
- Solo vende items de la lista de VENTA
- NO vende drops chetados
- Notificación flotante al activar/desactivar


--- HUD PERMANENTE ---
- Arriba a la derecha
- Muestra: dinero (formato compacto), rango, auto-sell
- Se actualiza cada 1 segundo (solo si cambió)
- Se oculta en GUIs


--- FORMATO COMPACTO ---
- 0-999: número normal (999)
- 1.000-999.999: "1k" - "999k"
- 1.000.000-999.999.999: "1M" - "999M"
- 1.000.000.000+: "1B" - "999B"


--- RECOMPENSAS POR ACCIÓN (base) ---
Minerales: carbón 2 | cobre 3 | hierro 5 | oro 8 | redstone 4 | lapis 6
           diamante 50 | esmeralda 80 | cuarzo 3 | ancient debris 500
Factor aleatorio: 1-10

Troncos: comunes 10 | jungla/mangle 20 | cerezo/crimson/warped 30
Factor aleatorio: 1-10

Pesca: bacalao 30 | salmón 50 | tropical 100 | globo 150
Factor aleatorio: 1-5

Mobs: zombi 2 | esqueleto 3 | creeper 5 | enderman 20 | blaze 25
      wither skeleton 30 | wither 5.000 | dragón 50.000
      cerdo/vaca/oveja/gallina 1
Factor aleatorio: 1-5


--- ESTRUCTURA DEL PROYECTO ---
src/main/java/com/bambumods/bambushop/
├── BambuShop.java
├── BambuShopClient.java
├── attachment/
│   └── ModAttachments.java (MONEY, RANK, MOB_LEVEL, MOB_OP_TYPE, AUTO_SELL)
├── client/
│   ├── ClientEvents.java
│   ├── ClientPlayerDataCache.java
│   └── gui/
│       ├── MainMenuScreen.java
│       ├── RankMenuScreen.java
│       ├── SellScreen.java
│       ├── ShopCategoriesScreen.java
│       ├── ShopCategoryScreen.java
│       ├── ShopHudOverlay.java
│       ├── ShopNotifications.java
│       ├── ShopQuantityScreen.java
│       └── MoneyHudOverlay.java
├── command/
│   ├── AutoSellCommand.java
│   ├── MenuCommand.java
│   ├── RankCommand.java
│   └── ShopDebugCommand.java
├── config/
│   └── ShopRewards.java
├── event/
│   ├── LootDropEvents.java
│   ├── MobLevelEvents.java
│   ├── MoneyEvents.java
│   └── PlayerDataSyncEvents.java
├── menu/
│   ├── MainMenu.java
│   ├── RankMenu.java
│   ├── SellMenu.java
│   ├── ShopCategoriesMenu.java
│   ├── ShopCategoryMenu.java
│   └── ShopQuantityMenu.java
├── mob/
│   ├── LootDropHelper.java
│   ├── MobLevelHelper.java
│   ├── MobNameHelper.java
│   ├── MobOpHelper.java
│   ├── MobRewardsHelper.java
│   ├── MobStatsHelper.java
│   └── PassiveLootHelper.java
├── network/
│   ├── BuyItemPacket.java
│   ├── ModNetworking.java
│   ├── OpenRankGuiPacket.java
│   ├── OpenSellGuiPacket.java
│   ├── OpenShopCategoriesPacket.java
│   ├── OpenShopCategoryItemPacket.java
│   ├── OpenShopCategoryPacket.java
│   ├── RankUpPacket.java
│   ├── SellItemPacket.java
│   ├── SyncPlayerDataPacket.java
│   └── TransactionFeedbackPacket.java
├── rank/
│   ├── RankAttributeEvents.java
│   ├── RankData.java
│   ├── RankManager.java
│   └── RankRequirement.java
├── registry/
│   └── ModMenus.java
├── shop/
│   ├── AutoSellHelper.java
│   ├── DefaultShopData.java
│   ├── ShopCategory.java
│   ├── ShopConfig.java
│   ├── ShopEntry.java
│   └── ShopManager.java
└── util/
    └── MoneyFormat.java


--- NOTAS TÉCNICAS IMPORTANTES (NeoForge 1.21.11) ---
1. Packets al servidor: ClientPacketDistributor.sendToServer(packet)
2. ResourceLocation = Identifier: Identifier.fromNamespaceAndPath(...)
3. @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT) para cliente
4. AttachmentType.serialize() requiere MapCodec
5. PlayerEvent.Clone se dispara ANTES de copiar attachments → usar PlayerRespawnEvent
6. BuiltInRegistries.ITEM.get(id) devuelve Optional<Item>
7. StreamCodec.composite() con ByteBufCodecs.STRING_UTF8, VAR_INT, VAR_LONG, BOOL
8. g.setTooltipForNextFrame() en vez de renderTooltip()
9. mouseClicked(MouseButtonEvent event, boolean isDoubleClick)
10. g.pose() no existe en 1.21.11
11. BlockDropsEvent.getDrops() devuelve List<ItemEntity>, no List<ItemStack>
12. Commands.hasPermission(Commands.LEVEL_GAMEMASTERS) para solo OPs
13. Los packets servidor→cliente se registran con playToClient(TYPE, CODEC) sin handler
    Y el handler va en ClientEvents con RegisterClientPayloadHandlersEvent


--- LO QUE QUIERE EL USUARIO ---
- Todo copy-paste: archivos completos, sin "añade esta línea"
- Respuestas directas, sin rodeos
- Ir paso a paso, sin correr
- Avisar ANTES de dar pasos largos
- Sin cosméticos, sin vuelo, sin dash, sin teletransporte
- Objetos siempre estaqueables
- Todo vanilla
- Sin spam de chat (notificaciones flotantes en su lugar)
- El mod está TERMINADO (fases pendientes descartadas)


--- MODS RELACIONADOS ---
- Bambu Essence (mod aparte, terminado)
  - Esencias, maestrías, encantamientos nivel 30, invocaciones
  - Se instala junto a Bambu Shop (no integrado, son mods separados)
  - Aporta el Mixin que deslimita los encantamientos
  - GitHub: https://github.com/BambuMods/bambuessence


--- PRÓXIMOS PASOS ---
- FASE 7.4: Este PROYECTO.md
- FASE 7.5: Compilar el .jar final y publicar (opcional)
- El mod está COMPLETO


========================================
FIN DEL DOCUMENTO
========================================