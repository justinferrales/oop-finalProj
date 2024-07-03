import processing.core.PImage;

import java.util.List;

public class Sapling extends stuffUsingTransform implements Exeggutor{
    private final double animationPeriod;
    private final int healthLimit;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;
    private final double actionPeriod;
    // public static Entity createSapling(String id, Point position, List<PImage> images, int health) {
    //        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, 0, WorldModel.SAPLING_HEALTH_LIMIT);
    //    }
    public Sapling(String id, Point position, List<PImage> images, int health){
        super(id, position, images, health);
        this.animationPeriod = SAPLING_ACTION_ANIMATION_PERIOD;
        this.healthLimit = SAPLING_HEALTH_LIMIT;
        this.actionPeriod = SAPLING_ACTION_ANIMATION_PERIOD;
        }
    @Override
    public double getAnimationPeriod(){return this.animationPeriod;}

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }

    // this needs a getter for stump key
    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(world.getStumpKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getStumpKey()));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);
// need a tree key
            return true;
        } else if (this.getHealth() >= this.healthLimit) {
            Entity tree = new Tree(world.getTreeKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getTreeKey()) ,Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), Functions.getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.updatehealth(getHealth() + 1);
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.actionPeriod);
        }
    }

}
