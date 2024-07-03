import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity {
    private final double animationPeriod;

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    @Override
    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }
}
