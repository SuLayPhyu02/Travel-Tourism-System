package ui;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.controlsfx.control.textfield.TextFields;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBhandler;
import tt.Destination;
import tt.Transportation;
import tt.TransportationType;

public class TransportationPage extends Application {
	private Label lname, lcity, ltype, lprice, lphone, lemail, laddress;
	private TextField tname, tprice, tphone, temail, timage;
	private ComboBox<TransportationType> tranType;
	private ComboBox<Destination> dcity;
	private TextArea taddress;
	private Button btnAdd1, btnAdd2, btnClear2, btnImage, btnChoose;
	private FlowPane btnRow1, btnRow2;
	private GridPane addPane;
	private VBox ImageBox;
	private BorderPane addMainpane;

	
	private FlowPane searchPane;
	private GridPane updatePane;
	private Label lTname, lTemail, lTaddress, lType, lTphno, lkeyword;
	private TextField tTname, tTemail, tTaddress, tType, tTphno, tsearch;
	private Button btnUpdate, btnClear1;
	private BorderPane mainPane = new BorderPane();
	private Stage stage;
	
	private FilteredList<Transportation> fl;
	private TableView<Transportation> tvTransportation;
	private TableColumn<Transportation, Integer> idCol;
	private TableColumn<Transportation, String> nameCol;
	private TableColumn<Transportation, String> emailCol;
	private TableColumn<Transportation, String> addressCol;
	private TableColumn<Transportation, String> typeCol;
	private TableColumn<Transportation, String> phCol;
	private TableSelectionModel<Transportation> selectionModel;
	private ArrayList<Transportation> transportationAl;

	public void createUpdatePane() {
		lTname = new Label("Name");
		lTemail = new Label("Email");
		lTaddress = new Label("Address");
		lType = new Label("Type");
		lTphno = new Label("Ph No");
		btnAdd1 = new Button("Add");
		btnAdd1.setOnAction(e -> {
			createAddPane();
		});
		btnUpdate = new Button("Update");
		btnClear1 = new Button("Clear");

		tTname = new TextField();
		tTemail = new TextField();
		tTaddress = new TextField();
		tType = new TextField();
		tTphno = new TextField();

		updatePane = new GridPane();
		updatePane.add(lTname, 0, 0);
		updatePane.add(tTname, 1, 0);

		updatePane.add(lTemail, 0, 1);
		updatePane.add(tTemail, 1, 1);

		updatePane.add(lTaddress, 0, 2);
		updatePane.add(tTaddress, 1, 2);

		updatePane.add(lType, 0, 3);
		updatePane.add(tType, 1, 3);

		updatePane.add(lTphno, 0, 4);
		updatePane.add(tTphno, 1, 4);

		btnRow1 = new FlowPane(20, 20, btnAdd1, btnUpdate, btnClear1);
		updatePane.add(btnRow1, 1, 5);

		updatePane.setVgap(20);
		updatePane.setAlignment(Pos.CENTER_RIGHT);

		// Ensure that the labels can fully display their text
		lTname.setMaxWidth(Double.MAX_VALUE);
		lTemail.setMaxWidth(Double.MAX_VALUE);
		lTaddress.setMaxWidth(Double.MAX_VALUE);
		lType.setMaxWidth(Double.MAX_VALUE);
		lTphno.setMaxWidth(Double.MAX_VALUE);

		// Set a preferred width for the labels to ensure they align properly
		lTname.setPrefWidth(150);
		lTemail.setPrefWidth(150);
		lTaddress.setPrefWidth(150);
		lType.setPrefWidth(150);
		lTphno.setPrefWidth(150);

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
		fl = new FilteredList<Transportation>(FXCollections.observableArrayList(transportationAl));
		fl.setPredicate(new Predicate<Transportation>() {

			@Override
			public boolean test(Transportation t) {
				String value = tsearch.getText().toLowerCase();
				String name = t.getTname().toLowerCase();
				String email = t.getTemail().toLowerCase();
				String phno = t.getTphNo().toLowerCase();
				String type = t.getTtName().toLowerCase();
				System.out.println("value length " + value.length());
				if (value.length() == 0) {
					return true;
				} else {
					System.out.println("name" + name + "\n email " + email + "\n phno " + phno + "\n type " + type
							+ "\n value " + value);
					return value.equals(name) || value.equals(email) || value.equals(phno) || value.equals(type);
				}

			}

		});
		tvTransportation.setPrefSize(900, 150);
		tvTransportation.getItems().clear();
		tvTransportation.getItems().addAll(fl);

	}

