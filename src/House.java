import processing.core.PImage;

import java.util.List;

public class House extends Entity{

    public House(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    }
    @Override
    public double getAnimationPeriod(){
        return 0;
    }
}
