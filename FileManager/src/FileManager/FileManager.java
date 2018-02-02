package FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileManager {

    public static void main(String[] args) {

        System.out.println("Insert one of the following commands: LIST, INFO, "
                + "CREATE_DIR, RENAME, COPY, MOVE or DELETE.");

        Scanner s = new Scanner(System.in);

        switch (s.next()) {
            
            case "list": {
                System.out.println("Enter path: ");
                File f = new File(s.next());
                if (f.exists() && f.isDirectory()) {
                    String[] strings = f.list();
                    for (String string : strings) {
                        System.out.println(string);
                    }
                    break;
                } else {
                    System.out.println("Folder does not exist!");
                }
            }
            break;

            case "info": {
                System.out.println("Enter path: ");
                File f = new File(s.next());
                if (f.exists()) {
                    System.out.println("Name = " + f.getName());
                    System.out.println("Path = " + f.getPath());
                    System.out.println("Is abosolute = " + f.isAbsolute());
                    System.out.println("Length = " + f.length() + " B");
                    Instant instantlf = Instant.ofEpochMilli(f.lastModified());
                    LocalDateTime dateTime = LocalDateTime.ofInstant(instantlf, ZoneId.systemDefault());
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy. HH:mm:ss");
                    System.out.println("Last time changed = " + dateTime.format(dateTimeFormatter));
                    break;
                } else {
                    System.out.println("File or folder does not exist!");
                }
            }
            break;

            case "create_dir": {
                System.out.println("Enter path and folder name: ");
                File f = new File(s.next());

                if (!f.exists()) {
                    f.mkdir();
                    System.out.println("Folder " + f.getName() + " is successfully created.");
                    break;
                } else {
                    System.out.println("Folder does not exist!");
                }
            }
            break;

            case "rename": {
                System.out.println("Enter folder or file path:");
                File f = new File(s.next());
                System.out.println("Enter folder or file path with new name:");
                File f2 = new File(s.next());

                if (!f.exists()) {
                    System.out.println("File does not exist!");
                    return;
                }
                if (f2.exists()) {
                    return;
                }
                f.renameTo(f2);
                System.out.println(f.getName() + " is successfully renamed in " + f2.getName());
                break;
            }

            case "copy": {
                System.out.println("Enter file path: ");
                File f = new File(s.next());
                System.out.println("Enter file destination path: ");
                File f2 = new File(s.next());

                try (FileInputStream inStream = new FileInputStream(f);
                        FileOutputStream outStream = new FileOutputStream(f2)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }
                    System.out.println("File is successfully copied!");

                } catch (IOException exc) {
                    System.out.println(exc);
                }
                break;
            }

            case "move": {
                System.out.println("Enter source folder path: ");
                File f = new File(s.next());
                System.out.println("Enter destination folder path: ");
                File f2 = new File(s.next());

                if (!f.exists()) {
                    f2.mkdir();
                }

                if (f.exists() && f.isDirectory()) {
                    File[] listOfFiles = f.listFiles();

                    if (listOfFiles != null) {
                        for (File child : listOfFiles) {
                            child.renameTo(new File(f2 + "\\" + child.getName()));       
                        }
                        System.out.println("Folder successfully moved!");
                        f.delete();
                    }
                } else {
                    System.out.println(f + "  Folder does not exist!");
                }
                break;
            }

            case "delete": {
                while (true) {
                    System.out.println("Enter file path: ");
                    File f = new File(s.next());

                    if (f.exists()) {
                        f.delete();
                        System.out.println("Successfully deleted!");
                        break;
                    } else {
                        System.out.println("Unable to delete " + f.getName() + " because file " + f.getName() 
                                + " does not exist.");
                    }
                }
                break;
            }

            default:
                System.out.println("You entered incorrect command!");
        }
    }

}
