package api.view.gui.windows;

import api.BuiltInExamples;
import api.controller.Controller;
import api.model.ProgramState;
import api.model.collections.MyHeap;
import api.model.collections.MyList;
import api.model.collections.MyMap;
import api.model.collections.MyStack;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.InvalidTypeException;
import api.model.statements.IStatement;
import api.repository.Repository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button               browseButton;
    @FXML
    private Button               selectButton;
    @FXML
    private Button               cancelButton;
    @FXML
    private TextField            logNameTextField;

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
            logNameTextField.setText(Repository.generateLogPath(new Date()));
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
            selectedStatement.typeCheck(new MyMap<>());

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
                                    new MyHeap(),
                                    selectedStatement
                            ),
                            logNameTextField.getText()
                    )
            ));
        } catch (IOException exception) {
            new Alert(Alert.AlertType.WARNING, "Unknown error occurred when loading scene.", ButtonType.OK).show();
        } catch (ExpectedRefTypeException | InvalidTypeException exception) {
            new Alert(Alert.AlertType.WARNING, "TypeChecker detected inconsistent types.", ButtonType.OK).show();
        }
    }

    @FXML
    void onBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        var file = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
        logNameTextField.setText(file.getPath());
    }

    @FXML
    void onCancel() {
        Platform.exit();
    }
}
