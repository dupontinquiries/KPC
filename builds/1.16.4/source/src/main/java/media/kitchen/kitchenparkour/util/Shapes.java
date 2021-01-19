package media.kitchen.kitchenparkour.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Vector;

public class Shapes {

    // media.kitchen.kitchenparkour.gen a list of coords for a sphere

    // helper for sphere
    private static double lengthSq(int x, int y, int z) {
        return x * x + y * y + z * z;
    }
    // !helper for sphere

    public static Vector<Coord> makeSphereVector(World world, BlockPos pos, double radius, boolean filled) {
        return makeSphereVector(world, pos.getX(), pos.getY(), pos.getZ(), radius, filled);
    }

    public static ArrayList<Coord> makeSphere(World world, BlockPos pos, double radius, boolean filled) {
        return makeSphere(world, pos.getX(), pos.getY(), pos.getZ(), radius, filled);
    }

    public static ArrayList<Coord> makeSphere(World world, Coord c, double radius, boolean filled) {
        return makeSphere(world, c.x, c.y, c.z, radius, filled);
    }

    public static ArrayList<Coord> makeSphere(World world, int _x, int _y, int _z, double radius, boolean filled) {
        ArrayList<Coord> list = new ArrayList<Coord>();
        radius += 0.5D;
        double radiusSq = radius * radius;
        // calcs
        double radius1Sq = (radius - 1.0D) * (radius - 1.0D);
        int ceilRadius = (int) Math.ceil(radius);
        for (int x = 0; x <= ceilRadius; x++) {
            for (int y = 0; y <= ceilRadius; y++) {
                for (int z = 0; z <= ceilRadius; z++) {
                    double dSq = lengthSq(x, y, z);
                    if (dSq > radiusSq) {
                        continue;
                    }
                    if ((!filled) && ((dSq < radius1Sq) || ((lengthSq(x + 1, y, z) <= radiusSq)
                            && (lengthSq(x, y + 1, z) <= radiusSq) && (lengthSq(x, y, z + 1) <= radiusSq)))) {
                        continue;
                    }

                    list.add(new Coord(x + _x, y + _y, z + _z));

                    list.add(new Coord(-x + _x, y + _y, z + _z));

                    list.add(new Coord(x + _x, -y + _y, z + _z));

                    list.add(new Coord(x + _x, y + _y, -z + _z));

                    list.add(new Coord(-x + _x, -y + _y, z + _z));

                    list.add(new Coord(x + _x, -y + _y, -z + _z));

                    list.add(new Coord(-x + _x, y + _y, -z + _z));

                    list.add(new Coord(-x + _x, -y + _y, -z + _z));
                }
            }
        }
        return list;
    }

    public static Vector<Coord> makeSphereVector(World world, int _x, int _y, int _z, double radius, boolean filled) {
        Vector<Coord> v = new Vector<>();
        radius += 0.5D;
        double radiusSq = radius * radius;
        // calcs
        double radius1Sq = (radius - 1.0D) * (radius - 1.0D);
        int ceilRadius = (int) Math.ceil(radius);
        for (int x = 0; x <= ceilRadius; x++) {
            for (int y = 0; y <= ceilRadius; y++) {
                for (int z = 0; z <= ceilRadius; z++) {
                    double dSq = lengthSq(x, y, z);
                    if (dSq > radiusSq) {
                        continue;
                    }
                    if ((!filled) && ((dSq < radius1Sq) || ((lengthSq(x + 1, y, z) <= radiusSq)
                            && (lengthSq(x, y + 1, z) <= radiusSq) && (lengthSq(x, y, z + 1) <= radiusSq)))) {
                        continue;
                    }

                    v.add(new Coord(x + _x, y + _y, z + _z));

                    v.add(new Coord(-x + _x, y + _y, z + _z));

                    v.add(new Coord(x + _x, -y + _y, z + _z));

                    v.add(new Coord(x + _x, y + _y, -z + _z));

                    v.add(new Coord(-x + _x, -y + _y, z + _z));

                    v.add(new Coord(x + _x, -y + _y, -z + _z));

                    v.add(new Coord(-x + _x, y + _y, -z + _z));

                    v.add(new Coord(-x + _x, -y + _y, -z + _z));
                }
            }
        }
        return v;
    }

    public static ArrayList<Coord> makeCube(int input) {
        ArrayList<Coord> list = new ArrayList<Coord>();
        int a = input / 2;
        int b = input / 2;
        int c = input / 2;

        for (int j = -1; j < input - 1; ++j) {
            for (int i = input / -2; i < input / 2.0 + .5; ++i) {
                for (int k = input / -2; k < input / 2.0 + .5; ++k) {
                    final Coord tmp = new Coord(i, j, k);
                    list.add(tmp);
                }
            }
        }

        return list;
    }
    // !media.kitchen.kitchenparkour.gen a list of coords for a sphere
}
