
/**
 * Our fancy way of representing the grid
 * @author Allen Tang
 */
public class GridNode {

    private Point coord;
    private int heuristic;
    private Point parent = null;

    public GridNode(Point coord, int heuristic){
        this.coord = coord;
        this.heuristic = heuristic;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public Point getParent() {
        return parent;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }
}
