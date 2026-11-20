/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tannyjung.tanscomplexmagic.init;

import tannyjung.tanscomplexmagic.client.particle.ParticleFireballWhiteParticle;
import tannyjung.tanscomplexmagic.client.particle.BookStayMarkParticle;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class TanscomplexmagicModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(TanscomplexmagicModParticleTypes.PARTICLE_FIREBALL_WHITE.get(), ParticleFireballWhiteParticle::provider);
		event.registerSpriteSet(TanscomplexmagicModParticleTypes.BOOK_STAY_MARK.get(), BookStayMarkParticle::provider);
	}
}