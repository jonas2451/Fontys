package TLI.position;

public class Position implements Comparable {

    int x = 0;
    int y = 0;
    double rotation = 270;
    CardinalDirection facing = CardinalDirection.EAST; //FIXME not nice

    public Position(int x, int y, double rotation, CardinalDirection facing) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.facing = facing;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position other) {
        this.x = other.getX();
        this.y = other.getY();
        this.rotation = other.getRotation();
        this.facing = other.getFacing();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void increase(int value) {
        switch (this.facing) {
            case NORTH:
                this.setY(this.getY() - value);
                break;
            case NORTHEAST:
                this.setX(this.getX() + value/2);
                this.setY(this.getY() - value/2);
                break;
            case EAST:
                this.setX(this.getX() + value);
                break;
            case SOUTHEAST:
                this.setX(this.getX() + value/2);
                this.setY(this.getY() + value/2);
                break;
            case SOUTH:
                this.setY(this.getY() + value);
                break;
            case SOUTHWEST:
                break;
            case WEST:
                this.setX(this.getX() - value);
                break;
            case NORTHWEST:
                break;
        }
    }

    public Position addX(int x) {
        return new Position(this.x + x, this.y, this.rotation, this.facing);
    }

    public Position addY(int y) {
        return new Position(x, this.y + y, this.rotation, this.facing);
    }

    public CardinalDirection getFacing() {
        return facing;
    }

    public void setFacing(CardinalDirection facing) {
        this.facing = facing;
    }

    @Override
    public int compareTo(Object o) {
        Position that = (Position) o;
        return (this.getX() - that.getX()) + (this.getY() - that.getY());
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}