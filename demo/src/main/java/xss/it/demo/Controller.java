package xss.it.demo;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import xss.it.nfx.icons.AbstractIcon;
import xss.it.nfx.icons.Icon;
import xss.it.nfx.list.NfxCell;
import xss.it.nfx.list.NfxListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author XDSSWAR
 * Created on 02/20/2025
 */
public final class Controller implements Initializable {
    /**
     * Label displaying the selected icon or additional information.
     */
    @FXML
    private Label iconsLabel;

    /**
     * Custom list view for displaying a list of {@link IconData} items.
     */
    @FXML
    private NfxListView<IconData> listView;

    /**
     * Text field for searching and filtering icons in the list view.
     */
    @FXML
    private TextField searchField;

    /**
     * Tab pane for organizing different sections or categories of icons.
     */
    @FXML
    private TabPane tabPane;


    /**
     * A static thread pool with a fixed thread count of 1.
     * Each thread in the pool is set to daemon mode to ensure it does not prevent application shutdown.
     */
    static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    /**
     * Initializes the controller when the associated FXML file is loaded.
     * Calls the internal {@code initialize()} method to set up components.
     *
     * @param url    the location of the FXML file
     * @param bundle the resource bundle containing localized data
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        initialize();
    }

    /**
     * Initializes the list view and sets up the cell factory for rendering icons.
     * Configures the behavior for copying icon keys to the clipboard.
     */
    private void initialize(){
        listView.setCellFactory(new Callback<>() {
            @Override
            public NfxCell<IconData> call(NfxListView<IconData> list) {
                return new NfxCell<>(list){
                    @Override
                    public void update(IconData item) {
                        super.update(item);
                        if (item != null){
                            Button button = new Button(item.key);
                            button.setContentDisplay(ContentDisplay.TOP);
                            button.setGraphic(build(item.icon));
                            button.getStyleClass().add("box-button");

                            button.setOnAction(event -> {
                                button.getStyleClass().add("box-button-copied");
                                Clipboard cb = Clipboard.getSystemClipboard();
                                ClipboardContent cc = new ClipboardContent();
                                cc.putString(item.key);
                                cb.setContent(cc);
                                PauseTransition pt = new PauseTransition(Duration.millis(1000));
                                pt.setOnFinished(event1 -> {
                                    button.setText(item.key);
                                    button.getStyleClass().remove("box-button-copied");
                                });
                                button.setText("Copied");
                                pt.play();
                            });

                            var box = new VBox(button);
                            box.getStyleClass().add("box-base");
                            button.setPrefWidth(3000);
                            button.setPrefHeight(300);
                            VBox.setMargin(button, new Insets(5));
                            setGraphics(box);
                        }
                        setText(null);
                    }
                };
            }
        });



        loadIcons();
    }

    /**
     * Loads icons asynchronously and populates the list view.
     * Icons are retrieved and formatted into {@link IconData} objects before being displayed.
     */
    private void loadIcons(){
        THREAD_POOL.submit(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                var icon = new Icon();
                var icons = icon.icons();
                ObservableList<IconData> iconData = FXCollections.observableArrayList();
                for (String s : icons) {
                    var d = new IconData();
                    d.key= s;
                    d.icon = new Icon();
                    d.icon.setIcon(d.key);
                    iconData.add(d);
                }
                Platform.runLater(()->{
                    listView.setItems(iconData);
                    iconsLabel.setText(String.format("Total AbstractIcon : %s", listView.getItems().size()));
                    filter(listView, searchField);
                });
                return null;
            }
        });
    }

    /**
     * Filters the {@code listView} based on the text input in {@code textField}.
     * Updates the displayed list dynamically as the user types.
     *
     * @param listView   the {@link NfxListView} containing {@link IconData} items
     * @param textField  the {@link TextField} used for input filtering
     */
    private void filter(NfxListView<IconData> listView, TextField textField) {
        ObservableList<IconData> originalItems = listView.getItems();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();

            Task<FilteredList<IconData>> task = new Task<>() {
                @Override
                protected FilteredList<IconData> call() {
                    return new FilteredList<>(originalItems, iconData -> {
                        if (filter.isEmpty()) {
                            return true; // No filter
                        }
                        return (iconData.key.toLowerCase().contains(filter))
                                || (iconData.icon.fontFamily().toLowerCase().contains(filter));
                    });
                }

                @Override
                protected void succeeded() {
                    ObservableList<IconData> list = FXCollections.observableArrayList(getValue());
                    Platform.runLater(() -> {
                        listView.setItems(list);
                        iconsLabel.setText(String.format("Total AbstractIcon : %s", listView.getItems().size()));
                    });
                }
            };
            // Submit the filtering task to the thread pool
            THREAD_POOL.submit(task);
        });
    }


    /**
     * Represents an icon entry containing a key and its associated {@link AbstractIcon}.
     */
    static class IconData {
        /**
         * The key representing the icon name or identifier.
         */
        String key;

        /**
         * The icon instance associated with this key.
         */
        AbstractIcon icon;
    }

    /**
     * Builds a {@link VBox} container for displaying an icon.
     * Aligns the icon to the center within the box.
     *
     * @param icon the {@link AbstractIcon} to be displayed
     * @return a {@link VBox} containing the icon
     */
    private static VBox build(AbstractIcon icon) {
        var box = new VBox(icon);
        box.setAlignment(Pos.CENTER);
        return box;
    }

}
