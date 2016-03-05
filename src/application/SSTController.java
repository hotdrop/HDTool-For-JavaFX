package application;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import jp.ojt.sst.file.StackTraceData;
import jp.ojt.sst.file.StackTraceFile;
import jp.ojt.sst.view.TableViewData;

public class SSTController implements Initializable {
	
	@FXML
	private TextField filePathField;
	@FXML
	private TextField searchWordField;
	
	@FXML
	private Button buttonExecute;
	
	@FXML
	private TableView<TableViewData> resultTableView;
	@FXML
	private TableColumn<TableViewData, String> dateViewId;
	@FXML
	private TableColumn<TableViewData, Integer> numViewId;
	@FXML
	private TableColumn<TableViewData, String> exceptionViewId;
	@FXML
	private TableColumn<TableViewData, String> messageViewId;
	@FXML
	private TableColumn<TableViewData, String> locationViewId;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dateViewId.setCellValueFactory(new PropertyValueFactory<>("date"));
		numViewId.setCellValueFactory(new PropertyValueFactory<>("number"));
		exceptionViewId.setCellValueFactory(new PropertyValueFactory<>("exception"));
		messageViewId.setCellValueFactory(new PropertyValueFactory<>("message"));
		locationViewId.setCellValueFactory(new PropertyValueFactory<>("location"));
		
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
		stFile.read();
		HashMap<String, StackTraceData> resultMap = stFile.getResultMap();
		resultTableView.getItems().clear();
		for(StackTraceData stData : resultMap.values()) {
			TableViewData tbd = new TableViewData(stData.getDateStr(), stData.getExceptionStr(),
					stData.getMessage(),stData.getFindLine(), stData.getCount());
			resultTableView.getItems().add(tbd);
		}
	}
	
	@FXML
	protected void onExit(ActionEvent event) {
		((Stage) ((Button)event.getSource()).getScene().getWindow()).close();
	}
}
