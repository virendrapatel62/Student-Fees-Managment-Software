
package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utility {
    public static ObservableList<Student> getStudents(ObservableList students , Batch b){
        ObservableList<Student> list = FXCollections.observableArrayList();
        ObservableList<Student> filtered = FXCollections.observableArrayList();
        list.addAll(students);
        System.err.println("Students : "+students);
        System.err.println("List: "+list);
        
        Comparator com = new Comparator<Student>(){
            @Override
            public int compare(Student b1, Student b2) {
                System.out.println(b1.getBatch() + "---" + b2.getBatch() );
                
                return Integer.compare(b2.getBatch().getBatchId() , b1.getBatch().getBatchId());
            }
        };
        boolean flag = true;
        Student s = new Student();
        s.setBatch(b);
        while(flag){
            int i = Collections.binarySearch(list, s, com );
            
            if(i>-1){
                System.out.println("Found" + i + "" + list.get(i));
                filtered.add(list.get(i));
                list.remove(i);
            }else{
               flag = false; 
            }
        }
        
        return filtered;
    }
}
