package chat;

import javax.swing.*;
import java.io.File;

public class choosefile {
    public File choose(){
        JFileChooser jf=new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.showDialog(new JLabel(), "选择");
        int value=jf.showSaveDialog(null);
        if(value==JFileChooser.CANCEL_OPTION)
            return null;
        File file=jf.getSelectedFile();
        return file;
    }
    public File choosedir(){
        JFileChooser jf=new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jf.showDialog(new JLabel(), "选择");
        int value=jf.showSaveDialog(null);
        if(value==JFileChooser.CANCEL_OPTION)
            return null;
        File file=jf.getSelectedFile();
        return file;
    }
}
