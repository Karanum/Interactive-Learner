import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * Created by Dylan on 18-1-2017.
 */
public class GUI {

    private JButton insertData;
    private JPanel panel1;
    private JButton startLearningButton;
    private JTabbedPane tabbedPane1;
    private JLabel warningLabel;
    private JLabel pathMessage;
    private JLabel classNames;
    private static String directoryPath;


    public GUI() {

        JFrame frame = new JFrame("Interactive Learner");
        warningLabel.setText("<html>Important! The folder selected must contain the train folders seperated. <br> " +
                "Example: Train/M and Train/F where Train is the selected folder.<html>");
        pathMessage.setText("No train folder specified. Please select a folder first ");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)dim.getWidth()/2, (int)dim.getHeight()/2);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);


        insertData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //Dialog dialog = new Dialog();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    directoryPath = fileChooser.getSelectedFile().getAbsolutePath();
                    String folders = "<html>The folder contains the following test classes: <br>";
                    for (File folder : fileChooser.getSelectedFile().listFiles()) {
                        folders += folder.getName() + " <br><html>";

                    }
                    //pathMessage.setText("selected folder: " + fileChooser.getSelectedFile().getName());
                    pathMessage.setText("selected folder: " + fileChooser.getSelectedFile().getAbsolutePath());
                    classNames.setText(folders);

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
