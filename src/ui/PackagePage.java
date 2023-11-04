package ui;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DBhandler;
import tt.Destination;
import tt.Package;

public class PackagePage extends Application {
	// add pane
	private Label lpackageName, lDestination, lDep, lArrival, lPrice;
	private TextField tpn, tp, timg;
	private ComboBox<Destination> desCombo;
	private Button btnImage,btnAdd;
	private ArrayList<Destination> al;
	private List<Package> packageArray;
	private FilteredList<Package> fl;
	private TableSelectionModel<Package> selectionModel;

	private DatePicker dpd, dpa, tde, ta;
	// update
	private Label lname, ldepart, larriv, lprice, ldestination, lkeyword;
	private TextField tname, tsearch, tprice;
	private ComboBox<Destination> destinations;
	private TableView<Package> tvPackages;
	private TableColumn<Package, Integer> idCol;
	private TableColumn<Package, String> nameCol;
	private TableColumn<Package, String> depCol;
	private TableColumn<Package, String> arrCol;
	private TableColumn<Package, Integer> priceCol;
	private TableColumn<Package, String> destinationCol;
	private BorderPane mainPane = new BorderPane();
	private GridPane updatePane;
	private FlowPane  searchPane, threebtnPane;
	private Button btnAdding, btnUpdate, btnClear;
	
	private HBox mainBox = new HBox();
	private VBox beforeMainBox;
	private HBox forupperH;
	private Stage stage;

	public void createUpdatePane() {
		lname = new Label("Package Name");
		ldepart = new Label("Departure Date");
		larriv = new Label("Arrival Date");
		lprice = new Label("Package Price");
		ldestination = new Label("Destination");

		tname = new TextField();
		dpd = new DatePicker();
		dpa = new DatePicker();
		tprice = new TextField();
		List<Destination> arrayD = DBhandler.getAllDestinations();
		destinations = new ComboBox<>(FXCollections.observableArrayList(arrayD));

		btnAdding = new Button("Add");
		btnAdding.setOnAction(e -> {
			addPackagePane();
		});
		btnUpdate = new Button("Update");
		btnClear = new Button("Clear");

		updatePane = new GridPane();
		updatePane.add(lname, 0, 0);
		updatePane.add(tname, 1, 0);
		updatePane.add(ldepart, 0, 1);
		updatePane.add(dpd, 1, 1);
		updatePane.add(larriv, 0, 2);
		updatePane.add(dpa, 1, 2);
		updatePane.add(lprice, 0, 3);
		updatePane.add(tprice, 1, 3);
		updatePane.add(ldestination, 0, 4);
		updatePane.add(destinations, 1, 4);

		threebtnPane = new FlowPane(20, 20, btnAdding, btnUpdate, btnClear);
		updatePane.add(threebtnPane, 1, 7);

		tname.setMaxWidth(200);
		dpd.setMaxWidth(200);
		dpa.setMaxWidth(200);
		tprice.setMaxWidth(200);
		destinations.setMaxWidth(200);
	}

