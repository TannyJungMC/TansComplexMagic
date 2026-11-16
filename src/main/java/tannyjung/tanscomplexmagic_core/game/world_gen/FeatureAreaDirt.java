package tannyjung.tanscomplexmagic_core.game.world_gen;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import tannyjung.tanscomplexmagic_core.game.BlockManager;

public class FeatureAreaDirt extends Feature <NoneFeatureConfiguration> {

    private static final int RADIUS = 16;

    public FeatureAreaDirt () {

        super(NoneFeatureConfiguration.CODEC);

    }

    @Override
    public boolean place (FeaturePlaceContext <NoneFeatureConfiguration> context) {

        ServerLevel level_server = context.level().getLevel();
        BlockPos center_pos = context.origin();
        RandomSource random = RandomSource.create(level_server.getSeed() ^ ((center_pos.getX() * 341873128712L) + (center_pos.getZ() * 132897987541L)));

        double area_sphere = RADIUS * RADIUS;
        double area_test = 0;
        BlockState previous_block = null;
        BlockPos pos = null;
        BlockState block = null;

        for (int scanX = -RADIUS; scanX <= RADIUS; scanX++) {

            for (int scanY = -RADIUS; scanY <= RADIUS; scanY++) {

                for (int scanZ = -RADIUS; scanZ <= RADIUS; scanZ++) {

                    area_test = (scanX * scanX) + (scanY * scanY) + (scanZ * scanZ);

                    if (area_test < area_sphere) {

                        if (random.nextDouble() < 1.0 - (area_test / area_sphere)) {

                            pos = new BlockPos(center_pos.getX() + scanX, center_pos.getY() + scanY, center_pos.getZ() + scanZ);
                            previous_block = level_server.getBlockState(pos);

                            if (BlockManager.test(previous_block, "#minecraft:dirt / #minecraft:sand / #minecraft:base_stone_overworld") == true) {

                                block = Blocks.COARSE_DIRT.defaultBlockState();
                                BlockManager.placeNormal(level_server, pos, block);

                            }

                        }

                    }

                }

            }

        }

        return true;

    }

}