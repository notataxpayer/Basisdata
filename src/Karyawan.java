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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Karyawan implements Initializable { // implements initia

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        ID_Column.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("ID"));
        Nama_Column.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Nama"));
        Gaji_Column.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("Gaji"));
        Posisi_Column.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Posisi"));
        TanggalRekrut_Column.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Tanggal_perekrutan"));
        Table.setItems(listTemp);

        Table.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                EmployeeData selectedEmployee = Table.getSelectionModel().getSelectedItem();

                if (selectedEmployee != null) {
                    // Set the data to the input fields for editing
                    ID_Field.setText(String.valueOf(selectedEmployee.getID()));
                    Nama_Field.setText(selectedEmployee.getNama());
                    Posisi_Field.setText(selectedEmployee.getPosisi());
                    Gaji_Field.setText(String.valueOf(selectedEmployee.getGaji()));
                    tanggalMasuk_Field.setText(selectedEmployee.getTanggal_perekrutan());
                }
            }
        });
    }

    ObservableList<EmployeeData> listTemp = FXCollections.observableArrayList(); // buat table view

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
                String deleteServisDetailQuery = "DELETE FROM Employee WHERE Employee_ID = ?;";
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

    @FXML
    private Button Add_Button;

    @FXML
    private Label Data_Karyawan;

    @FXML
    private Button Delete_Button;

    @FXML
    private Button Find_Button;

    @FXML
    private TableColumn<EmployeeData, Integer> Gaji_Column;

    @FXML
    private TextField Gaji_Field;

    @FXML
    private Label Gaji_Text;

    @FXML
    private TableColumn<EmployeeData, Integer> ID_Column;

    @FXML
    private TextField ID_Field;

    @FXML
    private Label ID_Text;

    @FXML
    private MenuBar Menu_Bar;

    @FXML
    private MenuItem Menu_Customer;

    @FXML
    private MenuItem Menu_Customer1;

    @FXML
    private MenuItem Menu_Employee;

    @FXML
    private Menu Menu_File;

    @FXML
    private MenuItem Menu_Logout;

    @FXML
    private MenuItem Menu_Shipment;

    @FXML
    private TableColumn<EmployeeData, String> Nama_Column;

    @FXML
    private TextField Nama_Field;

    @FXML
    private Label Nama_Text;

    @FXML
    private TableColumn<EmployeeData, String> Posisi_Column;

    @FXML
    private TextField Posisi_Field;

    @FXML
    private MenuItem Invoice;

    @FXML
    private Label Posisi_Text;

    @FXML
    private Button Refresh_Button1;

    @FXML
    private TextField tanggalMasuk_Field;

    @FXML
    private Label tanggalMasuk_Text;

    @FXML
    private TextField Search_Field;

    @FXML
    private Label Search_Text;

    @FXML
    private MenuItem Menu_Employee1;

    @FXML
    private SeparatorMenuItem Seperator_Menu;

    @FXML
    private TableView<EmployeeData> Table;

    @FXML
    private TableColumn<EmployeeData, String> TanggalRekrut_Column;

    @FXML
    private Button Update_Button;

    @FXML
    void Add_Btn(ActionEvent event) throws IOException, SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Employee (Employee_ID, Nama_karyawan, Posisi_karyawan, Gaji_karyawan, Tanggal_perekrutan) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int ID = Integer.parseInt(ID_Field.getText());
                String Nama = Nama_Field.getText();
                String Posisi = Posisi_Field.getText();
                int Gaji_karyawan = Integer.parseInt(Gaji_Field.getText());
                String Tanggal_perekrutan = tanggalMasuk_Field.getText();

                // Set nilai parameter
                preparedStatement.setInt(1, ID);
                preparedStatement.setString(2, Nama);
                preparedStatement.setString(3, Posisi);
                preparedStatement.setInt(4, Gaji_karyawan);
                preparedStatement.setString(5, Tanggal_perekrutan);

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
    void Delete_Btn(ActionEvent event) {
        int index = Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToDelete = ID_Column.getCellData(index).toString();
        deleteRow(IdToDelete);
        getList();
    }

    @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Find_Btn(ActionEvent event) {
        String searchId = Search_Field.getText();
        if (searchId.isEmpty()) {
            showWarning("Warning", "Please enter an Employee ID for search.");
            return;
        }

        try {
            // Perform search operation
            searchEmployeeById(searchId);
        } catch (SQLException e) {
            showErrorAlert("Error while searching for Employee: " + e.getMessage());
        }
    }

    @FXML
    void Logout_Btn(ActionEvent event) throws IOException {
        System.out.println("p");
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) Menu_Bar.getScene().getWindow();
        scene = new Scene(root);
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
    void Switch_Payment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pembayaran.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.show();
    }

    @FXML
    void Switch_Invoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Invoice.fxml"));
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
    void Update_Btn(ActionEvent event) {
        EmployeeData selectedEmployee = Table.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedEmployee.setID(Integer.parseInt(ID_Field.getText()));
        selectedEmployee.setNama(Nama_Field.getText());
        selectedEmployee.setPosisi(Posisi_Field.getText());
        selectedEmployee.setGaji(Integer.parseInt(Gaji_Field.getText()));
        selectedEmployee.setTanggal_perekrutan(tanggalMasuk_Field.getText());

        updateEmployee(selectedEmployee);
        clearFields();
        showSuccessAlert("Employee data updated successfully!");
        refreshTable();
    }

    @FXML
    void Switch_Paket(ActionEvent event) throws IOException {
        System.out.println("p");
        root = FXMLLoader.load(getClass().getResource("Paket.fxml"));
        stage = (Stage) Menu_Bar.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Employee";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int employeeId = resultSet.getInt("Employee_ID");
                    String employeeName = resultSet.getString("Nama_karyawan");
                    String employeePosition = resultSet.getString("Posisi_karyawan");
                    int employeeSalary = resultSet.getInt("Gaji_karyawan");
                    String recruitmentDate = resultSet.getString("Tanggal_perekrutan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new EmployeeData(employeeId, employeeName, employeePosition, employeeSalary,
                            recruitmentDate));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FUNCTION RELATIVE

    private void updateEmployee(EmployeeData employee) {
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Employee SET Nama_karyawan = ?, Posisi_karyawan = ?, Gaji_karyawan = ?, Tanggal_perekrutan = ? WHERE Employee_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, employee.getNama());
                preparedStatement.setString(2, employee.getPosisi());
                preparedStatement.setInt(3, employee.getGaji());
                preparedStatement.setString(4, employee.getTanggal_perekrutan());
                preparedStatement.setInt(5, employee.getID());

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
            showErrorAlert("Error updating employee data in the database: " + e.getMessage());
        }
    }

    private void refreshTable() {
    getList(); 
    Table.setItems(listTemp); 
    }

    private void searchEmployeeById(String employeeId) throws SQLException {
    String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
    listTemp.clear();

    try (Connection connection = DriverManager.getConnection(url)) {
        String sql = "SELECT * FROM Employee WHERE Employee_ID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int employeeIdResult = resultSet.getInt("Employee_ID");
                    String employeeName = resultSet.getString("Nama_karyawan");
                    String employeePosition = resultSet.getString("Posisi_karyawan");
                    int employeeSalary = resultSet.getInt("Gaji_karyawan");
                    String recruitmentDate = resultSet.getString("Tanggal_perekrutan");

                    // Add the result to the list
                    listTemp.add(new EmployeeData(employeeIdResult, employeeName, employeePosition, employeeSalary, recruitmentDate));
                }

                // Update the table with the search result
                Table.setItems(listTemp);

                if (listTemp.isEmpty()) {
                    showAlert("Information", "Search Result", "No employee found with ID: " + employeeId);
                }
            }
        }
    }
}

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

     private void clearFields() {
        ID_Field.clear();
        Nama_Field.clear();
        Posisi_Field.clear();
        Search_Field.clear();
        Gaji_Field.clear();
        tanggalMasuk_Field.clear();
        Search_Field.clear();
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

}
