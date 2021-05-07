package controllers;

import ImportExport.Export;
import app.StartApp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import impl.org.controlsfx.skin.NotificationBar;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import models.*;
import org.controlsfx.control.Notifications;
import org.ibex.nestedvm.util.InodeCache;
import prefrences.Password;
import prefrences.Profile;
import prefrences.Theme;

public class MainFrameController implements Initializable {
    
    // instanses
    
    private File imageOfEnquiry = null;
    
    final ObservableList batchList = FXCollections.observableArrayList();
    final ObservableList studentList = FXCollections.observableArrayList();
    final ObservableList studentDetails = FXCollections.observableArrayList();
    final ObservableList enquiryStudentList = FXCollections.observableArrayList();;
    final ObservableList feesModes = FXCollections.observableArrayList();
    final ObservableList<Month> months = FXCollections.observableArrayList();
    final ObservableList<JFXCheckBox> monthCheckBoxes = FXCollections.observableArrayList();
    Student studentToUpdate ;
    Student selectedStudent;
    Batch batchToUpdate ;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    final ObservableList<PieChart.Data> pichartList = FXCollections.observableArrayList();
    
    
    @FXML
    private AnchorPane main;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Tab addStudentTab;
    @FXML
    private AnchorPane addStudentAnchorPane;
    @FXML
    private ScrollPane addStudentFormScrollPane;
    @FXML
    private VBox studentFormBox;
    @FXML
    private JFXTextField studentName;
    @FXML
    private JFXTextField studentFatherName;
    @FXML
    private JFXTextField studentAddress;
    @FXML
    private JFXTextField studentSchool;
    @FXML
    private Tab addBatchTab;
    @FXML
    private AnchorPane addBatchAnchorPane;
    @FXML
    private TableView<Batch> batchTable;
    @FXML
    private JFXButton saveStudent;
    @FXML
    private JFXTextField batchName;
    @FXML
    private JFXTextField batchFee;
    @FXML
    private JFXButton saveBatch;
    @FXML
    private TableColumn<Batch, Integer> batchIdColumn;
    @FXML
    private TableColumn<Batch, String> batchNameColumn;
    @FXML
    private TableColumn<Batch, Integer> batchFeeColumn;
    @FXML
    private JFXComboBox<Batch> selectBatch;
    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, String> studentFatherNameColumn;
    @FXML
    private TableColumn<Student, String> studentAddressColumn;
    private TableColumn<Student, String> studentSchoolColumn;
    @FXML
    private TableColumn<Student, Batch> studentBatchColumn;
    @FXML
    private TableView<Student> studentTable;
    private TableColumn<Student, String> studentDOBColumn;
    @FXML
    private JFXTabPane tabContainer;
    @FXML
    private ContextMenu studentTableContextMenu;
    @FXML
    private MenuItem studentTableEdit;
    @FXML
    private MenuItem studentTableDelete;
    @FXML
    private PieChart pichart;
    @FXML
    private Tab dashboardTab;
    @FXML
    private Label chartDetail;
    @FXML
    private JFXComboBox<Batch> dashboardSelectBatch;
    @FXML
    private JFXComboBox<Student> dashBoardSelectStudent;
    @FXML
    private JFXButton dashboardEditButton;
    @FXML
    private JFXButton dashboardSubmitFeeButton;
    @FXML
    private JFXButton dashboardDeleteButton;
    @FXML
    private TableColumn<Entry, String> dashboardTablePropertyColumn;
    @FXML
    private TableColumn<Entry, Object> dashboardTableValueColumn;
    @FXML
    private TableView<Entry> dashboardTable;
    @FXML
    private HBox dashboardButtonBox;
    @FXML
    private Tab feesPanelTab;
    @FXML
    private JFXComboBox<FeesMode> selectFeesMode;
    @FXML
    private TableColumn<Batch, FeesMode> batchFeesModeColumn;
    @FXML
    private JFXComboBox<Batch> feesPanelSelectBatch;
    @FXML
    private JFXComboBox<Student> feesPanelSelectStudent;
    @FXML
    private JFXTextField feesPanelStudentIdTextField;
    @FXML
    private JFXTextField studentMobil;
    @FXML
    private JFXTextField studentAdharNumber;
    @FXML
    private JFXDatePicker studentDOB;
    @FXML
    private JFXComboBox<Gender> selectGender;
    @FXML
    private TableColumn<Student, String> studentMobilColumn;
    @FXML
    private Label profileNameLabel;
    @FXML
    private JFXButton dashboardCancelButton;
    @FXML
    private ContextMenu batchTableContextMenu;
    @FXML
    private JFXButton batchFormCancelButton;
    @FXML
    private JFXTextField pName;
    @FXML
    private JFXTextField pId;
    @FXML
    private JFXTextField pFname;
    @FXML
    private JFXTextField pAddress;
    @FXML
    private JFXTextField pMobil;
    @FXML
    private JFXTextField pGender;
    @FXML
    private JFXTextField pAdhar;
    @FXML
    private JFXTextField pDOB;
    @FXML
    private JFXTextField pSchool;
    @FXML
    private JFXTextField pBatch;
    @FXML
    private JFXComboBox<Month> batchFormselectMonth;
    @FXML
    private JFXComboBox<Month> studentJoiningMonth;
    @FXML
    private HBox joinedOnLabel;
    @FXML
    private Label joinedOnMonth;
    @FXML
    private JFXCheckBox month1;
    @FXML
    private JFXCheckBox month2;
    @FXML
    private JFXCheckBox month3;
    @FXML
    private JFXCheckBox month4;
    @FXML
    private JFXCheckBox month5;
    @FXML
    private JFXCheckBox month6;
    @FXML
    private JFXCheckBox month7;
    @FXML
    private JFXCheckBox month8;
    @FXML
    private JFXCheckBox month9;
    @FXML
    private JFXCheckBox month10;
    @FXML
    private JFXCheckBox month11;
    @FXML
    private JFXCheckBox month12;
    @FXML
    private JFXComboBox<Month> selectMonthToSubmitFee;
    @FXML
    private JFXButton submitFeeButtonOnFeesPanel;
    @FXML
    private VBox feePayPanelForMonthlyBatch;
    @FXML
    private VBox feeDetailForMonthlyBatch;
    @FXML
    private VBox feePayPanelForInstalment;
    @FXML
    private VBox feeDetailForInstalmentBatch;
    @FXML
    private PieChart instalmentFeesDetailChart;
    @FXML
    private JFXTextField amountForInstalment;
    @FXML
    private JFXButton submitFeeForMonthyBatch;
    @FXML
    private FontAwesomeIconView clearStudentSearchButton;
    @FXML
    private JFXTextField studentSearchBox;
    @FXML
    private JFXButton loadPreviousStudents;
    @FXML
    private JFXButton loadNextStudents;
    @FXML
    private Tab enquiryStudentTab;
    @FXML
    private AnchorPane addStudentAnchorPaneenquiry;
    @FXML
    private ScrollPane addStudentFormScrollPaneEnquiry;
    @FXML
    private VBox studentFormBox1;
    @FXML
    private JFXTextField studentName1;
    @FXML
    private JFXTextField studentFatherName1;
    @FXML
    private JFXTextField studentAddress1;
    @FXML
    private JFXTextField studentMobil1;
    @FXML
    private JFXTextField studentAdharNumber1;
    @FXML
    private JFXDatePicker studentDOB1;
    @FXML
    private JFXComboBox<Gender> selectGender1;
    @FXML
    private JFXTextField studentSchool1;
    @FXML
    private JFXComboBox<Batch> selectBatch1;
    @FXML
    private JFXComboBox<Month> studentJoiningMonth1;
    @FXML
    private JFXTextField studentSearchBoxEnquiry;
    @FXML
    private FontAwesomeIconView clearStudentSearchButtonEnquiry;
    @FXML
    private TableView<Student> studentTableEnquiry;
    @FXML
    private ContextMenu studentTableContextMenuEnquiry;
    @FXML
    private MenuItem studentTableEditEnquiry;
    @FXML
    private MenuItem studentTableDeleteEnquiry;
    @FXML
    private TableColumn<Student, Integer> studentIdColumnEnquiry;
    @FXML
    private TableColumn<Student, String> studentNameColumnEnquiry;
    @FXML
    private TableColumn<Student, String> studentFatherNameColumnEnquiry;
    @FXML
    private TableColumn<Student, String> studentAddressColumnEnquiry;
    @FXML
    private TableColumn<Student, Batch> studentBatchColumnEnquiry;
    @FXML
    private TableColumn<Student, String> studentMobilColumnEnquiry;
    @FXML
    private JFXButton loadPreviousStudentsEnquiry;
    @FXML
    private JFXButton loadNextStudentsEnquiry;
    @FXML
    private JFXButton saveStudentEnquiry;
    @FXML
    private MenuItem markAsAdmitted;
    @FXML
    private JFXTextField pJoiningMonth;
    @FXML
    private BarChart<String, Integer> monthlyincomeChart;
    @FXML
    private VBox feesPanelChartBox;
    @FXML
    private MenuItem setPasswordMenuItem;
    @FXML
    private MenuItem removePasswordMenuItem;
    @FXML
    private JFXDatePicker fromDateForIncome;
    @FXML
    private JFXDatePicker toDateForIncome;
    @FXML
    private BarChart<?, ?> incomeChartOnAnlytics;
    @FXML
    private Tab analyticsTab;
    @FXML
    private JFXComboBox<Batch> selectBatchToFilter;
    @FXML
    private TableColumn<Student, Integer> paidColumn;
    @FXML
    private TableColumn<Student, String> pendingColumn;
    @FXML
    private TableColumn<Student, Integer> totalFeesColumn;
    @FXML
    private TableColumn<Student, FeesMode> batchPaymentMode;
    @FXML
    private ImageView profilepic;
    @FXML
    private Menu settingMenu;
    @FXML
    private Menu themesMenu;
    @FXML
    private CheckMenuItem blackTheme;
    @FXML
    private CheckMenuItem chocolateTheme;
    @FXML
    private CheckMenuItem blueTheme;
    @FXML
    private TableColumn<Student, String> studentAdmissionDateColumn;
    @FXML
    private CheckMenuItem theme4;
    @FXML
    private CheckMenuItem OfficeTheme;
    @FXML
    private JFXDatePicker dateofSubmissionofMonthly;
    @FXML
    private JFXDatePicker dateOfSubmissionOfInstallment;
    @FXML
    private HBox dashboardButtonBox2;
    @FXML
    private JFXTextField studentIdTextBoxOnDashboard;
    @FXML
    private Label greetingText;
    @FXML
    private ImageView enquirypic;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        batchTableSetup();
        studentTableSetup();
        pichartInitilization();
        pichartEvents();
        dashboardTableSetup();
        feesPanelSetup();
        fillMonthList();
        setItemsOnSelectMonthComboBox();
        setSelectedTheme();
        datePickersInitialValue();
        numaricTextFieldSetup();
        greetingText();
        
