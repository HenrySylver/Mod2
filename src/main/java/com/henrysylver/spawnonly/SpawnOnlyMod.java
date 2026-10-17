package com.henrysylver.spawnonly;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.commands.Commands.literal;

public class SpawnOnlyMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) ->
                dispatcher.register(
                    literal("spawn")
                        .executes(context -> {
                            ServerPlayer player =
                                context.getSource().getPlayerOrException();

                            ServerLevel overworld =
                                context.getSource().getServer().overworld();

                            BlockPos spawn =
                                overworld.getSharedSpawnPos();

                            Vec3 position = Vec3.atBottomCenterOf(spawn);

                            player.teleportTo(
                                overworld,
                                position.x,
                                position.y,
                                position.z,
                                java.util.Set.of(),
                                player.getYRot(),
                                player.getXRot()
                            );

                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}
