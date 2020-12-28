package api.view.gui.windows;

import api.BuiltInExamples;
import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.Heap;
import api.model.collections.MyList;
import api.model.collections.MyMap;
import api.model.collections.MyStack;
import api.model.statements.IStatement;
import api.repository.Repository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgramSelectWindowController {
    IStatement selectedStatement = null;

    @FXML
    private ListView<IStatement> programStateListView;
    @FXML
    private TextArea             programPreviewTextArea;
    @FXML
    private Button               helpButton;
    @FXML
    private Button               selectButton;
    @FXML
    private Button               cancelButton;

    @FXML
    private void initialize() {
        programStateListView.setItems(FXCollections.observableList(
                Arrays.stream(BuiltInExamples.values())
                        .map(BuiltInExamples::getStatementTree)
                        .collect(Collectors.toList())
                )
        );

        programStateListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            selectedStatement = programStateListView.getSelectionModel().getSelectedItem();
            selectButton.setDisable(selectedStatement == null);
            programPreviewTextArea.setText((selectedStatement != null)
                    ? IStatement.unfoldStatement(selectedStatement).stream()
                    .map(Objects::toString)
                    .reduce("", ((s, statement) -> s + '\n' + statement))
                    .trim()
                    : ""
            );
        });
    }

    @FXML
    void onHelp() {
        try {
            var root  = (Parent) FXMLLoader.load(getClass().getResource("ProgramHelpWindow.fxml"));
            var stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Help & Instructions");
            stage.show();
        } catch (IOException ignored) {
        }
    }

    @FXML
    void onSelect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramExecutionWindow.fxml"));
            Stage      stage  = (Stage) selectButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            ProgramExecutionWindowController controller = loader.getController();
            controller.appController.setValue(new Controller(
                    new Repository(
                            new ProgramState(
                                    new MyStack<>(),
                                    new MyMap<>(),
                                    new MyList<>(),
                                    new MyMap<>(),
                                    new Heap(),
                                    selectedStatement
                            ),
                            "logs/log_" +
                            new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date()) +
                            ".txt"
                    )
            ));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    void onCancel() {
        Platform.exit();
    }
}
