package net.rinorclient.client.api.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import net.rinorclient.client.util.Globals;

import org.joml.Matrix4f;

/**
 * @author linus
 * @since 1.0
 */
public class RenderManager implements Globals {
    //
    public static final Tessellator TESSELLATOR = RenderSystem.renderThreadTesselator();
    //public static final BufferBuilder BUFFER = TESSELLATOR.getBuffer();



    /**
     * @param matrices
     * @param text
     * @param pos
     */
    public static void renderSign(MatrixStack matrices, String text, Vec3d pos) {
        renderSign(matrices, text, pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * @param matrices
     * @param text
     * @param x1
     * @param x2
     * @param x3
     */
    public static void renderSign(MatrixStack matrices, String text,
                                  double x1, double x2, double x3) {
    }

    /**
     * @param box
     * @return
     */
    public static boolean isFrustumVisible(Box box) {
        return true;
    }

    /**
     * @param matrices
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     */
    public static void rect(MatrixStack matrices, double x1, double y1,
                            double x2, double y2, int color) {
        rect(matrices, x1, y1, x2, y2, 0.0, color);
    }

    /**
     * @param matrices
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param z
     * @param color
     */
    public static void rect(MatrixStack matrices, double x1, double y1,
                            double x2, double y2, double z, int color) {
        x2 += x1;
        y2 += y1;
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        double i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        float f = (float) ColorHelper.Argb.getAlpha(color) / 255.0f;
        float g = (float) ColorHelper.Argb.getRed(color) / 255.0f;
        float h = (float) ColorHelper.Argb.getGreen(color) / 255.0f;
        float j = (float) ColorHelper.Argb.getBlue(color) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder BUFFER = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        BUFFER.vertex(matrix4f, (float) x1, (float) y1, (float) z).color(g, h, j, f);
        BUFFER.vertex(matrix4f, (float) x1, (float) y2, (float) z).color(g, h, j, f);
        BUFFER.vertex(matrix4f, (float) x2, (float) y2, (float) z).color(g, h, j, f);
        BUFFER.vertex(matrix4f, (float) x2, (float) y1, (float) z).color(g, h, j, f);
        BufferRenderer.drawWithGlobalProgram(BUFFER.end());
        RenderSystem.disableBlend();
    }

    public static void renderText(DrawContext context, String text, float x, float y, int color) {
        context.drawText(mc.textRenderer, text, (int) x, (int) y, color, true);
    }

    public static int textWidth(String text) {
        return mc.textRenderer.getWidth(text);
    }
}