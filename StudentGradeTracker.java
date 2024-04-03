// In the name of Almighty Allah
// This code is written by "Md Abu Omayer Babu"

// CodeAlpha Internship in "Java Programming" project named "Student Grade Tracker"

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StudentGradeTracker {
    private String studentName;
    private String rollNumber;
    private String regNumber;
    private String session;
    private ArrayList<String> courseNames = new ArrayList<>();
    private ArrayList<String> courseCodes = new ArrayList<>();
    private ArrayList<Double> grades = new ArrayList<>();
    private JFrame studentFrame;
    private JFrame courseFrame;
    private JFrame finalFrame;
    private JPanel gradesPanel;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::runApplication);
    }

    private static void runApplication() {
        StudentGradeTracker app = new StudentGradeTracker();
        app.createStudentDetailsWindow();
    }

    private void createStudentDetailsWindow() {
        studentFrame = new JFrame("Enter Student Details");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(10);
        JLabel rollLabel = new JLabel("Roll Number:");
        JTextField rollField = new JTextField(10);
        JLabel regLabel = new JLabel("Registration Number:");
        JTextField regField = new JTextField(10);
        JLabel sessionLabel = new JLabel("Session:");
        JTextField sessionField = new JTextField(10);
        JButton nextButton = new JButton("Next");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(rollLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(rollField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(regLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(regField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(sessionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(sessionField, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(nextButton, constraints);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentName = nameField.getText();
                rollNumber = rollField.getText();
                regNumber = regField.getText();
                session = sessionField.getText();
                studentFrame.dispose();
                createCourseDetailsWindow();
            }
        });

        studentFrame.add(panel);
        studentFrame.pack();
        studentFrame.setLocation(680, 250);
        studentFrame.setVisible(true);
    }

    private void createCourseDetailsWindow() {
        courseFrame = new JFrame("Enter Course Details");
        courseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel courseNameLabel = new JLabel("Enter course name:");
        JTextField courseNameField = new JTextField(10);
        JLabel courseCodeLabel = new JLabel("Enter course code:");
        JTextField courseCodeField = new JTextField(10);
        JLabel gradeLabel = new JLabel("Enter a grade:");
        JTextField gradeField = new JTextField(10);
        JButton addButton = new JButton("Add Grade");
        JButton doneButton = new JButton("Done");
        gradesPanel = new JPanel(new GridLayout(0, 3));
        averageLabel = new JLabel("Average: ");
        highestLabel = new JLabel("Highest: ");
        lowestLabel = new JLabel("Lowest: ");

        constraints.gridx = 0;
        constraints.gridy = 0;
        inputPanel.add(courseNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        inputPanel.add(courseNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(courseCodeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        inputPanel.add(courseCodeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        inputPanel.add(gradeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        inputPanel.add(gradeField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        inputPanel.add(addButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        inputPanel.add(doneButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        inputPanel.add(averageLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        inputPanel.add(highestLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        inputPanel.add(lowestLabel, constraints);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String courseCode = courseCodeField.getText();
                String input = gradeField.getText();
                try {
                    double grade = Double.parseDouble(input);
                    if (grade < 0 || grade > 100) {
                        showError("Please enter a valid grade between 0 and 100.");
                    } else {
                        courseNames.add(courseName);
                        courseCodes.add(courseCode);
                        grades.add(grade);
                        updateGradesPanel();
                        updateStatistics();
                        courseNameField.setText("");
                        courseCodeField.setText("");
                        gradeField.setText("");
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid input. Please enter a valid grade.");
                }
            }
        });

        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (grades.isEmpty()) {
                    showError("No grades entered.");
                } else {
                    courseFrame.dispose();
                    createFinalWindow();
                }
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gradesPanel), BorderLayout.CENTER);

        courseFrame.add(panel);
        courseFrame.pack();
        courseFrame.setLocation(680, 250);
        courseFrame.setVisible(true);
    }

    private void updateGradesPanel() {
        gradesPanel.removeAll(); // Clear the panel before updating
        for (int i = 0; i < grades.size(); i++) {
            JPanel entryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(new JLabel(i + 1 + ". " + courseNames.get(i) + " (" + courseCodes.get(i) + "): " + grades.get(i)));
            JButton deleteButton = new JButton("Delete");
            int index = i;
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    courseNames.remove(index);
                    courseCodes.remove(index);
                    grades.remove(index);
                    updateGradesPanel();
                    updateStatistics();
                }
            });
            entryPanel.add(deleteButton);
            gradesPanel.add(entryPanel);
        }
        gradesPanel.revalidate();
        gradesPanel.repaint();
    }

    private void updateStatistics() {
        double average = calculateAverage();
        double highest = Collections.max(grades);
        double lowest = Collections.min(grades);
        averageLabel.setText("Average: " + average);
        highestLabel.setText("Highest: " + highest);
        lowestLabel.setText("Lowest: " + lowest);
    }

    private void createFinalWindow() {
        finalFrame = new JFrame("Grade Summary");
        finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        double average = calculateAverage();
        double highest = Collections.max(grades);
        double lowest = Collections.min(grades);
        JLabel resultLabel = new JLabel("<html>Average Grade: " + average + "<br/>Highest Grade: " + highest + "<br/>Lowest Grade: " + lowest + "</html>");

        // Show student information
        JLabel studentInfoLabel = new JLabel("<html>Student Name: " + studentName + "<br/>Roll Number: " + rollNumber + "<br/>Registration Number: " + regNumber + "<br/>Session: " + session + "</html>");

        JButton printButton = new JButton("Print Report");
        JButton recalculateButton = new JButton("Recalculate");
        JButton exitButton = new JButton("Exit");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(studentInfoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(resultLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(printButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(recalculateButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(exitButton, constraints);

        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printReport();
            }
        });

        recalculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finalFrame.dispose();
                runApplication();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        finalFrame.add(panel);
        finalFrame.pack();
        finalFrame.setLocation(680, 250);
        finalFrame.setVisible(true);
    }

    private double calculateAverage() {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    private void printReport() {
        try {
            FileWriter writer = new FileWriter("academic_report.txt");
            writer.write("Name       :  " + studentName + "\n");
            writer.write("Roll No.   :  " + rollNumber + "\n");
            writer.write("Reg. No.   :  " + regNumber + "\n");
            writer.write("Session    :  " + session + "\n\n");
            writer.write("Course\t\tCode\t\tGrade\n");
            for (int i = 0; i < courseNames.size(); i++) {
                writer.write(i + 1 + ". " + courseNames.get(i) + "\t\t" + courseCodes.get(i) + "\t\t" + grades.get(i) + "\n");
            }
            
            writer.write("\n");
            writer.write("Average   :  " + calculateAverage() + "\n");
            writer.write("Highest   :  " + Collections.max(grades) + "\n");
            writer.write("Lowest   :  " + Collections.min(grades) + "\n");
            
            writer.close();
            JOptionPane.showMessageDialog(null, "Academic report printed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while printing the report!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
