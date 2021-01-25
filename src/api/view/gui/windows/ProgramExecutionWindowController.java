package api.view.gui.windows;

import api.controller.Controller;
import api.controller.GarbageCollector;
import api.model.ProgramState;
import api.model.collections.IHeap;
import api.model.statements.IStatement;
import api.model.values.IValue;
import api.model.values.StringValue;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProgramExecutionWindowController {
    ObjectProperty<Controller>   appController = new SimpleObjectProperty<>(null);
    ObjectProperty<ProgramState> viewedThread  = new SimpleObjectProperty<>(null);
    StringProperty               statusText    = new SimpleStringProperty("");

    @FXML
    private TableView<Map.Entry<String, IValue>>  symbolTableView;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML
    private ListView<StringValue>                 fileListView;
    @FXML
    private ListView<IValue>                      outputListView;
    @FXML
    private ListView<IStatement>                  executionStackListView;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String>   SymbolTableNameTableColumn;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, IValue>   SymbolTableValueTableColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> heapAddressTableColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, IValue>  heapValueTableColumn;

    @FXML
    private ListView<ProgramState> activeThreadsListView;
    @FXML
    private Label                  activeThreadLabel;
    @FXML
    private TextField              threadCountField;
    @FXML
    private Label                  statusLabel;

    @FXML
    private void initialize() {

        heapAddressTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue().getKey()));
        heapValueTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue().getValue()));

        SymbolTableNameTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue().getKey()));
        SymbolTableValueTableColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue().getValue()));

        statusText.addListener((observableValue, oldValue, newValue) -> {
            statusLabel.setText(newValue);
        });
        appController.addListener((observableValue, oldValue, newValue) -> {
            if (viewedThread.get() == null)
                appController.get().getRepository().getStates()
                        .stream()
                        .findFirst()
                        .ifPresent(state -> viewedThread.set(state));
            updateStateData();
            System.out.println("Fetched Data");
        });
        activeThreadsListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue != newValue && newValue != null && activeThreadsListView.getItems().size() > 0)
                viewedThread.setValue(newValue);
        });
        viewedThread.addListener((observableValue, oldValue, newValue) -> {
            activeThreadLabel.setText("Viewing: " + viewedThread.get().toString());
            updateStateData();
        });
    }

    @FXML
    private void handleRunAll() {
        statusText.setValue("Running all steps...");
        var time = Instant.now().toEpochMilli();

        appController.getValue().allStep();

        time = Instant.now().minusMillis(time).toEpochMilli();
        updateStateData();
        statusText.setValue(String.format("Ran all steps (%dms)", time));
    }

    @FXML
    private void handleRunOnce() {
        statusText.setValue("Running one step...");
        var time = Instant.now().toEpochMilli();

        var appControllerInstance = appController.get();
        appControllerInstance.setExecutor(Executors.newFixedThreadPool(Controller.MAX_THREADS));
        var states = appControllerInstance.removeCompletedPrograms(appControllerInstance.getRepository().getStates());

        var usedHeapContent = GarbageCollector.conservativeGarbageCollector(
                appControllerInstance.getRepository().getStates()
                        .stream()
                        .map(s -> s.getSymbolTable().getContent().values())
                        .collect(Collectors.toList()),
                appControllerInstance.getRepository().getStates()
                        .stream()
                        .map(ProgramState::getHeap)
                        .map(IHeap::getContent)
                        .findAny().orElse(new HashMap<>())
        );
        appControllerInstance.getRepository().getStates()
                .stream()
                .findAny()
                .ifPresent(s -> s.getHeap().setContent(usedHeapContent));
        appControllerInstance.oneStep(states);
        states = appControllerInstance.removeCompletedPrograms(appControllerInstance.getRepository().getStates());

        appControllerInstance.getExecutor().shutdownNow();
        appControllerInstance.getRepository().setStates(states);

        time = Instant.now().minusMillis(time).toEpochMilli();
        updateStateData();
        statusText.setValue(String.format("Ran one step (%dms)", time));
    }

    @FXML
    private void updateStateData() {
        if (appController.get() == null) {
            System.out.println("Skipped a state update: Null controller");
            return;
        }
        if (viewedThread.get() == null) {
            System.out.println("Skipped a state update: Null Viewed Thread");
            return;
        }

        symbolTableView.setItems(FXCollections.observableList(
                viewedThread != null ?
                        List.copyOf(viewedThread.get().getSymbolTable().getContent().entrySet()) :
                        List.of()
        ));
        appController.getValue().getRepository().getStates().stream().findAny().ifPresent(
                (state -> heapTableView.setItems(FXCollections.observableList(
                        new ArrayList<>(List.copyOf(state.getHeap().getContent().entrySet()))
                )))
        );
        appController.getValue().getRepository().getStates().stream().findAny().ifPresent(
                (state -> fileListView.setItems(FXCollections.observableList(
                        List.copyOf(state.getFileTable().getContent().keySet())
                )))
        );
        appController.getValue().getRepository().getStates().stream().findAny().ifPresent(
                (state -> outputListView.setItems(FXCollections.observableList(
                        state.getOutputList().getContent()
                )))
        );
        activeThreadsListView.setItems(FXCollections.observableList(
                appController.getValue().getRepository().getStates()
        ));
        executionStackListView.setItems(FXCollections.observableList(
                viewedThread != null ?
                        viewedThread.get().getExecutionStack().getContent() :
                        List.of()
        ));
        threadCountField.setText(viewedThread != null ?
                String.valueOf(appController.getValue().getRepository().getStates().size()) :
                "None"
        );
    }
}

