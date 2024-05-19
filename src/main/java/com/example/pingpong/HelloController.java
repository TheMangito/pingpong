package com.example.pingpong;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML private AnchorPane panel;
    private Circle pelota;
    private int posicionInicialPelotaX = 300;
    private int posicionInicialPelotaY = 200;
    private int posicionPelotaX = 300;
    private int posicionPelotaY = 200;
    private int movimientoPelotaX = 3;
    private int movimientoPelotaY = 1;
    private Timer timer;
    private boolean gameScoreChange = false;

    private int jugador1X = 0;

    @FXML private Rectangle player1;
    public void initialize(){

    }

    public void restart() {
        movimientoPelotaX = -movimientoPelotaX;
        movimientoPelotaY = movimientoPelotaY;
        gameScoreChange = false;
        panel.getChildren().remove(pelota);

        pelota = new Circle(10);
        setupPelota();

        startTimer();
    }

    public void start() {
        panel.requestFocus();
        setupKeyHandlers();

        pelota = new Circle(10);
        setupPelota();

        startTimer();
    }

    private void setupPelota() {
        if (!panel.getChildren().contains(pelota)) {
            panel.getChildren().add(pelota);
        }
        posicionPelotaX = posicionInicialPelotaX;
        posicionPelotaY = posicionInicialPelotaY;
        pelota.setCenterX(posicionPelotaX);
        pelota.setCenterY(posicionPelotaY);
    }

    private void setupKeyHandlers() {
        panel.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            System.out.println(event.getCode());
            switch (event.getCode()) {
                case W -> {
                    if (!(player1.getLayoutY() + 20 < 40))
                        Platform.runLater(() -> player1.setLayoutY(player1.getLayoutY() - 20));
                }
                case S -> {
                    if (!(player1.getLayoutY() + 20 > 300))
                        Platform.runLater(() -> player1.setLayoutY(player1.getLayoutY() + 20));
                }
                default -> {
                }
            }
        });
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moverPelota();
                if (gameScoreChange) {
                    this.cancel();
                    timer.cancel();
                }
            }
        }, 0, 7);
    }

    public void moverPelota() {
        Platform.runLater(() -> {
            posicionPelotaX += movimientoPelotaX;
            posicionPelotaY += movimientoPelotaY;
            pelota.setCenterX(posicionPelotaX);
            pelota.setCenterY(posicionPelotaY);

            if (posicionPelotaX > 600 || posicionPelotaX < 0) {
                gameScoreChange = true;
                restart();
            }

            if (posicionPelotaY > 400 || posicionPelotaY < 0) {
                movimientoPelotaY = -movimientoPelotaY;
            }

            if (pelota.getBoundsInParent().intersects(player1.getBoundsInParent())) {
                movimientoPelotaX = -movimientoPelotaX;
            }
        });
    }
}