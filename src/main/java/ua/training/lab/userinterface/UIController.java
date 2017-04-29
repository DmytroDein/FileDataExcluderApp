package ua.training.lab.userinterface;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.apache.commons.io.FilenameUtils;
import ua.training.lab.Runner;
import ua.training.lab.service.SubscriberService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URLDecoder;

public class UIController {

    private static final String DEFAULT_FILE_EXTENSION = ".csv";
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
            resultFile = fixFileExtension(resultFile);
            proceedFileConversion(sourceFile, filteringFile, resultFile);
        }
    }

    private File fixFileExtension(File resultFile) {
        if (FilenameUtils.getExtension(resultFile.getAbsolutePath()).isEmpty()) {
            resultFile = new File(resultFile.getName().concat(DEFAULT_FILE_EXTENSION));
        }
        return resultFile;
    }

    private void proceedFileConversion(File sourceFile, File filteringFile, File resultFile) {
        //System.out.println("proceedFileConversion() executing");
        new SubscriberService().handle(sourceFile, filteringFile, resultFile);
        showDialog(Alert.AlertType.INFORMATION, "Info", "Файл сохранен");
    }

    private JFileChooser getFileChooser(){
        String currentAppPath = getCurrentAppPath();
        JFileChooser chooser = new JFileChooser(currentAppPath);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Формат файлов - .xls, .xlsx, .csv.", "xls", "xlsx", "csv");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    private String getCurrentAppPath() {
        String currentAppPath = Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedAppPath = null;
        try {
            decodedAppPath = URLDecoder.decode(currentAppPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedAppPath;
    }

    private void showDialog(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
