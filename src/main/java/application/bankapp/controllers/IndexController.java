package application.bankapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.bankapp.FXApp;
import application.hibernate.entities.Account;
import application.hibernate.entities.Person;
import application.hibernate.services.PersonService;
import application.hibernate.services.PersonServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class IndexController implements Initializable {
	PersonService personService;

	@FXML
	private TableView<Person> tvPersons;

	@FXML
	private TableColumn<Person, String> colFirstName;

	@FXML
	private TableColumn<Person, String> colLastName;

	@FXML
	private TableColumn<Person, String> colAddress;

	@FXML
	private TableColumn<Person, Person> colDelete;

	@FXML
	private TextField tfFirstName;

	@FXML
	private TextField tfLastName;

	@FXML
	private TextField tfAddress;

	@FXML
	private Button bntAddPerson;

	@FXML
	private TableView<Account> tvAccounts;

	@FXML
	private TableColumn<Account, String> colPerson;

	@FXML
	private TableColumn<Account, Double> colBalance;

	@FXML
	private TableColumn<Account, Double> colMaxWithdrawal;

	@FXML
	private TableColumn<Account, Double> colMaxOverdraft;

	@FXML
	private Button btnAddAccount;

	@FXML
	private ComboBox<Person> comboPersons;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Rezising index to match window size!");
		FXApp.resizeWindow(1000, 600);

		// Make table editable
		tvPersons.setEditable(true);
		colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
		colLastName.setCellFactory(TextFieldTableCell.forTableColumn());
		colAddress.setCellFactory(TextFieldTableCell.forTableColumn());

		// Link table columns to class attributes
		colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

		// Add the delete button to the persons row
		colDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		colDelete.setCellFactory(param -> new TableCell<Person, Person>() {
			private final Button btnDeletePerson = new Button("Delete");

			@Override
			public void updateItem(Person person, boolean empty) {
				super.updateItem(person, empty);
				if (person == null) {
					setGraphic(null);
					return;
				}
				btnDeletePerson.setId("btnDelete");
				setGraphic(btnDeletePerson);
				btnDeletePerson.setOnAction(event -> {
					personService.deletePersonById(person.getId());
					refreshPersons();
				});
			}
		});

		// Fetch the accounts data
		this.personService = new PersonServiceImpl();
		refreshPersons();
	}

	@FXML
	void handleExit(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void onAddAccount(ActionEvent event) {

	}

	@FXML
	void handleAddPerson(ActionEvent event) {
		if (tfFirstName.getText().isEmpty() || tfLastName.getText().isEmpty() || tfAddress.getText().isEmpty()) {
			System.out.println("One or more fields are empty!");
			return;
		}
		this.personService.savePerson(new Person(tfFirstName.getText(), tfLastName.getText(), tfAddress.getText()));
		refreshPersons();
	}

	// ----------------------------------//
	void refreshPersons() {
		tvPersons.getItems().setAll(personService.getAllPersons());
	}

}
