import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Pembayaran implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    String statuses;
    ObservableList<PembayaranData> listTemp = FXCollections.observableArrayList(); // buat table view

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        tcidpembayaran.setCellValueFactory(new PropertyValueFactory<PembayaranData, Integer>("PaymentID"));
        tcidinvoice.setCellValueFactory(new PropertyValueFactory<PembayaranData, Integer>("InvoiceID"));
        tcDate.setCellValueFactory(new PropertyValueFactory<PembayaranData, String>("Date"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<PembayaranData, Double>("Amount"));
        // Column_Email.setCellValueFactory(new PropertyValueFactory<PembayaranData, String>("Email"));
        tvpembayaran.setItems(listTemp);

        tvpembayaran.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                PembayaranData Payment = tvpembayaran.getSelectionModel().getSelectedItem();

                if (Payment != null) {
                    // Set the data to the input fields for editing
                    tfidpembayaran.setText(String.valueOf(Payment.getPaymentID()));
                    tfidinvoice.setText(String.valueOf(Payment.getInvoiceID()));
                    tfDate.setText(Payment.getDate());
                    tfAmount.setText(String.valueOf(Payment.getAmount()));
                }
            }
        });
    }

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Payment";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int Id = resultSet.getInt("Payment_ID");
                    int InvoiceID = resultSet.getInt("Invoice_ID");
                    String Date = resultSet.getString("Payment_Date");
                    double Amount = resultSet.getInt("Amount");
                    // String Email = resultSet.getString("Email_Pelanggan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new PembayaranData(Id, InvoiceID, Date, Amount));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private MenuBar Menu_Bar;

    @FXML
    private MenuItem Menu_Customer;

    @FXML
    private MenuItem Menu_Employee;

    @FXML
    private MenuItem Menu_Employee1;

    @FXML
    private Menu Menu_File;

    @FXML
    private MenuItem Menu_Logout;

    @FXML
    private MenuItem Menu_Shipment;

    @FXML
    private Button Refresh;

    @FXML
    private SeparatorMenuItem Seperator_Menu;

    @FXML
    private AnchorPane appembayaran;

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnfind;

    @FXML
    private Button btnupdate;

    @FXML
    private Label lblidinvoice;

    @FXML
    private Label lblidpelanggan;

    @FXML
    private Label lblidpelanggan1;

    @FXML
    private Label lblidpembayaran;

    @FXML
    private Label lbljudul;

    @FXML
    private Label lblsearch;

    @FXML
    private TableColumn<PembayaranData, Double> tcAmount;

    @FXML
    private TableColumn<PembayaranData, String> tcDate;

    @FXML
    private TableColumn<PembayaranData, Integer> tcidinvoice;

    @FXML
    private TableColumn<PembayaranData, Integer> tcidpembayaran;

    @FXML
    private TextField tfAmount;

    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfidinvoice;

    @FXML
    private TextField tfidpembayaran;

    @FXML
    private TextField tfsearch;

    @FXML
    private TableView<PembayaranData> tvpembayaran;

    @FXML
