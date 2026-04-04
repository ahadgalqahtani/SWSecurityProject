package swsecurity.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UiUtil {

    private UiUtil() {
    }

    public static GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }
}