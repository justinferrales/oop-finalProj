import processing.core.PImage;

import java.awt.*;
import java.util.List;
// the dudes, i.e. full and not full both have some transform
// additionally so does tree and sapling

public abstract class stuffUsingTransform extends Entity {
    //     public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    // similar to execute
    private int health;
    public int getHealth(){
        return this.health;
    }
    // there is a problem with dudenotfull with the target.health--
    // need to decrease the health by 1
    // the updateHealth, represents the new value, whilst the getHealth() retrieves the current value
    public void updatehealth(int health){
        this.health = health;
    }

    public stuffUsingTransform(String id, Point position, List<PImage> images, int health) {
        super(id, position, images);
        this.health = health;
    }

    public abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}






