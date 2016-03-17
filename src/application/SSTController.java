package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

import jp.ojt.sst.file.StackTraceFile;
import jp.ojt.sst.model.StackTraceProperty;

public class SSTController implements Initializable {

	@FXML
	private TextField filePathField;
	@FXML
	private TextField searchWordField;
	@FXML
	private Button buttonExecute;
	@FXML
	private TableView<StackTraceProperty> resultTableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding bb = filePathField.textProperty().isEmpty().or(searchWordField.textProperty().isEmpty());
		buttonExecute.disableProperty().bind(bb);
	}

	@FXML
	protected void onReference(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose StackTraceLog");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Log Files", "*.log"));
		File selectedFile = fc.showOpenDialog(null);

		if(selectedFile != null) {
			filePathField.setText(selectedFile.getAbsolutePath());
		}
	}

	@FXML
	protected void onExecute(ActionEvent event) {

		StackTraceFile stFile = new StackTraceFile(filePathField.getText(), searchWordField.getText());
		ObservableList<StackTraceProperty> dataList = stFile.read();

		createResultTableView(stFile.getMonthKeys());
		// add data in view
		resultTableView.getItems().addAll(dataList);
	}

	@FXML
	protected void onExit(ActionEvent event) {
		((Stage) ((Button)event.getSource()).getScene().getWindow()).close();
	}

	/**
	 * create table view
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createResultTableView(List<String> monthList) {

		resultTableView.getColumns().clear();
		resultTableView.getItems().clear();

		// create fixed column
		TableColumn exceptionTypeCol = new TableColumn("EXCEPTION");
		exceptionTypeCol.setCellValueFactory(new PropertyValueFactory<>("exception"));
		resultTableView.getColumns().add(exceptionTypeCol);

		TableColumn messageCol = new TableColumn("MESSAGE");
		messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
		resultTableView.getColumns().add(messageCol);

		TableColumn locationCol = new TableColumn("LOCATION");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		resultTableView.getColumns().add(locationCol);

		// create variable column
		for(String month : monthList) {
			TableColumn monthCol = new TableColumn(month);
			monthCol.setCellValueFactory(
				new Callback<CellDataFeatures<StackTraceProperty, String>, ObservableValue>() {
					public ObservableValue call(CellDataFeatures<StackTraceProperty, String> p) {
						return p.getValue().monthCountProperty(month).countProperty();
					}
				});
			resultTableView.getColumns().add(monthCol);
		}
	}
}
