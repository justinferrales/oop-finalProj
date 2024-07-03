public class Node implements Comparable<Node>{
    private final Point point;
    private final Node prev;
    private final int G;
    private final int H;
    private final int F;
    public Point getPoint(){return this.point;}

    public Node getPrev(){return this.prev;}

    public int getGscore() {
        return G;
    }

    public int getHscore() {
        return H;
    }

    public int getFScore(){return F;}


    public Node(Point point, Node prev, int GVal, int HVal, int FVal){
        this.point = point;
        this.prev = prev;
        this.G = GVal;
        this.H = HVal;
        this.F = FVal;
    }
    @Override
    public int compareTo(Node other){
        return this.F - other.F;
    }
}


