package media.kitchen.kitchenparkour.serializers;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Hashtable;

public class BlockStateSerializer<BlockState> implements IDataSerializer {

    HashSet<BlockState> blockstates = new HashSet<BlockState>();

    @Override
    public void write(PacketBuffer buf, Object value) {
        buf.alloc();
    }

    @Override
    public BlockState read(PacketBuffer buf) {
        return null;
    }

    /*
    @Override
    public DataParameter createKey(int id) {
        return null;
    }
     */

    @Override
    public BlockState copyValue(Object value) {
        return null;
    }

}
