package crypto.trading;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class TicTacToeListener implements ChangeListener {

    private int lineThickness;

    private TicTacToeBoard ticTacToeBoard;

    private final JSlider slider;

    public TicTacToeListener(final int lineThickness,
                             final JSlider slider) {
        this.lineThickness = lineThickness;
        this.slider = slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        lineThickness = slider.getValue();
        ticTacToeBoard.repaint();
    }

    public final void setTicTacToeBoard(final TicTacToeBoard ticTacToeBoard) {
        this.ticTacToeBoard = ticTacToeBoard;
    }

    public final int getLineThickness() {
        return lineThickness;
    }
}