	public void autoComplete(String l) {
		if (l.equalsIgnoreCase("a") || l.equalsIgnoreCase("")) {
			System.out.println("I am already here of airline, coach");
			ArrayList<TransportationType> type = DBhandler.getTType();
			TextFields.bindAutoCompletion(tsearch, type);
		} else {
			System.out.println("this is t Name & email area.");
			ArrayList<Transportation> trans = DBhandler.getAllTransportation();
			ArrayList<String> transportationName = new ArrayList<>();
			ArrayList<String> tarremail = new ArrayList<>();
			for (Transportation t : trans) {
				transportationName.add(t.getTname());
			}
			TextFields.bindAutoCompletion(tsearch, transportationName);

			for (Transportation te : trans) {
				tarremail.add(te.getTemail());
			}
			TextFields.bindAutoCompletion(tsearch, tarremail);
		}
	}

	public void createAddPane() {
		lname = new Label("Transportation Name");
		lcity = new Label("Destination");
		ltype = new Label("Transportation Type");
		lprice = new Label("Price");
		lphone = new Label("Contact No");
		lemail = new Label("Email");
		laddress = new Label("Address");

		tname = new TextField();
		ArrayList<Destination> dal = DBhandler.getAllDestinations();
		dcity = new ComboBox<Destination>(FXCollections.observableArrayList(dal));

		ArrayList<TransportationType> tal = DBhandler.getTType();
		tranType = new ComboBox<TransportationType>(FXCollections.observableArrayList(tal));

		tprice = new TextField();
		tphone = new TextField();
		temail = new TextField();
		taddress = new TextArea();
		timage = new TextField();

		btnAdd2 = new Button("Add");
		btnAdd2.setOnAction(e -> {
			addNewTransportation();
		});
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
		addPane.add(dcity, 1, 1);
		addPane.add(ltype, 0, 2);
		addPane.add(tranType, 1, 2);
		addPane.add(lprice, 0, 3);
		addPane.add(tprice, 1, 3);
		addPane.add(lphone, 0, 4);
		addPane.add(tphone, 1, 4);
		addPane.add(lemail, 0, 5);
		addPane.add(temail, 1, 5);
		addPane.add(laddress, 0, 6);
		addPane.add(taddress, 1, 6);
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
		btnAdd2.getStyleClass().add("btnAdd");
		btnClear2.getStyleClass().add("clearBtn");

		callingNewStage();

	}

