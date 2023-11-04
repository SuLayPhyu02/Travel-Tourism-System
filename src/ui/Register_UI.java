package ui;

import java.io.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.print.attribute.standard.RequestingUserName;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Checker;
import model.DBhandler;

public class Register_UI extends Application{
	private Label lname,lemail,lpassword,lrepassword,lposition,ledate,lImage;
	//lerr section
	private Label lnameErr,lemailErr,lpasswordErr,lrepasswordErr,lEnrollDateErr;
	private VBox nameBox,emailBox,passBox,repassBox,enrollBox;
	private FlowPane wholePane;
	private ImageView imgView;
	
	private TextField tname,temail,timage;
	private ToggleGroup tgPos;
	private RadioButton rAdmin,rManager,rReceptionist;
	private PasswordField ppassword,prepassword;
	private Button btnClear,btnReg,btnOpen;
	private DatePicker edate;
	private HBox btnBox,PosBox,imageBox;
	private VBox vb;
	private BorderPane mainPane;
	private GridPane gp;
	private Stage stage;
	
	public void createNodes()
	{
		lname=new Label("Name");
		lemail=new Label("Email");
		lpassword=new Label("Password");
		lrepassword=new Label("Confirm Password");
		lposition=new Label("Position");
		ledate=new Label("Enroll Date");
		lImage=new Label("Image");
		
		
		//lerr section
		lnameErr=new Label();
		lemailErr=new Label();
		lpasswordErr=new Label();
		lrepasswordErr=new Label();
		lEnrollDateErr=new Label();
		
		tname=new TextField();
		temail=new TextField();
		timage=new TextField();
		
		tgPos=new ToggleGroup();
		rAdmin=new RadioButton("Admin");
		rAdmin.setSelected(true);
		rAdmin.setToggleGroup(tgPos);
		rManager=new RadioButton("Manager");
		rManager.setToggleGroup(tgPos);
		rReceptionist=new RadioButton("Receptionist");
		rReceptionist.setToggleGroup(tgPos);
		
		ppassword=new PasswordField();
		prepassword=new PasswordField();
		
		edate=new DatePicker();
		btnReg=new Button("Login");
		btnReg.setOnAction(e->{
			goRegister();
		});
		btnClear=new Button("Clear");
		btnClear.setOnAction(e->{
			goClear();
		});
		btnOpen=new Button("Choose File");
		btnOpen.setOnAction(e->{
			OpenDialogue();
		});
		mainPane=new BorderPane();
		gp=new GridPane();
	}
	public void setLayout()
	{
		gp.add(lname, 0, 0);
		nameBox=new VBox(5,tname,lnameErr);
		gp.add(nameBox, 1, 0);
		
		gp.add(lemail, 0, 1);
		emailBox=new VBox(5,temail,lemailErr);
		gp.add(emailBox, 1, 1);
		
		gp.add(lpassword, 0, 2);
		passBox=new VBox(5,ppassword,lpasswordErr);
		gp.add(passBox, 1, 2);
		
		gp.add(lrepassword, 0, 3);
		repassBox=new VBox(5,prepassword,lrepasswordErr);
		gp.add(repassBox, 1, 3);
		
		gp.add(lposition, 0, 4);
		PosBox=new HBox(20,rAdmin,rManager,rReceptionist);
		gp.add(PosBox, 1, 4);
		
		gp.add(ledate, 0, 5);
		enrollBox=new VBox(5,edate,lEnrollDateErr);
		gp.add(enrollBox, 1, 5);
		
		imageBox=new HBox(timage,btnOpen);
		gp.add(lImage, 0, 6);
		gp.add(imageBox, 1, 6);
		btnBox=new HBox(10,btnClear,btnReg);
		gp.add(btnBox, 1, 7);
	}
	public void createImage()
	{
		FileInputStream fis;
		try {
			fis=new FileInputStream("ttImages/working.gif");
			Image img=new Image(fis);
			imgView=new ImageView(img);
			wholePane=new FlowPane(20,20,imgView,gp);
			wholePane.setMargin(imgView, new Insets(20));
			wholePane.setMargin(gp, new Insets(20));
			wholePane.setAlignment(Pos.CENTER);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainPane.setCenter(wholePane);
	}
	public void goRegister()
	{
		String name,email,pass,repass,pos,sdate,staffimage;
		name=tname.getText();
		email=temail.getText();
		pass=ppassword.getText();
		repass=prepassword.getText();
		System.out.println("before value "+edate);
		sdate=edate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		staffimage=timage.getText();
		System.out.println("image name "+staffimage);
		if(rAdmin.isSelected())
			pos="Admin";
		else if(rManager.isSelected())
			pos="Manager";
		else 
			pos="Receptionist";
		if(!Checker.isValidName(name))
		{
			lnameErr.setText("Your Name is unvalid,please try again!");
		}
		else if(!pass.equals(repass))
		{
			lpasswordErr.setText("Your Password is out of format!(The password should be at least 8 characters long. The password should contain at least one uppercase letter, one lowercase letter, and one number.)");
		}
		else if(!Checker.isValidEmail(email))
		{
			lemailErr.setText("Your Email should be in form of [Uppercase and lowercase letters in English (A-Z, a-z).Digits from 0 to 9. Special characters such as ! # $ % & ' * + - / = ? ^ _ ` { |]");
		}
		else if(!Checker.isValidPassword(pass) && !Checker.isValidPassword(repass))
		{
			lrepasswordErr.setText("Your Password should be the same as Repassword.");
		}
		else 
		{
			if (DBhandler.addStaff(name, email, pass, pos, sdate, staffimage)) {
	            System.out.println("You have successfully registered");
	            Alert alt = new Alert(AlertType.CONFIRMATION);
	            alt.setHeaderText("Confirmation");
	            alt.setContentText("Are you sure you want to add?");
	            Optional<ButtonType> ans = alt.showAndWait();
	            if (ans.isPresent() && ans.get() == ButtonType.OK) {
	                Alert con = new Alert(AlertType.INFORMATION);
	                con.setHeaderText("Successful");
	                con.setContentText("You have added a new staff member successfully");
	                con.show();
	            }
	        } else {
	            System.out.println("Failed to add staff member");
	            Alert sorry = new Alert(AlertType.INFORMATION);
	            sorry.setHeaderText("Unsuccessful");
	            sorry.setContentText("Sorry, please try again!");
	            sorry.show();
	        }
		}
	}
	public void goClear()
	{
		tname.setText("");
		temail.setText("");
		ppassword.setText("");
		prepassword.setText("");
		rAdmin.setSelected(true);
	}
	public void OpenDialogue()
	{
		FileChooser fc=new FileChooser();
		fc.setTitle("GetImage");
		File f=fc.showOpenDialog(stage);
		System.out.println(f);
		timage.setText(f.toString());
	}
	public void setAling()
	{
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(30));
	}
	public void setStyle()
	{
		mainPane.getStyleClass().add("background");
		gp.getStyleClass().add("gpBackground");
		lname.getStyleClass().add("relabel");
		lemail.getStyleClass().add("relabel");
		lpassword.getStyleClass().add("relabel");
		lrepassword.getStyleClass().add("relabel");
		lposition.getStyleClass().add("relabel");
		lImage.getStyleClass().add("relabel");
		rAdmin.getStyleClass().add("relabel");
		rManager.getStyleClass().add("relabel");
		rReceptionist.getStyleClass().add("relabel");
		ledate.getStyleClass().add("relabel");
		btnReg.getStyleClass().add("lrBtn");
		btnClear.getStyleClass().add("clearBtn");
	}
	@Override
	public void start(Stage st) throws Exception {
		createNodes();
		setLayout();
		setAling();
		Scene sc=new Scene(mainPane);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("Register Form");
		st.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
	public BorderPane getMainPane()
	{
		createNodes();
		setLayout();
		setAling();
		createImage();
		setStyle();
		return mainPane;
	}
}
