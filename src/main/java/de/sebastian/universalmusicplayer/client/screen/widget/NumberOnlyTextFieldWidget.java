package de.sebastian.universalmusicplayer.client.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class NumberOnlyTextFieldWidget extends TextFieldWidget {
    private final int minValue;
    private final int maxValue;

    public NumberOnlyTextFieldWidget(int x, int y, int width, int height, Text placeholder, int minValue, int maxValue) {
        super(MinecraftClient.getInstance().textRenderer, x, y, width, height, placeholder);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        // Allow only digits
        if (Character.isDigit(chr)) {
            String newText = this.getText() + chr;
            if (isValidRange(newText)) {
                return super.charTyped(chr, keyCode);
            }
            return false;
        }
        return false; // Block all other characters
    }

    @Override
    public void setText(String text) {
        // Validate the text and clamp it within the range
        if (isValidRange(text)) {
            super.setText(text);
        } else {
            int clampedValue = Math.max(minValue, Math.min(maxValue, parseIntSafely(text, minValue)));
            super.setText(String.valueOf(clampedValue));
        }
    }

    private boolean isValidRange(String input) {
        // Allow empty input temporarily (useful while editing)
        if (input.isEmpty()) return true;

        // Parse the input and check if it falls within the allowed range
        int value = parseIntSafely(input, minValue);
        return value >= minValue && value <= maxValue;
    }

    private int parseIntSafely(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getIntValue() {
        return parseIntSafely(this.getText(), minValue);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Allow the default behavior for backspace and navigation keys
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
