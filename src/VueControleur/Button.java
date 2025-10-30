package VueControleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Button extends JButton {

    private final ImageIcon hover;
    private final ImageIcon base;

    public Button(String text,Image im) {
        super(text, new ImageIcon(im));
        setAlignmentX(JButton.CENTER_ALIGNMENT);
        setOpaque(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder());
        base = new ImageIcon(
                ImagesGetter.getImageBackGround(4).getScaledInstance(
                        10*VC.COEF+5, 2*VC.COEF+5, java.awt.Image.SCALE_SMOOTH));
        hover = new ImageIcon(
                ImagesGetter.getImageBackGround(5).getScaledInstance(
                        10*VC.COEF+5, 2*VC.COEF+5, java.awt.Image.SCALE_SMOOTH));

        setForeground(Color.WHITE);
        setFont(new Font("Comic Sans MS",Font.BOLD,20));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setIcon(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setIcon(base);
            }
        });
    }
}
