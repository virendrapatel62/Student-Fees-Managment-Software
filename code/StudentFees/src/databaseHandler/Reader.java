
package databaseHandler;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Batch;
import models.Student;

public class Reader {
    static ObjectInputStream ois;
    static FileInputStream fis;
    
    public static ObservableList getBatchList() throws Exception{
        File file = FileManager.getBatchFile();
        ObservableList<Batch> list = FXCollections.observableArrayList();
        if(file.exists()){
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            int i ;
            System.out.println(ois.available()+"   num");
            while((i = ois.available())>0){
                System.out.println(i+"++++");
                ois.readInt();
                Batch batch = (Batch)ois.readObject();
                System.out.println(batch);
                list.add(batch);
            }
        }
        
        System.out.println(file);
        System.out.println(list);
        ois.close();
        fis.close();
        
        return list;
    } 
    
    
    public static ObservableList getStudentList() throws Exception{
        File file = FileManager.getStudentFile();
        ObservableList<Student> list = FXCollections.observableArrayList();
        if(file.exists()){
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            int i ;
            while((i = ois.available())>0){
                ois.readInt();
                Student student = (Student)ois.readObject();
                System.out.println(student);
                list.add(student);
            }
        }
        System.out.println(file);
        System.out.println(list);
        ois.close();
        fis.close();
        return list;
    } 
}
