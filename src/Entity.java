import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {
    // Static variables


    // Instance variables
    //private final EntityKind kind;
    private final String id;
    private Point position;
    private final List<PImage> images;
    public List<PImage> getImages(){
        return images;
    }
    private int imageIndex;
    // look @ the usages and the funcs, figure out where the funcs go and move the instance variables there into the class
    //private final int resourceLimit;
    private int resourceCount;
    //private final double actionPeriod;
    //final double animationPeriod;
   // public int health;


    //private final int healthLimit;
    //getters and setters
//    public EntityKind getKind() {
//        return this.kind;
//    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    private int health;
    public int getHealth() {
        return health;
    }

    public PImage getCurrentImage(){
        return this.images.get(this.imageIndex % this.images.size());
    }

    // need just id position and images, imageidx for all of the enum
    //     public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
    public Entity(String id, Point position, List<PImage> images) {
        //this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        //this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//        this.health = health;
//        this.healthLimit = healthLimit;
    }

    public static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.position.distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.position.distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    // commented over into Dude class, probably will be inside dudenotfull
//    // maybe make it an abstract class so it inherits attributes of dude
//    public Point nextPositionDude(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

    // copied over to fairy
//    public Point nextPositionFairy(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }
    // copied over to Full dude
//    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            return true;
//        } else {
//            Point nextPos = this.nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            this.resourceCount += 1;
//            target.health--;
//            return true;
//        } else {
//            Point nextPos = this.nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            world.removeEntity(scheduler, target);
//            return true;
//        } else {
//            Point nextPos = this.nextPositionFairy(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.health <= 0) {
//            Entity stump = createStump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(stump);
//
//            return true;
//        } else if (this.health >= this.healthLimit) {
//            Entity tree = createTree(WorldModel.TREE_KEY + "_" + this.id, this.position, Functions.getNumFromRange(WorldModel.TREE_ACTION_MAX, WorldModel.TREE_ACTION_MIN), Functions.getNumFromRange(WorldModel.TREE_ANIMATION_MAX, WorldModel.TREE_ANIMATION_MIN), Functions.getIntFromRange(WorldModel.TREE_HEALTH_MAX, WorldModel.TREE_HEALTH_MIN), imageStore.getImageList(WorldModel.TREE_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(tree);
//            tree.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

//    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.health <= 0) {
//            Entity stump = createStump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//
//        return false;
//    }

    // need to figure out where this is going to go
//    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.kind == EntityKind.TREE) {
//            return this.transformTree(world, scheduler, imageStore);
//        } else if (this.kind == EntityKind.SAPLING) {
//            return this.transformSapling(world, scheduler, imageStore);
//        } else {
//            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
//        }
//    }


    // moving to full dude
//    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        Entity dude = createDudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);
//
//        world.removeEntity(scheduler, this);
//
//        world.addEntity(dude);
//        dude.scheduleActions(scheduler, world, imageStore);
//    }
    // moved
//    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity dude = createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);
//
//            world.removeEntity(scheduler, this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(dude);
//            dude.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

    // how am i going to change all these? do i just override them into each subclass with their corresponding funcs
    // actually yeah i think so
    public  abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
//        switch (this.kind) {
//            case DUDE_FULL:
//                scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            case DUDE_NOT_FULL:
//                scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            case OBSTACLE:
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            case FAIRY:
//                scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            case SAPLING:
//                scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            case TREE:
//                scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//                scheduler.scheduleEvent(this, Action.createAnimationAction(this, 0), getAnimationPeriod());
//                break;
//
//            default:
//        }
//    }

    // moving to dude full
//    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fullTarget = world.findNearest(this.position, new ArrayList<>(List.of(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
//            this.transformFull(world, scheduler, imageStore);
//        } else {
//            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//        }
//    }
    // moving to dude not full
//    public void executeDudeNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (target.isEmpty() || !this.moveToNotFull(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//        }
//    }
    // moving to fairy
//    public void executeFairyActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fairyTarget = world.findNearest(this.position, new ArrayList<>(List.of(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
//
//                Entity sapling = createSapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList(WorldModel.SAPLING_KEY), 0);
//
//                world.addEntity(sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//    }
    // moving to tree
//    public void executeTreeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//
//        if (!this.transformPlant(world, scheduler, imageStore)) {
//
//            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//        }
//    }
    // moving to sapling
//    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        this.health++;
//        if (!this.transformPlant(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
//        }
//    }
    // If all goes well with my plan and i make dudeFULL and dudeNOTFULL an extension of dude just call it there
    // for all the others need to add it in like how i will do above with that last func i looked at
    public abstract double getAnimationPeriod();
//    return switch (this.kind) {
//        case DUDE_FULL, DUDE_NOT_FULL, OBSTACLE, FAIRY, SAPLING, TREE -> this.animationPeriod;
//        default ->
//                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
//    };


    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }

    /*
    public static Entity createHouse(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    public static Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
    }

    public static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
    }

    public static Entity createStump(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.STUMP, id, position, images, 0, 0, 0, 0, 0, 0);
    }

   //public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {


    // health starts at 0 and builds up until ready to convert to Tree
    public static Entity createSapling(String id, Point position, List<PImage> images, int health) {
        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, 0, WorldModel.SAPLING_HEALTH_LIMIT);
    }

    public static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }

    // need resource count, though it always starts at 0
    public static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }

    public static Entity createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }
    */

    public static boolean adjacentTo(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    public static Optional<Entity> findNearest(WorldModel world, Point pos, List<Class<?>> kinds) {
        // i was so stuck with trying to get the executes for fairy and dudes to move so i moved it into entity
        // especially bc it uses getKind, which uses the EntityKind class which we're supposed to eliminate
        // the only changes are changing the List to an ? class which can hold instances of classes but the type can be any class
        // https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html
        List<Entity> ofType = new LinkedList<>();
        for (Class<?> kind : kinds) {
            for (Entity entity : world.entities) {
                if (entity.getClass() == kind) {
                    ofType.add(entity);
                }
            }
        }
        return nearestEntity(ofType, pos);
    }

/*
    public Optional<Entity> findNearest(Point pos, List<EntityKind> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (EntityKind kind : kinds) {
            for (Entity entity : this.entities) {
                if (entity.getKind() == kind) {
                    ofType.add(entity);
                }
            }
        }

 */
}
