/* Two-Player Connect-Four Graphics */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

class ConnectFourFrame extends JFrame {


    private Game game;
    private boolean gameActive = false;

    private Player redPlayer;
    private Player yellowPlayer;
    private Player currentPlayer;
    private Random random;
    private boolean yellowPlayerTurn;

    private Board connectFourCanvas;
    private JLabel status;

    ConnectFourFrame(Game game) {
        super();
        this.game = game;
        this.redPlayer = new Player(false, game);
        this.yellowPlayer = new Player(true, game);
        random = new Random();
        connectFourCanvas = new Board(game);

        addBoardMouseListener();

        JPanel buttonPane = createButtonPane();

        JPanel statusPane = createStatusPane();

        add(connectFourCanvas, BorderLayout.CENTER);
        add(statusPane, BorderLayout.SOUTH);
        add(buttonPane, BorderLayout.NORTH);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connect-Four");
        setVisible(true);
        setResizable(false);
    }

    private JPanel createStatusPane() {
        JPanel statusPane = new JPanel();
        status = new JLabel("Welcome to Connect Four");
        status.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPane.setBackground(Color.WHITE);
        statusPane.add(status);
        statusPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return statusPane;
    }

    private JPanel createButtonPane() {
        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> startNewGame());

        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(newGameButton);
        return buttonPane;
    }

    private void addBoardMouseListener() {
        connectFourCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int colSelected = mouseX / Board.getCellSize();

                if (gameActive) {
                    try {
                        currentPlayer.makeMove(colSelected);
                        updateGame();
                    } catch (InvalidMoveException e1) {
                        alert(currentPlayer.toString() + " invalid move, try again");
                    }
                }
            }
        });
    }

    private void updateGame() {
        if (game.isWon()) {
            gameActive = false;
            alert("CONGRATULATIONS, " + currentPlayer + " is the winner!");
        } else if (game.isDraw()) {
            gameActive = false;
            alert("GAME OVER, it is a draw!");
        } else {
            switchPlayer();
        }
    }

    private void startNewGame() {
        game.clear();
        gameActive = true;
        yellowPlayerTurn = random.nextBoolean();
        currentPlayer = yellowPlayerTurn ? yellowPlayer : redPlayer;
        alert(currentPlayer.toString() + " it is your turn");
    }

    private void alert(String message) {
        status.setText(message);
        this.repaint();
    }

    private void switchPlayer() {
        yellowPlayerTurn = !yellowPlayerTurn;
        currentPlayer = yellowPlayerTurn ? yellowPlayer : redPlayer;
        alert(currentPlayer.toString() + " it is your turn");
    }
}