void Add(ActionEvent event) throws SQLException {
        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Buka koneksi
            System.out.println("Connecting to database...");
            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Payment (Payment_ID, Invoice_ID, Payment_Date, Amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int idpayment = Integer.parseInt(tfidpembayaran.getText());
                int idinvoice = Integer.parseInt(tfidinvoice.getText());
                String date = tfDate.getText();
                Double amount = Double.parseDouble(tfAmount.getText());

                // Set nilai parameter
                preparedStatement.setInt(1, idpayment);
                preparedStatement.setInt(2, idinvoice);
                preparedStatement.setString(3, date);
                preparedStatement.setDouble(4, amount);

                // Eksekusi pernyataan
                preparedStatement.executeUpdate();
                clearFields();

                System.out.println("Employee added successfully!");
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }        

    @FXML
    void Delete(ActionEvent event) {
         int index = tvpembayaran.getSelectionModel().getSelectedIndex();
    if (index <= -1) {
        return;
    }
    int invoiceIdToDelete = tcidinvoice.getCellData(index); // Menggunakan invoiceId yang dipilih
    deleteRow(invoiceIdToDelete);
    getList();
    }

    @FXML
    void Find(ActionEvent event) {
        String searchId = tfsearch.getText();
        if (searchId.isEmpty()) {
            showWarning("Warning", "Please enter a Package ID for search.");
            return;
        }

        try {
            // Perform search operation
            searchEmployeeById(searchId);
        } catch (SQLException e) {
            showErrorAlert("Error while searching for Package: " + e.getMessage());
        }
    }

    @FXML
    void Refresh(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Switch_Employee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Karyawan.fxml"));
        root = loader.load();
        // root = FXMLLoader.load(getClass().getResource("Karyawan.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void Switch_Paket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Paket.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Sipment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Shipment.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Swtich_Customer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Update(ActionEvent event) {
        PembayaranData selectedEmployee = tvpembayaran.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedEmployee.setPaymentID(Integer.parseInt(tfidpembayaran.getText()));
        selectedEmployee.setInvoiceID(Integer.parseInt(tfidinvoice.getText()));
        selectedEmployee.setDate(tfDate.getText());
        selectedEmployee.setAmount(Double.parseDouble(tfAmount.getText()));

        updateEmployee(selectedEmployee);
        clearFields();
        showSuccessAlert("Employee data updated successfully!");
        refreshTable();
    }

    // FNC

    private void searchEmployeeById(String PackageId) throws SQLException {
    String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
    listTemp.clear();

    try (Connection connection = DriverManager.getConnection(url)) {
        String sql = "SELECT * FROM Payment WHERE Payment_ID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, PackageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int PackageIdResult = resultSet.getInt("Payment_ID");
                    int Deskripsi = resultSet.getInt("Invoice_ID");
                    String Nilai = resultSet.getString("Payment_Date");
                    double Dimensi = resultSet.getDouble("Amount");

                    // Add the result to the list
                    listTemp.add(new PembayaranData(PackageIdResult, Deskripsi, Nilai, Dimensi));
                }

                // Update the table with the search result
                tvpembayaran.setItems(listTemp);

                if (listTemp.isEmpty()) {
                    showAlert("Information", "Search Result", "No Package found with ID: " + PackageId);
                }
            }
        }
    }
}

    private void updateEmployee(PembayaranData Payment) {
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Payment SET Invoice_ID = ?, Payment_Date = ?, Amount = ? WHERE Payment_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Payment.getInvoiceID());
                preparedStatement.setString(2, Payment.getDate());
                preparedStatement.setDouble(3, Payment.getAmount());
                preparedStatement.setInt(4, Payment.getPaymentID());

                // Eksekusi pernyataan
                int rowsAffected = preparedStatement.executeUpdate();

                // Commit the changes to the database
                if (rowsAffected > 0) {
                    System.out.println("Data berhasil diupdate.");
                } else {
                    System.out.println("Data tidak ditemukan untuk diupdate.");
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            showErrorAlert("Error updating Payment data in the database: " + e.getMessage());
        }
    }

    private void refreshTable() {
    getList(); 
    tvpembayaran.setItems(listTemp); 
    }

    private void clearFields() {
        tfidpembayaran.clear();
        tfidinvoice.clear();
        tfDate.clear();
        tfAmount.clear();
        tfsearch.clear();
    }

     private void showWarning(String title, String content) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Success");
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.showAndWait();

       
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Error");
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.showAndWait();
    }

    public void deleteRow(int invoiceId) {
    // Informasi koneksi database
    String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

    try {
        // Membuat koneksi
        Connection connection = DriverManager.getConnection(jdbcUrl);

        try {
            // Hapus data dari tabel Payment berdasarkan Invoice_ID
            String deletePaymentQuery = "DELETE FROM Payment WHERE Invoice_ID = ?;";
            try (PreparedStatement preparedStatementPayment = connection.prepareStatement(deletePaymentQuery)) {
                preparedStatementPayment.setInt(1, invoiceId);
                preparedStatementPayment.executeUpdate();
            }

            System.out.println("Data berhasil dihapus.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Kembalikan otomatis commit ke kondisi awal
            connection.setAutoCommit(true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
