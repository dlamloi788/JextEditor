package view;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.reactfx.collection.LiveList;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

public class NumberStripFactory implements IntFunction<Node> {

    public static final BackgroundFill FILL = new BackgroundFill(Color.web("#ddd"), null, null);
    public static final Background DEFAULT_BACKGROUND = new Background(FILL);
    private Val<Integer> numberOfParagraphs;

    public NumberStripFactory(org.fxmisc.richtext.GenericStyledArea area) {
        numberOfParagraphs = LiveList.sizeOf(area.getParagraphs());
    }

    @Override
    public Node apply(int value) {
        ObservableValue<String> formattedValue = this.numberOfParagraphs.map(n -> {
            return format(value + 1, n);
        });
        Label lineLabel = new Label();
        lineLabel.textProperty().bind(formattedValue);
        lineLabel.setBackground(DEFAULT_BACKGROUND);
        lineLabel.setAlignment(Pos.CENTER);
        lineLabel.setFont(Font.font("monospace", FontPosture.ITALIC, 13.0D));
        return lineLabel;
    }

    /**
     * Determines the number of white spaces needed for the line number
     * Calculated by using log on the largest number
     * @param value the value to format
     * @param max the maximum number to format against
     * @return a value formatted with preceding blanks where needed
     */
    private String format(int value, int max) {
        //Log10 of the value + 1 returns the number of digits
        //Use floor to round the number
        int digits = (int)Math.floor(Math.log10((max))) + 1;
        //%1$ digits s is for white space (width like in c)
        //Align to the right
        return String.format("%1$" + digits + "s", value);
    }


}

