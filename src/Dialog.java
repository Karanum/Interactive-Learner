import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    //private JButton buttonCancel;

    public Dialog() {
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)dim.getWidth()/3, (int)dim.getHeight()/3);
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setVisible(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

//        // call onCancel() when cross is clicked
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                onCancel();
//            }
//        });

//        // call onCancel() on ESCAPE
//        contentPane.registerKeyboardAction(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
       }

    private void onOK() {
        dispose();
    }

//    private void onCancel() {
//        // add your code here if necessary
//        dispose();
//    }

//    public static void main(String[] args) {
//        Dialog dialog = new Dialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        //System.exit(0);
//    }
}