	private void addNewTransportation() {
	    String name = tname.getText();
	    Destination selectedDestination = dcity.getValue();
	    TransportationType selectedTransportationType = tranType.getValue();
	    String priceStr = tprice.getText();
	    String phone = tphone.getText();
	    String email = temail.getText();
	    String address = taddress.getText();
	    String imageFilePath = timage.getText(); // You might want to handle image storage in a different way

	    if (name.isEmpty() || selectedDestination == null || selectedTransportationType == null || 
	        priceStr.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || imageFilePath.isEmpty()) {
	       System.out.println("something is wrong");
	        return;
	    }

	    try {
	        int price = Integer.parseInt(priceStr); // Parse the price string to an integer

	        boolean success = DBhandler.addTransportation(name, email, address, selectedTransportationType.getTtName(), phone,
	                selectedDestination, price, imageFilePath);

	        if (success) {
	            // Show success message to the user
	            Alert con = new Alert(AlertType.INFORMATION);
	            con.setHeaderText("Successful");
	            con.setContentText("Transportation added successfully");
	            con.show();
	        } else {
	            // Show error message to the user
	            Alert sorry = new Alert(AlertType.ERROR);
	            sorry.setHeaderText("Unsuccessful");
	            sorry.setContentText("Sorry, transportation addition failed. Please try again.");
	            sorry.show();
	        }
	    } catch (NumberFormatException e) {
	        // Handle the case where the price is not a valid integer
	        // Show an error message to the user
	        Alert priceError = new Alert(AlertType.ERROR);
	        priceError.setHeaderText("Invalid Price");
	        priceError.setContentText("Please enter a valid price.");
	        priceError.show();
	    }
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

	public void setGe() {

		// Adjust the preferred width of TableView and updatePane
		tvTransportation.setPrefWidth(800); // Adjust the width as needed
		updatePane.setPrefWidth(400); // Adjust the width as needed

		// Set a preferred width for the searchPane
		searchPane.setPrefWidth(1200); // Adjust the width as needed

		// Set the top and center regions of the BorderPane
		mainPane.setTop(searchPane);
		mainPane.setCenter(tvTransportation);

		// Set the right region of the BorderPane
		BorderPane.setAlignment(updatePane, Pos.CENTER);
		mainPane.setRight(updatePane);
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

	public void createTranTable() {
		tvTransportation = new TableView<Transportation>();

		idCol = new TableColumn<Transportation, Integer>("ID");
		nameCol = new TableColumn<Transportation, String>("Name");
		emailCol = new TableColumn<Transportation, String>("Email");
		addressCol = new TableColumn<Transportation, String>("Address");
		typeCol = new TableColumn<Transportation, String>("Type");
		phCol = new TableColumn<Transportation, String>("Ph NO");
		
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("tid"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("tname"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("temail"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("taddress"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("ttName"));
		phCol.setCellValueFactory(new PropertyValueFactory<>("tphNo"));

		
		tvTransportation.getColumns().add(idCol);
		tvTransportation.getColumns().add(nameCol);
		tvTransportation.getColumns().add(emailCol);
		tvTransportation.getColumns().add(addressCol);
		tvTransportation.getColumns().add(typeCol);
		tvTransportation.getColumns().add(phCol);
		
		tvTransportation.setPrefSize(500, 500);
		selectionModel = tvTransportation.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		tvTransportation.setEditable(true);
		tvTransportation.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				setUpdateInfo();
			}
		});
		setData();
	}

	public void setUpdateInfo() {
		Transportation tsel = selectionModel.getSelectedItem();
		tTname.setText("" + tsel.getTname());
		tTemail.setText("" + tsel.getTemail());
		tTaddress.setText("" + tsel.getTaddress());
		tType.setText("" + tsel.getTtName());
		tTphno.setText("" + tsel.getTphNo());
	}

	public void setData() {
		transportationAl = DBhandler.getAllTransportation();
		fl = new FilteredList<Transportation>(FXCollections.observableArrayList(transportationAl));
		tvTransportation.getItems().addAll(fl);
	}

	public void setStyle() {
		lkeyword.getStyleClass().add("searchkey");
		searchPane.getStyleClass().add("searchbackground");

//		lTname.getStyleClass().add("setstyleforupdate");
//		lTemail.getStyleClass().add("setstyleforupdate");
//		lTaddress.getStyleClass().add("setstyleforupdate");
//		lType.getStyleClass().add("setstyleforupdate");
//		lTphno.getStyleClass().add("setstyleforupdate");

		lTname.getStyleClass().add("forLabelSize");
		lTemail.getStyleClass().add("forLabelSize");
		lTaddress.getStyleClass().add("forLabelSize");
		lType.getStyleClass().add("forLabelSize");
		lTphno.getStyleClass().add("forLabelSize");

		tTaddress.getStyleClass().add("forTextSize");
		tTname.getStyleClass().add("forTextSize");
		tTemail.getStyleClass().add("forTextSize");
		tType.getStyleClass().add("forTextSize");
		tTphno.getStyleClass().add("forTextSize");

		btnUpdate.getStyleClass().add("lrBtn");
		btnAdd1.getStyleClass().add("btnAdd");
		btnClear1.getStyleClass().add("clearBtn");

		// forLabelSize
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage st) throws Exception {
		stage = st;
		createTranTable();
		createUpdatePane();
		Scene sc = new Scene(mainPane);
		URL url = this.getClass().getResource("ttstyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		setStyle();
		st.setScene(sc);
		st.setTitle("TransportationPage");
		st.show();
	}

	public BorderPane getMainPane() {
		createTranTable();
		createSearchBar();
		createUpdatePane();
		setGe();
		setStyle();
		return mainPane;
	}
}
