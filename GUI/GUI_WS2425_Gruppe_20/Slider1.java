import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Slider1 extends Application {
    private static final int NUM_COUNT = 10;  // Anzahl der Zahlen
    private Rectangle highlightRect;         // Rechteck für die Hervorhebung
    private double[] numberPositions;        // Array für die Positionen der Zahlen

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // HBox für die Zahlen
        HBox numberBox = new HBox(20); // Abstand zwischen Zahlen
        numberBox.setAlignment(Pos.CENTER);

        // Array zur Speicherung der Positionen der Zahlen
        numberPositions = new double[NUM_COUNT];

        // Zahlen hinzufügen und ihre Positionen berechnen
        for (int i = 0; i < NUM_COUNT; i++) {
            Text number = new Text(String.valueOf(i));
            number.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // Jede Zahl wird in ein StackPane gelegt, um ihre Position zu bestimmen
            StackPane stackPane = new StackPane(number);
            stackPane.setMinSize(30, 30); // Größe der Zahl
            numberPositions[i] = i * (30 + 20); // 30px Breite + 20px Abstand
            numberBox.getChildren().add(stackPane);
        }

        // Highlight-Rechteck (um die ausgewählte Zahl)
        highlightRect = new Rectangle(30, 30); // Breite und Höhe
        highlightRect.setStroke(Color.BLUE);
        highlightRect.setFill(null); // Keine Füllfarbe

        // StackPane für die Zahlen und das Rechteck
        StackPane highlightPane = new StackPane();
        highlightPane.getChildren().addAll(numberBox, highlightRect);

        // Slider für die Auswahl der Zahl (ohne Tick-Labels und Markierungen)
        Slider slider = new Slider(0, NUM_COUNT - 1, 0);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(false); // Keine Tick-Marks anzeigen
        slider.setShowTickLabels(false); // Keine Zahlen unter dem Slider anzeigen
        slider.setSnapToTicks(true); // Snap to whole numbers

        // Listener für den Slider
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int index = newValue.intValue(); // Index der Zahl
            moveHighlightRect(index);       // Rechteck zur ausgewählten Zahl bewegen
        });

        // VBox Layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(highlightPane, slider);

        // Scene und Stage
        Scene scene = new Scene(root, 600, 200);
        primaryStage.setTitle("Number Highlighter");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Rechteck initial um die erste Zahl positionieren
        moveHighlightRect(0);
    }

    // Bewegt das Rechteck zur ausgewählten Zahl
    private void moveHighlightRect(int index) {
        double xOffset = numberPositions[index] - (numberPositions[NUM_COUNT - 1] / 2);
        highlightRect.setTranslateX(xOffset);
    }
}
