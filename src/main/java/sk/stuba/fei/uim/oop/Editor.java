package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Editor extends JFrame implements ActionListener {
    String text = "";
    int position = 0;
    JMenuItem exit;
    JMenuItem open;
    JMenuItem save;
    TextArea edit;

    public Editor() throws HeadlessException {
      edit = new TextArea();
      add(edit);


      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
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
      menuBar.add(file);



      setSize(600,600);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);





    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String operation = e.getActionCommand();
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


}
