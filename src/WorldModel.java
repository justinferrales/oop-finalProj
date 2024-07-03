import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;
    private static final String STUMP_KEY = "stump";
    public String getStumpKey() {
        return STUMP_KEY;
    }

    private static final int STUMP_NUM_PROPERTIES = 0;
    private static final String SAPLING_KEY = "sapling";
    public String getSaplingKey() {
        return SAPLING_KEY;
    }

    private static final int SAPLING_HEALTH = 0;
    private static final int SAPLING_NUM_PROPERTIES = 1;
    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_ANIMATION_PERIOD = 0;
    private static final int OBSTACLE_NUM_PROPERTIES = 1;
    private static final String DUDE_KEY = "dude";
    private static final int DUDE_ACTION_PERIOD = 0;
    public String getDudeKey(){return DUDE_KEY;}
    private static final int DUDE_ANIMATION_PERIOD = 1;
    private static final int DUDE_LIMIT = 2;
    private static final int DUDE_NUM_PROPERTIES = 3;
    private static final String HOUSE_KEY = "house";
    private static final int HOUSE_NUM_PROPERTIES = 0;
    private static final String FAIRY_KEY = "fairy";
    public String getPeachKey(){return "peach";}
    private static final String BOWSER_KEY = "bowser";
    public String getBowserKey(){return BOWSER_KEY;}
    private static final String MYSTERYBLOCK_KEY = "mysteryblock";
    public String getMysteryblockKey(){return MYSTERYBLOCK_KEY;}

    public String getFairyKey() {return FAIRY_KEY;}
    private static final int FAIRY_ANIMATION_PERIOD = 0;
    public int getFairyAnimationPeriod(){return FAIRY_ANIMATION_PERIOD;}
    private static final int FAIRY_ACTION_PERIOD = 1;
    public int getFairyActionPeriod(){return FAIRY_ACTION_PERIOD;}

    public int getFairyNumProperties(){return FAIRY_NUM_PROPERTIES;}
    private static final int FAIRY_NUM_PROPERTIES = 2;
    private static final String TREE_KEY = "tree";
    public String getTreeKey() {
        return TREE_KEY;
    }
    private static final int TREE_ANIMATION_PERIOD = 0;
    private static final int TREE_ACTION_PERIOD = 1;
    private static final int TREE_HEALTH = 2;
    private static final int COIN_HEALTH = 1;
    private static final int TREE_NUM_PROPERTIES = 3;
    //public static final double TREE_ANIMATION_MAX = 0.600;
    //public static final double TREE_ANIMATION_MIN = 0.050;
    //public static final double TREE_ACTION_MAX = 1.400;
    //public static final double TREE_ACTION_MIN = 1.000;
    //public static final int TREE_HEALTH_MAX = 3;
    //public static final int TREE_HEALTH_MIN = 1;

    private static final int KEYED_IMAGE_MIN = 5;
    private static final int PROPERTY_KEY = 0;
    private static final int PROPERTY_ID = 1;
    private static final int PROPERTY_COL = 2;
    private static final int PROPERTY_ROW = 3;
    private static final int ENTITY_NUM_PROPERTIES = 4;
    private static final int COLOR_MASK = 0xffffff;
    static final int KEYED_RED_IDX = 2;
    static final int KEYED_GREEN_IDX = 3;
    static final int KEYED_BLUE_IDX = 4;
    private int numRows;
    public int getnumRows(){
        return numRows;
    }
    private int numCols;
    public int getNumCols(){
        return numCols;
    }
    private Background[][] background;
    private Entity[][] occupancy;
    public Set<Entity> entities;

    public WorldModel() {
    }

    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    public static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    /*
          Called with color for which alpha should be set and alpha value.
          setAlpha(img, color(255, 255, 255), 0));
        */
    public static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    public static void loadImages(Scanner in, ImageStore imageStore, PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(imageStore.getImages(), in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.printf("Image format error on line %d\n", lineNumber);
            }
            lineNumber++;
        }
    }
