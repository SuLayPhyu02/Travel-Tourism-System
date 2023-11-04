package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.DBhandler;

public class ReceptionistDashBoard extends Application{
	private BorderPane mainPane;
	private VBox leftpane;
	private Label lstaff,lheader,loption0,loption1,loption2,loption3,loption4,loption5;
	private Stage stage;
	private Button userpfp;
	private FlowPane staffPane;
	private Image profileImg;
	private ImageView imgV;
	private Menu destMenu;
	public void setLayout()
	{
		mainPane=new BorderPane();
		leftpane=new VBox(20,lheader,loption0,loption1,loption2,loption3,loption4,loption5);
		mainPane.setLeft(leftpane);
		mainPane.setCenter(new HomePage().getMainPane());
	}
	public void createTopPane() {
		String email=LoginForm.getStaffEmail();
		lstaff = new Label(email);
		try {
		    FileInputStream fis = new FileInputStream(DBhandler.getProfileImage(email));
		    profileImg = new Image(fis);
		    imgV = new ImageView(profileImg);
		    imgV.setPreserveRatio(true);
		    imgV.setFitWidth(100);
		    imgV.setFitHeight(100);

		    Pane pane = new Pane(imgV);
		   // pane.getStyleClass().add("rounded-image");

		    // Apply a clip to the pane for a rounded image effect
		    Rectangle clip = new Rectangle(100, 100);
		    clip.setArcWidth(100); // Adjust the arc width to control the roundness
		    clip.setArcHeight(100); // Adjust the arc height to control the roundness
		    pane.setClip(clip);

		    userpfp = new Button("", pane); // Pass the pane with the image to the Button constructor
		    //userpfp.getStyleClass().add("rounded-image");
		    userpfp.getStyleClass().add("userbtn");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	    lstaff = new Label(LoginForm.getStaffEmail());
	    staffPane = new FlowPane(20,20,userpfp, lstaff);
	    staffPane.setAlignment(Pos.CENTER_LEFT);
		staffPane.setPadding(new Insets(10));

	    mainPane.setTop(staffPane);
	    
		staffPane.setId("toppane");
		lstaff.setId("username");
		lheader.setId("dashboard");
	}

	public void createLeftPane()
	{
		lheader=new Label("Receptionist DashBoard");
		loption0=new Label("Home");
		loption0.setOnMouseClicked(e->{
			mainPane.setCenter(new HomePage().getMainPane());
		});
		loption1=new Label("My Profile");
		loption1.setOnMouseClicked(e->{
			mainPane.setCenter(new Register_UI().getMainPane());
		});
		loption2=new Label("Sell Packages");
		loption2.setOnMouseClicked(e->{
			mainPane.setCenter(new SalePackagePage().getMainPane());
		});
		loption3=new Label("Book Vehicles");
		loption3.setOnMouseClicked(e->{
			mainPane.setCenter(new TransportationPage().getMainPane());
		});
		loption4=new Label("Book Hotels");
		loption4.setOnMouseClicked(e->{
			mainPane.setCenter(new HotelPage().getMainPane());
		});
		loption5=new Label("Customer Info");
		loption5.setOnMouseClicked(e->{
			mainPane.setCenter(new StaffPage().getMainPane());
		});
		
	}
	public void setGeometry()
	{
		loption0.setMaxWidth(250);
		loption1.setMaxWidth(250);
		loption2.setMaxWidth(250);
		loption3.setMaxWidth(250);
		loption4.setMaxWidth(250);
		loption5.setMaxWidth(250);
		
		
		loption0.setAlignment(Pos.CENTER);
		loption1.setAlignment(Pos.CENTER);
		loption2.setAlignment(Pos.CENTER);
		loption3.setAlignment(Pos.CENTER);
		loption4.setAlignment(Pos.CENTER);
		loption5.setAlignment(Pos.CENTER);
		
		leftpane.setPadding(new Insets(20));
	}
	public void setStyle()
	{
		loption0.getStyleClass().add("option");
		loption1.getStyleClass().add("option");
		loption2.getStyleClass().add("option");
		loption3.getStyleClass().add("option");
		loption4.getStyleClass().add("option");
		loption5.getStyleClass().add("option");
		
		leftpane.getStyleClass().add("leftpane");
	}
	public static void main(String[] args) {
		//System.out.println(LoginForm.getStaffEmail());
		Application.launch(args);
	}

	@Override
	public void start(Stage st) throws Exception {
		stage=st;
		createLeftPane();
		setLayout();
		setGeometry();
		//createTopPane();
		Scene sc=new Scene(mainPane,1500,800);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("AdminDashBoard");
		st.show();
	}

}
