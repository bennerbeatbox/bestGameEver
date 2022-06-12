package com.example.colorbox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class HelloController {
    private ArrayList<Rectangle> rectangles = new ArrayList();
    private Color color0 = Color.rgb(255,255,255);
    private Color color1 = Color.rgb(0,255,0);
    private Color color2 = Color.rgb(255,255,0);
    private Color color3 = Color.rgb(255,0,0);
    private Color color4 = Color.rgb(0,255,255);
    private int currentLevel;
    private int currentSquare;
    private int toSolve;
    private int solved;
    private String[] levels = {"2,2,0,2,2,0,0,0,0,5",
            "2,2,1,2,2,3,2,2,2,3",
            "2,2,2,2,2,3,2,3,2,9",
            "2,3,2,2,2,2,2,2,2,2",
            "0,2,0,2,4,2,0,0,0,5",
            "4,0,4,0,0,0,4,0,4,5",
    };
    private String[] playerPositions = {"390,210","540,210","690,210","390,360","540,360","690,360","390,510","540,510","690,510"};
    private String[] levelText = {"Your goal is to turn all of the colored squares green! \nClick on an adjacent square to jump to it and change \nits color. When all are green, you win!",
    "The red squares require two jumps to turn green.\nSo, remember to plan accordingly! :P",
    "It's possible to start on a green square. This square \ncan safely be ignored!",
    "A cute one. This time you START on a red square... \nGotta come back here twice!",
    "The teal squares require three jumps to turn green!\nRemember to plan adequately for this! ^^",
    "You win! Congrats :D"};

    @FXML
    private TextArea textArea = new TextArea();
    @FXML
    private Label levelLabel = new Label();
    @FXML
    private Label solvedLabel = new Label();
    @FXML
    private Button startButton = new Button();
    @FXML
    private Button resetButton = new Button();
    @FXML
    private Circle playerCircle = new Circle();

    @FXML
    private Rectangle rectangle1 = new Rectangle();
    @FXML
    private Rectangle rectangle2 = new Rectangle();
    @FXML
    private Rectangle rectangle3 = new Rectangle();
    @FXML
    private Rectangle rectangle4 = new Rectangle();
    @FXML
    private Rectangle rectangle5 = new Rectangle();
    @FXML
    private Rectangle rectangle6 = new Rectangle();
    @FXML
    private Rectangle rectangle7 = new Rectangle();
    @FXML
    private Rectangle rectangle8 = new Rectangle();
    @FXML
    private Rectangle rectangle9 = new Rectangle();

    public void start() {
        addRectangles();
        startButton.setVisible(false);
        startButton.setDisable(true);
        currentLevel = 1;
        loadLevel(levels[currentLevel-1].split(","));
        playerCircle.setVisible(true);
    }

    public void resetLevel() {
        if (currentLevel != levels.length) {
            loadLevel(levels[currentLevel-1].split(","));
        }
        else {
            currentLevel = 1;
            loadLevel(levels[currentLevel-1].split(","));
        }
    }

    public void setLevel(int level) {
        levelLabel.setText("Level " + level);
    }

    public void setTextArea(int level) {
        textArea.setText(levelText[level-1]);
    }

    public void addRectangles() {
            rectangles.add(rectangle1);
            rectangles.add(rectangle2);
            rectangles.add(rectangle3);
            rectangles.add(rectangle4);
            rectangles.add(rectangle5);
            rectangles.add(rectangle6);
            rectangles.add(rectangle7);
            rectangles.add(rectangle8);
            rectangles.add(rectangle9);
    }

    public void fillRectangle(Rectangle rectangle, int color) {
        switch (color) {
            case 1:
                rectangle.setFill(color1);
                rectangle.setStrokeWidth(1);
                break;
            case 2:
                rectangle.setFill(color2);
                rectangle.setStrokeWidth(1);
                break;
            case 3:
                rectangle.setFill(color3);
                rectangle.setStrokeWidth(1);
                break;
            case 4:
                rectangle.setFill(color4);
                rectangle.setStrokeWidth(1);
                break;
            default:
                rectangle.setFill(color0);
                rectangle.setStrokeWidth(0);
                break;
        }
    }

    public void loadLevel(String[] level) {
            toSolve = 0;
            solved = 0;
            int color;
            for (int i = 0; i < 9; i++) {
                color = Integer.parseInt(level[i]);
                fillRectangle(rectangles.get(i),color);
                if (color == 0 || color == 1) {
                    rectangles.get(i).setDisable(true);
                }
                else {
                    rectangles.get(i).setDisable(false);
                    toSolve++;
                }
            }
            currentSquare = Integer.parseInt(level[9]);
            movePlayerCircle(currentSquare);
            setTextArea(currentLevel);
            resetButton.setDisable(false);
            if (currentLevel == levels.length) {
                solvedLabel.setText("999/999");
                setLevel(999);
                setTextArea(currentLevel);
            }
            else {
                updateSolvedLabel();
                setLevel(currentLevel);
            }
        }

    public void updateSolvedLabel() {
        solvedLabel.setText(solved+"/"+toSolve);
        if (solved == toSolve) {
            currentLevel++;
            loadLevel(levels[currentLevel-1].split(","));
        }
    }

    public void movePlayerCircle(int currentSquare) {
        String[] coordinates = playerPositions[currentSquare-1].split(",");
        playerCircle.setLayoutX(Integer.parseInt(coordinates[0]));
        playerCircle.setLayoutY(Integer.parseInt(coordinates[1]));
    }

    public boolean checkValidRectangle (Rectangle rectangle) {
        int newSquare = rectangles.indexOf(rectangle)+1;
        int difference = currentSquare-newSquare;
        if (difference == 3 || difference == -3 || difference == 1 || difference == -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public void movePlayer(MouseEvent e) {
        Rectangle rectangle = (Rectangle) e.getSource();
        if (checkValidRectangle(rectangle)) {
            Color color = (Color) rectangle.getFill();
            currentSquare = rectangles.indexOf(rectangle) + 1;
            movePlayerCircle(currentSquare);
            if (color == color4) {
                fillRectangle(rectangle, 3);
            }
            if (color == color3) {
                fillRectangle(rectangle, 2);
            } else if (color == color2) {
                fillRectangle(rectangle, 1);
                rectangle.setDisable(true);
                solved++;
                updateSolvedLabel();
            }
        }
    }
}