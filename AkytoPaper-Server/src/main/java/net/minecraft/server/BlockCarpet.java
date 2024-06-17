package net.minecraft.server;

import java.util.List;

public class BlockCarpet extends Block {

    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);

    protected BlockCarpet() {
        super(Material.WOOL);
        this.j(this.blockStateList.getBlockData().set(BlockCarpet.COLOR, EnumColor.WHITE));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.a(true);
        this.a(CreativeModeTab.c);
        this.b(0);
    }

    public MaterialMapColor g(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).e();
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public void j() {
        this.b(0);
    }

    public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
        this.b(0);
    }

    protected void b(int i) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    // SportPaper start - No height on carpet in feet height, avoid 1.7 players glitching
    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
        if (entity instanceof EntityHuman && blockposition.getY() == (int) entity.getBoundingBox().b) {
            return;
        }
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
    }
    // SportPaper end

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && this.e(world, blockposition);
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.e(world, blockposition)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
            return false;
        } else {
            return true;
        }
    }

    private boolean e(World world, BlockPosition blockposition) {
        return !world.isEmpty(blockposition.down());
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).getColorIndex();
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockCarpet.COLOR, EnumColor.fromColorIndex(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockCarpet.COLOR});
    }
}
