package thaumic.tinkerer.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import thaumcraft.api.aspects.Aspect;

public class ModelVisweaver extends ModelBase {

    public ModelRenderer base;
    public ModelRenderer top;
    public ModelRenderer pillar1;
    public ModelRenderer pillar2;
    public ModelRenderer pillar3;
    public ModelRenderer pillar4;
    public ModelRenderer orbBase;

    public ModelRenderer orbIgnis;
    public ModelRenderer orbAqua;
    public ModelRenderer orbAer;
    public ModelRenderer orbTerra;
    public ModelRenderer orbOrdo;
    public ModelRenderer orbPerditio;

    public ModelVisweaver() {
        this.textureWidth = 64;
        this.textureHeight = 36;

        this.pillar1 = new ModelRenderer(this, 0, 0);
        this.pillar1.setRotationPoint(-7.0F, 14.0F, 5.0F);
        this.pillar1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.pillar2 = new ModelRenderer(this, 0, 0);
        this.pillar2.setRotationPoint(5.0F, 14.0F, 5.0F);
        this.pillar2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.pillar3 = new ModelRenderer(this, 0, 0);
        this.pillar3.setRotationPoint(5.0F, 14.0F, -7.0F);
        this.pillar3.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.pillar4 = new ModelRenderer(this, 0, 0);
        this.pillar4.setRotationPoint(-7.0F, 14.0F, -7.0F);
        this.pillar4.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);

        this.top = new ModelRenderer(this, 0, 0);
        this.top.setRotationPoint(-8.0F, 12.0F, -8.0F);
        this.top.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);

        this.base = new ModelRenderer(this, 0, 18);
        this.base.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);

        this.orbBase = new ModelRenderer(this, 48, 0);
        this.orbBase.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbBase.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);

        this.orbIgnis = new ModelRenderer(this, 56, 4);
        this.orbIgnis.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbIgnis.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.orbAqua = new ModelRenderer(this, 56, 12);
        this.orbAqua.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbAqua.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.orbAer = new ModelRenderer(this, 56, 8);
        this.orbAer.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbAer.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.orbTerra = new ModelRenderer(this, 48, 8);
        this.orbTerra.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbTerra.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.orbOrdo = new ModelRenderer(this, 48, 12);
        this.orbOrdo.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbOrdo.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.orbPerditio = new ModelRenderer(this, 48, 4);
        this.orbPerditio.setRotationPoint(-1.0F, 8.0F, -1.0F);
        this.orbPerditio.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
    }

    public void render(boolean isWorking, int tickCounter, Aspect cvType) {
        final float scale = 1F / 16F;

        float xyRotate = tickCounter / 20F;
        float zRotate = tickCounter / 100F;

        setRotateAngle(orbBase, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbIgnis, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbAqua, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbAer, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbTerra, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbOrdo, xyRotate, xyRotate, zRotate);
        setRotateAngle(orbPerditio, xyRotate, xyRotate, zRotate);

        if (!isWorking) {
            this.orbBase.render(scale);
        } else {
            if (cvType == Aspect.AIR) {
                this.orbAer.render(scale);
            } else if (cvType == Aspect.FIRE) {
                this.orbIgnis.render(scale);
            } else if (cvType == Aspect.ORDER) {
                this.orbOrdo.render(scale);
            } else if (cvType == Aspect.ENTROPY) {
                this.orbPerditio.render(scale);
            } else if (cvType == Aspect.WATER) {
                this.orbAqua.render(scale);
            } else if (cvType == Aspect.EARTH) {
                this.orbTerra.render(scale);
            }
        }

        this.pillar3.render(scale);
        this.top.render(scale);
        this.pillar4.render(scale);
        this.base.render(scale);
        this.pillar1.render(scale);
        this.pillar2.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
