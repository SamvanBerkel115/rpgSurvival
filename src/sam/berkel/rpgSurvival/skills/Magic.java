package sam.berkel.rpgSurvival.skills;


import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

import java.util.Collection;


public class Magic {
    public static void castSpell(Player player) {
        System.out.println("Cast spell");
        User user = Server.getInstance().getUser(player.getUniqueId());
        String spell = user.getActiveSpell();

        user.addXp(1000, Main.Skill.MAGIC);

        switch(spell) {
            case "fireBolt":
                Magic.castFireBolt(player);
                break;
            case "jailBolt":
                Magic.castJailBolt(player);
                break;
            case "avadaKedavra":
                Magic.castAvadaKadavra(player);
                break;
            case "sigma":
                Magic.castSigma(player);
                break;
            case "volley":
                Magic.castVolley(player);
                break;
        }
    }

    public static void castAvadaKadavra(Player player) {
        player.sendTitle("", ChatColor.DARK_GREEN + "Avada Kedavra!", 10, 20, 40);
        new BukkitRunnable() {
            private int timer = 30;
            private Location loc = player.getEyeLocation();

            @Override
            public void run() {
                if (timer <= 0) {
                    this.cancel();
                } else {
                    player.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 1, new Particle.DustOptions(Color.GREEN, 4));

                    // Cancel the ray if it hits a block.
                    if ( !loc.getBlock().getType().equals(Material.AIR) && !loc.getBlock().getType().equals(Material.TALL_GRASS) && !loc.getBlock().getType().equals(Material.GRASS) ) {
                        this.cancel();
                    }

                    Collection<Entity> hitEntities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    if (hitEntities.size() > 0) {
                        for (Entity hitEntity : hitEntities) {
                            if (hitEntity instanceof LivingEntity && hitEntity.getUniqueId() != player.getUniqueId()) {
                                System.out.println("Hit entity");
                                LivingEntity liveEntity = (LivingEntity) hitEntity;
                                liveEntity.damage(100, player);

                                player.spawnParticle(Particle.EXPLOSION_LARGE, loc.getX(), loc.getY(), loc.getZ(), 1);

                                this.cancel();
                            }
                        }
                    }

                    loc.add(loc.getDirection().multiply(3));
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
    }

    public static void castJailBolt(Player player) {
        new BukkitRunnable() {
            private int timer = 30;
            private Location loc = player.getEyeLocation();

            @Override
            public void run() {
                if (timer <= 0) {
                    this.cancel();
                } else {
                    player.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 1, new Particle.DustOptions(Color.WHITE, 4));

                    // Cancel the ray if it hits a block.
                    if ( !loc.getBlock().getType().equals(Material.AIR) && !loc.getBlock().getType().equals(Material.TALL_GRASS) && !loc.getBlock().getType().equals(Material.GRASS) ) {
                        this.cancel();
                    }

                    Collection<Entity> hitEntities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    if (hitEntities.size() > 0) {
                        for (Entity hitEntity : hitEntities) {
                            if (hitEntity instanceof LivingEntity && hitEntity.getUniqueId() != player.getUniqueId()) {
                                System.out.println("Hit entity");
                                LivingEntity liveEntity = (LivingEntity) hitEntity;
                                Location entityLoc = liveEntity.getLocation();
                                entityLoc.getWorld().getBlockAt(entityLoc.add(0, 1, 0)).setType(Material.COBWEB);

                                this.cancel();
                            }
                        }
                    }

                    loc.add(loc.getDirection().multiply(3));
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
    }

    public static void castFireBolt(Player player) {
        new BukkitRunnable() {
            private int timer = 30;

            private Location loc = player.getEyeLocation();

            @Override
            public void run() {
                if (timer <= 0) {
                    this.cancel();
                } else {
                    player.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 1, new Particle.DustOptions(Color.RED, 4));

                    // Cancel the ray if it hits a block.
                    if ( !loc.getBlock().getType().equals(Material.AIR) && !loc.getBlock().getType().equals(Material.TALL_GRASS) && !loc.getBlock().getType().equals(Material.GRASS) ) {
                        this.cancel();
                    }

                    Collection<Entity> hitEntities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    if (hitEntities.size() > 0) {
                        for (Entity hitEntity : hitEntities) {
                            if (hitEntity instanceof LivingEntity && hitEntity.getUniqueId() != player.getUniqueId()) {
                                System.out.println("Hit entity");
                                LivingEntity liveEntity = (LivingEntity) hitEntity;
                                liveEntity.damage(7, player);

                                player.spawnParticle(Particle.EXPLOSION_LARGE, loc.getX(), loc.getY(), loc.getZ(), 1);

                                this.cancel();
                            }
                        }
                    }

                    loc.add(loc.getDirection().multiply(3));
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
    }

    public static void castSigma(Player player) {
        new BukkitRunnable() {
            private int timer = 30;

            private Location loc = player.getEyeLocation();

            @Override
            public void run() {
                if (timer <= 0) {
                    this.cancel();
                } else {
                    player.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 1, new Particle.DustOptions(Color.BLUE, 4));

                    // Cancel the ray if it hits a block.
                    if ( !loc.getBlock().getType().equals(Material.AIR) && !loc.getBlock().getType().equals(Material.TALL_GRASS) && !loc.getBlock().getType().equals(Material.GRASS) ) {
                        this.cancel();
                    }

                    Collection<Entity> hitEntities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    if (hitEntities.size() > 0) {
                        for (Entity hitEntity : hitEntities) {
                            if (hitEntity instanceof LivingEntity && hitEntity.getUniqueId() != player.getUniqueId()) {
                                System.out.println("Hit entity");
                                LivingEntity liveEntity = (LivingEntity) hitEntity;
                                liveEntity.setVelocity(new Vector(0, 1.5, 0));

                                player.spawnParticle(Particle.EXPLOSION_LARGE, loc.getX(), loc.getY(), loc.getZ(), 1);

                                this.cancel();
                            }
                        }
                    }

                    loc.add(loc.getDirection().multiply(3));
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
    }

    public static void castVolley(Player player) {
        new BukkitRunnable() {
            private int timer = 30;

            private Location loc = player.getEyeLocation();

            @Override
            public void run() {
                if (timer <= 0) {
                    this.cancel();
                } else {
                    // Dont spawn the particle immedeatly so it is not in the user's face.
                    if (timer < 30) {
                        player.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 1, new Particle.DustOptions(Color.BLUE, 4));
                    }

                    // Cancel the ray if it hits a block.
                    if ( !loc.getBlock().getType().equals(Material.AIR) && !loc.getBlock().getType().equals(Material.TALL_GRASS) && !loc.getBlock().getType().equals(Material.GRASS) ) {
                        this.cancel();
                    }

                    Collection<Entity> hitEntities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    if (hitEntities.size() > 0) {
                        for (Entity hitEntity : hitEntities) {
                            if (hitEntity instanceof LivingEntity && hitEntity.getUniqueId() != player.getUniqueId()) {
                                Location hitEntityLoc = hitEntity.getLocation();
                                for (int i = 0; i < 10; i++) {
                                    Bukkit.getWorld("world").spawnArrow(hitEntityLoc.add(0, 10, 0), new Vector(0,-4,0), 10, 4);
                                }
                            }
                        }
                    }

                    loc.add(loc.getDirection().multiply(3));
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
    }

    public static void giveWand(Player player) {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.RED + "Wand");
        wand.setItemMeta(wandMeta);
        player.getInventory().addItem(wand);
        Location loc = player.getLocation();
    }
}
