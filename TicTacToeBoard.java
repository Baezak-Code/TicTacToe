package crypto.trading;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.*;

import static crypto.trading.TicTacToeConstants.*;

public final class TicTacToeBoard extends JPanel implements MouseListener {

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    private final Random random = new Random();
    
    private final char[] positions = {BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK};
    
    private final int[][] rows = {{0, 2}, {3, 5}, {6, 8}, {0, 6}, {1, 7}, {2, 8}, {0, 8}, {2, 6}};

    private final transient TicTacToeAction ticTacToeAction;

    private final transient TicTacToeListener ticTacToeListener;

    public TicTacToeBoard(final TicTacToeAction ticTacToeAction, final TicTacToeListener ticTacToeListener) {
        this.ticTacToeAction = ticTacToeAction;
        this.ticTacToeListener = ticTacToeListener;
        addMouseListener(this);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        drawGrid((Graphics2D) g, getWidth(), getHeight());
    }

    private void drawGrid(final Graphics2D graphics2D, final int width, final int height) {
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fill(new Rectangle2D.Double(0, 0, width, height));
        graphics2D.setPaint(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(ticTacToeListener.getLineThickness()));
        graphics2D.draw(new Line2D.Double(0, height / 3, width, height / 3));
        graphics2D.draw(new Line2D.Double(0, height * 2 / 3, width, height * 2 / 3));
        graphics2D.draw(new Line2D.Double(width / 3, 0, width / 3, height));
        graphics2D.draw(new Line2D.Double(width * 2 / 3, 0, width * 2 / 3, height));
        doDrawGrid(graphics2D, width, height);
    }

    private void doDrawGrid(final Graphics2D graphics2D, final int width, final int height) {
        IntStream.range(0, 9).forEach(i -> {
            double xPosition = (i % 3 + 0.5) * width / 3.0;
            double yPosition = (i / 3 + 0.5) * height / 3.0;
            double xr = width / 8.0;
            double yr = height / 8.0;
            if (positions[i] == O) {
                graphics2D.setPaint(ticTacToeAction.getOColor());
                graphics2D.draw(new Ellipse2D.Double(xPosition - xr, yPosition - yr, xr * 2, yr * 2));
            } else if (positions[i] == X) {
                graphics2D.setPaint(ticTacToeAction.getXColor());
                graphics2D.draw(new Line2D.Double(xPosition - xr, yPosition - yr, xPosition + xr, yPosition + yr));
                graphics2D.draw(new Line2D.Double(xPosition - xr, yPosition + yr, xPosition + xr, yPosition - yr));
            }
        });
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        final int position = (e.getX() * 3 / getWidth()) + 3 * (e.getY() * 3 / getHeight());
        if (position >= 0 && position < 9 && positions[position] == BLANK) {
            positions[position] = O;
            repaint();
            putX();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    void putX() {
        if (hasWon(O)) {
            newGame(O);
        } else if (isDraw()) {
            newGame(BLANK);
        } else {
            nextMove();
            if (hasWon(X)) {
                newGame(X);
            } else if (isDraw()) {
                newGame(BLANK);
            }
        }
    }

    private boolean hasWon(final char player) {
        return IntStream.range(0, 8).anyMatch(i -> testRow(player, rows[i][0], rows[i][1]));
    }

    private boolean testRow(final char player, final int a, final int b) {
        return positions[a] == player && positions[b] == player && positions[(a + b) / 2] == player;
    }

    private void nextMove() {
        int r = findRow(X);
        if (r < 0) {
            r = findRow(O);
        }
        if (r < 0) {  
            do {
                r = random.nextInt(9);
            } while (positions[r] != BLANK);
        }
        positions[r] = X;
    }

    private int findRow(final char player) {
        return IntStream.range(0, 8).map(i -> find1Row(player, rows[i][0], rows[i][1]))
                        .filter(result -> result >= 0)
                        .findFirst()
                        .orElse(-1);
    }

    private int find1Row(final char player, final int a, final int b) {
        final int c = (a + b) / 2;
        if (positions[a] == player && positions[b] == player && positions[c] == BLANK) {
            return c;
        }
        if (positions[a] == player && positions[c] == player && positions[b] == BLANK) {
            return b;
        }
        return positions[b] == player && positions[c] == player && positions[a] == BLANK ? a : -1;
    }

    private boolean isDraw() {
        return IntStream.range(0, 9).noneMatch(i -> positions[i] == BLANK);
    }

    private void newGame(final char winner) {
        repaint();

        if (isNewGame(winner) != JOptionPane.YES_OPTION) {
            System.exit(0);
        }

        // Clear the board to start a new game
        IntStream.range(0, 9).forEach(j -> positions[j] = BLANK);

        // Computer starts first every other game
        if ((wins + losses + draws) % 2 == 1) {
            nextMove();
        }
    }

    private int isNewGame(final char winner) {
        final String result = determineResult(winner);
        return JOptionPane.showConfirmDialog(
                null, "You have " + wins + " wins, " + losses + " losses, " + draws + " draws\n"
                        + "Play again?", result, JOptionPane.YES_NO_OPTION);
    }

    private String determineResult(final char winner) {
        switch (winner) {
            case O:
                ++wins;
                return "You Win!";
            case X:
                ++losses;
                return "You Lose!";
            default:
                ++draws;
                return "Tie";
        }
    }

    public static void main(String[] args) {
        int i = 5;
        int j = 2;
        System.out.println("" + (i << j) + " " + (i >> j));
    }
}
