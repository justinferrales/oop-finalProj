import java.awt.*;

public class Activity extends Action{
    //
    private final WorldModel world;
    private final ImageStore store;

    //public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
//        return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
//    }

    // this create is within the constructor, everytime there is an Action.createActivityAction
    // I have to create a new.

    public Activity(Entity entity, ImageStore store, WorldModel world) {
        super(entity);
        this.store = store;
        this.world = world;
    }

    /*
    public void executeActivityAction(EventScheduler scheduler) {
        switch (this.entity.getKind()) {
            case SAPLING -> this.entity.executeSaplingActivity(this.world, this.imageStore, scheduler);
            case TREE -> this.entity.executeTreeActivity(this.world, this.imageStore, scheduler);
            case FAIRY -> this.entity.executeFairyActivity(this.world, this.imageStore, scheduler);
            case DUDE_NOT_FULL -> this.entity.executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
            case DUDE_FULL -> this.entity.executeDudeFullActivity(this.world, this.imageStore, scheduler);
            default ->
                    throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
        }
    }
     */
    @Override
    public void executeAction(EventScheduler scheduler) {
        // this.entity.executeSaplingActivity(this.world, this.imageStore, scheduler);
        // need to generalize, the above
        // dynamically casting getEntity
        Entity entity = getEntity();
        Exeggutor executeEntity = (Exeggutor) entity;
        executeEntity.executeActivity(this.world, this.store, scheduler);

    }
}