//     public Sapling(String id, Point position, List<PImage> images, int health){
    public static void parseSapling(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = new Sapling(id, pt, imageStore.getImageList(SAPLING_KEY), health);
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }
//     public DudeNotFull(String id, Point position, List<PImage> images,int resourceLimit ,double actionPeriod,double animationPeriod){
    // new DudeNotFull(id, position, imageStore,

    // have to match the new Dude with the parameters that i given it in the class
    public static void parseDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new DudeNotFull(id, pt, imageStore.getImageList(DUDE_KEY), Integer.parseInt(properties[DUDE_LIMIT]), Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), 0) ;
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }
    //     public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
    public static void parseFairy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Entity entity = new Fairy(id, pt, imageStore.getImageList(FAIRY_KEY), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]), Double.parseDouble(properties[FAIRY_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }
//     public Tree(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health) {
    public static void parseTree(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Entity entity = new Tree(id, pt,  imageStore.getImageList(TREE_KEY), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Double.parseDouble(properties[TREE_ACTION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

//     public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
    public static void parseObstacle(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Entity entity = new Obstacle(id, pt, imageStore.getImageList(OBSTACLE_KEY), Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }
// public House(String id, Point position, List<PImage> images) {
    public static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            Entity entity = new House(id, pt, imageStore.getImageList(HOUSE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }

    public static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Entity entity = new Stump(id, pt, imageStore.getImageList(STUMP_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[PROPERTY_COL]), Integer.parseInt(properties[PROPERTY_ROW]));

            properties = properties.length == ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case OBSTACLE_KEY -> parseObstacle(this, properties, pt, id, imageStore);
                case DUDE_KEY -> parseDude(this, properties, pt, id, imageStore);
                case FAIRY_KEY -> parseFairy(this, properties, pt, id, imageStore);
                case HOUSE_KEY -> parseHouse(this, properties, pt, id, imageStore);
                case TREE_KEY -> parseTree(this, properties, pt, id, imageStore);
                case SAPLING_KEY -> parseSapling(this, properties, pt, id, imageStore);
                case STUMP_KEY -> parseStump(this, properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < this.numRows){
            int rows = Math.min(cells.length, this.numCols);
            for (int col = 0; col < rows; col++){
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    public void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> this.background = new Background[this.numRows][this.numCols];
                    case "Entities:" -> {
                        this.occupancy = new Entity[this.numRows][this.numCols];
                        this.entities = new HashSet<>();
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> this.numRows = Integer.parseInt(line);
                    case "Cols:" -> this.numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> this.parseBackgroundRow(line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> this.parseEntity(line, imageStore);
                }
            }
        }
    }

    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        this.parseSaveFile(saveFile, imageStore, defaultBackground);
        if(this.background == null){
            this.background = new Background[this.numRows][this.numCols];
            for (Background[] row : this.background)
                Arrays.fill(row, defaultBackground);
        }
        if(this.occupancy == null){
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public void removeEntity(EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents(entity);
        this.removeEntityAt(entity.getPosition());
    }

    public void moveEntity(EventScheduler scheduler, Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = this.getOccupant(pos);
            occupant.ifPresent(target -> this.removeEntity(scheduler, target));
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    /*
           Assumes that there is no entity currently occupying the
           intended destination cell.
        */
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

//    public Optional<Entity> findNearest(Point pos, List<EntityKind> kinds) {
//        List<Entity> ofType = new LinkedList<>();
//        for (EntityKind kind : kinds) {
//            for (Entity entity : this.entities) {
//                if (entity.getKind() == kind) {
//                    ofType.add(entity);
//                }
//            }
//        }
//
//        return Entity.nearestEntity(ofType, pos);
  //  }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0 && pos.x < this.numCols;
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = entity.log();
            if(log != null) list.add(log);
        }
        return list;
    }
}
