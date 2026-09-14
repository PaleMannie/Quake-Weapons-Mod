package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.QWExplosionHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class GrenadeProjectileEntity extends Projectile {

    public GrenadeProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    private boolean exploded;

    private void quakeExplosion(Level level) {
        if (!(level instanceof ServerLevel serverLevel) || exploded) return;
        exploded = true;

        Vec3 center = this.position();
        QWExplosionHelper.grenadeExplosion(serverLevel, this, this.getOwner(), center);

        serverLevel.sendParticles(ParticleTypes.FLAME,
                center.x, center.y, center.z,
                40,
                0.0, 0.0, 0.0,
                0.2);

        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                center.x, center.y, center.z,
                20,
                0.0, 0.0, 0.0,
                0.1);

        this.level().playSound(null, center.x, center.y, center.z,
                ModSounds.EXPLOSION.get(), SoundSource.PLAYERS,
                2.0F, 1.0F);

        this.discard();
    }

    public float lastTumbleX = 0;
    public float lastTumbleY = 0;
    public float lastTumbleZ = 0;

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    public void tick() {

        super.tick();

        Vec3 motion = this.getDeltaMovement();

        if (this.level().isClientSide()) {

            Vec3 motion1 = this.getDeltaMovement().normalize().scale(-0.25);
            double px = this.getX() + motion1.x;
            double py = this.getY() + motion1.y;
            double pz = this.getZ() + motion1.z;

            this.level().addParticle(ParticleTypes.SMOKE, px, py, pz, 0, 0, 0);
        }

        ClipContext ctx = new ClipContext(this.position(),
                this.position().add(motion),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this);
        BlockHitResult blockHit = this.level().clip(ctx);

        if (blockHit.getType() != HitResult.Type.MISS) {
            this.onHitBlock(blockHit);

            /// TODO: fix diese
            Vec3 normal = Vec3.atLowerCornerOf(blockHit.getDirection().getUnitVec3i());
            Vec3 bounced = motion.subtract(normal.scale(2 * motion.dot(normal)));

            float loss = 0.5f + this.random.nextFloat() * 0.25f;
            bounced = bounced.scale(loss);

            if (bounced.lengthSqr() < 0.04) {
                this.setDeltaMovement(Vec3.ZERO);
            } else {
                this.setDeltaMovement(bounced);
            }

            this.setPos(blockHit.getLocation());
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                this,
                this.position(),
                this.position().add(motion),
                this.getBoundingBox().expandTowards(motion).inflate(0.3d),
                this::canHitEntity,
                motion.lengthSqr()
        );

        if (entityHit != null) {
            this.onHitEntity(entityHit);
        }

        if (!this.getDeltaMovement().equals(Vec3.ZERO)) {
            this.setPos(this.getX() + this.getDeltaMovement().x,
                    this.getY() + this.getDeltaMovement().y,
                    this.getZ() + this.getDeltaMovement().z);
        }

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.06, 0.0));
        }

        if(this.tickCount > 50) quakeExplosion(level());
    }

    public boolean hasStopped = false;

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        Vec3 motion = this.getDeltaMovement();
        double speed = motion.length();

        if (speed > 0.2 && !hasStopped) {
            this.playSound(ModSounds.GRENADE_BOUNCE.get(), 2f, 1f);
        }

        if (speed < 0.2) {
            hasStopped = true;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        quakeExplosion(this.level());
        this.discard();
    }
}
