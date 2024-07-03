import processing.core.PImage;

import java.util.List;

public class Stump extends Entity{
    public Stump(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
    @Override
    public double getAnimationPeriod(){
        return 0;
    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
    }
}
