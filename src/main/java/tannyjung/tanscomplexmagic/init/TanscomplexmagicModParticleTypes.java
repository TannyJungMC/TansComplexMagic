/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tannyjung.tanscomplexmagic.init;

import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

public class TanscomplexmagicModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, TanscomplexmagicMod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PARTICLE_FIREBALL_WHITE = REGISTRY.register("particle_fireball_white", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BOOK_STAY_MARK = REGISTRY.register("book_stay_mark", () -> new SimpleParticleType(true));
}