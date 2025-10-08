package gui.working_session.graph;



import java.awt.*;
import java.util.*;

public class ColorFactory {

    private static final Map<String, Color> colors = new HashMap<>();
    private static final Set<Color> colorSet = new HashSet<>();
    private static final Random random = new Random();

    public static Color getColor(String colorName) {
        if (colors.containsKey(colorName)) {
            return colors.get(colorName);
        } else {
            Color newColor = generateUniqueRandomColor();
            colors.put(colorName, newColor);
            colorSet.add(newColor);
            return newColor;
        }

    }

    private static Color generateUniqueRandomColor() {
        Color newColor;
        do {
            float hue = random.nextFloat();
            float saturation = 0.7f + random.nextFloat() * 0.3f;
            float brightness = 0.7f + random.nextFloat() * 0.3f;
            newColor = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        } while (colorSet.contains(newColor));

        return newColor;
    }
}