	public void createPackageTable() {
		tvPackages = new TableView<Package>();

		idCol = new TableColumn<Package, Integer>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Package, Integer>("packageID"));
		nameCol = new TableColumn<Package, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Package, String>("packageName"));
		depCol = new TableColumn<Package, String>("Departure Date");
		depCol.setCellValueFactory(new PropertyValueFactory<Package, String>("departureDate"));
		arrCol = new TableColumn<Package, String>("Arrival Date");
		arrCol.setCellValueFactory(new PropertyValueFactory<Package, String>("arrivalDate"));

		priceCol = new TableColumn<Package, Integer>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Package, Integer>("packPrice"));

		destinationCol = new TableColumn<Package, String>("Destination");
		destinationCol.setCellValueFactory(new PropertyValueFactory<Package, String>("destinationName"));

		tvPackages.getColumns().add(idCol);
		tvPackages.getColumns().add(nameCol);
		tvPackages.getColumns().add(depCol);
		tvPackages.getColumns().add(arrCol);
		tvPackages.getColumns().add(priceCol);
		tvPackages.getColumns().add(destinationCol);

		tvPackages.setPrefSize(500, 500);
		selectionModel = tvPackages.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		tvPackages.setEditable(true);
		tvPackages.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				setUpdateInfo();
			}
		});
		setData();
	}

	private void setUpdateInfo() {
		Package pack = selectionModel.getSelectedItem();
		tname.setText("" + pack.getPackageName());
		String depStr = pack.getDepartureDate();
		LocalDate depDate = LocalDate.parse(depStr);
		dpd.setValue(depDate);

		String arrStr = pack.getArrivalDate();
		LocalDate arrDate = LocalDate.parse(arrStr);
		dpa.setValue(arrDate);

		tprice.setText("" + pack.getPackPrice());

		String destinationName = pack.getDestinationName();

		Destination selectedDestination = destinations.getItems().stream()
				.filter(destination -> destination.getDetName().equals(destinationName)).findFirst().orElse(null);

		destinations.setValue(selectedDestination);
		createUpdatePane();
	}

	public void createSearchBar() {
		lkeyword = new Label("Search");
		tsearch = new TextField();
		tsearch.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				// filtering();
			}
		});
		searchPane = new FlowPane(20, 20, lkeyword, tsearch);
		searchPane.setAlignment(Pos.CENTER_RIGHT);
		searchPane.setPadding(new Insets(10));

	}

	public void setData() {
		packageArray = DBhandler.getAllPackage();
		fl = new FilteredList<Package>(FXCollections.observableArrayList(packageArray));
		tvPackages.getItems().addAll(fl);
	}

	public void createMainPane() {
		forupperH=new HBox();
		forupperH.getChildren().add(tvPackages);
		forupperH.getChildren().add(updatePane);
		beforeMainBox=new VBox();
		beforeMainBox.getChildren().add(forupperH);
		beforeMainBox.getChildren().add(mainBox);
		mainPane = new BorderPane();
		mainPane.setTop(searchPane);
		mainPane.setCenter(beforeMainBox);
	}

	public void addPackagePane() {
		lpackageName = new Label("Package Name");
		lDestination = new Label("Destination");
		lDep = new Label("Deparature Date");
		lArrival = new Label("Arrival Date");
		lPrice = new Label("Package Price");

		tpn = new TextField();
		al = DBhandler.getAllDestinations();
		desCombo = new ComboBox<>(FXCollections.observableArrayList(al));
		tde = new DatePicker();
		ta = new DatePicker();
		tp = new TextField();
		timg = new TextField();

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
		btnImage.setOnAction(e->{
			OpenDialogue();
		});
		btnAdd = new Button("Add Package");
		btnAdd.setOnAction(e -> {
			addPackages();
		});


		HBox packageNameBox = new HBox(10); // HBox for Package Name
		packageNameBox.getChildren().addAll(new Label("Package Name"), tpn);
		
		HBox destinationBox = new HBox(10); // HBox for Destination
		destinationBox.getChildren().addAll(new Label("Destination"), desCombo);

		HBox departureBox = new HBox(10); // HBox for Departure Date
		departureBox.getChildren().addAll(new Label("Departure Date"), tde);

		HBox arrivalBox = new HBox(20); // HBox for Arrival Date
		arrivalBox.getChildren().addAll(new Label("Arrival Date"), ta);

		HBox priceBox = new HBox(10); // HBox for Package Price
		priceBox.getChildren().addAll(new Label("Package Price"), tp);

		HBox imageBox = new HBox(10); // HBox for Image
		imageBox.getChildren().addAll(new Label("Image"), timg);
		
		HBox openDiBox=new HBox(10); // HBox for Package Name
		openDiBox.getChildren().addAll(btnImage);

		HBox buttonBox = new HBox(10); // HBox for Buttons
		buttonBox.getChildren().addAll(btnAdd);

		mainBox.setSpacing(20); // Set the vertical gap between VBox elements to 20 pixels

		VBox vbox1 = new VBox();
		vbox1.getChildren().add(packageNameBox);
		vbox1.getChildren().add(destinationBox);
		vbox1.setPadding(new Insets(20, 0, 20, 0));// Apply padding as needed

		VBox vbox2 = new VBox();
		vbox2.getChildren().add(departureBox);
		vbox2.getChildren().add(arrivalBox);
		vbox2.setPadding(new Insets(20, 0, 20, 0)); // Apply padding as needed

		VBox vbox3 = new VBox();
		vbox3.getChildren().add(priceBox);
		vbox3.getChildren().add(imageBox);
		vbox3.setPadding(new Insets(20, 0, 20, 0)); // Apply padding as needed
		
		VBox vbox4=new VBox();
		vbox4.getChildren().add(openDiBox);
		vbox4.getChildren().add(buttonBox);
		vbox4.setPadding(new Insets(20, 0, 20, 0));
		
		
		mainBox.getChildren().addAll(vbox1, vbox2, vbox3,vbox4);
		mainBox.setPadding(new Insets(10));

		
		

		// Add your styles here...

		lpackageName.getStyleClass().add("setstyleforadd");
		lDestination.getStyleClass().add("setstyleforadd");
		lDep.getStyleClass().add("setstyleforadd");
		lArrival.getStyleClass().add("setstyleforadd");
		lPrice.getStyleClass().add("setstyleforadd");

		btnAdd.getStyleClass().add("btnAdd");

		tname.getStyleClass().add("text-field");
		tde.getStyleClass().add("text-field");
		ta.getStyleClass().add("text-field");
		tp.getStyleClass().add("text-field");
		timg.getStyleClass().add("text-field");

		//addMainPane.getStyleClass().add("addBackground");
	}

