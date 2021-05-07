
package ImportExport;

import java.io.File;
import java.util.Iterator;
import javafx.collections.ObservableList;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import models.Batch;
import models.FeesMode;
import models.Student;

public class Export {
    private Integer sheetIndex = 0;
    public void export(File file , Batch batch , ObservableList<Student> students)throws Exception{
        
        Iterator<Student> it = students.iterator();
        WritableWorkbook writableWorkbook  = null;
        
        if(file.exists()){
            Workbook workbook =  Workbook.getWorkbook(file);
            writableWorkbook = Workbook.createWorkbook(file, workbook);
        }else{
             writableWorkbook = Workbook.createWorkbook(file);
        }
        
        
        
        WritableSheet sheet = writableWorkbook.createSheet(batch.getBatchName(), sheetIndex++);
        int column = 0;
        int row = 0;
        sheet.addCell(new Label(column++, row, "Student Id"));
        sheet.addCell(new Label(column++, row, "Student Name"));
        sheet.addCell(new Label(column++, row, "Father's Name"));
        sheet.addCell(new Label(column++, row, "Mobil"));
        sheet.addCell(new Label(column++, row, "Aadhar number"));
        sheet.addCell(new Label(column++, row, "DOB"));
        sheet.addCell(new Label(column++, row, "Gender"));
        sheet.addCell(new Label(column++, row, "Address"));
        sheet.addCell(new Label(column++, row, "School/College"));
        sheet.addCell(new Label(column++, row, "Joining Month"));
        sheet.addCell(new Label(column++, row, "Student Type"));
        sheet.addCell(new Label(column++, row, "Batch Name"));
        sheet.addCell(new Label(column++, row, "Batch Id"));
        sheet.addCell(new Label(column++, row, "Payment Mode"));
        sheet.addCell(new Label(column++, row, "Batch Fee"));
        sheet.addCell(new Label(column++, row, "Submitted"));
        sheet.addCell(new Label(column++, row, "Pending"));
        
        row++;
        while(it.hasNext()){
            Student student = it.next();
            column = 0;
            String dob = "not AVialable";
            String gender = "not AVialable";
            String joiningMonth = "not AVialable";
            Integer paid = Student.getTotalPaidFee(student);
            
            String pending = "-";
            
            if(student.getBatch().getFeesMode()==FeesMode.instalment){
                pending = "Rs. "+(student.getBatch().getBatchFee() - paid);
            }
            if(student.getDob()!=null){
                dob = student.getDob().toString();
            }
            if(student.getGender()!=null){
                gender = student.getGender().toString();
            }
            if(student.getJoiningMonth()!=null){
                joiningMonth = student.getJoiningMonth().toString();
            }
            sheet.addCell(new jxl.write.Number(column++, row, student.getStudentId()));
            sheet.addCell(new Label(column++, row, student.getStudentName()));
            sheet.addCell(new Label(column++, row, student.getFatherName()));
            sheet.addCell(new Label(column++, row, student.getMobilNumber()));
            sheet.addCell(new Label(column++, row, student.getAddharNumber()));
            sheet.addCell(new Label(column++, row, dob));
            sheet.addCell(new Label(column++, row, gender));
            sheet.addCell(new Label(column++, row, student.getAddress()));
            sheet.addCell(new Label(column++, row, student.getSchool()));
            sheet.addCell(new Label(column++, row, joiningMonth));
            sheet.addCell(new Label(column++, row, student.getStudentType().toString()));
            sheet.addCell(new Label(column++, row, student.getBatch().getBatchName()));
            sheet.addCell(new jxl.write.Number(column++, row, student.getBatch().getBatchId()));
            sheet.addCell(new Label(column++, row, student.getBatch().getFeesMode().toString()));
            sheet.addCell(new jxl.write.Number(column++, row, student.getBatch().getBatchFee()));
            sheet.addCell(new Label(column++, row, "Rs. "+paid));
            sheet.addCell(new Label(column++, row, pending));
            row++;
        }
        
        writableWorkbook.write();
        writableWorkbook.close();
    }
}
