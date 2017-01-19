import javafx.stage.FileChooser;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by Dylan on 18-1-2017.
 */
public class GUI {

    private JButton button1;
    private JPanel panel1;
    private JButton startLearningButton;
    private JTabbedPane tabbedPane1;
    private JLabel label1;
    private JLabel pathMessage;
    private static String directoryPath;


    public GUI() {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                System.out.println(info.getName());
//                if("Metal".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        JFrame frame = new JFrame("Interactive Learner");
        label1.setText("<html>Important! The folder selected must contain the train folders seperated. <br> " +
                "Example: Train/M and Train/F where Train is the selected folder<html>");
        pathMessage.setText("no train file specified");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)dim.getWidth()/2, (int)dim.getHeight()/2);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //Dialog dialog = new Dialog();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    directoryPath = fileChooser.getSelectedFile().getAbsolutePath();
                    pathMessage.setText("selected folder: " + fileChooser.getSelectedFile().getName());

                    //System.out.println(selectedFile);
                }

            }
        });
        startLearningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(directoryPath == null) {
                    Dialog dialog = new Dialog();

                } else {
                    DocumentLearner documentLearner = new DocumentLearner();
                    documentLearner.createList();
                }
            }
        });
    }

    public static String getDirectoryPathPath() {
        return directoryPath;
    }

}
