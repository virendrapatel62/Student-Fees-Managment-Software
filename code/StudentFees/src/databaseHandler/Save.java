
package databaseHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import javafx.collections.ObservableList;
import models.Batch;
import models.Student;

public class Save {
    
    static FileOutputStream fo ;
    static ObjectOutputStream oos ;
    
    public static void saveBatchList(ObservableList list) throws IOException{
        if(!FileManager.batchFileExists()){
            FileManager.createBatchFile();
        }
        
        File file = FileManager.getBatchFile();
        System.out.println(file);
        fo = new FileOutputStream(file);
        
        oos = new ObjectOutputStream(fo);
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            Batch batch = (Batch)itr.next();
            System.out.println("Object to save == " + batch );
            oos.writeInt(1);
            oos.writeObject(batch);
        }
        oos.close();
        fo.close();
        
    }
    
    public static void saveStudentList(ObservableList list) throws IOException{
        if(!FileManager.studentFileExists()){
            FileManager.createStudentFile();
        }
        
        File file = FileManager.getStudentFile();
        System.out.println(file);
        fo = new FileOutputStream(file);
        
        oos = new ObjectOutputStream(fo);
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            Student student = (Student)itr.next();
            System.out.println("Object to save == " + student );
            oos.writeInt(1);
            oos.writeObject(student);
        }
        oos.close();
        fo.close();
        
    }

}
