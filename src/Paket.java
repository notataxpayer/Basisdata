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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Paket implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ObservableList<PaketData> listTemp = FXCollections.observableArrayList();

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        tcid.setCellValueFactory(new PropertyValueFactory<PaketData, Integer>("ID"));
        tcdeskripsi.setCellValueFactory(new PropertyValueFactory<PaketData, String>("Deskripsi"));
        tcnilai.setCellValueFactory(new PropertyValueFactory<PaketData, Double>("Nilai"));
        tcdimensi.setCellValueFactory(new PropertyValueFactory<PaketData, String>("Dimensi"));
        // TanggalRekrut_Column.setCellValueFactory(new PropertyValueFactory<PaketData, String>("Tanggal_perekrutan"));
        tvpaket.setItems(listTemp);

        tvpaket.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                PaketData selectedEmployee = tvpaket.getSelectionModel().getSelectedItem();

                if (selectedEmployee != null) {
                    // Set the data to the input fields for editing
                    tfid.setText(String.valueOf(selectedEmployee.getID()));
                    tfdeskripsi.setText(selectedEmployee.getDeskripsi());
                    tfnilai.setText(String.valueOf(selectedEmployee.getNilai()));
                    tfdimensi.setText(selectedEmployee.getDimensi());
                    // tanggalMasuk_Field.setText(selectedEmployee.getTanggal_perekrutan());
                }
            }
        });
    }
    @FXML
    private Button Refresh;

    @FXML
    private AnchorPane appaket;

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnfind;

    @FXML
    private Button btnupdate;

    @FXML
    private Label lbldeskripsi;

    @FXML
    private Label lbldimensi;

    @FXML
    private Label lblid;

    @FXML
    private Label lbljudul;

    @FXML
    private Label lblnilai;

    @FXML
    private Label lblsearch;

    @FXML
    private MenuBar mbpaket;

    @FXML
    private TableColumn<PaketData, String> tcdeskripsi;

    @FXML
    private TableColumn<PaketData, String> tcdimensi;

    @FXML
    private TableColumn<PaketData, Integer> tcid;

    @FXML
    private TableColumn<PaketData, Double> tcnilai;

    @FXML
    private TextField tfdeskripsi;

    @FXML
    private TextField tfdimensi;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfnilai;

    @FXML
    private TextField tfsearch;

    @FXML
    private MenuItem Menu_Shipment;

    @FXML
    private MenuItem Menu_Logout;

    @FXML
    private MenuItem Menu_Employee;

    @FXML
    private MenuItem Menu_Customer;

    @FXML
    private Menu Menu_File;

    @FXML
    private TableView<PaketData> tvpaket;

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
    void Add(ActionEvent event) throws SQLException {
        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Buka koneksi
            System.out.println("Connecting to database...");
            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Package (Package_ID, Deskripsi_paket, Nilai_paket, Dimensi_paket) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int ID = Integer.parseInt(tfid.getText());
                String Deskripsi = tfdeskripsi.getText();
                Double Nilai = Double.parseDouble(tfnilai.getText());
                String Dimensi = tfdimensi.getText();

                // Set nilai parameter
                preparedStatement.setInt(1, ID);
                preparedStatement.setString(2, Deskripsi);
                preparedStatement.setDouble(3, Nilai);
                preparedStatement.setString(4, Dimensi);

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
        int index = tvpaket.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToDelete = tcid.getCellData(index).toString();
        deleteRow(IdToDelete);
        getList();
    }

    @FXML
    void Find_Btn(ActionEvent event) {
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
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Update(ActionEvent event) {
        PaketData selectedEmployee = tvpaket.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedEmployee.setID(Integer.parseInt(tfid.getText()));
        selectedEmployee.setDeskripsi(tfdeskripsi.getText());
        selectedEmployee.setNilai(Double.parseDouble(tfnilai.getText()));
        selectedEmployee.setDimensi(tfdimensi.getText());

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
        String sql = "SELECT * FROM Package WHERE Package_ID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, PackageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int PackageIdResult = resultSet.getInt("Package_ID");
                    String Deskripsi = resultSet.getString("Deskripsi_paket");
                    Double Nilai = resultSet.getDouble("Nilai_paket");
                    String Dimensi = resultSet.getString("Dimensi_paket");

                    // Add the result to the list
                    listTemp.add(new PaketData(PackageIdResult, Deskripsi, Nilai, Dimensi));
                }

                // Update the table with the search result
                tvpaket.setItems(listTemp);

                if (listTemp.isEmpty()) {
                    showAlert("Information", "Search Result", "No Package found with ID: " + PackageId);
                }
            }
        }
    }
}

    private void clearFields() {
        tfid.clear();
        tfdeskripsi.clear();
        tfnilai.clear();
        tfdimensi.clear();
        tfsearch.clear();
    }

    public void deleteRow(String ID) {
        // Informasi koneksi database
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        // Data yang ingin dihapus
        String id = ID;

        try {
            // Membuat koneksig
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                // Hapus data dari tabel ServisDetail
                String deleteServisDetailQuery = "DELETE FROM Package WHERE Package_ID = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, id);
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

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Package";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int ID = resultSet.getInt("Package_ID");
                    String Deskripsi = resultSet.getString("Deskripsi_paket");
                    Double Nilai = resultSet.getDouble("Nilai_paket");
                    String Dimensi = resultSet.getString("Dimensi_paket");
                    // String recruitmentDate = resultSet.getString("Tanggal_perekrutan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new PaketData(ID,Deskripsi,Nilai,Dimensi));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(PaketData Package) {
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Package SET Nama_karyawan = ?, Posisi_karyawan = ?, Gaji_karyawan = ?, Tanggal_perekrutan = ? WHERE Package_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Package.getID());
                preparedStatement.setString(2, Package.getDeskripsi());
                preparedStatement.setDouble(3, Package.getNilai());
                preparedStatement.setString(4, Package.getDimensi());
                preparedStatement.setInt(5, Package.getID());
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
            showErrorAlert("Error updating employee data in the database: " + e.getMessage());
        }
    }

    private void refreshTable() {
    getList(); 
    tvpaket.setItems(listTemp); 
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

}