//	public void callingNewStage() {
//		Stage newstage = new Stage();
//		Scene sc = new Scene(addMainPane, 600, 500);
//		newstage.setScene(sc);
//		newstage.initModality(Modality.APPLICATION_MODAL);
//		URL url = this.getClass().getResource("ttstyle.css");
//		sc.getStylesheets().add(url.toExternalForm());
//		newstage.setTitle("Add Dialogue Box");
//		newstage.show();
//	}

	public void OpenDialogue() {
		FileChooser fc = new FileChooser();
		fc.setTitle("GetImage");
		File f = fc.showOpenDialog(stage);
		timg.setText(f.toString());
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

	public void addPackages() {
		Destination selectedDestination = desCombo.getValue();
		if (selectedDestination != null) {
			int destID = selectedDestination.getdID();
			String packageName = tname.getText();

			// Check if departure date is selected
			LocalDate departureLocalDate = tde.getValue();
			if (departureLocalDate == null) {
				System.out.println("departureLocalDate is null");
				return;
			}

			// Check if arrival date is selected
			LocalDate arrivalLocalDate = ta.getValue();
			if (arrivalLocalDate == null) {
				System.out.println("arrivalLocalDate is null");
				return;
			}

			// Convert LocalDate to String in the desired format (e.g., "yyyy-MM-dd")
			String departureDate = departureLocalDate.toString();
			String arrivalDate = arrivalLocalDate.toString();

			int packPrice = Integer.parseInt(tp.getText());
			String pImage = timg.getText();

			Alert alt = new Alert(AlertType.CONFIRMATION);
			alt.setHeaderText("Confirmation");
			alt.setContentText("Are you sure to add ?");
			Optional<ButtonType> ans = alt.showAndWait();
			if (ans.isPresent() && ans.get() == ButtonType.OK) {
				boolean success = DBhandler.addPackage(packageName, departureDate, arrivalDate, packPrice, pImage,
						destID);
				if (success) {
					// Show success message to the user
					Alert con = new Alert(AlertType.INFORMATION);
					con.setHeaderText("Successful");
					con.setContentText("Package added successfully");
					con.show();
				} else {
					// Show error message to the user
					Alert sorry = new Alert(AlertType.ERROR);
					sorry.setHeaderText("Unsuccessful");
					sorry.setContentText("Sorry, package addition failed. Please try again.");
					sorry.show();
				}
			}
		}
	}

	public void setStyle() {
		lkeyword.getStyleClass().add("searchkey");
		searchPane.getStyleClass().add("searchbackground");
		lname.getStyleClass().add("setstyleforupdate");
		ldepart.getStyleClass().add("setstyleforupdate");
		larriv.getStyleClass().add("setstyleforupdate");
		lprice.getStyleClass().add("setstyleforupdate");
		ldestination.getStyleClass().add("setstyleforupdate");

		btnUpdate.getStyleClass().add("lrBtn");
		btnClear.getStyleClass().add("clearBtn");
		btnAdding.getStyleClass().add("btnAdd");
		mainPane.getStyleClass().add("background");
	}

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage st) throws Exception {
		stage = st;
		createPackageTable();
		createSearchBar();
		addPackagePane();
		createMainPane();
		Scene sc = new Scene(mainPane);
		URL url = this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("Packages");
		st.show();
	}

	public BorderPane getMainPane() {
		createPackageTable();
		createUpdatePane();
		createSearchBar();
		addPackagePane();
		createMainPane();
		setStyle();
		return mainPane;
	}

}
