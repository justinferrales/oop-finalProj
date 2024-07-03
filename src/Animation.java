public class Animation extends Action{
//    public static Action createAnimationAction(Entity entity, int repeatCount) {
//        return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
//    }

    // this create is within the constructor, everytime there is an Action.createAnimationAction
    // I have to create a new.
    private final int repeatCount;

    public Animation(Entity entity, int repeatCount){
        super(entity);
        this.repeatCount = repeatCount;
    }
    @Override
    public void executeAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.getEntity(), new Animation(this.getEntity(), Math.max(this.repeatCount - 1, 0)), this.getEntity().getAnimationPeriod());
        }
    }

    /*
    public void executeAnimationAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }
     */
}
