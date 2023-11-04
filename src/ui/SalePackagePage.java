package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.DBhandler;
import tt.Destination;
import tt.Package;

public class SalePackagePage extends Application{

	private BorderPane mainPane=new BorderPane();
	private FlowPane imagePane=new FlowPane();
	private VBox dataBox=new VBox();
	private List<Button> btnarr=new ArrayList<>();
	private Label lName,ldeDate,larrDate,lPrice;
	private TextField tName,tdeDate,tarrDate,tPrice;
	private Stage stage;
	private GridPane gp=new GridPane();
	private Button btnHome,btnAddCart,btnClear;
	public void createNode()
	{
		lName=new Label("Package Name");
		ldeDate=new Label("Departure Date");
		larrDate=new Label("Arrival Date");
		lPrice=new Label("Package Price");
		
		tName=new TextField();
		tdeDate=new TextField();
		tarrDate=new TextField();
		tPrice=new TextField();
		
		btnAddCart=new Button("Add Cart");
		btnClear=new Button("Clear");
	}
	public void getImage()
	{
		List<Package>photo=DBhandler.getAllPackage();
		
		for(Package p:photo)
		{
			FileInputStream fis;
			try {
				fis=new FileInputStream(p.getpImage());
				Image img=new Image(fis);
				ImageView imgV=new ImageView(img);
				imgV.setFitHeight(250);
				imgV.setFitWidth(250);
				Button b=new Button();
				b.setOnAction(e->{
					MoreDetailPane(p.getpImage());
				});
				btnarr.add(b);
				b.setGraphic(imgV);
				b.getStyleClass().add("btntran");
				imagePane.getChildren().add(b);
				imagePane.setPadding(new Insets(10));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mainPane=new BorderPane(imagePane);
		mainPane.setCenter(imagePane);
	}
	public void MoreDetailPane(String data)
	{
		gp.add(lName, 0, 0);
		gp.add(tName, 1, 0);
		
		gp.add(ldeDate, 0, 1);
		gp.add(tdeDate, 1, 1);
		
		gp.add(larrDate, 0, 2);
		gp.add(tarrDate, 1, 2);
		
		gp.add(lPrice, 0, 3);
		gp.add(tPrice, 1, 3);
		
		gp.add(btnAddCart, 1, 4);
		gp.add(btnClear, 2, 4);
		gp.setHgap(10);
		gp.setVgap(10);
		FileInputStream fis;
		try {
			fis=new FileInputStream(data);
			Image img=new Image(fis);
			ImageView view=new ImageView(img);
			dataBox.getChildren().add(view);
			view.setFitHeight(250);
			view.setFitWidth(250);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setData(data);
		dataBox.getChildren().add(gp);
		dataBox.setPadding(new Insets(30));
		mainPane.setRight(dataBox);
	}
	public void setData(String data)
	{
		Package pac=DBhandler.getPackage(data);
		tName.setText(pac.getPackageName());
		tdeDate.setText(pac.getDepartureDate());
		tarrDate.setText(pac.getArrivalDate());
		tPrice.setText(pac.getPackPrice()+"$");
	}
	public void setStyle()
	{
		mainPane.getStyleClass().add("background");
		dataBox.getStyleClass().add("gpBackground");
		
		lName.getStyleClass().add("whiteText");
		ldeDate.getStyleClass().add("whiteText");
		larrDate.getStyleClass().add("whiteText");
		lPrice.getStyleClass().add("whiteText");
		
		tName.setPrefSize(100, 10);
		tdeDate.setPrefSize(100, 10);
		tarrDate.setPrefSize(100, 10);
		tPrice.setPrefSize(100, 10);
		
		btnAddCart.getStyleClass().add("lrBtn");
		btnClear.getStyleClass().add("clearBtn");
		
		
	}
	@Override
	public void start(Stage st) throws Exception {
		stage=st;
		Scene sc=new Scene(mainPane);
		URL url=this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("Sales");
		st.show();
	}
	public static void main(String[] args) {
		
		Application.launch(args);
	}
	public BorderPane getMainPane()
	{
		createNode();
		getImage();
		setStyle();
		return mainPane;
	}

}
