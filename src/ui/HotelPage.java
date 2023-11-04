package ui;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBhandler;
import tt.Hotel;
import tt.HotelStars;
import tt.Destination;

public class HotelPage extends Application {
	private GridPane updatePane;
	private FlowPane searchPane;
	private Label lHname, lHemail, lHaddress, lkeyword;
	private TextField tHname, tHemail, tHaddress, tsearch;
	private Button btnUpdate, btnClear1;
	private BorderPane mainPane = new BorderPane();
	private Stage stage, newStage;
	private TableView<Hotel> tvHotels;
	private TableColumn<Hotel, Integer> idCol;
	private TableColumn<Hotel, String> nameCol;
	private TableColumn<Hotel, String> emailCol;
	private TableColumn<Hotel, String> addressCol;
	private TableColumn<Hotel, Integer> starCol;
	private TableColumn<Hotel, String> destinationCol;
	private ArrayList<Hotel> al;
	private FilteredList<Hotel> fl;

	private Label lname, lcity, ltype, lprice, lphone, lemail, laddress, lstars;
	private ComboBox<String> roomtype;
	private ComboBox<Integer> rating;
	private TextField tname, tcity, tprice, tphone, temail, timage;
	private TextArea taddress;
	private Button btnAdd1, btnAdd2, btnClear2, btnImage, btnChoose;
	private VBox ImageBox;
	private FlowPane btnRow1, btnRow2;
	private GridPane addPane;
	private BorderPane addMainpane;

	public void createUpdatePane() {
		lHname = new Label("Name");
		lHemail = new Label("Email");
		lHaddress = new Label("Address");
		btnAdd1 = new Button("Add");
		btnAdd1.setOnAction(e -> {
			createAddPane();
		});
		btnUpdate = new Button("Update");
		btnClear1 = new Button("Clear");

		tHname = new TextField();
		tHemail = new TextField();
		tHaddress = new TextField();

		updatePane = new GridPane();
		updatePane.add(lHname, 0, 0);
		updatePane.add(tHname, 1, 0);

		updatePane.add(lHemail, 0, 1);
		updatePane.add(tHemail, 1, 1);

		updatePane.add(lHaddress, 0, 2);
		updatePane.add(tHaddress, 1, 2);

		btnRow1 = new FlowPane(20, 20, btnAdd1, btnUpdate, btnClear1);
		updatePane.add(btnRow1, 1, 3);
		updatePane.setVgap(20);

	}

	public void createSearchBar() {
		lkeyword = new Label("Search");
		tsearch = new TextField();
		tsearch.setOnKeyPressed(e -> {
			String l = tsearch.getText();
			System.out.println("this is l string in creachSearchBar :" + l);
			autoComplete(l);
			if (e.getCode() == KeyCode.ENTER) {
				filtering();
			}
		});
		searchPane = new FlowPane(20, 20, lkeyword, tsearch);
		searchPane.setAlignment(Pos.CENTER_RIGHT);
		searchPane.setPadding(new Insets(10));

	}

	public void filtering() {
		fl = new FilteredList<Hotel>(FXCollections.observableArrayList(al));
		fl.setPredicate(new Predicate<Hotel>() {

			@Override
			public boolean test(Hotel h) {
				String value = tsearch.getText().toLowerCase();
				String name = h.getHotelName().toLowerCase();
				String email = h.getHemail().toLowerCase();
				String address = h.getHaddress().toLowerCase();
				int star = h.getStar();
				String dest = h.getDetName().toLowerCase();
				if (value.length() == 0) {
					return true;
				}
				try {
					int s = Integer.parseInt(value);
					return s == star;

				} catch (Exception e) {
					return value.equals(name) || value.equals(email) || value.equals(address) || value.equals(dest);
				}

			}

		});
		tvHotels.getItems().clear();
		tvHotels.getItems().addAll(fl);
	}

	public void autoComplete(String l) {
		ArrayList<HotelStars> starsArr = DBhandler.getStars();
		ArrayList<Destination> des = DBhandler.getAllDestinations();
	}

