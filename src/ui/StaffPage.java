package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.DBhandler;
import tt.Staff;

public class StaffPage extends Application{
	private TableView tvStaffs;
	private TableColumn<Staff, Integer>idCol;
	private TableColumn<Staff, String>nameCol;
	private TableColumn<Staff, String>emailCol;
	private TableColumn<Staff, String>posCol;
	private TableColumn<Staff, String>edateCol;

	private BorderPane mainPane;
	private GridPane updatePane;
	private FlowPane miniPane;
	private Stage stage;
	private ArrayList<Staff> al;
	private FilteredList<Staff>fl;
	private Label lkeyword,lname,lemail,lposition;
	private TextField tsearch,tname,temail;
	private ComboBox<String> sposition;
	private Button btnUpdate,btnClear;
	private FlowPane searchPane;
	
	public void createSearchBar()
	{
		lkeyword=new Label("Search");
		tsearch=new TextField();
		tsearch.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER)
			{
				filtering();
			}
		});
		searchPane=new FlowPane(20,20,lkeyword,tsearch);
		searchPane.setAlignment(Pos.CENTER_RIGHT);
		searchPane.setPadding(new Insets(10));
		
	}
	public void createUpdatePane()
	{
		lname=new Label("Name");
		lemail=new Label("Email");
		lposition=new Label("Position");
		
		tname=new TextField();
		temail=new TextField();
		ArrayList<String>sp=new ArrayList<>();
		sp.add("Admin");
		sp.add("Manager");
		sp.add("Receptionist");
		sposition=new ComboBox<>(FXCollections.observableArrayList(sp));
		btnClear=new Button("Clear");
		btnUpdate=new Button("Update");
	}
	public void createTable()
	{
		tvStaffs=new TableView<>();
		
		idCol=new TableColumn<Staff,Integer>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("sid"));
		
		nameCol=new TableColumn<Staff,String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("sname"));
		
		emailCol=new TableColumn<Staff,String>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("semail"));
		
		posCol=new TableColumn<Staff,String>("Position");
		posCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("sposition"));
		
		edateCol=new TableColumn<Staff,String>("Enroll Date");
		edateCol.setCellValueFactory(new PropertyValueFactory<Staff,String>("enrollDate"));
		
		tvStaffs.getColumns().add(idCol);
		tvStaffs.getColumns().add(nameCol);
		tvStaffs.getColumns().add(emailCol);
		tvStaffs.getColumns().add(posCol);
		tvStaffs.getColumns().add(edateCol);
		tvStaffs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setData();
	}

	public void filtering()
	{
		fl=new FilteredList<Staff>(FXCollections.observableArrayList(al));
		fl.setPredicate(new Predicate<Staff>() 
		{
			public boolean test(Staff s)
			{
				String email=s.getSemail().toLowerCase();
				String position=s.getSposition().toLowerCase();
				String sname=s.getSname().toLowerCase();
				String value=tsearch.getText();
				if(value.length()==0)
				{
					return true;
				}
				if(value.equals(sname)|| value.equals(email) ||value.equals(position))
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
		});
		tvStaffs.getItems().clear();
		tvStaffs.getItems().addAll(fl);
	}
	public void setData()
	{
		al=DBhandler.getAllStaffs();
		fl=new FilteredList<Staff>(FXCollections.observableArrayList(al));
		tvStaffs.getItems().addAll(fl);
	}
	public void setLayout()
	{
		updatePane=new GridPane();
		updatePane.add(lname, 0, 0);
		updatePane.add(tname, 1, 0);
		updatePane.add(lemail, 0, 1);
		updatePane.add(temail, 1, 1);
		updatePane.add(lposition, 0, 2);
		updatePane.add(sposition, 1, 2);
		updatePane.add(btnClear, 0, 3);
		updatePane.add(btnUpdate, 1, 3);
		
		miniPane=new FlowPane(20,20,tvStaffs,updatePane);
		mainPane=new BorderPane();
		mainPane.setTop(searchPane);
		mainPane.setCenter(miniPane);
	}
	public void setStyle()
	{
		lkeyword.getStyleClass().add("searchkey");
		searchPane.getStyleClass().add("searchbackground");
		lname.getStyleClass().add("setstyleforupdate");
		lemail.getStyleClass().add("setstyleforupdate");
		lposition.getStyleClass().add("setstyleforupdate");
		btnUpdate.getStyleClass().add("lrBtn");
		btnClear.getStyleClass().add("clearBtn");
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage st) throws Exception {
		stage=st;
		createTable();
		createUpdatePane();
		createSearchBar();
		setLayout();
		Scene sc=new Scene(mainPane);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		stage.setScene(sc);
		stage.setTitle("StaffUI");
		stage.show();
		
	}
	public BorderPane getMainPane()
	{
		createUpdatePane();
		createSearchBar();
		createTable();
		setLayout();
		setStyle();
		return mainPane;
	}

}
