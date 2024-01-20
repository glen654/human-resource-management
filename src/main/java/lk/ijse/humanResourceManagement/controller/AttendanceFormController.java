package lk.ijse.humanResourceManagement.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.AttendacenBO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFormController implements Initializable {
    @FXML
    private AnchorPane miniPanel;

    @FXML
    private TextArea txtArea;

    @FXML
    private Label txtUserName;

    @FXML
    private TextField txtEmpId;

    @FXML
    private AnchorPane rootNode;
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private boolean isReading = false;

    AttendacenBO attendacenBO = (AttendacenBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.ATTENDANCE);
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtArea.clear();
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnPauseOnAction(ActionEvent event) {
        stopWebcam();
    }

    private boolean stopWebcam() {
        if (webcamPanel != null) {
            webcamPanel.stop();
            webcamPanel = null;
        }
        if (webcam != null) {
            webcam.close();
            webcam = null;
        }
        return false;
    }

    @FXML
    void btnStartOnAction(ActionEvent event) {
        isReading = (!isReading) ? startWebcam() : stopWebcam();
    }

    private boolean startWebcam() {
        if (webcam == null) {
            Dimension size = new Dimension(640,480);
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);

            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(size);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setMirrored(true);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(webcamPanel);

            miniPanel.getChildren().clear();
            miniPanel.getChildren().add(swingNode);
        }

        Thread thread = new Thread(() -> {
            while (isReading) {
                try {
                    Thread.sleep(1000);
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result result = new MultiFormatReader().decode(binaryBitmap);
                        Platform.runLater(() -> {
                            if (result != null) {
                                webcam.close();

                                LocalDateTime currentTime = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String formattedTime = currentTime.format(formatter);

                                txtArea.appendText(result.getText() + "\n");
                                txtArea.appendText("Scanned Time: " + formattedTime + "\n");
                                try {
                                    LocalDateTime scannedTime = LocalDateTime.now();

                                    boolean isSaved = attendacenBO.saveAttendnace(scannedTime, result.getText());
                                    if (isSaved) {
                                        txtArea.appendText("Attendance record saved successfully!\n");
                                    } else {
                                        txtArea.appendText("Failed to save attendance record.\n");
                                    }
                                } catch (SQLException e) {
                                    txtArea.appendText("Error saving attendance: " + e.getMessage() + "\n");
                                }
                                new Alert(Alert.AlertType.CONFIRMATION, "Data Scanned Successfully!").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "No Data Found!").showAndWait();
                            }
                        });
                    }
                } catch (NotFoundException | InterruptedException | RuntimeException ignored) {

                }
            }
        });
        thread.start();
        return true;
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, JRException {
        String emp_id = txtEmpId.getText();
        clearFields();
        Connection connection = DbConnection.getInstance().getConnection();;

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/attendanceReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        System.out.println("SQL Query: " + jasperReport.getQuery().getText());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("emp_id", emp_id);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,connection);


        JasperViewer.viewReport(jasperPrint, false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clearFields(){
        txtEmpId.setText("");
    }
}
