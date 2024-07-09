package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutEntityVelocity implements Packet<PacketListenerPlayOut> {

    private int a;
    private int b;
    private int c;
    private int d;

    public PacketPlayOutEntityVelocity() {
    }

    public PacketPlayOutEntityVelocity(Entity var1) {
        this(var1.getId(), var1.motX, var1.motY, var1.motZ);
    }

    public PacketPlayOutEntityVelocity(int var1, double d0, double d1, double d2) {
        this.a = var1;
        double d3 = 3.9;
        this.b = (int) (MathHelper.a(d0, -d3, d3) * 8000.0D);
        this.c = (int) (MathHelper.a(d1, -d3, d3) * 8000.0D);
        this.d = (int) (MathHelper.a(d2, -d3, d3) * 8000.0D);
    }

    public void a(PacketDataSerializer var1) throws IOException {
        this.a = var1.e();
        this.b = var1.readShort();
        this.c = var1.readShort();
        this.d = var1.readShort();
    }

    public void b(PacketDataSerializer var1) throws IOException {
        var1.b(this.a);
        var1.writeShort(this.b);
        var1.writeShort(this.c);
        var1.writeShort(this.d);
    }

    public int getID() { return this.a; }

    public void a(PacketListenerPlayOut var1) {
        var1.a(this);
    }
}
