package swsecurity;

import javax.swing.SwingUtilities;
import swsecurity.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}