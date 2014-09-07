
import java.awt.*;
import javax.swing.*;
public class gb extends JFrame{
      private static final Insets insets = new Insets(0, 0, 0, 0);
      
      public static void main(String[] args){
            new gb("GridBagLayout Sample");
      }
      
      public gb(String str){
            super(str);
            
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            
            JButton btn1 = new JButton("One");
            JButton btn2 = new JButton("Two");
            JButton btn3 = new JButton("Three");
            JButton btn4 = new JButton("Four");
            JButton btn5 = new JButton("Five");
            JButton btn6 = new JButton("Six");
            JButton btn7 = new JButton("Seven");
            
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            setLayout(gbl);
            
            addGrid(gbl, gbc, btn1, 0, 0, 1, 1, 0, 0);
            addGrid(gbl, gbc, btn2, 1, 0, 1, 1, 1, 0);
            addGrid(gbl, gbc, btn3, 2, 0, 1, 1, 0, 0);
            addGrid(gbl, gbc, btn4, 0, 1, 2, 1, 1, 1);
            addGrid(gbl, gbc, btn5, 2, 1, 1, 2, 0, 1);
            addGrid(gbl, gbc, btn6, 0, 2, 1, 1, 0, 0);
            addGrid(gbl, gbc, btn7, 1, 2, 1, 1, 1, 0);
            
            pack();
            setVisible(true);
      }
      
      private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c,  
                  int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty) {
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            gbc.weightx = weightx;
            gbc.weighty = weighty;
            gbl.setConstraints(c, gbc);
            add(c);
      }
}