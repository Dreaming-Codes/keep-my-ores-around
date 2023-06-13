package codes.dreaming.keepmyoresaround.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.level.levelgen.feature.OreFeature.class)
public class OreFeature {
    @Inject(at = @At("HEAD"), method = "doPlace(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/feature/configurations/OreConfiguration;DDDDDDIIIII)Z", cancellable = true)
    private void doPlace(WorldGenLevel pLevel, RandomSource pRandom, OreConfiguration pConfig, double pMinX, double pMaxX, double pMinZ, double pMaxZ, double pMinY, double pMaxY, int pX, int pY, int pZ, int pWidth, int pHeight, CallbackInfoReturnable<Boolean> cir) {
        LevelData levelData = pLevel.getLevelData();

        float distance = (float) Math.sqrt(Math.pow(pX - levelData.getXSpawn(), 2) + Math.pow(pZ - levelData.getZSpawn(), 2));

        //y = 100 * e^(-x/200)
        //This is the percentage of spawning
        float percentage = (float) (100 * Math.exp(-distance / 200)) / 100;

        if (pRandom.nextFloat() > percentage) {
            cir.setReturnValue(false);
        }


    }
}
