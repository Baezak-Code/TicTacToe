package crypto.trading;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class TicTacToeAction implements ActionListener {

    private final TicTacToe ticTacToe;

    private TicTacToeBoard ticTacToeBoard;

    private final JButton oButton;
    private final JButton xButton;

    private Color oColor;
    private Color xColor;

    public TicTacToeAction(final TicTacToe ticTacToe,
                           final JButton oButton,
                           final JButton xButton,
                           final Color oColor,
                           final Color xColor) {
        this.ticTacToe = ticTacToe;
        this.oButton = oButton;
        this.xButton = xButton;
        this.oColor = oColor;
        this.xColor = xColor;
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == oButton) {
            oColor = chooseNewColorForPlayer(oColor, TicTacToeConstants.O);
        } else if (e.getSource() == xButton) {
            xColor = chooseNewColorForPlayer(xColor, TicTacToeConstants.X);
        }
        ticTacToeBoard.repaint();
    }

    /**
     * Allows the user to choose a new color for either 'X' or 'O'.
     * If the new color is {@code null}, then by default if the
     * player equals 'O', {@link Color#BLUE} will be returned, else if the
     * player is 'X", then {@link Color#RED} will be returned.
     *
     * @param colour Initial color for the dialog to display.
     * @param player 'O' or 'X'.
     * @return {@code Color} selected by the player. If the new color
     * is {@code null}, then by default if the player equals 'O',
     * {@link Color#BLUE} will be returned, else if the player is 'X",
     * then {@link Color#RED} will be returned.
     */
    private Color chooseNewColorForPlayer(final Color colour, final char player) {
        final Color newColor = JColorChooser.showDialog(
                ticTacToe, "Choose a new color for " + player, colour);
        return newColor != null ? newColor : player == TicTacToeConstants.O ? Color.BLUE : Color.RED;
    }

    public final void setTicTacToeBoard(final TicTacToeBoard ticTacToeBoard) {
        this.ticTacToeBoard = ticTacToeBoard;
    }

    public final Color getOColor() {
        return oColor;
    }

    public final Color getXColor() {
        return xColor;
    }
}
