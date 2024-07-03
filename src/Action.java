/**
 * An action that can be taken by an entity
 */
public abstract class Action {
     //private final ActionKind kind;
     private final Entity entity;
     public Entity getEntity(){
         return entity;
    }
     //private final WorldModel world;
     //private final ImageStore imageStore;
     //private final int repeatCount;

    public Action(Entity entity) {
        //this.kind = kind;
        this.entity = entity;
        //this.world = world;
        //this.imageStore = imageStore;
        //this.repeatCount = repeatCount;
    }
//
//    public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
//        return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
//    }
//
//    public static Action createAnimationAction(Entity entity, int repeatCount) {
//        return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
//    }

//    public void executeActivityAction(EventScheduler scheduler) {
//        switch (this.entity.getKind()) {
//            case SAPLING -> this.entity.executeSaplingActivity(this.world, this.imageStore, scheduler);
//            case TREE -> this.entity.executeTreeActivity(this.world, this.imageStore, scheduler);
//            case FAIRY -> this.entity.executeFairyActivity(this.world, this.imageStore, scheduler);
//            case DUDE_NOT_FULL -> this.entity.executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
//            case DUDE_FULL -> this.entity.executeDudeFullActivity(this.world, this.imageStore, scheduler);
//            default ->
//                    throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
//        }
//    }

//    public void executeAnimationAction(EventScheduler scheduler) {
//        this.entity.nextImage();
//
//        if (this.repeatCount != 1) {
//            scheduler.scheduleEvent(this.entity, createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
//        }
//    }

    public abstract void executeAction(EventScheduler scheduler);
}
