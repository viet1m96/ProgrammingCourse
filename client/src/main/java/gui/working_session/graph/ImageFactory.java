package gui.working_session.graph;

import gui.utilities.tools.AlertUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ImageFactory {

    public static ImageView createImage(String path, String creator) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Color fillColor = ColorFactory.getColor(creator);

            int startX = 100;
            int startY = 220;

            int targetColor = img.getRGB(startX, startY);
            floodFill(img, startX, startY, targetColor, fillColor.getRGB());
            WritableImage fxImage = SwingFXUtils.toFXImage(img, null);
            return new ImageView(fxImage);
        } catch (IOException e) {
            AlertUtil.showErrorAlert("Loading Error", "Color Image", (Stage)null);
            return null;
        }
    }


    private static void floodFill(BufferedImage img, int x, int y, int targetColor, int fillColor) {
        int width = img.getWidth();
        int height = img.getHeight();
        if (targetColor == fillColor) return;

        boolean[][] visited = new boolean[width][height];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});

        while (!q.isEmpty()) {
            int[] p = q.remove();
            int cx = p[0], cy = p[1];
            if (cx < 0 || cx >= width || cy < 0 || cy >= height) continue;
            if (visited[cx][cy]) continue;

            int currentColor = img.getRGB(cx, cy);
            if (currentColor != targetColor) continue;

            img.setRGB(cx, cy, fillColor);
            visited[cx][cy] = true;

            q.add(new int[]{cx + 1, cy});
            q.add(new int[]{cx - 1, cy});
            q.add(new int[]{cx, cy + 1});
            q.add(new int[]{cx, cy - 1});
        }
    }
}
