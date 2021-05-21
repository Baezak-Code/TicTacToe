package crypto.trading;

import java.awt.*;
import javax.swing.*;

import static crypto.trading.TicTacToeConstants.LINE_THICKNESS;

/**
 * The computer's strategy is first to complete 3 X's in a row, or if that
 * is not possible, to block a row of 3 O's, or if that is not possible,
 * to move randomly.
 */
public final class TicTacToe extends JFrame {

    public TicTacToe() {
        super(TicTacToeConstants.BOARD_NAME);
        initialiseComponents();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(TicTacToeConstants.WIDTH, TicTacToeConstants.HEIGHT);
        setVisible(true);
    }

    private void initialiseComponents() {
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Line Thickness:"));
        initialiseBoardAndAddToFrame(topPanel);
    }

    private void initialiseBoardAndAddToFrame(JPanel topPanel) {
        final JButton oButton = new JButton("O Color");
        final JButton xButton = new JButton("X Color");
        final TicTacToeBoard ticTacToeBoard = createAction(oButton, xButton, createSlider(topPanel));
        topPanel.add(oButton);
        topPanel.add(xButton);
        add(topPanel, BorderLayout.NORTH);
        add(ticTacToeBoard, BorderLayout.CENTER);
    }

    private TicTacToeBoard createAction(final JButton oButton, final JButton xButton, final JSlider slider) {
        final TicTacToeAction ticTacToeAction = new TicTacToeAction(this, oButton, xButton, Color.BLUE, Color.RED);
        final TicTacToeBoard ticTacToeBoard = createListener(slider, ticTacToeAction);
        ticTacToeAction.setTicTacToeBoard(ticTacToeBoard);
        oButton.addActionListener(ticTacToeAction);
        xButton.addActionListener(ticTacToeAction);
        return ticTacToeBoard;
    }

    private TicTacToeBoard createListener(final JSlider slider, final TicTacToeAction ticTacToeAction) {
        final TicTacToeListener ticTacToeListener = new TicTacToeListener(LINE_THICKNESS, slider);
        final TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(ticTacToeAction, ticTacToeListener);
        ticTacToeListener.setTicTacToeBoard(ticTacToeBoard);
        slider.addChangeListener(ticTacToeListener);
        return ticTacToeBoard;
    }

    private JSlider createSlider(final JPanel topPanel) {
        final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 4);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        topPanel.add(slider);
        return slider;
    }

    public static void main(final String[] args) {
        new TicTacToe();
    }
}

