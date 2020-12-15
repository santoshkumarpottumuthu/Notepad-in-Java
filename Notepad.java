import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.Buffer;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area;
    JScrollPane pane;
    String text ="";
    public Notepad()
    {
        setBounds(0,0,1000,1000);
        JMenuBar menubar = new JMenuBar();
        JMenu file =  new JMenu("File");
        //Creating Menu Items
        JMenuItem newfile = new JMenuItem("New");
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        file.add(newfile);
        newfile.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        file.add(save);
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        file.add(print);
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        file.add(exit);
        exit.addActionListener(this);

        //Menu and list for Edit
        JMenu edit =  new JMenu("Edit");

        JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        edit.add(undo);
        undo.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        edit.add(cut);
        cut.addActionListener(this);

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        edit.add(copy);
        copy.addActionListener(this);

        JMenuItem del = new JMenuItem("Delete");
        del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        edit.add(del);
        del.addActionListener(this);
    // End of Edit menu

        JMenu help =  new JMenu("Help");

        JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        help.add(about);
        help.addActionListener(this);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);
        add(menubar);
        setJMenuBar(menubar);


        area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SAN SERIF",Font.PLAIN, 20));
        pane = new JScrollPane(area); //scroll bar
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane,BorderLayout.CENTER);



    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getActionCommand().equals("New"))
        {
            //new Notepad();
            area.setText("");
        }
        else if(ae.getActionCommand().equals("Open"))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setApproveButtonText("Open");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt Files are acceptable","txt");
            chooser.addChoosableFileFilter(restrict);

            int act = chooser.showOpenDialog(this);
            if(act!= JFileChooser.APPROVE_OPTION)
                return;
            else{
                File file = chooser.getSelectedFile();
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    area.read(br,null);
                }
                catch (Exception e) { }
            } }

        else if(ae.getActionCommand().equals("Save")) {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("Save");
            int action = saveas.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File fname = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter outputfile = null;
            try {
                outputfile = new BufferedWriter(new FileWriter(fname));
                area.write(outputfile);
            }
            catch (Exception e){ }
        }
        else if(ae.getActionCommand().equals("Print"))
        {
            try{
                area.print();
            }
            catch (Exception e){}
        }
        else if(ae.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        else if(ae.getActionCommand().equals("Copy"))
        {
            try{
                text = area.getSelectedText(); }
            catch(Exception e){}
        }
        else if(ae.getActionCommand().equals("Paste"))
        {
            area.insert(text, area.getCaretPosition());
        }
        else if(ae.getActionCommand().equals("Cut"))
        {
            text = area.getSelectedText();
            area.replaceRange(" ",area.getSelectionStart(),area.getSelectionEnd());
        }
        else if(ae.getActionCommand().equals("Delete"))
        {
            area.replaceRange("",area.getSelectionStart(),area.getSelectionEnd());
        }
    }
    public static void main(String...sk)
    {
        new Notepad().setVisible(true);
    }
}
