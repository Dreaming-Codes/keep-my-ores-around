package codes.dreaming.keepmyoresaround.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.level.levelgen.feature.GlowstoneFeature.class)
public class GlowstoneFeature {
    @Inject(at = @At("HEAD"), method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", cancellable = true)
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> p_159861_, CallbackInfoReturnable<Boolean> cir) {
        WorldGenLevel worldgenlevel = p_159861_.level();
        BlockPos blockpos = p_159861_.origin();
        RandomSource randomsource = p_159861_.random();

        LevelData levelData = worldgenlevel.getLevelData();

        float distance = (float) Math.sqrt(Math.pow(blockpos.getX() - levelData.getXSpawn(), 2) + Math.pow(blockpos.getZ() - levelData.getZSpawn(), 2));

        //y = 100 * e^(-x/200)
        //This is the percentage of spawning
        float percentage = (float) (100 * Math.exp(-distance / 200)) / 100;

        if (randomsource.nextFloat() > percentage) {
            cir.setReturnValue(false);
        }


    }
}
