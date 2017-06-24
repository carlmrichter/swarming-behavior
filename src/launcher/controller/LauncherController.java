package launcher.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import swarming.SwarmingBehavior;
import swarming.frame.Window;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {

    @FXML private Slider sliderSnappers;
    @FXML private TextField tfSnappers;
    @FXML private Slider sliderBarracudas;
    @FXML private TextField tfBarracudas;
    @FXML private CheckBox checkboxShark;
    @FXML private TextField tfWidth;
    @FXML private TextField tfHeight;
    @FXML private ChoiceBox cbWindowMode;

    private int selectedWindowMode = Window.NORMAL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //init sliders
        sliderSnappers.setMin(0);
        sliderSnappers.setMax(1000);
        sliderSnappers.setValue(300);
        sliderSnappers.setBlockIncrement(100);
        sliderSnappers.setMajorTickUnit(250);
        sliderSnappers.setMinorTickCount(4);
        sliderSnappers.setShowTickMarks(true);
        sliderSnappers.setShowTickLabels(true);
        sliderSnappers.valueProperty().addListener((observable, oldValue, newValue) ->
            tfSnappers.setText(String.valueOf(newValue.intValue()))
        );


        sliderBarracudas.setMin(0);
        sliderBarracudas.setMax(20);
        sliderBarracudas.setValue(2);
        sliderBarracudas.setBlockIncrement(1);
        sliderBarracudas.setMajorTickUnit(5);
        sliderBarracudas.setMinorTickCount(4);
        sliderBarracudas.setShowTickMarks(true);
        sliderBarracudas.setShowTickLabels(true);
        sliderBarracudas.valueProperty().addListener((observable, oldValue, newValue) ->
                tfBarracudas.setText(String.valueOf(newValue.intValue()))
        );


        // init textfields
        tfSnappers.setText(String.valueOf((int)sliderSnappers.getValue()));

        tfSnappers.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tfSnappers.isFocused() && !newValue.equals("")) {
                sliderSnappers.setValue(Integer.valueOf(newValue));
            }
        });
        tfSnappers.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && tfSnappers.getText().equals("")) {
                tfSnappers.setText("" + 0);
                sliderSnappers.setValue(0);
            }
        });


        tfBarracudas.setText(String.valueOf((int)sliderBarracudas.getValue()));
        tfBarracudas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tfBarracudas.isFocused() && !newValue.equals("")) {
                sliderBarracudas.setValue(Integer.valueOf(newValue));
            }
        });
        tfBarracudas.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && tfBarracudas.getText().equals("")) {
                tfBarracudas.setText("" + 0);
                sliderBarracudas.setValue(0);
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tfWidth.setText(String.valueOf((int)screenSize.getWidth() / 2));
        tfHeight.setText(String.valueOf((int)screenSize.getHeight() / 2));
        tfWidth.setPromptText("Width");
        tfHeight.setPromptText("Height");

        // init choiceBox (WindowMode)
        cbWindowMode.setItems(FXCollections.observableArrayList("Window", "Maximized", "Fullscreen"));
        cbWindowMode.setValue("Window");
        cbWindowMode.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedWindowMode = newValue.intValue();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                if (selectedWindowMode > 0) {
                    tfWidth.setText(String.valueOf((int)screenSize.getWidth()));
                    tfHeight.setText(String.valueOf((int)screenSize.getHeight()));
                } else {
                    tfWidth.setText(String.valueOf((int)screenSize.getWidth() / 2));
                    tfHeight.setText(String.valueOf((int)screenSize.getHeight() / 2));
                }
            }
        });
    }

    @FXML
    private void buttonLaunchClicked() {

        Runnable runnable = () -> {
            String[] args = {
                    "",
                    tfWidth.getText(),
                    tfHeight.getText(),
                    String.valueOf(selectedWindowMode),
                    tfSnappers.getText(),
                    tfBarracudas.getText(),
                    checkboxShark.isSelected() ? "1" : "0"
            };
            SwarmingBehavior.main(args);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.interrupt();
    }
}
