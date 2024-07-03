import processing.core.PImage;

import java.util.List;

public abstract class Dude extends stuffUsingTransform{

    private final double animationPeriod;

    private final double actionPeriod;
    public double getactionPeriod(){
        return actionPeriod;
    }

    private final int resourceLimit;
    public int getResourceLimit(){
        return resourceLimit;
    }

    private PathingStrategy strategy = new AStarPathingStrategy();

    //    public static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
    //        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    //    }
    public Dude(String id, Point position, List<PImage> images, int resourceLimit , double actionPeriod, double animationPeriod, int health){
        super(id, position, images, health);
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.resourceLimit = resourceLimit;
    }
    // needs to use getters when I refactor
    // was going to create an interface for new position, for both dude and fairy. But, its just two things and the spec said not
    // to overcomplicate and do too much
    public Point nextPositionDude(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
        List <Point> path = strategy.computePath(getPosition(), destPos,
                //p -> ((world.withinBounds(p) && !world.isOccupied(p)) || (world.getOccupancyCell(p).getClass() != Stump.class)),  // essentially is canPassThrough
                point -> (world.withinBounds(point) && !world.isOccupied(point)),  // when i had the above code it cut down stumps and trees
                Point::adjacent,
                PathingStrategy.CARDINAL_NEIGHBORS);
        if (!path.isEmpty())
            return path.getFirst();
        return getPosition();
    }


    @Override
    public double getAnimationPeriod() {
        return this.animationPeriod;
    }


}
