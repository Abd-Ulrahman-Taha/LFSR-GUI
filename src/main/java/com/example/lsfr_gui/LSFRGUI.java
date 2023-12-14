package com.example.lsfr_gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

public class LSFRGUI extends Application {
    private int mValue;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("LSFR - Inputs");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label messageLabel = new Label("Message: ");
        gridPane.add(messageLabel, 0, 0);
        TextField messageInput = new TextField();
        messageInput.setStyle("-fx-text-box-fill: red; -fx-text-box-border: red;");
        messageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            messageInput.setStyle("-fx-text-fill: green; -fx-text-box-border: green;");
        });
        gridPane.add(messageInput, 1, 0);

        Label mLabel = new Label("M: ");
        gridPane.add(mLabel, 0, 1);
        ComboBox<Integer> mComboBox = new ComboBox<>();
        mComboBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9);
        gridPane.add(mComboBox, 1, 1);


        GridPane xGridPane = new GridPane();
        xGridPane.setHgap(5);
        xGridPane.setVgap(5);
        int rowCount = 2;
        int colCount = 0;

        TextField[] polynomialInputs = new TextField[9];
        for (int i = 0; i < 9; i++) {
            Label xLabel = new Label("X^" + (i + 1) + ": ");
            polynomialInputs[i] = new TextField();
            xGridPane.add(xLabel, colCount, rowCount);
            xGridPane.add(polynomialInputs[i], colCount + 1, rowCount);
            xLabel.setVisible(false);
            polynomialInputs[i].setVisible(false);
            if ((i + 1) % 3 == 0) {
                rowCount++;
                colCount = 0;
            } else {
                colCount += 2;
            }
        }

        mComboBox.setOnAction(e -> {
            mValue = mComboBox.getValue();

            for (int i = 0; i < 9; i++) {
                //polynomialInputs[i].setVisible(i < mValue);

                if (i < mValue) {
                    polynomialInputs[i].setVisible(true);
                    polynomialInputs[i].setText("1");
                    polynomialInputs[0].setText("0");
                    polynomialInputs[i].setStyle("-fx-text-box-fill: green; -fx-text-box-border: green;");
                    final int index = i;
                    polynomialInputs[i].textProperty().addListener((observable, oldValue, newValue) -> {
                        if(newValue.equals("0") || newValue.equals("1"))
                            polynomialInputs[index].setStyle("-fx-text-box-fill: green; -fx-text-box-border: green;");
                        else
                            polynomialInputs[index].setStyle("-fx-text-box-fill: red; -fx-text-box-border: red;");
                    });

                    xGridPane.getChildren().get(i * 2).setVisible(true);
                } else {
                    polynomialInputs[i].setVisible(false);
                    xGridPane.getChildren().get(i * 2).setVisible(false);
                }
            }
        });


        Label initialVectorLabel = new Label("Initial Vector: ");
        gridPane.add(initialVectorLabel, 0, 12);
        TextField initialVectorInput = new TextField();
        initialVectorInput.textProperty().addListener((observable, oldValue, newValue) -> {
            String initialHolder = initialVectorInput.getText();
            if(initialHolder.length() == mValue) {
                boolean changeColor = true;
                for(int i=0; i<initialHolder.length(); i++){
                    char c = initialHolder.charAt(i);
                    if(c != '0' && c != '1'){
                        changeColor = false;
                        break;
                    }
                }
                if(changeColor)
                    initialVectorInput.setStyle("-fx-text-box-fill: green; -fx-text-box-border: green;");
            }
            else{
                initialVectorInput.setStyle("-fx-text-box-fill: red; -fx-text-box-border: red;");
            }
        });
        gridPane.add(initialVectorInput, 1, 12);

        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 13);
        GridPane.setHgrow(submitButton, Priority.ALWAYS);
        submitButton.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(xGridPane, 0, 2, 2, rowCount - 1);



        submitButton.setOnAction(e -> {
            String message = messageInput.getText();
            int mValue = mComboBox.getValue();
            boolean[] polynomialValues = new boolean[mValue];
            String initialVector = initialVectorInput.getText();

            for (int i = 0; i < mValue; i++) {
                String polynomialText = polynomialInputs[i].getText();
                if(polynomialText.equals("1"))
                    polynomialValues[i] = true;
                else if(polynomialText.equals("0"))
                    polynomialValues[i] = false;
            }











            CyberSecurity cyber = new CyberSecurity(message, mValue, polynomialValues, initialVector);
            /**     New Window for Encryption/Decrption Output      **/
            Stage CyberStage = new Stage();
            BorderPane borderPane = new BorderPane();
            Scene CyberScene = new Scene(borderPane, 1000, 800);



            VBox leftBox = new VBox();
            VBox rightBox = new VBox();

            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);

            Button encryptionButton = new Button("Encrypt");
            Button decryptionButton = new Button("Decrypt");

            encryptionButton.setPrefWidth(250);
            decryptionButton.setPrefWidth(250);

            encryptionButton.setMaxWidth(Double.MAX_VALUE);
            decryptionButton.setMaxWidth(Double.MAX_VALUE);



            Label encryptionLabel = new Label("Cipher Text:");
            Label decryptionLabel = new Label("Pain Text:");
            TextArea encryptionMessage = new TextArea();
            encryptionMessage.setPrefWidth(450);
            encryptionMessage.setPrefHeight(300);
            encryptionMessage.setWrapText(true);
            encryptionMessage.setEditable(false);
            encryptionMessage.setText("Encrypted Message is here.!");

            TextArea decryptionMessage = new TextArea();
            decryptionMessage.setPrefWidth(450);
            decryptionMessage.setPrefHeight(300);
            decryptionMessage.setEditable(false);
            decryptionMessage.setWrapText(true);
            decryptionMessage.setText("Decrypted Message is here.!");

            Button tableButton = new Button("Show Table");
            tableButton.setPrefWidth(450);
            TextArea tableArea = new TextArea("Click To See what's going on!");
            tableArea.setPrefWidth(450);
            tableArea.setPrefHeight(CyberScene.getHeight());
            tableArea.setEditable(false);
            tableArea.setWrapText(true);

            VBox encryptionBox = new VBox(encryptionButton, encryptionLabel, encryptionMessage);
            encryptionBox.setAlignment(Pos.TOP_CENTER);
            encryptionBox.setSpacing(10);

            VBox decryptionBox = new VBox(decryptionButton, decryptionLabel, decryptionMessage);
            decryptionBox.setAlignment(Pos.BOTTOM_CENTER);
            decryptionBox.setSpacing(10);

            leftBox.getChildren().addAll(encryptionBox,decryptionBox);
            rightBox.getChildren().addAll(tableButton,tableArea);



            borderPane.setLeft(leftBox);
            borderPane.setRight(rightBox);
            borderPane.setCenter(separator);











            String fontSize = "18px";
            String style = "-fx-font-size: " + fontSize + ";";

            CyberScene.getRoot().setStyle(style);
            CyberStage.setTitle("LSFR [Encryption/Decryption]");
            CyberStage.setScene(CyberScene);
            CyberStage.show();





            encryptionButton.setOnAction(ee -> {
                String cipherText = cyber.encryption();
                encryptionMessage.setText(cipherText);
            });
            decryptionButton.setOnAction(ee -> {
                String painText = cyber.decryption();
                decryptionMessage.setText(painText);
            });
            tableButton.setOnAction(ee -> {
                String TextArea = "";
                String KeyStream = cyber.KeyStream;
                TextArea += "KeyStream ("+KeyStream.length()+"): "+KeyStream+"\n";
                TextArea += "=================================\n\n";
                for(int i=0; i<cyber.m; i++){
                    TextArea += "FF"+i+"\t";
                }
                TextArea += "\n";
                TextArea += cyber.Table();

                tableArea.setText(TextArea);
            });

        });













        String fontSize = "20px";
        String style = "-fx-font-size: " + fontSize + ";";

        Scene scene = new Scene(gridPane, 1000, 600);
        scene.getRoot().setStyle(style);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}