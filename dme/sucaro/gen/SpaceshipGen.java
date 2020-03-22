// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.gen;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import dme.sucaro.block.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class SpaceshipGen implements IWorldGenerator {
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
			final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		// final PlacementSettings settings = new
		// PlacementSettings().setRotation(Rotation.NONE);
		switch (world.getWorldInfo().getVanillaDimension()) {
		case -1: {
			this.generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
		case 0: {
			this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
		case 1: {
			this.generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
		}
	}

	private ArrayList<Coord> gen3DRect(int l, int h, int w) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		int a = l;
		int b = w;
		int c = h;
		while (l > -1) {
			l--;
			while (w > -1) {
				w--;
				while (h > -1) {
					h--;
					//System.out.println("lwh = " + l + ", " + w + ", " + h);
					final Coord tmp = new Coord(l, h, w);
					list.add(tmp);
				}
				h = c;
			}
			w = b;
		}
		// System.out.println("array size = " + list.size());
		return list;
	}
	
	private ArrayList<Coord> gen3DPyramidUpsideDown(int l, int h, int w) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		int a = l;
		int b = w;
		int c = h;
		int pin = Math.min(l, w);
		while (l > -1 - pin) {
			l--;
			while (w > -1 - pin) {
				w--;
				while (h > -1) {
					h--;
					//System.out.println("lwh = " + l + ", " + w + ", " + h);
					final Coord tmp = new Coord(l + pin / 2, h + pin / 2, w);
					list.add(tmp);
				}
				h = c;
			}
			pin--;
			w = b;
		}
		// System.out.println("array size = " + list.size());
		return list;
	}
	
	private double magDist3D(double a, double b, double c, double x, double y, double z) {
		return Math.pow((Math.pow(a - x, 2) + Math.pow(b - y, 2) + Math.pow(c - z, 2)), .5);
	}
	
	private ArrayList<Coord> gen3DSphere(int l, int h, int w) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		int a = l;
		int b = w;
		int c = h;
		while (l > -1 ) {
			l--;
			while (w > -1) {
				w--;
				while (h > -1) {
					h--;
					//System.out.println("lwh = " + l + ", " + w + ", " + h);
					final Coord tmp = new Coord(l, h, w);
					if (magDist3D(0, 0, 0, tmp.x, tmp.y, tmp.z) <= w) {
						list.add(tmp);
					}
				}
				h = c;
			}
			w = b;
		}
		// System.out.println("array size = " + list.size());
		return list;
	}
	
	private ArrayList<Coord> basic3D(int l, int h, int w) {
		ArrayList<Coord> list = new ArrayList<Coord>();
		list.add(new Coord(l, h, w));
		return list;
	}

	private void generateEnd(final World world, final Random random, final int i, final int j) {
	}

	private void generateSurface(final World world, final Random random, final int i, final int j) {
		double decider = random.nextDouble();
		if (decider < .05) {
			int num = 2; // 2;
			int ceil = 340; // 240;
			for (int k = 0; k < num; ++k) {
				if (random.nextDouble() < 0.075) { // .05
					final int fX = i + random.nextInt(16);
					final int fY = random.nextInt(ceil);
					final int fZ = j + random.nextInt(16);
					genSpaceShip(world, random, fX, fY, fZ);
				}
			}
		} else if (decider < .11) {
			int num = 12;
			int ceil = 340;
			for (int k = 0; k < num; ++k) {
				if (random.nextDouble() < 0.05) {
					final int fX = i + random.nextInt(16);
					final int fY = random.nextInt(ceil);
					final int fZ = j + random.nextInt(16);
					genSpaceShip(world, random, fX, fY, fZ);
				}
			}
		}
		else {
			//do nothing
		}
	}

	private void generateNether(final World world, final Random random, final int i, final int j) {

	}

	public boolean genSpaceShip(World world, Random random, int x, int y, int z) {
		Block block;

		do {
			Block test = world.getBlock(x, y, z);
			if (!test.isAir(world, x, y, z) && test.isBlockNormalCube())
				break;
			y--;
		} while (y > 1);
		int lift = -4;
		if (y < 1 + 4) {
			return false;
		} else {
			y++;
			
			int thickness = random.nextInt(3) + random.nextInt(3) + 3;
			ArrayList<Coord> surface = this.gen3DSphere(thickness, thickness, thickness);
			double val = random.nextDouble();
				for (Coord coord : surface) {
					if (val < 0.4) {
						//do nothing
					} else if (val < .6) {
						world.setBlock(x + coord.getX(), y + coord.getY() + lift, z + coord.getZ(), BlockManager.spaceship);
					} else {
						world.setBlock(x + coord.getX(), y + coord.getY() + lift, z + coord.getZ(), BlockManager.brokenSpaceship);
					}
				}
				return true;
		}
	}
	
	public boolean generateSingle(World world, Random random, int x, int y, int z) {
		Block block;

		do {
			Block test = world.getBlock(x, y, z);
			if (!test.isAir(world, x, y, z) && test.isBlockNormalCube())
				break;
			y--;
		} while (y > 1);

		if (y < 1) {
			return false;
		} else {
			y++;
			int lift = 10;
			int thickness = random.nextInt(3) + random.nextInt(3) + 1;
			ArrayList<Coord> surface = this.basic3D(thickness, random.nextInt(2) + random.nextInt(2) + 3, thickness); //thickness, random.nextInt(2) + random.nextInt(2) + 3, thickness
			for (Coord coord : surface) {
				if (!world.isAirBlock(x + coord.getX(), y + coord.getY() + lift, z + coord.getZ())) {
					return false;
				}
			}	
				for (Coord coord : surface) {
					if (random.nextDouble() < 0.20) {
						world.setBlock(x + coord.getX(), y + coord.getY() + lift, z + coord.getZ(), Blocks.gold_block);
					} else {
						world.setBlock(x + coord.getX(), y + coord.getY() + lift, z + coord.getZ(), BlockManager.gravOre);
					}
				}
				return true;
		}
	}
}