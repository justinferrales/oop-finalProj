import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude implements Exeggutor, Movers{
//    private final double animationPeriod;
//    private final double actionPeriod;
//    private final int resourceLimit;
//    private final int health;

    //    public static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
    //        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    //    }
    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod, int health) {
        //     public Dude(String id, Point position, List<PImage> images, int resourceLimit , double actionPeriod, double animationPeriod, int health){
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod, health);
    }

//    @Override
//    public double getAnimationPeriod() {
//        return this.animationPeriod;
//    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.getactionPeriod());
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = nextPositionDude(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = new DudeNotFull(this.getId(), this.getPosition(), this.getImages(), this.getResourceLimit(), this.getactionPeriod(), this.getactionPeriod(), this.getHealth());

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = findNearest(world, this.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.getactionPeriod());
        }
    }

}
