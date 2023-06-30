package com.xiaohunao.equipmentbenediction.block;

import com.xiaohunao.equipmentbenediction.block_entity.RecastingDeskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class RecastingDeskBlock extends Block implements EntityBlock {
    public RecastingDeskBlock() {
        super(Properties.of(Material.STONE).strength(1.5f, 6.0f));
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new RecastingDeskBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof RecastingDeskBlockEntity blockEntity && player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openGui(serverPlayer, blockEntity, pos);

            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

}
