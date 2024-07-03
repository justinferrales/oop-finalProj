import processing.core.PImage;

import java.util.List;

// GOIN BREAK, IMPLEMENT THE TRANSFORM
public class MysteryBlock extends stuffUsingTransform implements Exeggutor {
    private final double animationPeriod;
    private final double actionPeriod;
    private final int health;

    //public Entity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {

    //    public static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
//        return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
//    }
    public MysteryBlock(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health) {
        super(id, position, images, health);
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.health = health;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore store, EventScheduler scheduler) {
        if (!this.transform(world, scheduler, store)) {
            scheduler.scheduleEvent(this, new Activity(this, store, world), this.actionPeriod);
        }
    }

    @Override
    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore store) {
        scheduler.scheduleEvent(this, new Activity(this, store, world), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
        //Entity mys = new MysteryBlock(world.getMysteryblockKey() + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(world.getSaplingKey()), 1,1,1);
            //                world.addEntity(mys);
            //                mys.scheduleActions(scheduler, world, imageStore);
            Entity mys = new MysteryBlock(world.getMysteryblockKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getMysteryblockKey()), 1,1,1);
            world.removeEntity(scheduler, this);

            world.addEntity(mys);

            return true;
        }

        return false;
    }

}