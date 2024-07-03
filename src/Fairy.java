import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Fairy extends Entity implements Exeggutor, Movers{
    //public static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
    private final double animationPeriod;
    private final double actionPeriod;

    private PathingStrategy strategy = new AStarPathingStrategy();

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
          super(id, position, images);
          this.animationPeriod = animationPeriod;
          this.actionPeriod = actionPeriod;

      }
      // needs to use getters when I refactor

    @Override
    public double getAnimationPeriod() {return this.animationPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }
    public Point nextPositionFairy(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;

// when calling this function, you have a start end,
// some sort of predicate that returns a boolean
        // the secondary bipredicate checks if we are adjacent to the object
        // finally the function takes in a point and adds its neighbors to a stream
        List <Point> path = strategy.computePath(getPosition(), destPos,  // this is the start and end
                p -> (world.withinBounds(p) && !world.isOccupied(p)),  // checks if it is within bounds and if point is unoccupied
                Point::adjacent,
                PathingStrategy.CARDINAL_NEIGHBORS);
        if (!path.isEmpty())
            return path.getFirst();
        return getPosition();
    }


    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPositionFairy(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    // the problem that i am having here is with the gets
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = findNearest(world, this.getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
//     public Sapling(String id, Point position, List<PImage> images, int health, double actionPeriod){
                Entity sapling = new Sapling(world.getSaplingKey() + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(world.getSaplingKey()), 0);
                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, imageStore, world), this.actionPeriod);
    }




}