      // initializeMonthIncomeChart();
    }
    private void greetingText(){
        String text = "Hello";
        String name = Profile.getName();
        
        if(name.trim().isEmpty()){
            name = "Sir";
        }
        
        java.util.Date date = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("a");
        
        String ampm = df.format(date);
        
        if(ampm.equalsIgnoreCase("AM")){
            greetingText.setText("Good Morning "+name + " Ji");
        }else{
            df = new SimpleDateFormat("hh");
            int h = Integer.parseInt(df.format(date));
            df = new SimpleDateFormat("mm");
            int m = Integer.parseInt(df.format(date));
            
            if(h==12 || (h >= 1 && h <=4) ){
                greetingText.setText("Good Afternoon "+name + " Ji");
            }
            
            else if(h>=5){
                greetingText.setText("Good Evening "+name + " Ji");
            }
        }
        
    }
    private void numaricTextFieldSetup(){
        
        UnaryOperator<TextFormatter.Change> num =new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                String input = t.getText();
                if (input.matches("[0-9]*")) {
                    return t;
                }
                return null;
            }
        };
         
        studentIdTextBoxOnDashboard.setTextFormatter(new TextFormatter<String>(num));
        studentAdharNumber.setTextFormatter(new TextFormatter<String>(num));
        studentAdharNumber1.setTextFormatter(new TextFormatter<String>(num));
        studentMobil.setTextFormatter(new TextFormatter<String>(num));
        studentMobil1.setTextFormatter(new TextFormatter<String>(num));
    }
    
    private void datePickersInitialValue(){
        dateOfSubmissionOfInstallment.setValue(LocalDate.now());
        dateofSubmissionofMonthly.setValue(LocalDate.now());
        fromDateForIncome.setValue(null);
        toDateForIncome.setValue(LocalDate.now());
    }
    
    
    
    void initializeMonthIncomeChart(){
        
           
           XYChart.Series<String , Integer> dataSet = new XYChart.Series();
           ObservableList<HashMap> list = InstalmentFee.getCurrentMonthIncomeBatchVise();
           list.addAll(MonthlyFee.getCurrentMonthIncomeBatchVise());
           Iterator<HashMap> itr = list.iterator();
           while(itr.hasNext()){
               HashMap m = itr.next();
               XYChart.Data<String , Integer> data = new XYChart.Data(m.get("batch").toString() , m.get("income"));
               dataSet.getData().add(data);
           }
           System.out.println("controllers.MainFrameController.initializeMonthIncomeChart()"+ dataSet );
           System.out.println("controllers.MainFrameController.initializeMonthIncomeChart()"+ list );
           
           monthlyincomeChart.getData().addAll(dataSet);
    }
    
    private void feesPanelSetup(){
        feesPanelSelectBatch.setItems(batchList);
    }
    
    

    public ObservableList<PieChart.Data> getPichartList() {
        return pichartList;
    }

    public ObservableList getEnquiryStudentList() {
        return enquiryStudentList;
    }
    
    
    
    public void pichartInitilization(){
        Iterator<Batch> bs = batchList.iterator();
        while(bs.hasNext()){
            Batch batch  = bs.next();
            int c = batch.getStundentCount();
            getPichartList().add(new PieChart.Data(batch.getBatchName()+" [ "+c+" ]" ,c ));
        }
        System.out.println(pichartList);
        pichart.setData(pichartList);
        pichart.setLabelsVisible(true);
        pichart.setLabelLineLength(20);
        pichart.setLegendSide(Side.BOTTOM);
        pichartEvents();
    }

    public ObservableList getBatchList() {
        return batchList;
    }

    public ObservableList getStudentList() {
        return studentList;
    }
    
    
    
    private void batchTableSetup(){
        batchTable.setItems(batchList);
        batchTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        feesModes.addAll(FeesMode.instalment , FeesMode.monthly);
        selectFeesMode.setItems(feesModes);
        batchNameColumn.setCellValueFactory(new PropertyValueFactory<>("batchName"));
        batchIdColumn.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        batchFeeColumn.setCellValueFactory(new PropertyValueFactory<>("batchFee"));
        batchFeesModeColumn.setCellValueFactory(new PropertyValueFactory<>("feesMode"));
    }
    
    private void studentTableSetup(){
        
        studentTable.setEditable(true);
        
        studentNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentFatherNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentMobilColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
        selectBatch.setItems(batchList);
        selectBatch1.setItems(batchList);
        selectBatchToFilter.setItems(batchList);
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentTableEnquiry.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList genders = FXCollections.observableArrayList();
        genders.addAll(Gender.male , Gender.female);
        selectGender.setItems(genders);
        selectGender1.setItems(genders);
        
        studentTable.setItems(studentList);
        studentTableEnquiry.setItems(enquiryStudentList);
        
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentFatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        studentBatchColumn.setCellValueFactory(new PropertyValueFactory<>("batch"));
        studentAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentMobilColumn.setCellValueFactory(new PropertyValueFactory<>("mobilNumber"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        pendingColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> param) {
                Student a = param.getValue();
                if(a.getPending()<0){
                    return new SimpleStringProperty("-");
                }else{
                    return new SimpleStringProperty(a.getPending()+"");
                }
            }
        });
        totalFeesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
                return new SimpleObjectProperty<>(param.getValue().getBatch().getBatchFee());
            }
        });
        
        batchPaymentMode.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, FeesMode>, ObservableValue<FeesMode>>() {
            @Override
            public ObservableValue<FeesMode> call(TableColumn.CellDataFeatures<Student, FeesMode> param) {
                return new SimpleObjectProperty<>(param.getValue().getBatch().getFeesMode());
            }
        });
         
        
        
        studentNameColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentFatherNameColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        studentBatchColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("batch"));
        studentAddressColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentMobilColumnEnquiry.setCellValueFactory(new PropertyValueFactory<>("mobilNumber"));
        studentAdmissionDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> param) {
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                 if(param.getValue().getAdmissionDate()!=null){
                    return new SimpleStringProperty(dateFormat.format(param.getValue().getAdmissionDate()));
                 }else{
                    return new SimpleStringProperty("Not Available");
                 }
            }
        });
        
        studentTableEnquiry.getSelectionModel().selectedItemProperty().addListener((observable) -> {
                Student s = studentTableEnquiry.getSelectionModel().getSelectedItem();
                System.err.println(s);
                if (s != null) {
                    selectedStudent = s;
                    setEnquiryStudentDetailsOnTextField(s);
                    enquirypic.setImage(s.getProfilePic());
                }
            
        });
        
        
        
        
        
    }
    private void dashboardTableSetup(){
        //dashboardTable.getColumns().get(0).setVisible(false);
        dashboardTable.setItems(studentDetails);
        dashboardTablePropertyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        dashboardTableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }
    
   

    @FXML
    private void saveStudent(ActionEvent event) {
      
        boolean flag = true;
        String name = studentName.getText();
        String fname = studentFatherName.getText();
        String address = studentAddress.getText();
        String mobil  = studentMobil.getText();
        String aadhar = studentAdharNumber.getText();
        LocalDate dob = studentDOB.getValue();
        Month joiningMonth = studentJoiningMonth.getSelectionModel().getSelectedItem();
        java.sql.Date dobsql = null;
        Gender gender = (Gender)(selectGender.getSelectionModel().getSelectedItem());
        String school = studentSchool.getText();
        Batch batch = selectBatch.getSelectionModel().getSelectedItem();
        if(name.trim().length()==0){
            flag = false;
            alert.setTitle("Student");
            alert.setContentText("Enter Student Name");
            alert.show();
        }else if(dob!=null){
                try{
                    dobsql = java.sql.Date.valueOf(dob);
                }catch(Exception ex){
                    alert.setTitle("Student");
                    alert.setContentText("Enter valid Format of Date Of Birth");
                    alert.show();
                    flag = false;
                }
        }else if(batch == null){
            alert.setTitle("Student");
            alert.setContentText("Select a batch");
            alert.show();
            flag = false;
        }else if(gender==null){
            gender = Gender.notSpecified;
        }
        
        if(batch.getFeesMode()==FeesMode.monthly){
            if(joiningMonth==null){
                alert.setTitle("Student");
                alert.setContentText("Select Joining Month");
                alert.show();
                flag = false;
            }
        }   
        
        if(flag){
            if(saveStudent.getText().equalsIgnoreCase("save")){ 
                Student student = new Student();
                student.setStudentName(name);
                student.setFatherName(fname);
                student.setAddress(address);
                student.setSchool(school);
                student.setMobilNumber(mobil);
                student.setAddharNumber(aadhar);
                student.setBatch(batch);
                student.setGender(gender);
                student.setDob(dobsql);
                student.setJoiningMonth(joiningMonth);
                
                try{
                    int id  = student.saveStudent();
                    student.setStudentId(id);
                    studentList.add(student);
                    /*
                    
                    Iterator<Batch> demo = Batch.getBatches().iterator();
                    while(demo.hasNext()){
                        Batch b = demo.next();
                        for(int i = 0 ; i < 25 ; i++){
                            student.setBatch(b);
                            student.saveStudent();
                        }
                    }
                    
                    */
                    
                    studentTable.refresh();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("studentfees.MainFrameController.saveStudent()");
                }
                //student.setStudentId((int)(new Random().nextDouble()*1000));
                ///studentList.add(student);
            }else{
                System.out.println(studentToUpdate + "student object");
                studentToUpdate.setStudentName(name);
                studentToUpdate.setFatherName(fname);
                studentToUpdate.setAddress(address);
                studentToUpdate.setSchool(school);
                studentToUpdate.setMobilNumber(mobil);
                studentToUpdate.setAddharNumber(aadhar);
                studentToUpdate.setBatch(batch);
                studentToUpdate.setGender(gender);
                studentToUpdate.setDob(dobsql);
                studentToUpdate.setJoiningMonth(joiningMonth);
                
                try{
                    
                    studentToUpdate.updateStudent();
                    Comparator com  = new Student.SortById();
                    Collections.sort(studentList, com);
                    int i = Collections.binarySearch(studentList ,  studentToUpdate , com);
                    studentList.remove(i);
                    studentList.add(i, studentToUpdate);
                    System.out.println(i + "found");
                    studentTable.refresh();
                    
                    showDetailsOnDashboard(null);
                    dashboardTable.refresh();
                    System.out.println(studentToUpdate.getAddharNumber() + "student object");
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("studentfees.MainFrameController.saveStudent()");
                }
                saveStudent.setText("Save");
                studentToUpdate = null;
                
            }
            resetStudentForm(null);
        }
        
    }
    
    @FXML
    private void saveEnquiryStudent(ActionEvent event) {
      
        boolean flag = true;
        String name = studentName1.getText();
        String fname = studentFatherName1.getText();
        String address = studentAddress1.getText();
        String mobil  = studentMobil1.getText();
        String aadhar = studentAdharNumber1.getText();
        LocalDate dob = studentDOB1.getValue();
        Month joiningMonth = studentJoiningMonth1.getSelectionModel().getSelectedItem();
        java.sql.Date dobsql = null;
        Gender gender = (Gender)(selectGender1.getSelectionModel().getSelectedItem());
        String school = studentSchool1.getText();
        Batch batch = selectBatch1.getSelectionModel().getSelectedItem();
        
        System.err.println(mobil.length() + "  " + mobil );
        if(name.trim().length()==0){
            flag = false;
            alert.setTitle("Student");
            alert.setContentText("Enter Student Name");
            alert.show();
        }else if(dob!=null){
                try{
                    dobsql = java.sql.Date.valueOf(dob);
                }catch(Exception ex){
                    alert.setTitle("Student");
                    alert.setContentText("Enter valid Format of Date Of Birth");
                    alert.show();
                    flag = false;
                }
        }else if(batch == null){
            alert.setTitle("Student");
            alert.setContentText("Select a batch");
            alert.show();
            flag = false;
        }else if(gender==null){
            gender = Gender.notSpecified;
        }
        
        if(mobil.length()>0 && flag){
            
            Pattern p = Pattern.compile("[0-9]{10}");
            Matcher m = p.matcher(mobil);
            if(!m.matches()){
                alert.setTitle("Student");
                alert.setContentText("Invalid Mobile Number");
                alert.show();
                flag = false;
            }
        }
        if(aadhar.length()>0 && flag == true){

            Pattern p = Pattern.compile("[0-9]{12}");
            Matcher m = p.matcher(aadhar);
            if(!m.matches()){
                alert.setTitle("Student");
                alert.setContentText("Invalid Aadhaar Number");
                alert.show();
                flag = false;
            }
        }
        
        
        if(flag){
            if(saveStudentEnquiry.getText().equalsIgnoreCase("save")){ 
                Student student = new Student();
                student.setStudentName(name);
                student.setFatherName(fname);
                student.setAddress(address);
                student.setStudentType(StudentType.ENQUIRY);
                student.setSchool(school);
                student.setMobilNumber(mobil);
                student.setAddharNumber(aadhar);
                student.setBatch(batch);
                student.setGender(gender);
                student.setDob(dobsql);
                student.setJoiningMonth(null);
                
                try{
                    int id  = student.saveStudent();
                    
                    // saving image
                 try{   
                     File newPath = new File(Profile.profilePicPath+"/" + id);
                     if (newPath.exists()) {

                     } else {
                         newPath.mkdirs();
                     }

                     String[] list = newPath.list();
                     String picName = "1.jpg";
                     if (list != null) {
                         picName = list.length + ".jpg";
                     }

                     File f = new File(newPath, picName);
                     f.createNewFile();

                     Files.copy(new FileInputStream(imageOfEnquiry),
                             Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

                 }catch(Exception ex){
                     ex.printStackTrace();
                 }
                    
                    student.setStudentId(id);
                    enquiryStudentList.add(student);
                    studentTableEnquiry.refresh();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("studentfees.MainFrameController.saveStudent()");
                }
                //student.setStudentId((int)(new Random().nextDouble()*1000));
                ///studentList.add(student);
            }else{
                System.out.println(studentToUpdate + "student object");
                studentToUpdate.setStudentName(name);
                studentToUpdate.setFatherName(fname);
                studentToUpdate.setAddress(address);
                studentToUpdate.setSchool(school);
                studentToUpdate.setMobilNumber(mobil);
                studentToUpdate.setAddharNumber(aadhar);
                studentToUpdate.setBatch(batch);
                studentToUpdate.setGender(gender);
                studentToUpdate.setDob(dobsql);
                studentToUpdate.setJoiningMonth(joiningMonth);
                
                try{
                    
                    studentToUpdate.updateStudent();
                    Comparator com  = new Student.SortById();
                    Collections.sort(enquiryStudentList, com);
                    int i = Collections.binarySearch(enquiryStudentList ,  studentToUpdate , com);
                    enquiryStudentList.remove(i);
                    enquiryStudentList.add(i, studentToUpdate);
                    System.out.println(i + "found");
                    studentTableEnquiry.refresh();
                    
                    showDetailsOnDashboard(null);
                    dashboardTable.refresh();
                    System.out.println(studentToUpdate.getAddharNumber() + "student object");
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("studentfees.MainFrameController.saveStudent()");
                }
                saveStudentEnquiry.setText("Save");
                
                studentToUpdate = null;
                
            }
            resetStudentForm(null);
        }
        
    }

    @FXML
    private void saveBatch(ActionEvent event) {
             alert.setTitle("Save Batch");
             boolean flag = false;
            String name = batchName.getText();
            String fee = batchFee.getText();
            Month month = batchFormselectMonth.getSelectionModel().getSelectedItem();
            FeesMode feesMode = (FeesMode)(selectFeesMode.getSelectionModel().getSelectedItem());
            System.out.println("studentfees.MainFrameController.saveBatch()");
            System.out.println(feesMode);
            //System.out.println(feesMode.getIndex());
            if(name.trim().length()==0){
                alert.setContentText("Please enter Batch Name");
                alert.show();
            }else if(fee.trim().length()==0){
                alert.setContentText("enter Batch Fee");
                alert.show();
            }else if(feesMode==null){
                alert.setContentText("select Fees Mode");
                alert.show();
            }else{
                  if(feesMode==FeesMode.monthly){
                      if(month==null){
                          alert.setContentText("select Start month");
                          alert.show();
                      }else{
                          
                          flag = true;
                      }
                  }else{
                      flag = true;
                  }
            }
            if(flag){
                try {
                    if(saveBatch.getText().equalsIgnoreCase("save")){
                        Batch batch = new Batch(name, Integer.parseInt(fee));
                        batch.setFeesMode(feesMode);
                        batch.setStartMonth(month);
                        batch.setBatchId(batch.saveBatch());
                        batchList.add(batch);
                    }else{
                        batchToUpdate.setBatchFee(Integer.parseInt(fee));
                        batchToUpdate.setBatchName(name);
                        batchToUpdate.upadte();
                        batchTable.refresh();
                        saveBatch.setText("Save");
                        batchFormCancelButton.setVisible(false);
                        selectFeesMode.setDisable(false);
                    }
                    batchName.clear();
                    batchFee.clear();
                    batchName.requestFocus();
                } catch (NumberFormatException ex) {
                    alert.setContentText("only numbers are acceptable in batch fee");
                    alert.show();
                }catch (Exception ex) {
                    ex.printStackTrace();
                    alert.setTitle("Save Batch");
                    alert.setContentText("Can't save Batch At this time");
                    alert.show();
                }
                
            }
    }
    
    

    @FXML
    private void saveBatchOnEnterPress(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
            saveBatch(null);
        }
    }

    @FXML
    private void showAddStudentTab(ActionEvent event) {
        tabContainer.getSelectionModel().select(addStudentTab);
    }

    @FXML
    private void showAddBatchTab(ActionEvent event) {
        tabContainer.getSelectionModel().select(addBatchTab);
    }

    @FXML
    private void studentTableContextMenu(ContextMenuEvent event) {
        if(studentTable.getSelectionModel().getSelectedIndex()<0){
            studentTableContextMenu.hide();
        }
    }

    @FXML
    private void studentTableMouseDrag(MouseEvent event) {
    }

    @FXML
    private void studentTableMouseClick(MouseEvent event) {
       
    }
    
    
    @FXML
    private void editStudentInfo(ActionEvent event) {
        if(event.getSource() == studentTableEditEnquiry){
            Student s = (Student)(studentTableEnquiry.getSelectionModel().getSelectedItem());
            System.out.println(s + "student object");
            setEnquiryStudentDetailsOnTextField(s);
            enquirypic.setImage(s.getProfilePic());
        }else{
            Student s = (Student)(studentTable.getSelectionModel().getSelectedItem());
            System.out.println(s + "student object");
            setAdmittedStudentDetailsOnTextField(s);
        }
        
    }
    
    public void setAdmittedStudentDetailsOnTextField(Student s){
        studentToUpdate = s;
        System.out.println(studentToUpdate + "student object");
        studentName.setText(s.getStudentName());
        studentAddress.setText(s.getAddress());
        studentSchool.setText(s.getSchool());
        studentFatherName.setText(s.getFatherName());
        selectBatch.setValue(s.getBatch());
        try {
            studentDOB.setValue(s.getDob().toLocalDate());
        } catch (Exception e) {
            studentDOB.setValue(null);
        }
        
        studentAdharNumber.setText(s.getAddharNumber());
        studentMobil.setText(s.getMobilNumber());
        selectGender.setValue(s.getGender());
        saveStudent.setText("Update");
    }

    public void setEnquiryStudentDetailsOnTextField(Student s){
        studentToUpdate = s;
        System.out.println(studentToUpdate + "student object");
        studentName1.setText(s.getStudentName());
        studentAddress1.setText(s.getAddress());
        studentSchool1.setText(s.getSchool());
        studentFatherName1.setText(s.getFatherName());
        selectBatch1.setValue(s.getBatch());
        try {
            studentDOB1.setValue(s.getDob().toLocalDate());
        } catch (Exception e) {
            studentDOB1.setValue(null);
        }
        
        studentAdharNumber1.setText(s.getAddharNumber());
        studentMobil1.setText(s.getMobilNumber());
        selectGender1.setValue(s.getGender());
        saveStudentEnquiry.setText("Update");
    }
    @FXML
    private void deleteStudents(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        boolean flag = false;
        alert.setContentText("Do You Really Want to Delete Selected Student ? ");
        Optional<ButtonType> o = alert.showAndWait();
        if(o.get().equals(ButtonType.OK)){
            flag = true;
        }
        if(flag){
            ObservableList list = FXCollections.observableArrayList();
            if(event.getSource() == dashboardDeleteButton){
                Student s = (Student)(dashBoardSelectStudent.getSelectionModel().getSelectedItem());
                list.add(s);
            }else if(event.getSource() == studentTableDeleteEnquiry){
                list = studentTableEnquiry.getSelectionModel().getSelectedItems();
            }else{
                list = studentTable.getSelectionModel().getSelectedItems();
            }

            try {
                Student.delete(list);
                studentList.removeAll(list);
                enquiryStudentList.removeAll(list);
                studentDetails.clear();
                dashboardButtonBox.setVisible(false);
                dashboardButtonBox2.setVisible(false);
                dashboardTable.setVisible(false);
            } catch (Exception ex) {
                alert.setContentText("Can not delete student ...");
                alert.show();
            }
        }
        
    }

    @FXML
    private void dashBoardOpening(Event event) {
        pichartList.clear();
        pichartInitilization();
    }
    private void pichartEvents() {
        pichart.getData().forEach(data->{
            data.getNode().addEventFilter(MouseEvent.ANY, e->{
                if(e.getEventType()==MouseEvent.MOUSE_ENTERED){
                    chartDetail.setText((int)data.getPieValue()
                    +" Student in "+data.getName() +" Batch");
                }
                
            });
        });
    }

    @FXML
    private void removeTooltip(MouseEvent event) {
    }
    
    public void dashboardSetup(){
        System.out.println(batchList);
        System.out.println("studentfees.MainFrameController.dashboardSetup()");
        dashboardSelectBatch.setItems(batchList);
    }

    @FXML
    private void loadStudentsOnDashboard(ActionEvent event) {
        Batch batch = (Batch)(dashboardSelectBatch.getSelectionModel().getSelectedItem());
        System.out.println(batch);
        ObservableList st;
        try {
            st = Student.getStudents(batch);
            System.out.println(studentList);
            dashBoardSelectStudent.setItems(st);
        } catch (Exception ex) {
            alert.setContentText("Can not load Students");
            alert.setTitle("student loading failed");
            alert.show();
        }
    }
    private void fillDashboardData(Student s){
        try {
            
            studentDetails.clear();
            System.out.println(s);
            studentDetails.add(new Entry("Name", s.getStudentName()));
            studentDetails.add(new Entry("Id", s.getStudentId()));
            studentDetails.add(new Entry("Father Name", s.getFatherName()));
            studentDetails.add(new Entry("Batch", s.getBatch()));
            studentDetails.add(new Entry("Address", s.getAddress()));
            studentDetails.add(new Entry("School", s.getSchool()));
            dashboardTable.refresh();

            dashboardButtonBox.setVisible(true);
            dashboardButtonBox2.setVisible(true);
            dashboardTable.setVisible(true);
        } catch (Exception e) {
            dashboardButtonBox.setVisible(false);
            dashboardButtonBox2.setVisible(false);
            dashboardTable.setVisible(false);
            System.out.println("studentfees.MainFrameController.showDetailsOnDashboard()");
            System.out.println(e);
        }
    }
    @FXML
    private void showDetailsOnDashboard(ActionEvent event) {
        try{
            studentIdTextBoxOnDashboard.clear();
            Student s = (Student) (dashBoardSelectStudent.getSelectionModel().getSelectedItem());
            studentIdTextBoxOnDashboard.setText(s.getStudentId() + "");
            fillDashboardData(s);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void dashBoardEditButtonAction(ActionEvent event) {
        
        
        Student s = (Student)(dashBoardSelectStudent.getSelectionModel().getSelectedItem());
        setAdmittedStudentDetailsOnTextField(s);
        tabContainer.getSelectionModel().select(addStudentTab);
    }
        
    @FXML
    private void dashBoardSubmitFeeButtonAction(ActionEvent event) {
        Student student = (Student)(dashBoardSelectStudent.getSelectionModel().getSelectedItem());
        selectedStudent = student;
        setContentOnProfilePage(selectedStudent);
        tabContainer.getSelectionModel().select(feesPanelTab);
        
    }

    @FXML
    private void dashBoardDeleteButtonAction(ActionEvent event) {
        deleteStudents(event);
    }

    @FXML
    private void dashBoardCancelButtonAction(ActionEvent event) {
        dashboardTable.setVisible(false);
        dashboardButtonBox.setVisible(false);
        dashboardButtonBox2.setVisible(false);
    }

    private void exit(ActionEvent event) {
        Stage s = (Stage)(main.getScene().getWindow());
        s.close();
        
    }

    private void minimizeWindow(ActionEvent event) {
        Stage s = (Stage)(main.getScene().getWindow());
        s.setIconified(true);
    }

    private void maximizeWindow(ActionEvent event) {
        Stage s = (Stage)(main.getScene().getWindow());
        s.setMaximized(true);
    }

    @FXML
    private void loadProfile(ActionEvent event) {
        if(event.getSource() == feesPanelSelectBatch){
            Batch batch = (Batch)(feesPanelSelectBatch.getSelectionModel().getSelectedItem());
            System.out.println("studentfees.MainFrameController.loadProfile()");
            System.out.println(batch);
            try {
                System.out.println(Student.getStudents(batch));
                feesPanelSelectStudent.setItems(Student.getStudents(batch));
            } catch (Exception ex) {
//                ex.printStackTrace();
                  System.err.println(ex.getMessage());
            }
        }else if(event.getSource() == feesPanelSelectStudent){
            Student s = (Student)(feesPanelSelectStudent.getSelectionModel().getSelectedItem());
            selectedStudent = s;
            setContentOnProfilePage(s);
            
        }
    }
    
    private void setContentOnProfilePage(Student s){
        
        feesPanelChartBox.setVisible(false);
        boolean visible = s.getBatch().getFeesMode()==FeesMode.monthly;
            // visible pay panel

        feeDetailForMonthlyBatch.setVisible(visible);
        feeDetailForInstalmentBatch.setVisible(!visible);
        feePayPanelForMonthlyBatch.setVisible(visible);
        feePayPanelForInstalment.setVisible(!visible);
        feePayPanelForInstalment.setManaged(!visible);
        feePayPanelForMonthlyBatch.setManaged(visible);
        
        
        feesPanelStudentIdTextField.setText(s.getStudentId()+"");
        
            
        System.out.println("studentfees.MainFrameController.setContentOnGridPane() ");
        selectedStudent = s;
        profileNameLabel.setText(s.getStudentName());
            ArrayList<Entry> list = new ArrayList<>();
            pName.setText(s.getStudentName());
            pId.setText(s.getStudentId().toString());
            pFname.setText(s.getFatherName());
            pAddress.setText(s.getAddress());
            pMobil.setText(s.getMobilNumber());
            pAdhar.setText(s.getAddharNumber());
            if(s.getDob()!=null){
                pDOB.setText( s.getDob().toString());
            }else{
                pDOB.setText("-");
            }
            pGender.setText("-");
            if(s.getGender()!=null){
                pGender.setText(s.getGender().toString());
            }
            
            try{
                pJoiningMonth.setText(s.getJoiningMonth().toString());
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
            pSchool.setText(s.getSchool().toString());
            pBatch.setText(s.getBatch().toString());
            System.out.println("studentfees.MainFrameController.setContentOnGridPane() joining month" + s.getJoiningMonth());
            //joinedOnMonth.setText(s.getJoiningMonth().toString());
            System.out.println(s.getBatch().getFeesMode());
            if(s.getBatch().getFeesMode().getIndex()==FeesMode.monthly.getIndex()){
                System.out.println("True you are monthy");
                int joiningIndex  = s.getJoiningMonth().getIndex();
                int batchStartIndex  = s.getBatch().getStartMonth().getIndex();
                int count = 0;
                   for( int i = joiningIndex-1;i<12;i++){
                        try {
                            if(MonthlyFee.isPaid(months.get(i),selectedStudent,selectedStudent.getBatch())){
                                monthCheckBoxes.get(count).setSelected(true);
                            }else{
                                monthCheckBoxes.get(count).setSelected(false);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                       monthCheckBoxes.get(count).setText(months.get(i).toString());
                       System.out.println("setting text");
                       count++;
                   }
                   for( int i = 0;i<joiningIndex-1;i++){
                       monthCheckBoxes.get(count).setText(months.get(i).toString());
                       System.out.println("setting text");
                       try {
                            if(MonthlyFee.isPaid(months.get(i),selectedStudent,selectedStudent.getBatch())){
                                monthCheckBoxes.get(count).setSelected(true);
                            }else{
                                monthCheckBoxes.get(count).setSelected(false);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                       count++;
                   }
                
            }else{
                ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
                ArrayList<InstalmentFee> details = InstalmentFee.getDetails(selectedStudent, selectedStudent.getBatch());
                Iterator<InstalmentFee> it = details.iterator();
                int remain= 0;
                int submitted= 0;
                int total = selectedStudent.getBatch().getBatchFee();
                while(it.hasNext()){
                    InstalmentFee xy = it.next();
                    submitted= submitted+xy.getAmount();
                }
                remain = total-submitted;
                PieChart.Data data1 = new PieChart.Data("Paid [ Rs."+submitted+" ]" , submitted);
                PieChart.Data data2 = new PieChart.Data("Remaining [ Rs."+remain+" ]" , remain);
                data.addAll(data1 , data2);
                instalmentFeesDetailChart.setData(data);
            }
            
            try{
                profilepic.setImage(selectedStudent.getProfilePic());
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }

    @FXML
    private void loadProfileById(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
            String id = feesPanelStudentIdTextField.getText();
            try{
                Integer sid = Integer.parseInt(id);
                Student s = Student.getStudent(sid);
                selectedStudent = s;
                if(s==null){
                    alert.setTitle("Student");
                    alert.setContentText("Student Not Found or invalid ID");
                    alert.show();
                    profileNameLabel.setText("Student Name");
                }else{
                    System.out.println("studentfees.MainFrameController.loadProfileById() feesmode "+s.getBatch().getFeesMode() );
                    setContentOnProfilePage(s);
                }
            }catch(NumberFormatException ex){
                alert.setTitle("Invalid Id");
                alert.setContentText("Enter numaric ID");
                alert.show();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }

    @FXML
    private void resetStudentForm(ActionEvent event) {
        
            studentName.clear();
            studentFatherName.clear();
            studentAddress.clear();
            studentAddress.clear();
            studentName.clear();
            studentAdharNumber.clear();
            studentMobil.clear();
            studentSchool.clear();
            selectBatch.setValue(null);
            selectGender.setValue(null);
            studentDOB.setValue(null);
            
            studentName1.clear();
            studentFatherName1.clear();
            studentAddress1.clear();
            studentAddress1.clear();
            studentName1.clear();
            studentAdharNumber1.clear();
            studentMobil1.clear();
            studentSchool1.clear();
            selectBatch1.setValue(null);
            selectGender1.setValue(null);
            studentDOB1.setValue(null);
            
            saveStudent.setText("Save");
            saveStudentEnquiry.setText("Save");
    }

    @FXML
    private void editBatch(ActionEvent event) {
        Batch batch = (Batch)(batchTable.getSelectionModel().getSelectedItem());
        batchToUpdate = batch;
        batchName.setText(batch.getBatchName());
        batchFee.setText(batch.getBatchFee()+"");
        selectFeesMode.setValue(batch.getFeesMode());
        selectFeesMode.setDisable(true);
        saveBatch.setText("Update");
        batchFormCancelButton.setVisible(true);
    }

    @FXML
    private void deleteBatch(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                            "if you delete batch , all student of this batch will be deleted , you cant undo ! Are you sure Want to Delete Batch ? ",
                            ButtonType.YES, ButtonType.CANCEL);
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ObservableList<Batch> batches = (ObservableList<Batch>) (batchTable.getSelectionModel().getSelectedItems());
                Batch.delete(batches);
                batchList.removeAll(batches);
                studentList.clear();
                studentList.addAll(Student.getAdmittedStudents());        
                return null;
            }
        };
        task.setOnFailed((evv) -> {
            alert.setTitle("Deletion failed");
            alert.setContentText("Cant delete batch .. sorry");
            alert.show();
            task.getException().printStackTrace();
        });
        if (a.showAndWait().get().equals(ButtonType.YES)) {
            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        }
        
    }

    @FXML
    private void batchFormButtonsAction(ActionEvent event) {
        selectFeesMode.setDisable(false);
        batchFormCancelButton.setVisible(false);
        batchName.clear();
           saveBatch.setText("Save");
        batchFee.clear();
        selectFeesMode.setValue(null);
    }
    
    private void setItemsOnSelectMonthComboBox(){
        
        batchFormselectMonth.setItems(months);
        studentJoiningMonth.setItems(months);
        selectMonthToSubmitFee.setItems(months);
    }
    
    private void fillMonthList(){
        
        months.add(Month.january);
        months.add(Month.february);
        months.add(Month.march);
        months.add(Month.april);
        months.add(Month.may);
        months.add(Month.june);
        months.add(Month.july);
        months.add(Month.august);
        months.add(Month.september);
        months.add(Month.october);
        months.add(Month.november);
        months.add(Month.december);
        
        monthCheckBoxes.addAll(month1,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12 );
    }

    @FXML
    private void submitFeeForMonthyBatch(ActionEvent event) {
        Month month = selectMonthToSubmitFee.getSelectionModel().getSelectedItem();
        
        Date date  = new Date(new java.util.Date().getTime());
        System.err.println(dateofSubmissionofMonthly.getValue());
        if(dateofSubmissionofMonthly.getValue()!=null){
             date = Date.valueOf(dateofSubmissionofMonthly.getValue());
        }
        if(month==null){
            alert.setContentText("Select Month");
            alert.show();
        }else{
        
            try {
                System.out.println(MonthlyFee.isPaid(month, selectedStudent, selectedStudent.getBatch()) + "Paid ");
                if(!MonthlyFee.isPaid(month, selectedStudent, selectedStudent.getBatch())){
                    MonthlyFee m = new MonthlyFee();
                    m.setBatch(selectedStudent.getBatch());
                    m.setDate(date);
                    m.setMonth(month);
                    m.setStudent(selectedStudent);
                    m.save();
                    setContentOnProfilePage(selectedStudent);
                    selectMonthToSubmitFee.setValue(null);
                }else{
                    alert.setContentText("Already paid");
                    alert.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void validateAmount(KeyEvent event) {
         String s = amountForInstalment.getText();
         amountForInstalment.getStyleClass().remove("text-field-error");
         if(s.trim().length()>0)
            try{
                System.err.println(s);
                Integer.parseInt(s);
            }catch(NumberFormatException ex){
                amountForInstalment.getStyleClass().add("text-field-error");
            }
         
         if(event.getCode() == KeyCode.ENTER){
             payFeeInstalment(null);
         }
    }

    @FXML
    private void payFeeInstalment(ActionEvent event) {
        String amountString = amountForInstalment.getText().trim();
        
         Date date  = new Date(new java.util.Date().getTime());
        System.err.println(dateOfSubmissionOfInstallment.getValue());
        if(dateofSubmissionofMonthly.getValue()!=null){
             date = Date.valueOf(dateOfSubmissionOfInstallment.getValue());
        }
        
        if(amountString.isEmpty()){
            amountForInstalment.getStyleClass().add("text-field-error");
        }else{
            amountForInstalment.getStyleClass().remove("text-field-error");
            
            try {
                int amount = Integer.parseInt(amountString);
                InstalmentFee in = new InstalmentFee(selectedStudent,
                        selectedStudent.getBatch(),
                        date,
                        amount);
                int totalPaid = Student.getTotalPaidFee(selectedStudent);
                int batchfee = selectedStudent.getBatch().getBatchFee();
                if(totalPaid>=batchfee){
                    Notifications.create().position(Pos.CENTER).
                            text("Student's Full Fees is Deposited").
                            title("Already Paid").showInformation();
                }else if((totalPaid+amount)>batchfee){
                    Notifications.create().position(Pos.CENTER).
                            text("You Are Submitting More Than Remaining Fee , Remaining fee is Rs."+(batchfee-totalPaid)).
                            title("Submitting More").showInformation();
                            
                }else{
                    in.save();
                    setContentOnProfilePage(selectedStudent);
                    amountForInstalment.clear();
                }
                
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                Notifications.create().text("Enter Valid Amount !! ").title("Amount").
                        position(Pos.CENTER).hideAfter(Duration.seconds(2)).showWarning();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void searchStudents(KeyEvent event) {
        JFXTextField search = (JFXTextField)event.getSource();
        String text = search.getText();
        clearStudentSearchButton.setVisible(true);
        if(text.trim().isEmpty()){
           clearStudentSearchButton.setVisible(false);
        }
        
        Task searchEntask = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    ObservableList<Student> list = null;
                    synchronized (this) {
                        if (!text.trim().isEmpty()) {
                            list = Student.getEnquiryStudents(text);
                        } else {
                            list = Student.getEnquiryStudents(0, 100);
                        }
                    }
                    enquiryStudentList.clear();
                    enquiryStudentList.addAll(list);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
        Task searchAdmittedtask = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    ObservableList<Student> list = null;
                    synchronized (this) {
                        if (!text.trim().isEmpty()) {
                            list = Student.getStudents(text);
                        } else {
                            list = Student.getAdmittedStudents(0, 100);
                        }
                    }
                    studentList.clear();
                    studentList.addAll(list);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
            
        if(studentSearchBoxEnquiry == search){
            System.out.println("controllers.MainFrameController.searchStudents()" + "enquory search" );
            new Thread(searchEntask).start();
        }else{
            new Thread(searchAdmittedtask).start();
        }  
            
    }

    @FXML
    private void clearSearch(MouseEvent event) {
        studentSearchBox.setText("");
        searchStudents(new KeyEvent(studentSearchBox, null, null, null, null, null,
                true, true, true, true));
    }

    @FXML
    private void loadPreviousPageStudent(ActionEvent event) {
        
        try {
            ObservableList<Student> students = Student.loadPreviousAdmitted();
            System.out.println(Student.rowShown);
            if(students.isEmpty()){
                alert.setTitle("Students");
                alert.setContentText("No more STudents To show");
                alert.show();
            }else{
                studentList.clear();
                studentList.addAll(students);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void loadNextPageStudent(ActionEvent event) {
        
        try {
            ObservableList<Student> students = Student.loadNextAdmitted();
            System.err.println(students);
            loadPreviousStudents.setVisible(true);
            if(students.isEmpty()){
                alert.setTitle("Students");
                alert.setContentText("No more STudents To show");
                alert.show();
            }else{
                studentList.clear();
                studentList.addAll(students);
                       
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void payFeeRedirect(ActionEvent event) {
        Student student = (Student)(studentTable.getSelectionModel().getSelectedItem());
        selectedStudent = student;
        setContentOnProfilePage(selectedStudent);
        tabContainer.getSelectionModel().select(feesPanelTab);
    }

    @FXML
    private void markAsAdmitted(ActionEvent event) {
        
        Platform.runLater(() -> {
            ObservableList<Student> students  = (studentTableEnquiry.getSelectionModel().getSelectedItems());
            Iterator<Student> it = students.iterator();
                while(it.hasNext()){
                    Student s = it.next();
                    System.err.println(s.markAdmitted());
                }
                studentList.addAll(students);
            enquiryStudentList.removeAll(students);
        });
        
    }

    @FXML
    private void markAsEnquiry(ActionEvent event) {
        Task tsk = new Task() {
            @Override
            protected Object call() throws Exception {
                ObservableList<Student> students = (studentTable.getSelectionModel().getSelectedItems());
                if (students == null) {
                    return null;
                }
                Iterator<Student> it = students.iterator();
                while (it.hasNext()) {
                    Student s = it.next();
                    if (s != null) {

                        System.err.println(s.markEnquiry());
                    }
                }

                enquiryStudentList.addAll(students);
                studentList.removeAll(students);
                studentTableEnquiry.refresh();
                   return null;
            }
        };
        
        new Thread(tsk).start();
               
    }

    @FXML
    private void loadPreviousPageStudentEnquiry(ActionEvent event) {
        try {
            ObservableList<Student> students = Student.loadPreviousEnquiry();
            if(students.isEmpty()){
                alert.setTitle("Students");
                alert.setContentText("No More Students To Show");
                alert.show();
            }else{
               enquiryStudentList.clear();
                enquiryStudentList.addAll(students);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void loadNextPageStudentEnquiry(ActionEvent event) {
        try {
            ObservableList<Student> students = Student.loadNextEnquiry();
            System.err.println(students);
            loadPreviousStudentsEnquiry.setVisible(true);
            if(students.isEmpty()){
                alert.setTitle("Students");
                alert.setContentText("No more STudents To show");
                alert.show();
            }else{
                enquiryStudentList.clear();
                enquiryStudentList.addAll(students);
                       
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void showContactUsForm(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://goo.gl/forms/Ar0TWhMGr0lTBmX62"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void showDetailsInstalment(ActionEvent event){
        if(selectedStudent==null){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/FeesDetail.fxml"));
       try{
            Parent root =  loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getFeesDetailsCss()).toExternalForm());
            FeesDetailsController con = (FeesDetailsController)loader.getController();
            con.setStudent(selectedStudent);
            stage.show();
       }catch(Exception ex){
           ex.printStackTrace();
       }
       
    }

    public MenuItem getSetPasswordMenuItem() {
        return setPasswordMenuItem;
    }
    
    

    @FXML
    private void feePanelOpning(Event event) {
        
    }
    
    
    

    @FXML
    private void setPassword(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PasswordFXML.fxml"));
            Parent root = loader.load();
            PasswordFXMLController con = (PasswordFXMLController) (loader.getController());
            
            if(((MenuItem)(event.getSource())).getText().equalsIgnoreCase("set password")){
                con.showAsSetPassword();
            }else if(((MenuItem)(event.getSource())) == removePasswordMenuItem){
                con.showAsRemovePassword();
            }else{
                con.showAsChangePassword();
            }
            
            con.setSetPassword(setPasswordMenuItem);
            con.setRemovePassword(removePasswordMenuItem);
            
            
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.setTitle("Password Settings");
            Scene scene = new Scene(root);
             scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getPasswordFxmlCss()).toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        }catch(Exception ex ){
            ex.printStackTrace();
        }
    }

    public MenuItem getRemovePasswordMenuItem() {
        return removePasswordMenuItem;
    }
    
    

    @FXML
    private void removePassword(ActionEvent event) {
        
        setPassword(event);
        
    }

    @FXML
    private void showDataOnIncomeChart(ActionEvent event) {
        Platform.runLater(() -> {
             LocalDate from = fromDateForIncome.getValue();
            LocalDate to = toDateForIncome.getValue();
            Alert al = new Alert(Alert.AlertType.WARNING, "Please enter From Date !", ButtonType.OK);
            boolean flag = true;
            if(from==null){
                flag = false;
                al.show();
                
            }

            if(to == null){
                to = new Date(new java.util.Date().getTime()).toLocalDate();
            }
            
            if(flag){
            
                XYChart.Series series = new XYChart.Series();
                ObservableList<HashMap> data1 = InstalmentFee.getIncomeBatchVise(Date.valueOf(from), Date.valueOf(to));
                ObservableList<HashMap> data2 = MonthlyFee.getIncomeBatchVise(Date.valueOf(from), Date.valueOf(to));
                ObservableList<HashMap> data = FXCollections.observableArrayList();
                data.addAll(data1);
                data.addAll(data2);
                System.err.println(from);
                System.err.println(to);
                System.err.println(data1);
                System.err.println(data2);
                System.err.println(data);
                Iterator<HashMap> itr = data.iterator();
                while (itr.hasNext()) {
                    HashMap nextElement = itr.next();
                    series.getData().addAll(new XYChart.Data<>(nextElement.get("batch").toString(), nextElement.get("income")));
                }
                incomeChartOnAnlytics.getData().clear();
                incomeChartOnAnlytics.getData().add(series);
            
            }
        });
        
    }

    @FXML
    private void sendDatabaseToGmail(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/sendEmailBox.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
             scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getSendMailCss()).toExternalForm());
            stage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void openMyProfile(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyProfileFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
             scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getMyProfileCss()).toExternalForm());
            stage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void export(ActionEvent event) {
        FileChooser saveTo = new FileChooser();
        saveTo.setTitle("Save to");
        saveTo.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel File", "*.xls"));
        saveTo.setInitialFileName("database.xls");
        File file = saveTo.showSaveDialog(studentName.getScene().getWindow());
        
        String name = file.toString().split("\\.")[0];
        final File finalfile = new File(name+".xls");
        System.err.println(file);
        final SimpleStringProperty info = new SimpleStringProperty("0 %");
        final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);
        final SimpleStringProperty heading = new SimpleStringProperty("Exporting");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ExportImportProgress.fxml"));
            Parent root = loader.load();
            final Stage stage = new Stage();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().
                    getResource(Theme.getExportImportProgressCss()).toExternalForm());
            ExportImportProgressController con = (ExportImportProgressController)(loader.getController());
            
                
             Task task = new Task() {
                 @Override
                 protected Object call() throws Exception{
                         ObservableList<Batch> batches = Batch.getBatches();
                        Iterator<Batch> itr = batches.iterator();
                        Export export = new Export();
                        double totalBatch = batches.size();
                        double done = 0;
                        while(itr.hasNext()){
                            Batch b = itr.next();
                            
                            final ObservableList<Student> students = Student.getStudents(b);
                            export.export(finalfile, b, students);
                            done++;
                            progress.set(done/totalBatch);
//                          info.set(((done/totalBatch)*100)+" %");
                            System.err.println(progress.get() + "____");
                        }
                     return null;
                 }

                @Override
                protected void updateMessage(String message) {
                    super.updateMessage(message); //To change body of generated methods, choose Tools | Templates.
                }
                 
                 
             };

             task.setOnScheduled((sv) -> {
                 System.out.println("Going to Save Data....");
                 stage.show();
             });

             task.setOnRunning((bbbb) -> {
                 System.out.println(info);
             });

             task.setOnSucceeded((eev) -> {
                 System.out.println("Saved");
                 heading.setValue("Exported");
                 heading.set("Exported Successfully");
                 info.set("100 % ");
                 
                 Notifications.create().title("Export").text("Data exported successfully !! ").show();
                         
             });

             task.setOnFailed((eeev) -> {
                 System.out.println("Faild ..."+ eeev.getSource());
                 task.getException().printStackTrace();
                 heading.set("Export Failed.");
                 //stage.close();
             });
             
             
             con.getProcesser().progressProperty().bind(progress);
             con.getHeading().textProperty().bindBidirectional(heading);
             Thread t = new Thread(task);
             t.setDaemon(true);
             t.setPriority(Thread.MAX_PRIORITY);
             t.start();
             stage.setOnCloseRequest((vv) -> {
                 System.out.println("controllers.MainFrameController.export() Closing ....." );
                 task.cancel(true);
             });
        }catch(Exception ex){
            ex.printStackTrace();
        }

        
    }

    @FXML
    private void filterByBatch(ActionEvent event) {
        try{
            Batch b = selectBatchToFilter.getSelectionModel().getSelectedItem();
            studentList.clear();
            studentList.addAll(Student.getStudents(b));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void refreshStudentTable(ActionEvent event) {
        try{
            studentList.clear();
            studentList.addAll(Student.getAdmittedStudents());
            Student.refresh();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void showReceipts(ActionEvent event) {
        selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        showDetailsInstalment(event);
    }

    @FXML
    private void showProfilePic(MouseEvent event) {
        try{
                File file = new File("student_photos");
                String[] files = file.list();
                Arrays.sort(files);
                int i = Arrays.binarySearch(files, selectedStudent.getStudentId()+".jpg");
                
                if(i>=0){
                    System.out.println(files[i]);
                    Desktop.getDesktop().open(new File(file , files[i]).getAbsoluteFile());
                }else{
                }
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
        
    }
    File pp = null;
    @FXML
    private void editProfilePic(MouseEvent event) {
        
        try {
            FileChooser chooseImage = new FileChooser();
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("All images", "*.jpg" , "*.png" , "*.jpeg" , "*.gif" ));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG images", "*.jpg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG images", "*.jpeg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF images", "*.gif"));
            pp = chooseImage.showOpenDialog(studentFatherName.getScene().getWindow());
            
            
            
//            String extension = "";

//            int i = file.toString().lastIndexOf('.');
//            if (i > 0) {
//                extension = file.toString().substring(i + 1);
//            }
            profilepic.setImage(new Image(new FileInputStream(pp)));
            
            File newPath = new File(Profile.profilePicPath+"/"+selectedStudent.getStudentId());
            if(newPath.exists()){
                
            }else{
                newPath.mkdirs();
            }
            
            String[] list = newPath.list();
            String picName = "1.jpg";
            if(list!=null){
                picName = list.length+".jpg";
            }
            
            File f = new File(newPath ,picName);
            f.createNewFile();
            
            Files.copy(new FileInputStream(pp),
                    Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            
        } catch (FileNotFoundException ex){
            
            ex.printStackTrace();
            alert.setContentText("Can not set student photo at this time ! ");
            alert.setTitle("Try Later");
            alert.show();
        }catch(IOException ex){
            alert.setContentText("Can not set student photo at this time ! ");
            alert.setTitle("Try Later");
            alert.show();
            ex.printStackTrace();
        }
    
    }

    @FXML
    private void backupDatabase(ActionEvent event){
        FileChooser chooseImage = new FileChooser();
        chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database File", "*.db"));
        chooseImage.setInitialFileName("database.db");

        final File file = chooseImage.showSaveDialog(studentFatherName.getScene().getWindow());
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                

                File f = new File("database.db");
                file.createNewFile();
                FileInputStream fis = new FileInputStream(f);
                FileOutputStream fos = new FileOutputStream(file);
                
                byte[] b = new byte[1024];
                while(fis.read(b)!=-1){
                    fos.write(b);
                }
                fos.close();
                fis.close();
                
                System.err.println(f.exists());
                Files.copy(new FileInputStream(f),
                        Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                return null;
            }
        };
       
        if (file != null) {
            new Thread(task).start();
        }        
    }

    @FXML
    private void restoreDatabase(ActionEvent event) {
        try {
            FileChooser chooseImage = new FileChooser();
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database File", "*.db"));
            File file = chooseImage.showOpenDialog(studentFatherName.getScene().getWindow());
            
            File f = new File("database.db");
            System.err.println(f.exists());
            Files.copy(new FileInputStream(file),
                    Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            refreshData();
            //((Stage)(amountForInstalment.getScene().getWindow())).close();
            //new StartApp().start(new Stage());
        } catch (Exception ex) {
           alert.setTitle("Try Later");
           alert.setContentText("Try Again After Sometime");
           alert.show();
            ex.printStackTrace();
        }
    }
    
    public void refreshData(){
        Platform.runLater(()->{
                try {
                    
                    ObservableList batches = Batch.getBatches();
                    ObservableList students = Student.getAdmittedStudents();
                    ObservableList enquiruStudents = Student.getEnquiryStudents();
                    
                    getBatchList().clear();
                    getStudentList().clear();
                    getEnquiryStudentList().clear();
                    pichartList.clear();
                    monthlyincomeChart.getData().clear();
                    
                    getBatchList().addAll(batches);
                    getStudentList().addAll(students);
                    getEnquiryStudentList().addAll(enquiruStudents);
                    
                    pichartInitilization();
                    dashboardSetup();
                    initializeMonthIncomeChart();
                    
                    
                    System.err.println("Password is : " + new Password().getPassword());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        });
    }
    
    public void setSelectedTheme(){
        CheckMenuItem[] arr = new CheckMenuItem[5];
        arr[1] = blackTheme;
        arr[0] = chocolateTheme;
        arr[2] = blueTheme;
        arr[3] = theme4;
        arr[4] = OfficeTheme;
        int n = Theme.getThemeCode();
        for(int i = 0 ; i < 5 ; i++){
           arr[i].setSelected(false);
        }
        arr[n-1].setSelected(true);
    }

    @FXML
    private void changeTheme(ActionEvent event) {
        CheckMenuItem ci = (CheckMenuItem)(event.getSource());
        if(ci==blackTheme){
            System.err.println("saving theme 1 ");
            Theme.setThemeCode(2);
        }else if(ci==chocolateTheme){
            System.err.println("saving theme 2 ");
            Theme.setThemeCode(1);
        }else if(ci==blueTheme){
            System.err.println("saving theme 3 ");
            Theme.setThemeCode(3);
        }else if(ci==theme4){
            System.err.println("saving theme 4 ");
            Theme.setThemeCode(4);
        }else if(ci==OfficeTheme){
            System.err.println("saving theme 4 ");
            Theme.setThemeCode(5);
        }
        
        ((Stage)(amountForInstalment.getScene().getWindow())).close();
        try {
            new StartApp().start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
    }

    @FXML
    private void mobileNumberValidation(KeyEvent event) {
        synchronized(this){
            JFXTextField t = ((JFXTextField) (event.getSource()));
            if (t.getText().length() > 10) {
                t.setText(t.getText().substring(0, 10));
                t.selectPositionCaret(t.getText().length());
                t.deselect();
            }
        }
    }

    @FXML
    private void adharNumberValidation(KeyEvent event) {
        synchronized(this){
            JFXTextField t = ((JFXTextField) (event.getSource()));
            if (t.getText().length() > 12) {
                t.setText(t.getText().substring(0, 12));
                t.selectPositionCaret(t.getText().length());
                t.deselect();
            }
        }
    }

    @FXML
    private void showDetailsOnDashboardUsingId(KeyEvent event) {
    
        if(event.getCode() != KeyCode.ENTER){
            dashBoardSelectStudent.setValue(null);
            return ;
        }
        
        String id = studentIdTextBoxOnDashboard.getText();
        try{
            int idd = Integer.parseInt(id);
            Student s = Student.getStudent(idd);
            if(s == null){
                throw  new IOException();
            }
            
            fillDashboardData(s);
            dashBoardSelectStudent.setValue(s);
        }catch(NumberFormatException ex){
            Notifications.create().title("Invalid Id").text("Student Id is Not Valid")
                    .position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
        }catch(Exception ex){
            dashBoardSelectStudent.setValue(null);
            Notifications.create().title("Student").text("No Student With Student Id "+ id).
                    position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
        }
    }

    @FXML
    private void uploadEnquiryImage(MouseEvent event) {
        
        try {
            FileChooser chooseImage = new FileChooser();
            
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png", "*.jpeg", "*.gif"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG images", "*.jpg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG images", "*.jpeg"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));
            chooseImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF images", "*.gif"));
            imageOfEnquiry = chooseImage.showOpenDialog(studentFatherName.getScene().getWindow());
            enquirypic.setImage(new Image(new FileInputStream(imageOfEnquiry)));
            
            
            if(studentToUpdate!=null){
                
                File newPath = new File(Profile.profilePicPath+"/"+selectedStudent.getStudentId());
                if(newPath.exists()){

                }else{
                    newPath.mkdirs();
                }

                String[] list = newPath.list();
                String picName = "1.jpg";
                if(list!=null){
                    picName = list.length+".jpg";
                }

                File f = new File(newPath ,picName);
                f.createNewFile();

                Files.copy(new FileInputStream(imageOfEnquiry),
                        Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void removeEnquiryDp(MouseEvent event) {
        if(studentToUpdate!=null){
            enquirypic.setImage(new Image(getClass().getResourceAsStream("/images/avtar.png")));
            System.out.println("controllers.MainFrameController.removeEnquiryDp()"  + "deleteing file");
            System.gc();
            File file = new File(Profile.profilePicPath+"/"+studentToUpdate.getStudentId());
            File[] fss = file.listFiles();
            
            for(int i = 0 ; i < fss.length ; i++){
                fss[i].delete();
                fss[i].deleteOnExit();
            }
            file.deleteOnExit();
        }
    }

    @FXML
    private void howToUse(ActionEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/playlist?list=PL5vZiwEHLMJbxjBaZ_I6i2yxd8NtNJ19O")
            );
        }catch(Exception ex){
            ex.printStackTrace();
        }
   }

    @FXML
    private void showAbout(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/AboutUsFXML.fxml"));
            Stage s = new Stage();
            Scene scene = new Scene(root);
            s.setScene(scene);
            s.setResizable(false);
            s.setAlwaysOnTop(true);
            s.show();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void studentTableEdit(TableColumn.CellEditEvent event) {
        Student s = (Student)event.getRowValue();
        String newValue = (String)event.getNewValue();
        String oldValue = (String)event.getOldValue();
        
        
        
        try{
            if(event.getSource()==studentNameColumn){
                s.setStudentName(newValue);
            }else if(event.getSource()==studentFatherNameColumn){
                s.setFatherName(newValue);
            }
            else if(event.getSource()==studentMobilColumn){
                s.setMobilNumber(newValue);
            }
            else if(event.getSource()==studentAddressColumn){
                s.setAddress(newValue);
            }
            s.updateStudent();
        }catch(Exception ex){
            ex.printStackTrace();
            if (event.getSource() == studentNameColumn) {
                s.setStudentName(oldValue);
            } else if (event.getSource() == studentFatherNameColumn) {
                s.setFatherName(oldValue);
            }else if(event.getSource()==studentMobilColumn){
                s.setMobilNumber(oldValue);
            }
            else if(event.getSource()==studentAddressColumn){
                s.setAddress(oldValue);
            }
            studentTable.refresh();
        }
    }
}
