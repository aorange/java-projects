
/**
 * Main POJO
 * @author Allen Tang
 */
public class GridInfo {
    private int dimension;
    private Point start;
    private Point end;
    private Point [] obstacles;

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(Point[] obstacles) {
        this.obstacles = obstacles;
    }
}
