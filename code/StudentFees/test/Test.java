
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Observable;
import javafx.collections.ObservableList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import models.Batch;


public class Test {
    public static void main(String[] args) throws Exception {
        ObservableList<Batch> batches = Batch.getBatches();
        
        Iterator<Batch> it = batches.iterator();
        
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File("batches.xls"));
        
        WritableSheet sheet = writableWorkbook.createSheet("batch Sheet", 0);
        
        sheet.addCell(new Label(0, 0, "Batch Name"));
        sheet.addCell(new Label(1, 0, "Batch Fee"));
        sheet.addCell(new Label(2, 0, "Batch ID"));
                 
        int row = 1;
        while(it.hasNext()){
            Batch b = it.next();
            sheet.addCell(new Label(0, row, b.getBatchName()));
            sheet.addCell(new jxl.write.Number(1, row, b.getBatchFee()));
            sheet.addCell(new jxl.write.Number(2, row, b.getBatchId()));
            row++;
        }
        
        writableWorkbook.write();
        writableWorkbook.close();
    }
 
}
