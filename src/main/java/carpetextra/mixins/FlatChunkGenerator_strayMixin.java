package carpetextra.mixins;

import carpet.CarpetSettings;
import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FlatChunkGenerator.class)
public abstract class FlatChunkGenerator_strayMixin extends ChunkGenerator<FlatChunkGeneratorConfig>
{
    public FlatChunkGenerator_strayMixin(IWorld world, BiomeSource biomeSource, FlatChunkGeneratorConfig config)
    {
        super(world, biomeSource, config);
    }
    
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "getEntitySpawnList", at = @At("HEAD"), cancellable = true)
    private void onGetEntitySpawnList(EntityCategory category, BlockPos pos, CallbackInfoReturnable<List<Biome.SpawnEntry>> cir)
    {
        if (CarpetSettings.flatWorldStructureSpawning && category == EntityCategory.MONSTER && CarpetExtraSettings.straySpawningInIgloos)
        {
            if (Feature.IGLOO.isApproximatelyInsideStructure(this.world, pos))
            {
                cir.setReturnValue(Feature.IGLOO.getMonsterSpawns());
            }
        }
    }
    
}
