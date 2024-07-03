import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class DudeNotFull extends Dude implements Exeggutor, Movers{
// not full needs that resource count
    private int resourceCount;
//    public int getResourceCount(){
//        return resourceCount;
//    }

    private int health;
    public DudeNotFull(String id, Point position, List<PImage> images,int resourceLimit ,double actionPeriod,double animationPeriod, int health){
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod, health);
    }

//    @Override
//    public double getAnimationPeriod() {
//        return this.animationPeriod;
//    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, imageStore, world), 0.3);
        scheduler.scheduleEvent(this, new Animation(this, 0), 0.3);
    }

    // problems with .health
    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.resourceCount += 1;
            ((stuffUsingTransform)target).updatehealth(getHealth()- 1); // updating the health value, idea behind it is in the abstract class Transform
            // need to cast it bc target is casted as entity, and it does not have the updateHealth
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
        if (this.resourceCount >= this.getResourceLimit()) {
            Entity dude = new DudeFull(this.getId(), this.getPosition(), this.getImages(), this.getResourceLimit(), this.getactionPeriod(), this.getactionPeriod(), this.health);

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    // the executes have that issue with the <?>
    // fixed it by moving findNearest
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = findNearest(world, this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));

        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.getactionPeriod());
        }
    }
}
