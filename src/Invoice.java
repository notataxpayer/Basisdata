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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Invoice implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {

        getList();
        IDInvoice_Column.setCellValueFactory(new PropertyValueFactory<InvoiceData, Integer>("IDInvoice"));
        IDShipment_Column.setCellValueFactory(new PropertyValueFactory<InvoiceData, Integer>("IDShipment"));
        IDPelanggan_Column.setCellValueFactory(new PropertyValueFactory<InvoiceData, Integer>("IDPelanggan"));
        Harga_Column.setCellValueFactory(new PropertyValueFactory<InvoiceData, Integer>("JumlahHarga"));
        Invoice_Table.setItems(listTemp);
    }

    ObservableList<InvoiceData> listTemp = FXCollections.observableArrayList(); // buat table view

    public void deleteRow(String IDInvoice) {
        // Informasi koneksi database
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        // Data yang ingin dihapus
        String VarIDInvoice = IDInvoice;

        try {
            // Membuat koneksig
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                // Hapus data dari tabel ServisDetail
                String deleteServisDetailQuery = "DELETE FROM Invoice WHERE Invoice_ID = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, VarIDInvoice);
                    preparedStatementServisDetail.executeUpdate();
                }
                // Commit transaksi jika berhasil
                connection.commit();
                System.out.println("Data berhasil dihapus.");
            } catch (SQLException e) {
                // Rollback transaksi jika terjadi kesalahan
                connection.rollback();
                e.printStackTrace();
            } finally {
                // Kembalikan otomatis commit ke kondisi awal
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchInvoiceById(String Invoice_ID) throws SQLException {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Invoice WHERE Invoice_ID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Invoice_ID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int InvoiceInvoice = resultSet.getInt("Invoice_ID");
                        int InvoiceShipment = resultSet.getInt("Shipment_ID");
                        int InvoiceCustomer = resultSet.getInt("Customer_ID");
                        int InvoiceTotalamount = resultSet.getInt("Total_Amount");

                        // Add the result to the list
                        listTemp.add(
                                new InvoiceData(InvoiceInvoice, InvoiceShipment, InvoiceCustomer, InvoiceTotalamount));
                    }

                    // Update the table with the search result
                    Invoice_Table.setItems(listTemp);

                    if (listTemp.isEmpty()) {
                        showAlert("Information", "Search Result", "No Customer with ID: " + Invoice_ID);
                    }
                }
            }
        }
    }

    @FXML
    private Button Add_button;

    @FXML
    private Button Delete_Button;

    @FXML
    private Menu File_Menu;

    @FXML
    private Button Find_Button;

    @FXML
    private TableColumn<InvoiceData, Integer> Harga_Column;

    @FXML
    private TextField Harga_Field;

    @FXML
    private TableColumn<InvoiceData, Integer> IDInvoice_Column;

    @FXML
    private TextField IDInvoice_Field;

    @FXML
    private TableColumn<InvoiceData, Integer> IDPelanggan_Column;

    @FXML
    private TextField IDPelanggan_Field;

    @FXML
    private TableColumn<InvoiceData, Integer> IDShipment_Column;

    @FXML
    private TextField IDShipment_Field;

    @FXML
    private MenuBar Invoice_Menu;

    @FXML
    private TableView<InvoiceData> Invoice_Table;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TextField Search_Field;

    @FXML
    private Button Update_Button;

    @FXML
    private AnchorPane apinvoice;

    @FXML
    private Label lblidinvoice;

    @FXML
    private Label lblidpelanggan;

    @FXML
    private Label lblidshipment;

    @FXML
    private Label lbljudul;

    @FXML
    private Label lbljumlahharga;

    @FXML
    private Label lblsearch;

    @FXML
    void Add_Btn(ActionEvent event) throws IOException, SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Invoice (Invoice_ID, Shipment_ID, Customer_ID, Total_Amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int IDInvoice = Integer.parseInt(IDInvoice_Field.getText());
                int IDShipment = Integer.parseInt(IDShipment_Field.getText());
                int IDPelanggan = Integer.parseInt(IDPelanggan_Field.getText());
                int JumlahHarga = Integer.parseInt(Harga_Field.getText());

                // Set nilai parameter
                preparedStatement.setInt(1, IDInvoice);
                preparedStatement.setInt(2, IDShipment);
                preparedStatement.setInt(3, IDPelanggan);
                preparedStatement.setInt(4, JumlahHarga);

                // Eksekusi pernyataan
                preparedStatement.executeUpdate();

                System.out.println("Invoice added successfully!");
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void Delete_Btn(ActionEvent event) {
        int index = Invoice_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IDInvoiceToDelete = IDInvoice_Column.getCellData(index).toString();
        deleteRow(IDInvoiceToDelete);
        getList();
    }

    @FXML
    void File_Btn(ActionEvent event) {

    }

    @FXML
    void Find_Btn(ActionEvent event) {
        String searchID = Search_Field.getText();
        if (searchID.isEmpty()) {
            showAlert("Warning", "Pencarian Gagal", "ID Pencarian tidak boleh kosong.");
        } else {
            try {
                String Trycon = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
                try (Connection connection = DriverManager.getConnection(Trycon)) {
                    String sql = "SELECT Invoice_ID, Shipment_ID, Customer_ID, Total_Amount FROM Invoice WHERE Invoice_ID = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, searchID);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                IDInvoice_Field.setText(resultSet.getString("Invoice_ID"));
                                IDShipment_Field.setText(resultSet.getString("Shipment_ID"));
                                IDPelanggan_Field.setText(resultSet.getString("Customer_ID"));
                                Harga_Field.setText(resultSet.getString("Total_Amount"));
                            } else {
                                // Jika ID tidak ditemukan, tampilkan pesan peringatan
                                showAlert("Warning", "Pencarian Gagal",
                                        "Data dengan ID " + searchID + " tidak ditemukan.");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Kesalahan Database", "Terjadi kesalahan saat mengakses database.");
            }
        }
    }

    @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Update_Btn(ActionEvent event) {
        InvoiceData selectedInvoice = Invoice_Table.getSelectionModel().getSelectedItem();

        if (selectedInvoice == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedInvoice.setIDInvoice(Integer.parseInt(IDInvoice_Field.getText()));
        selectedInvoice.setIDShipment(Integer.parseInt(IDShipment_Field.getText()));
        selectedInvoice.setIDPelanggan(Integer.parseInt(IDPelanggan_Field.getText()));
        selectedInvoice.setJumlahHarga(Integer.parseInt(Harga_Field.getText()));

        updateInvoice(selectedInvoice);
        clearFields();
        showSuccessAlert("Invoice data updated successfully!");
        refreshTable();
    }

    @FXML
    void Switch_Employee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Karyawan.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Shipment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Karyawan.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Swtich_Login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // FUNCTION

    private void updateInvoice(InvoiceData Invoice) {
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Invoice SET Invoice_ID = ?, Shipment_ID = ?, Customer_ID = ?, Total_Amount = ? WHERE Invoice_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Invoice.getIDInvoice());
                preparedStatement.setInt(2, Invoice.getIDShipment());
                preparedStatement.setInt(3, Invoice.getIDPelanggan());
                preparedStatement.setInt(4, Invoice.getJumlahHarga());

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
            showErrorAlert("Error updating Invoice data in the database: " + e.getMessage());
        }
    }

    private void refreshTable() {
        getList();
        Invoice_Table.setItems(listTemp);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        IDInvoice_Field.clear();
        IDShipment_Field.clear();
        IDPelanggan_Field.clear();
        Harga_Field.clear();
        // Search_Field.clear();
    }

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Invoice";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int invoiceid = resultSet.getInt("Invoice_ID");
                    int shipmentid = resultSet.getInt("Shipment_ID");
                    int customerid = resultSet.getInt("Customer_ID");
                    int totalamount = resultSet.getInt("Total_Amount");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new InvoiceData(invoiceid, shipmentid, customerid, totalamount));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}