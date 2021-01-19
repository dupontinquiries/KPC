package media.kitchen.parkour.itemtype.token.capabilities;

public class TankCapability implements IParkourCapability {

    protected int num = 0;

    @Override
    public int getValue() {
        return num;
    }

    @Override
    public void setValue(int n) {
        num = n;
    }
}
