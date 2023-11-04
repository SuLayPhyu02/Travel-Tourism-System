package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {
	private Stage st,stage;
	private Label l1,l2,l3,l4;
	private Button b1,b2,b3,b4;
	private HBox bottomPane;
	private VBox e1,e2,e3,e4;
	private Image i1,i2,i3,i4; 
	private ImageView v1,v2,v3,v4;
	private BorderPane mainPane;
	private ScrollPane scrollPane;
//	private String[] imagePaths = {
//		    "ttImages/Destinations/bagan2.jpeg",
//		    "ttImages/Destinations/inle4.jpg",
//		    "ttImages/Destinations/kalaw1.jpg",
//		    "ttImages/Destinations/mdy.jpg",
//		    "ttImages/Destinations/inle3.jpg"
//		};
//	public void createTopSlide() {
//	    scrollPane = new ScrollPane();
//	    FlowPane slideBox = new FlowPane(); // Use a FlowPane for horizontal layout
//	    slideBox.setHgap(10); // Adjust the gap between images
//	    slideBox.setAlignment(Pos.CENTER);
//	    
//	    scrollPane.setContent(slideBox);
//	    scrollPane.setFitToWidth(true);
//	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Allow horizontal scrolling
//
//	    for (String imagePath : imagePaths) {
//	        try {
//	            FileInputStream fis = new FileInputStream(imagePath);
//	            Image image = new Image(fis);
//	            ImageView imageView = new ImageView(image);
//	            imageView.setFitWidth(300); // Adjust the image width as needed
//	            imageView.setFitHeight(200); // Adjust the image height as needed
//	            slideBox.getChildren().add(imageView);
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	            // Handle exception if the image file is not found
//	        }
//	    }
//	}

	public void createNodes()
	{
		
		l1=new Label("Packages");
		l2=new Label("Transportaions");
		l3=new Label("Hotels");
		l4=new Label("Staffs");
		FileInputStream fis;
		try {
			fis=new FileInputStream("ttImages/b1.jpg");
			i1=new Image(fis);
			v1=new ImageView(i1);
			b1=new Button("",v1);
			b1.setOnAction(e->{
				mainPane.setCenter(new PackagePage().getMainPane());
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fis=new FileInputStream("ttImages/b2.jpg");
			i2=new Image(fis);
			v2=new ImageView(i2);
			b2=new Button("",v2);
			b2.setOnAction(e->{
				mainPane.setCenter(new TransportationPage().getMainPane());
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fis=new FileInputStream("ttImages/b3.jpg");
			i3=new Image(fis);
			v3=new ImageView(i3);
			b3=new Button("",v3);
			b3.setOnAction(e->{
				mainPane.setCenter(new HotelPage().getMainPane());
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fis=new FileInputStream("ttImages/b4.jpg");
			i4=new Image(fis);
			v4=new ImageView(i4);
			b4=new Button("",v4);
			b4.setOnAction(e->{
				mainPane.setCenter(new StaffPage().getMainPane());
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		e1=new VBox(10,b1,l1);
		e2=new VBox(10,b2,l2);
		e3=new VBox(10,b3,l3);
		e4=new VBox(10,b4,l4);
	}
	public void setGem() 
	{	
		b1.setMaxSize(100,150);
		b2.setMaxSize(100,150);
		b3.setMaxSize(100,150);
		b4.setMaxSize(100,150);
		
		bottomPane.setPadding(new Insets(10));
		bottomPane.setAlignment(Pos.CENTER);
	}
	public void setLayout()
	{
		bottomPane=new HBox(20,e1,e2,e3,e4);
		mainPane=new BorderPane();
		//mainPane.setTop(scrollPane);
		mainPane.setCenter(bottomPane);
	}
	public void setStyle()
	{
		l1.getStyleClass().add("homelabel");
		l2.getStyleClass().add("homelabel");
		l3.getStyleClass().add("homelabel");
		l4.getStyleClass().add("homelabel");
		
		b1.getStyleClass().add("btntran");
		b2.getStyleClass().add("btntran");
		b3.getStyleClass().add("btntran");
		b4.getStyleClass().add("btntran");

		mainPane.getStyleClass().add("background");
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage st1) throws Exception {
		st=st1;
		//createTopSlide();
		createNodes();
		setLayout();
		setGem();
		Scene sc=new Scene(mainPane);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st1.setScene(sc);
		st1.setTitle("HomePage");
		st1.show();
	}
	public BorderPane getMainPane()
	{
		//createTopSlide();
		createNodes();
		setLayout();
		setGem();
		setStyle();
		return mainPane;
	}

}