	public void createAddPane() {
		lname = new Label("Hotel Name");
		lcity = new Label("City");
		ltype = new Label("Room Type");
		lprice = new Label("Price");
		lphone = new Label("Contact No");
		lemail = new Label("Email");
		laddress = new Label("Address");
		lstars = new Label("Stars");

		tname = new TextField();
		tcity = new TextField();
		roomtype = new ComboBox<String>();
		tprice = new TextField();
		tphone = new TextField();
		temail = new TextField();
		taddress = new TextArea();
		timage = new TextField();
		rating = new ComboBox<Integer>();
		Integer[] arr = { 1, 2, 3, 4, 5 };
		rating.getItems().addAll(arr);

		btnAdd2 = new Button("Add Hotel");
		btnChoose = new Button("Choose File");
		btnChoose.setOnAction(e -> {
			openDialogue();
		});
		btnClear2 = new Button("Clear");
		btnImage = new Button();
		FileInputStream fis;
		try {
			fis = new FileInputStream("ttImages/image.png");
			Image img = new Image(fis);
			ImageView imgV = new ImageView(img);
			imgV.setFitWidth(150);
			imgV.setFitHeight(150);
			btnImage.setGraphic(imgV);
		} catch (Exception e) {
			e.printStackTrace();
		}

		addPane = new GridPane();
		addPane.add(lname, 0, 0);
		addPane.add(tname, 1, 0);
		addPane.add(lcity, 0, 1);
		addPane.add(tcity, 1, 1);
		addPane.add(ltype, 0, 2);
		addPane.add(roomtype, 1, 2);
		addPane.add(lprice, 0, 3);
		addPane.add(tprice, 1, 3);
		addPane.add(lphone, 0, 4);
		addPane.add(tphone, 1, 4);
		addPane.add(lemail, 0, 5);
		addPane.add(temail, 1, 5);
		addPane.add(laddress, 0, 6);
		addPane.add(taddress, 1, 6);
		addPane.add(lstars, 0, 7);
		addPane.add(rating, 1, 7);
		ImageBox = new VBox(20, btnImage, timage);
		addPane.add(ImageBox, 0, 8);
		btnRow2 = new FlowPane(20, 20, btnChoose, btnAdd2, btnClear2);
		addPane.add(btnRow2, 1, 8);
		addMainpane = new BorderPane();
		addMainpane.setCenter(addPane);

		lname.getStyleClass().add("setstyleforupdate");
		lcity.getStyleClass().add("setstyleforupdate");
		ltype.getStyleClass().add("setstyleforupdate");
		lprice.getStyleClass().add("setstyleforupdate");
		lphone.getStyleClass().add("setstyleforupdate");
		lemail.getStyleClass().add("setstyleforupdate");
		laddress.getStyleClass().add("setstyleforupdate");
		lstars.getStyleClass().add("setstyleforupdate");
		btnAdd2.getStyleClass().add("btnAdd");
		btnClear2.getStyleClass().add("clearBtn");

		callingNewStage();

	}

	public void callingNewStage() {
		Stage newstage = new Stage();
		Scene sc = new Scene(addMainpane, 700, 700);
		newstage.setScene(sc);
		newstage.initModality(Modality.APPLICATION_MODAL);
		URL url = this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		newstage.setTitle("Add Dialogue Box");
		newstage.show();
	}

	public void openDialogue() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Image");
		File f = fc.showOpenDialog(stage);
		timage.setText(f.toString());
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			Image img = new Image(fis);
			ImageView imgV = new ImageView(img);
			imgV.setFitWidth(150);
			imgV.setFitHeight(150);
			btnImage.setGraphic(imgV);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTableView() {
		tvHotels = new TableView();

		idCol = new TableColumn<Hotel, Integer>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("hotelID"));

		nameCol = new TableColumn<Hotel, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));

		emailCol = new TableColumn<Hotel, String>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hemail"));

		addressCol = new TableColumn<Hotel, String>("Address");
		addressCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("haddress"));

		starCol = new TableColumn<Hotel, Integer>("Stars");
		starCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("star"));

		destinationCol = new TableColumn<Hotel, String>("Destinations");
		destinationCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("detName"));

		tvHotels.getColumns().add(idCol);
		tvHotels.getColumns().add(nameCol);
		tvHotels.getColumns().add(emailCol);
		tvHotels.getColumns().add(addressCol);
		tvHotels.getColumns().add(starCol);
		tvHotels.getColumns().add(destinationCol);
		setData();
	}

	public void setData() {
		al = DBhandler.getAllHotels();
		tvHotels.getItems().addAll(FXCollections.observableArrayList(al));
	}

	public void setGe() {

		// Adjust the preferred width of TableView and updatePane
		tvHotels.setPrefWidth(900); // Adjust the width as needed
		updatePane.setPrefWidth(300); // Adjust the width as needed

		// Set a preferred width for the searchPane
		searchPane.setPrefWidth(1200); // Adjust the width as needed

		// Set the top and center regions of the BorderPane
		mainPane.setTop(searchPane);
		mainPane.setCenter(tvHotels);

		// Set the right region of the BorderPane
		BorderPane.setAlignment(updatePane, Pos.CENTER);
		mainPane.setRight(updatePane);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void setStyle() {
		lHname.getStyleClass().add("forLabelSize");
		lHemail.getStyleClass().add("forLabelSize");
		lHaddress.getStyleClass().add("forLabelSize");
		tHname.getStyleClass().add("forTextSize");
		tHemail.getStyleClass().add("forTextSize");
		tHaddress.getStyleClass().add("forTextSize");

		lkeyword.getStyleClass().add("searchkey");
		searchPane.getStyleClass().add("searchbackground");
		lHname.getStyleClass().add("setstyleforupdate");
		lHemail.getStyleClass().add("setstyleforupdate");
		lHaddress.getStyleClass().add("setstyleforupdate");
		btnAdd1.getStyleClass().add("btnAdd");
		btnUpdate.getStyleClass().add("lrBtn");
		btnClear1.getStyleClass().add("clearBtn");

	}


	@Override
	public void start(Stage st) throws Exception {
		stage = st;
		createUpdatePane();
		createTableView();
		Scene sc = new Scene(mainPane);
		URL url = this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("HotelPage");
		st.show();
	}

	public BorderPane getMainPane() {
		createTableView();
		createSearchBar();
		createUpdatePane();
		setGe();
		setStyle();
		return mainPane;
	}

}
