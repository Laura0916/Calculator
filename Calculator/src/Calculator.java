import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    // Window for our Calculator
    int boardWith = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(118, 118, 118);
    Color customBlack = new Color(25, 25, 25);
    Color customWhite = new Color(243, 243, 243);
    Color customLightTurquesa = new Color(69, 136, 170);
    Color customLightBrown = new Color(164, 140, 92);

    // Button Values
    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    String[] right_symbols = { "÷", "×", "-", "+", "=" };
    String[] top_symbols = { "AC", "+/-", "%" };

    // Text at the top of the window
    JFrame frame = new JFrame("Calculator");
    // Panel
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // A+B, A-B, A*B, A*B
    String A = "0";
    String operator = null;
    String B = null;

    // Constructor
    Calculator() {
        // Modify the window
        // frame.setVisible(true);
        frame.setSize(boardWith, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Panel configuration
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // Buttons configuration
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // Go throw every symbol
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            // Three category of buttons
            if (Arrays.asList(top_symbols).contains(buttonValue)) {
                button.setBackground(customLightTurquesa);
                button.setForeground(customBlack);
            } else if (Arrays.asList(right_symbols).contains(buttonValue)) {
                button.setBackground(customLightTurquesa);
                button.setForeground(customWhite);
            } else {
                button.setBackground(customLightGray);
                button.setForeground(customWhite);
            }
            buttonsPanel.add(button);

            // Button Actions
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(right_symbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if ((operator == "+")) {
                                    displayLabel.setText(removeZeroDecimal((numA + numB)));
                                } else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal((numA - numB)));
                                } else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal((numA * numB)));
                                } else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal((numA / numB)));
                                }
                                clearALL();
                            }
                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            } // So will the operator change if the user press from + to -
                            operator = buttonValue;
                        }

                    } else if (Arrays.asList(top_symbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            // Function
                            clearALL();
                            displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else { // digits or .
                        if (buttonValue == ".") { // Dont duplicate the decimal number
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains((buttonValue))) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }

                        }
                    }
                }
            });
            frame.setVisible(true);
        }

    }

    void clearALL() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);

    }
}
