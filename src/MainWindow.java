import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class MainWindow extends WindowAdapter
{
    private JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16;
    JFrame f;
    JProgressBar progress;
    Handler handler;
    JPanel panel=new JPanel();
    public MainWindow(String title)
    {
        f=new JFrame();
        f.setTitle(title);
        f.addWindowListener(this);
        panel.setLayout(new GridLayout(4,4,40,40));
        f.setLayout(new GridLayout());
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        b1=new JButton("cp");
        b2=new JButton("rm");
        b3=new JButton("mv");
        b4=new JButton("search");
        b5=new JButton("chmod");
        b6=new JButton("count");
        b7=new JButton("filesort");
        b8=new JButton("pwd");
        b9=new JButton("compress");
        b10=new JButton("date");
        b11=new JButton("cd");
        b12=new JButton("ls");
        b13=new JButton("extract");
        b14=new JButton("open");
        b15=new JButton("encrypt");
        b16=new JButton("decrypt");
        f.setVisible(true);
        f.setResizable(true);
        f.setSize(1000,800);
        f.setResizable(true);
        f.setLocationRelativeTo(null);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(b10);
        panel.add(b11);
        panel.add(b12);
        panel.add(b13);
        panel.add(b14);
        panel.add(b15);
        panel.add(b16);
        f.add(panel);
        b1.setSize(10,10);
        b2.setSize(10,10);
        b3.setSize(10,10);
        b4.setSize(10,10);
        b5.setSize(10,10);
        b6.setSize(10,10);
        b7.setSize(10,10);
        b8.setSize(10,10);
        b9.setSize(10,10);
        b10.setSize(10,10);
        b11.setSize(10,10);
        b12.setSize(10,10);
        b13.setSize(10,10);
        b14.setSize(10,10);
        b15.setSize(10,10);
        b16.setSize(10,10);
        handler = new Handler();
        b1.addActionListener(handler);
        b2.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
        b5.addActionListener(handler);
        b6.addActionListener(handler);
        b7.addActionListener(handler);
        b8.addActionListener(handler);
        b9.addActionListener(handler);
        b10.addActionListener(handler);
        b11.addActionListener(handler);
        b12.addActionListener(handler);
        b13.addActionListener(handler);
        b14.addActionListener(handler);
        b15.addActionListener(handler);
        b16.addActionListener(handler);
    }
    public void windowClosing(WindowEvent e)
    {
        int a=JOptionPane.showConfirmDialog(f, "Do you want to exit the terminal?", "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(a==JOptionPane.YES_OPTION)
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void showMessage(String msg)
    {
        JOptionPane.showMessageDialog(null, msg);
    }
    private class Handler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand()=="encrypt")
            {
                try
                {
                    String key=JOptionPane.showInputDialog("Enter the key: ");
                    String inppath=JOptionPane.showInputDialog("Enter the input file path: ");
                    String oppath=JOptionPane.showInputDialog("Enter the output file path: ");
                    File inpfile=new File(inppath);
                    File opfile=new File(oppath);
                    String x=utf8truncate(key,16);
                    System.out.println("Key is: "+x);
                    Key sec=new SecretKeySpec(x.getBytes(),"AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE, sec);
                    FileInputStream inputStream = new FileInputStream(inpfile);
                    byte[] inputBytes = new byte[(int) inpfile.length()];
                    inputStream.read(inputBytes);
                    byte[] outputBytes = cipher.doFinal(inputBytes);
                    FileOutputStream outputStream = new FileOutputStream(opfile);
                    outputStream.write(outputBytes);
                    inputStream.close();
                    outputStream.close();
                    JOptionPane.showMessageDialog(null,"Successful encryption\n"+"Key is: "+x);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
            if(e.getActionCommand()=="decrypt")
            {
                try
                {
                    String key=JOptionPane.showInputDialog("Enter the key: ");
                    String inppath=JOptionPane.showInputDialog("Enter the input file path: ");
                    String oppath=JOptionPane.showInputDialog("Enter the output file path: ");
                    File inpfile=new File(inppath);
                    File opfile=new File(oppath);
                    Key sec=new SecretKeySpec(key.getBytes(),"AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, sec);
                    FileInputStream inputStream = new FileInputStream(inpfile);
                    byte[] inputBytes = new byte[(int) inpfile.length()];
                    inputStream.read(inputBytes);
                    byte[] outputBytes = cipher.doFinal(inputBytes);
                    FileOutputStream outputStream = new FileOutputStream(opfile);
                    outputStream.write(outputBytes);
                    inputStream.close();
                    outputStream.close();
                    JOptionPane.showMessageDialog(null,"Successful decryption");
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
            if(e.getActionCommand()=="open")
            {
                String filename = JOptionPane.showInputDialog("Enter the file path: ");
                File file=new File(filename);
                if (!Desktop.isDesktopSupported())
                    JOptionPane.showMessageDialog(null, "This Platform does not support Desktop", "ERROR", JOptionPane.ERROR_MESSAGE);
                if (file.exists())
                {
                    JFileChooser fc = new JFileChooser(filename);
                    int i = fc.showOpenDialog(f);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        Desktop desktop = Desktop.getDesktop();
                        if (Desktop.isDesktopSupported())
                            try {
                                desktop.open(f);
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                    }
                }
            }
            if(e.getActionCommand()== "rm")
            {
                try
                {
                    String path=JOptionPane.showInputDialog("enter file path to delete");
                    File file=new File(path);

                    if(file.delete())
                        JOptionPane.showMessageDialog(null, "Successful deletion","RESULTS", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getActionCommand() == "cp")
            {
                try
                {
                    String a = JOptionPane.showInputDialog("Enter source file path");
                    String b = JOptionPane.showInputDialog("Enter destination file path");
                    FileInputStream inpstream = new FileInputStream(new File(a));
                    FileOutputStream outstream = new FileOutputStream(new File(b));
                    byte[] buffer = new byte[5000];
                    int length;
                    while ((length = inpstream.read(buffer)) > 0)
                        outstream.write(buffer, 0, length);
                    inpstream.close();
                    outstream.close();
                    JOptionPane.showMessageDialog(null, "Successfully copied","RESULTS", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (IOException ioe)
                {
                    JOptionPane.showMessageDialog(null,"Error in copying\n"+ioe.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="mv")
            {
                try
                {
                    String inp=JOptionPane.showInputDialog("Enter source file path");
                    String out=JOptionPane.showInputDialog("Enter destination file path");
                    Path temp=Files.move(Paths.get(inp),Paths.get(out));
                    if(temp != null)
                        JOptionPane.showMessageDialog(null, "Successfully moved","RESULTS", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Error moving the file","RESULTS", JOptionPane.WARNING_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in Moving\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="search")
            {

                try
                {
                    String path=JOptionPane.showInputDialog("Enter the file path");
                    String find=JOptionPane.showInputDialog("Enter the string");
                    Scanner scan=new Scanner(new File(path));
                    String line=scan.nextLine();
                    String lines=null;
                    do
                    {
                        if(line.contains(find))
                        {
                            if(lines==null)
                                lines=line+'\n';
                            else
                            {

                                lines=lines+line;
                                lines=lines+'\n';
                            }
                        }
                        line=scan.nextLine();
                    }while(scan.hasNextLine());
                    scan.close();
                    if(lines==null)
                        JOptionPane.showMessageDialog(null,"String not found!!","Search Results",JOptionPane.INFORMATION_MESSAGE);
                    else
                    {
                        TextFrame textframe = new TextFrame(lines);
                        textframe.setVisible(true);
                        textframe.setSize(600, 600);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in search\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="chmod")
            {
                try
                {
                    String path=JOptionPane.showInputDialog("Enter the file path");
                    File file=new File(path);
                    String ex=null;
                    ex="Readable: "+file.canRead()+'\n';
                    ex=ex+"Writable: "+file.canWrite()+'\n';
                    ex=ex+"Executable: "+file.canExecute();
                    JOptionPane.showMessageDialog(null, ex,"RESULTS", JOptionPane.INFORMATION_MESSAGE);
                    String perm=JOptionPane.showInputDialog("Enter the modes(4-read|2-write|1-execute");
                    if(perm.charAt(0)=='4')
                        file.setReadable(true);
                    if(perm.charAt(1)=='2')
                        file.setWritable(true);
                    if(perm.charAt(2)=='1')
                        file.setExecutable(true);
                    String ex1;
                    ex1="Readable: "+file.canRead()+'\n';
                    ex1=ex1+"Writable: "+file.canWrite()+'\n';
                    ex1=ex1+"Executable: "+file.canExecute();
                    JOptionPane.showMessageDialog(null, ex1,"RESULTS", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in changing permissions\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="count")
            {
                String path=JOptionPane.showInputDialog("Enter the file path");
                String p=null;
                try
                {
                    int lc=0,wc=0,cc=0;
                    BufferedReader inp=new BufferedReader(new FileReader(path));
                    String line;
                    while((line=inp.readLine())!=null)
                    {
                        lc++;
                        String[] words=line.split(" ");
                        wc=wc+words.length;
                        for(String x:words)
                            cc=cc+x.length();
                    }
                    inp.close();
                    p="WORD COUNT: "+Integer.toString(wc)+'\n';
                    p=p+"CHARACTER COUNT: "+Integer.toString(cc)+'\n';
                    p=p+"LINE COUNT: "+Integer.toString(lc);
                    JOptionPane.showMessageDialog(null, p,"RESULTS", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in Counting\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
            if(e.getActionCommand()=="filesort")
            {
                String path=JOptionPane.showInputDialog("Enter the file path");
                try
                {
                    BufferedReader inp=new BufferedReader(new FileReader(path));
                    List<String> list=new ArrayList<String>();
                    String line;
                    while((line=inp.readLine())!=null)
                        list.add(line);
                    Collections.sort(list);
                    String f=null;
                    for(String x:list)
                    {
                        if(f==null)
                            f=x+'\n';
                        else
                            f=f+x+'\n';
                    }
                    TextFrame t=new TextFrame(f);
                    t.setVisible(true);
                    //JOptionPane.showMessageDialog(null, f,"RESULTS", JOptionPane.INFORMATION_MESSAGE);
                    inp.close();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in Sorting\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="pwd")
                JOptionPane.showMessageDialog(null,(new File(".").getAbsolutePath()),"CURRENT DIRECTORY",JOptionPane.INFORMATION_MESSAGE);
            if(e.getActionCommand()=="compress")
            {
                try
                {
                    String path=JOptionPane.showInputDialog("Enter the file paths");
                    String[] paths=path.split(",");
                    File file=new File(paths[0]);
                    String zippath=file.getName().concat(".zip");
                    ZipOutputStream zip=new ZipOutputStream(new FileOutputStream(zippath));
                    for(int i=0;i<paths.length;i++)
                    {
                        zip.putNextEntry(new ZipEntry(new File(paths[i]).getName()));
                        byte[] bytes=Files.readAllBytes(Paths.get(paths[i]));
                        zip.write(bytes, 0, bytes.length);

                        zip.closeEntry();
                    }
                    zip.close();
                    JOptionPane.showMessageDialog(null, "Compression Successful\nStored in:\n"+new File(".").getAbsolutePath(), "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in Compressing\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="cd")
            {
                try
                {
                    String path=JOptionPane.showInputDialog("Enter the directory path");
                    File directory;
                    if(path=="default")
                    {
                        String path1="C:/Users/Bharath/eclipse-workspace/Sample";
                        directory = new File(path1).getAbsoluteFile();
                        if (directory.exists() || directory.mkdirs())
                            System.setProperty("user.dir", directory.getAbsolutePath());
                    }
                    else
                    {
                        directory = new File(path).getAbsoluteFile();
                        if (directory.exists() || directory.mkdirs())
                            System.setProperty("user.dir", directory.getAbsolutePath());
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in Changing/Creating Directory\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "Successful directory change","RESULTS", JOptionPane.INFORMATION_MESSAGE);
            }
            if(e.getActionCommand()=="ls")
            {
                try {
                    String path = JOptionPane.showInputDialog("Enter the directory path");
                    File directory = new File(path);
                    if (directory.list().length == 0)
                        JOptionPane.showMessageDialog(null, "The Directory is empty!!");
                    else
                        {
                            String mode = JOptionPane.showInputDialog("Enter the mode\n(f for files)\n(d for directories)");
                        String flist = "";
                        String dlist = "";
                        String format = "%s\n";
                        File[] files = directory.listFiles();
                        for (File f : files) {
                            if (mode.equalsIgnoreCase("f")) {
                                if (f.isFile()) {
                                    if (flist == "") {
                                        flist = String.format(format, "FileName");
                                    } else {
                                        flist = flist + String.format(format, f.getName());
                                    }
                                }
                            }
                            if (mode.equalsIgnoreCase("d")) {
                                if (f.isDirectory()) {
                                    dlist = dlist + String.format(format, f.getName());
                                }
                            }
                        }
                        if (mode.equalsIgnoreCase("f")) {
                            if (flist == "")
                                JOptionPane.showMessageDialog(null, "No files present", null, JOptionPane.INFORMATION_MESSAGE);
                            else {
                                TextFrame f = new TextFrame(flist);
                                f.setVisible(true);
                                f.setSize(600, 600);
                                f.setResizable(false);
                            }
                            //JOptionPane.showMessageDialog(null, flist,"FILES IN "+path, JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (mode.equalsIgnoreCase("d")) {
                            if (dlist == "")
                                JOptionPane.showMessageDialog(null, "No directories present", null, JOptionPane.INFORMATION_MESSAGE);
                            else {
                                TextFrame f1 = new TextFrame(dlist);
                                f1.setVisible(true);
                                f1.setSize(600, 600);
                                f1.setResizable(false);
                            }
                            //JOptionPane.showMessageDialog(null, dlist,"DIRECTORIES IN "+path, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error in listing Directory\n"+ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="date")
            {
                try
                {
                    String fname=JOptionPane.showInputDialog("Enter FileName with extension:");
                    File file=new File(fname);
                    System.out.println(file.getAbsolutePath());
                    if(file.exists())
                    {
                        Path path=file.toPath();
                        BasicFileAttributes att=Files.readAttributes(path, BasicFileAttributes.class);
                        FileTime tim=att.creationTime();
                        long millis=tim.toMillis();
                        Date date=new Date();
                        date.setTime(millis);
                        String time=(new SimpleDateFormat()).format(date);
                        JOptionPane.showMessageDialog(null, time,"Creation time of "+fname, JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Error finding file","ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getActionCommand()=="extract")
            {
                String path=JOptionPane.showInputDialog("Enter zip file path:");
                String dest=JOptionPane.showInputDialog("Enter destination path:");
                File file=new File(dest);
                if(!file.exists())
                    file.mkdirs();
                byte[] buffer=new byte[1024];
                FileInputStream f;
                try
                {
                    f=new FileInputStream(path);
                    ZipInputStream zip=new ZipInputStream(f);
                    ZipEntry z=zip.getNextEntry();
                    while(z!=null)
                    {
                        String name=z.getName();
                        File file1=new File(dest+File.separator+name);
                        FileOutputStream fos=new FileOutputStream(file1);
                        int len;
                        while((len=zip.read(buffer))>0)
                            fos.write(buffer, 0, len);
                        zip.closeEntry();
                        fos.close();
                        z=zip.getNextEntry();
                    }
                    zip.closeEntry();
                    zip.close();
                    f.close();
                    JOptionPane.showMessageDialog(null,"Extraction Successful!!!!","RESULTS",JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    public static String utf8truncate(String input, int length) {
        StringBuffer result = new StringBuffer(length);
        int resultlen = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int charlen = 0;
            if (c <= 0x7f)//1 byte
            {
                charlen = 1;
            } else if (c <= 0x7ff) //2 byte
            {
                charlen = 2;
            } else if (c <= 0xd7ff) {
                charlen = 3;
            } else if (c <= 0xdbff) {
                charlen = 4;
            } else if (c <= 0xdfff) {
                charlen = 0;
            } else if (c <= 0xffff) {
                charlen = 3;
            }
            if (resultlen + charlen > length) {
                break;
            }
            result.append(c);
            resultlen += charlen;
        }
        return result.toString();
    }
}
@SuppressWarnings("serial")
class TextFrame extends JFrame
{
    private JTextArea text;
    public TextFrame(String lines)
    {
        super("Results");
        Box box=Box.createHorizontalBox();
        text=new JTextArea(lines);
        text.setEditable(false);
        text.setLineWrap(true);
        JScrollPane pane=new JScrollPane(text);
        box.add(pane);
        add(box);
    }
}