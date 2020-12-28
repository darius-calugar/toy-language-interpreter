package api.view.gui.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ProgramHelpWindowController {
    @FXML
    private Button okButton;
    @FXML
    private Button githubButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void onOK() {
        var stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onGithub() {
    }
}
