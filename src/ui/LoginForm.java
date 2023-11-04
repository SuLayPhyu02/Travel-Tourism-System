package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DBhandler;

public class LoginForm extends Application{
	
	private Label lemail,lpassword,lposition,lerr;
	private VBox errBox;
	private TextField temail;
	private ToggleGroup tgPosition;
	private RadioButton rAdmin,rManager,rReceptionist;
	private PasswordField ppassword;
	private Button btnLogin,btnClear;
	private GridPane gp;
	private TilePane tp;
	private FlowPane btnPane;
	private HBox phPane;
	private VBox minBox;
	private BorderPane mainPane;
	
	private Stage stage,st2;
	private Image img;
	private ImageView imgV;
	private static int staffID=-1;
	private static String staffEmail;
	private static String staffName;
	private static String staffImage;
	
	private Scene sc;
	public void createNode()
	{
		lemail=new Label("Email");
		lpassword=new Label("Password");
		lposition=new Label("Position");
		
		lerr=new Label();
		
		tgPosition=new ToggleGroup();
		rAdmin=new RadioButton("Admin");
		rAdmin.setSelected(true);
		rAdmin.setToggleGroup(tgPosition);
		rManager=new RadioButton("Manager");
		rManager.setToggleGroup(tgPosition);
		rReceptionist=new RadioButton("Receptionsit");
		rReceptionist.setToggleGroup(tgPosition);
		
		temail=new TextField();
		ppassword=new PasswordField();
		
		btnLogin=new Button("Login");
		btnLogin.setOnAction(e->{
			goLogin();
		});
		btnClear=new Button("Clear");
		btnClear.setOnAction(e->{
			goClear();
		});
		gp=new GridPane();
		mainPane=new BorderPane();
		phPane=new HBox();
		minBox=new VBox();
	}
	private void setLayout()
	{
		
		gp.add(lemail, 0, 0);
		gp.add(temail, 1, 0);
		
		
		gp.add(lpassword, 0, 1);
		gp.add(ppassword, 1, 1);
		
		gp.add(lposition,0,2);
		tp=new TilePane(20,20,rAdmin,rManager,rReceptionist);
		
		errBox=new VBox(5,tp,lerr);
		gp.add(errBox, 1, 2);
		
		btnPane=new FlowPane(btnLogin,btnClear);
		FileInputStream fis;
		try {
			fis = new FileInputStream("ttImages/intro.jpg");
			img=new Image(fis);
			imgV=new ImageView(img);
			imgV.setFitWidth(400);
			imgV.setFitHeight(600);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		minBox.getChildren().add(gp);
		minBox.getChildren().add(btnPane);
		phPane.getChildren().add(imgV);
		phPane.getChildren().add(minBox);
		mainPane.setCenter(phPane);
	}
	public void setGemo()
	{
		lemail.setPadding(new Insets(20));
		lpassword.setPadding(new Insets(20));
		temail.setMaxSize(100, 10);
		ppassword.setMaxSize(100, 10);
		btnPane.setHgap(20);
		minBox.setPadding(new Insets(20));
		minBox.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(10));
	}
	public void setStyle()
	{
//		lemail.getStyleClass().add("ltext");
//		lpassword.getStyleClass().add("ltext");
//		lposition.getStyleClass().add("ltext");
		
		
		lemail.getStyleClass().add("forLabelSize");
		lpassword.getStyleClass().add("forLabelSize");
		lposition.getStyleClass().add("forLabelSize");
		
		
		rAdmin.getStyleClass().add("forLabelSize");
		rManager.getStyleClass().add("forLabelSize");
		rReceptionist.getStyleClass().add("forLabelSize");
		lerr.getStyleClass().add("lerrtext");
		//mainPane.getStyleClass().add("");
		btnLogin.getStyleClass().add("lrBtn");
		btnClear.getStyleClass().add("clearBtn");
	}
	public void goClear()
	{
		temail.setText("");
		ppassword.setText("");
		rAdmin.setSelected(true);
		lerr.setText("");
	}
	public void goLogin()
	{
		String email,pass,posi="";
		email=temail.getText();
		pass=ppassword.getText();
		if(rAdmin.isSelected())
			posi="Admin";
		else if(rManager.isSelected())
			posi="Manager";
		else if(rReceptionist.isSelected())
			posi="Receptionist";
		staffID=DBhandler.staff_login(email, pass, posi);
		staffEmail=email;
		if(staffID==-1)
		{
			lerr.setText("Please Try Again. Something is wrong!");
		}
		else if(staffID!=-1)
		{
			stage.hide();
			if(posi.equals("Admin"))
			{
				st2=new Stage(StageStyle.DECORATED);
				st2.initModality(Modality.APPLICATION_MODAL);
				try {
					new AdminDashBoard().start(st2);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(posi.equals("Manager"))
			{
				st2=new Stage(StageStyle.DECORATED);
				st2.initModality(Modality.APPLICATION_MODAL);
				try {
					new ManagerDashBoard().start(st2);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(posi.equals("Receptionist"))
			{
				st2=new Stage(StageStyle.DECORATED);
				st2.initModality(Modality.APPLICATION_MODAL);
				try {
					new ReceptionistDashBoard().start(st2);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			

		}
	}
	public void handleKeyPress(KeyEvent event)
	{
		if(event.getCode()==KeyCode.ENTER)
		{
			goLogin();
		}
	}
	public void start(Stage st1) throws Exception {
		stage=st1;
		createNode();
		setLayout();
		setGemo();
		sc=new Scene(mainPane,800,600);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st1.setTitle("Login");
		st1.setScene(sc);
		st1.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
	public static int getStaffID() {
		return staffID;
	}
	public static void setStaffID(int staffID) {
		LoginForm.staffID = staffID;
	}
	public static String getStaffEmail() {
		return staffEmail;
	}
	public static void setStaffEmail(String staffEmail) {
		LoginForm.staffEmail = staffEmail;
	}
	public static String getStaffName() {
		return staffName;
	}
	public static void setStaffName(String staffName) {
		LoginForm.staffName = staffName;
	}
	public static String getStaffImage() {
		return staffImage;
	}
	public static void setStaffImage(String staffImage) {
		LoginForm.staffImage = staffImage;
	}
	

}
