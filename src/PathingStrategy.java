import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

interface PathingStrategy
{
    /*
     * Returns a prefix of a path from the start point to a point within reach
     * of the end point.  This path is only valid ("clear") when returned, but
     * may be invalidated by movement of other entities.
     *
     * The prefix includes neither the start point nor the end point.
     */
    List<Point> computePath(Point start, Point end,
                            Predicate<Point> canPassThrough,
                            BiPredicate<Point, Point> withinReach,
                            Function<Point, Stream<Point>> potentialNeighbors);

    // when calling this function, you have a start end, some sort of predicate that returns a boolean
    // the secondary bipredicate checks if we are adjacent to the object
    // finally the function takes in a point and adds its neighbors to a stream
    static final Function<Point, Stream<Point>> CARDINAL_NEIGHBORS =
            point ->
                    Stream.<Point>builder()
    // adding in this order: north, south, west and east
                            .add(new Point(point.x, point.y - 1))  // North
                            .add(new Point(point.x, point.y + 1))  // South
                            .add(new Point(point.x - 1, point.y))  // West
                            .add(new Point(point.x + 1, point.y))  // East
//                            .add(new Point(point.x - 1, point.y - 1))  // Northwest
//                            .add(new Point(point.x + 1, point.y - 1))  // Northeast
//                            .add(new Point(point.x - 1, point.y + 1))  // Southwest
//                            .add(new Point(point.x + 1, point.y + 1))  // Southeast
                            .build();
}