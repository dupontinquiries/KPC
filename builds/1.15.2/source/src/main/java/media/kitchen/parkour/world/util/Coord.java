package media.kitchen.parkour.world.util;

import java.util.ArrayList;
import java.util.List;

public class Coord {

    int x = 0;
    int y = 0;
    int z = 0;

    private ArrayList<Integer> coordinates = new ArrayList<Integer>();

    public Coord(final int x, final int y, final int z) {
        this.x = x;
        this.coordinates.add(x);
        this.y = y;
        this.coordinates.add(y);
        this.z = z;
        this.coordinates.add(z);
        //System.out.println("xyz _ " + x + ", " + y + ", " + z); //problem with coord.x returning same for all in list
    }

    public List<Integer> getCoords(){
        return this.coordinates;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}

