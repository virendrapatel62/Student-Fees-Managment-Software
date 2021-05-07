
package databaseHandler;
import java.io.File;
import java.io.IOException;
public class FileManager {
    static String batch = "batch.database";
    static String student = "student.database";
    public static boolean batchFileExists(){
        File file  = new File(batch);
        return file.exists();
    }
    
    public static boolean createBatchFile() throws IOException{
        File file  = new File(batch);
        return file.createNewFile();
    }
    public static boolean studentFileExists(){
        File file  = new File(student);
        return file.exists();
    }
    
    public static boolean createStudentFile() throws IOException{
        File file  = new File(student);
        return file.createNewFile();
    }
    
    public static File getBatchFile(){
        return new File(batch);
    }

    public static File getStudentFile() {
        return new File(student);
    }
    
    
}
