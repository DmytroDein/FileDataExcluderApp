package ua.training.lab.userinterface;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ua.training.lab.service.SubscriberService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class UIController {

    private File sourceFile;
    private File filteringFile;
    private File resultFile;

    @FXML
    Label lblF1Name;

    @FXML
    Label lblF2Name;


    public void importFile1(ActionEvent event){
        JFileChooser chooser = getFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            sourceFile = chooser.getSelectedFile();
//            System.out.println("Source File name: " + sourceFile.getAbsolutePath());
            lblF1Name.setText(sourceFile.getAbsolutePath());
        }

    }

    public void importFile2(ActionEvent event){
        JFileChooser chooser = getFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filteringFile = chooser.getSelectedFile();
//            System.out.println("Filtering File name: " + filteringFile.getAbsolutePath());
            lblF2Name.setText(filteringFile.getAbsolutePath());
        }
    }

    public void export(ActionEvent event) {
        JFileChooser chooser = getFileChooser();
        if(sourceFile == null || filteringFile == null) {
            showDialog(Alert.AlertType.ERROR, "Error", "Файлы не выбраны!");
            return;
        }
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            resultFile = chooser.getSelectedFile();
            proceedFileConversion(sourceFile, filteringFile, resultFile);
        }
    }

    private void proceedFileConversion(File sourceFile, File filteringFile, File resultFile) {
        //System.out.println("proceedFileConversion() executing");
        new SubscriberService().handle(sourceFile, filteringFile, resultFile);
        showDialog(Alert.AlertType.INFORMATION, "Info", "Файл сохранен");
    }

    private JFileChooser getFileChooser(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Формат файлов - .xls, .xlsx, .csv.", "xls", "xlsx", "csv");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    private void showDialog(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
