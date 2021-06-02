package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class Editor extends JFrame implements ActionListener, ItemListener {
    String text = "";
    JMenuItem exit;
    JMenuItem open;
    JMenuItem save;
    JTextArea edit;
    String[] fonts= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String currentFont = "Times New Roman";
    JCheckBox italic;
    JCheckBox bold;
    Font font = new Font(currentFont,Font.PLAIN,12);
    JTextField size;
    int currNum = 12;
    int fontStyle = 0;

    public Editor() throws HeadlessException {
      edit = new JTextArea();
      edit.setFont(font);
      add(edit);
      Choice choice = new Choice();
      for(String a:fonts){
          choice.add(a);
      }
      choice.addItemListener(this);
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      size = new JTextField("12");
      size.addActionListener(this);
      JMenu file = new JMenu("File");
      exit = new JMenuItem("Exit");
      open = new JMenuItem("Open");
      save = new JMenuItem("Save");
      save.addActionListener(this);
      open.addActionListener(this);
      exit.addActionListener(this);
      file.add(open);
      file.add(save);
      file.add(exit);
      bold = new JCheckBox("Bold");
      italic = new JCheckBox("Italic");
      italic.addActionListener(this);
      bold.addActionListener(this);

      menuBar.add(file);
      menuBar.add(italic);
      menuBar.add(bold);
      menuBar.add(choice);
      menuBar.add(new JLabel("Size"));
      menuBar.add(size);

      setSize(600,600);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);





    }
    private void changeStyleOfText(){
        if(italic.isSelected()&& bold.isSelected()){
            fontStyle = 3;
        }
        else if(italic.isSelected()){
           fontStyle = 2;
        }
        else if (bold.isSelected()){
            fontStyle = 1;
        }
        else {
            fontStyle = Font.PLAIN;
        }
        font = new Font(currentFont, fontStyle, currNum);

        edit.setFont(font);
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String operation = e.getActionCommand();
        if(e.getSource()==size){
            currNum = Integer.parseInt(operation);
            changeStyleOfText();

        }
        if(operation.equals("Italic")||operation.equals("Bold")){
         changeStyleOfText();
        }
        if(operation.equals("Open")){
            FileDialog dialog = new FileDialog(this,"Open", FileDialog.LOAD);
            dialog.setVisible(true);
            if(dialog.getFile()!= null){
                try{
                    FileInputStream inputStream = new FileInputStream(dialog.getDirectory()+dialog.getFile());
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    text = "";
                    while (true){
                        String line = bufferedReader.readLine();
                        if(line== null){
                            break;
                        }
                        text= text+line+"\n";
                    }
                    edit.setText(text);
                    inputStream.close();
                    inputStreamReader.close();
                    bufferedReader.close();
                }
                catch (IOException exception){

                }
            }
        }
        else if(operation.equals("Exit")){

        }
        else if (operation.equals("Save")) {
            try {
                FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
                dialog.setVisible(true);
                if(dialog!=null) {
                    FileOutputStream outputStream = new FileOutputStream(dialog.getDirectory() + dialog.getFile());
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    PrintWriter writer = new PrintWriter(outputStreamWriter);
                    System.out.println(edit.getText());
                    writer.println(edit.getText());
                    writer.close();
                    outputStreamWriter.close();
                    outputStream.close();
                    // obidve metody funguju len treba vzdy zavriet writera

                    //byte[] bytes = edit.getText().getBytes(StandardCharsets.UTF_8);
                   // outputStream.write(bytes);
                    //outputStream.close();
                }
            }
            catch (IOException exception){

            }

        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println(e.getItem());
        currentFont = (String) e.getItem();
        System.out.println(currentFont);
        changeStyleOfText();
    }
}
