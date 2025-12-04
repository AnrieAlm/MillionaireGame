package Bday;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MillionaireGame extends Application {

    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean[] usedLifelines = new boolean[3];
    private ImageView darrenImageView;

    private final String poorImage = "/Assets/poor.png";
    private final String[] richImages = {
        "/Assets/rich1.png",
        "/Assets/rich2.png",
        "/Assets/rich3.png",
        "/Assets/rich4.png",
        "/Assets/rich5.png",
        "/Assets/rich6.png",
        "/Assets/rich7.png",
        "/Assets/rich8.png",
        "/Assets/rich9.png"
    };
    private final String rich10Image = "/Assets/rich10.png"; // Added rich10.png

    private RadioButton option1, option2, option3, option4;

    private final Question[] questions = {
            new Question("Who says 'Methej'?", "Jennifer", "Boris", "Tracey", "King Charles", "Tracey"),
            new Question("Name Darrens next prey?", "Elvira", "20 year old", "Microsoft Azure", "20 year old gym girl", "Elvira"),
            new Question("Who prays the most?", "Roman citizen of Pompeus", "Victor everywhere", "Priest in intimate shop", "Muslim on the gay parade", "Victor everywhere"),
            new Question("Continue the phrase 'Not my monkey...", "It is Hope", "Not my bananas", "'hi' yourself", "Not my coconuts", "It is Hope"),
            new Question("Which surface reflects the most light?", "Mirrors", "Polished silver", "Darrens head", "Still water", "Darrens head"),
            new Question("Choose the most broken thing?", "Darrens heart", "Darrens back", "Darrens spirit", "Darrens soul", "Darrens back"),
            new Question("what do you say when you see Sharat?", "Hi yourself", "Hows your mum", "Yo bro", "I love you my best bro", "I love you my best bro"),
            new Question("Who has ebola?", "Ebola", "Sharad", "Darshana", "Filoviridae", "Darshana"),
            new Question("Choose the Hope's prey?", "Darrens bicyle", "Sewa", "Darrens bicyle", "Tracyes lecture", "Darrens bicyle"),
            new Question("What does Darren say when you enter the church?", "Kneel", "Allahu Akbar", "Behold your master", "Darshana", "Allahu Akbar")
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("How to Be a Millionaire - Happy Birthday Darren!");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1e1e1e;");

        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.CENTER);
        topSection.setStyle("-fx-background-color: #252525; -fx-padding: 10; -fx-border-radius: 5;");

        Label scoreLabel = new Label("Score: $0");
        scoreLabel.setFont(Font.font("Arial", 18));
        scoreLabel.setTextFill(Color.LIMEGREEN);

        Button callFriendButton = createLifelineButton("Call a Friend", 0);
        Button fiftyFiftyButton = createLifelineButton("50/50", 1);
        Button askAudienceButton = createLifelineButton("Ask the Audience", 2);

        topSection.getChildren().addAll(scoreLabel, callFriendButton, fiftyFiftyButton, askAudienceButton);
        root.setTop(topSection);

        darrenImageView = new ImageView(new Image(getClass().getResourceAsStream(poorImage)));
        darrenImageView.setFitWidth(200);
        darrenImageView.setPreserveRatio(true);

        VBox imageCenter = new VBox();
        imageCenter.setAlignment(Pos.CENTER);
        imageCenter.getChildren().add(darrenImageView);
        root.setCenter(imageCenter);

        VBox questionSection = new VBox(15);
        questionSection.setAlignment(Pos.CENTER);
        questionSection.setPadding(new Insets(10, 20, 20, 20));
        questionSection.setStyle("-fx-background-color: #2d2d2d; -fx-background-radius: 10; -fx-padding: 15;");

        Label questionLabel = new Label();
        questionLabel.setFont(Font.font("Arial", 24));
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(700);
        questionLabel.setAlignment(Pos.CENTER);
        questionLabel.setTextFill(Color.WHITE);

        ToggleGroup optionsGroup = new ToggleGroup();
        option1 = createOptionButton(optionsGroup);
        option2 = createOptionButton(optionsGroup);
        option3 = createOptionButton(optionsGroup);
        option4 = createOptionButton(optionsGroup);

        styleRadioButton(option1);
        styleRadioButton(option2);
        styleRadioButton(option3);
        styleRadioButton(option4);

        HBox optionsBox = new HBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.getChildren().addAll(option1, option2, option3, option4);

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", 18));
        submitButton.setDisable(true);
        submitButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");

        questionSection.getChildren().addAll(questionLabel, optionsBox, submitButton);
        root.setBottom(questionSection);

        Scene scene = new Scene(root, 800, 600, Color.web("#1e1e1e"));
        primaryStage.setScene(scene);
        primaryStage.show();

        loadQuestion(currentQuestionIndex, questionLabel, option1, option2, option3, option4, submitButton, optionsGroup);

        submitButton.setOnAction(e -> {
            RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedOption != null) {
                String selectedAnswer = selectedOption.getText();
                if (selectedAnswer.equals(questions[currentQuestionIndex].getCorrectAnswer())) {
                    score += 100_000;
                    scoreLabel.setText("Score: $" + score);

                    int richIndex = Math.min(currentQuestionIndex, richImages.length - 1);
                    darrenImageView.setImage(new Image(getClass().getResourceAsStream(richImages[richIndex])));

                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        loadQuestion(currentQuestionIndex, questionLabel, option1, option2, option3, option4, submitButton, optionsGroup);
                    } else {
                        // Final win: show congratulations with rich10.png
                        showWinAlert();
                        primaryStage.close();
                    }
                } else {
                    darrenImageView.setImage(new Image(getClass().getResourceAsStream(poorImage)));
                    showAlert("You are the Hobo!", "Hobo is Hobo and nothig can change it!");
                    primaryStage.close();
                }
            }
        });
    }

    // New method: show win alert with rich10.png
    private void showWinAlert() {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Congratulations!");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #2d2d2d;");

        Label titleLabel = new Label("Congratulations!");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setTextFill(Color.LIMEGREEN);

        Label messageLabel = new Label("Your pesant life is in the past. You are finally a high society now!");
        messageLabel.setFont(Font.font("Arial", 18));
        messageLabel.setTextFill(Color.WHITE);

        // Add rich10.png image
        ImageView rich10View = new ImageView(new Image(getClass().getResourceAsStream(rich10Image)));
        rich10View.setFitWidth(200);
        rich10View.setPreserveRatio(true);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alertStage.close());
        closeButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");

        vbox.getChildren().addAll(titleLabel, messageLabel, rich10View, closeButton);

        Scene scene = new Scene(vbox, 600, 600, Color.web("#1e1e1e"));
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    private void styleRadioButton(RadioButton rb) {
        rb.setTextFill(Color.WHITE);
    }

    private void loadQuestion(int index, Label questionLabel, RadioButton option1, RadioButton option2, RadioButton option3, RadioButton option4, Button submitButton, ToggleGroup optionsGroup) {
        Question question = questions[index];
        questionLabel.setText((index + 1) + ". " + question.getQuestion());

        List<String> answers = new ArrayList<>();
        answers.add(question.getOption1());
        answers.add(question.getOption2());
        answers.add(question.getOption3());
        answers.add(question.getOption4());
        Collections.shuffle(answers);

        option1.setText(answers.get(0));
        option2.setText(answers.get(1));
        option3.setText(answers.get(2));
        option4.setText(answers.get(3));

        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);

        optionsGroup.selectToggle(null);
        submitButton.setDisable(false);
    }

    private Button createLifelineButton(String text, int lifelineIndex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-base: #2196F3; -fx-text-fill: white;");

        button.setOnAction(e -> {
            if (!usedLifelines[lifelineIndex]) {
                usedLifelines[lifelineIndex] = true;
                button.setDisable(true);
                button.setStyle("-fx-base: #757575; -fx-text-fill: #cccccc;");

                switch (lifelineIndex) {
                    case 0:
                        showAlert("Call a Friend", "Sorry Darren, you have no friends.");
                        break;
                    case 1:
                        showAlert("50/50", "Not my monkey, not my circus.");
                        removeTwoIncorrectOptions();
                        break;
                    case 2:
                        showAlert("Ask the Audience", "The audience is clueless!");
                        break;
                }
            } else {
                showAlert("Lifeline Used", "You've already used this lifeline!");
            }
        });
        return button;
    }

    private void removeTwoIncorrectOptions() {
        RadioButton[] options = {option1, option2, option3, option4};
        String correctAnswer = questions[currentQuestionIndex].getCorrectAnswer();

        List<RadioButton> incorrectOptions = new ArrayList<>();
        for (RadioButton option : options) {
            if (!option.getText().equals(correctAnswer)) {
                incorrectOptions.add(option);
            }
        }

        Collections.shuffle(incorrectOptions);
        for (int i = 0; i < 2 && i < incorrectOptions.size(); i++) {
            incorrectOptions.get(i).setDisable(true);
        }
    }

    private RadioButton createOptionButton(ToggleGroup group) {
        RadioButton button = new RadioButton();
        button.setFont(Font.font("Arial", 18));
        button.setToggleGroup(group);
        return button;
    }

    private void showAlert(String title, String message) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle(title);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #2d2d2d;");

        Label label = new Label(message);
        label.setFont(Font.font("Arial", 18));
        label.setTextFill(Color.WHITE);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alertStage.close());
        closeButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white;");

        vbox.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(vbox, 600, 200, Color.web("#1e1e1e"));
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class Question {
        private final String question;
        private final String option1;
        private final String option2;
        private final String option3;
        private final String option4;
        private final String correctAnswer;

        public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}