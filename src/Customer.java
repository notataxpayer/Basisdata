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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Customer implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    String statuses;
    ObservableList<CustData> listTemp = FXCollections.observableArrayList(); // buat table view

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        Column_ID.setCellValueFactory(new PropertyValueFactory<CustData, Integer>("CustomerID"));
        Column_Nama.setCellValueFactory(new PropertyValueFactory<CustData, String>("Nama"));
        Column_Alamat.setCellValueFactory(new PropertyValueFactory<CustData, String>("Alamat"));
        Column_NoHP.setCellValueFactory(new PropertyValueFactory<CustData, Integer>("NoHp"));
        Column_Email.setCellValueFactory(new PropertyValueFactory<CustData, String>("Email"));
        Cust_Table.setItems(listTemp);

        Cust_Table.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                CustData Customer = Cust_Table.getSelectionModel().getSelectedItem();

                if (Customer != null) {
                    // Set the data to the input fields for editing
                    Field_ID.setText(String.valueOf(Customer.getCustomerID()));
                    Field_Nama.setText(Customer.getNama());
                    Field_Alamat.setText(Customer.getAlamat());
                    Field_NoHP.setText(String.valueOf(Customer.getNoHp()));
                    Field_Email.setText(Customer.getEmail());
                }
            }
        });
    }

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Customer";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int Id = resultSet.getInt("Customer_ID");
                    String Nama= resultSet.getString("Nama_Pelanggan");
                    String Alamat = resultSet.getString("Alamat_Pelanggan");
                    int NoHP = resultSet.getInt("Nomor_Handphone_Pelanggan");
                    String Email = resultSet.getString("Email_Pelanggan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new CustData(Id, Nama, Alamat, NoHP, Email));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button Add_Button;

    @FXML
    private TableColumn<CustData, String> Column_Alamat;

    @FXML
    private TableColumn<CustData, String> Column_Email;

    @FXML
    private TableColumn<CustData, Integer> Column_ID;

    @FXML
    private TableColumn<CustData, String> Column_Nama;

    @FXML
    private TableColumn<CustData, Integer> Column_NoHP;

    @FXML
    private TableView<CustData> Cust_Table;

    @FXML
    private Button Delete_Button;

    @FXML
    private TextField Field_Alamat;

    @FXML
    private TextField Field_Email;

    @FXML
    private TextField Field_ID;

    @FXML
    private TextField Field_Nama;

    @FXML
    private TextField Field_NoHP;

    @FXML
    private TextField Field_Search;

    @FXML
    private Button Find_Button;

    @FXML
    private Label Label_Alamat;

    @FXML
    private Label Label_Email;

    @FXML
    private Label Label_ID;

    @FXML
    private Label Label_Nama;

    @FXML
    private Label Label_NoHP;

    @FXML
    private Label Label_Search;

    @FXML
    private Button Refresh_Button;

    @FXML
    private Button Update_Button;

    @FXML
    private MenuItem Login;

    @FXML
    private MenuItem Employee;

    @FXML
    private MenuItem Customer;

    @FXML
    private MenuItem Shipment;

    @FXML
    void Add_Btn(ActionEvent event) throws IOException, SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Customer (Customer_ID, Nama_Pelanggan, Alamat_Pelanggan, Nomor_Handphone_Pelanggan, Email_Pelanggan) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int ID = Integer.parseInt(Field_ID.getText());
                String Nama = Field_Nama.getText();
                String Alamat = Field_Alamat.getText();
                int NoHP = Integer.parseInt(Field_NoHP.getText());
                String Email = Field_Email.getText();

                // Set nilai parameter
                preparedStatement.setInt(1, ID);
                preparedStatement.setString(2, Nama);
                preparedStatement.setString(3, Alamat);
                preparedStatement.setInt(4, NoHP);
                preparedStatement.setString(5, Email);

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
        int index = Cust_Table.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String IdToDelete = Column_ID.getCellData(index).toString();
            deleteRow(IdToDelete);
            getList();
    }

    @FXML
    void Find_Btn(ActionEvent event) {
        String searchId = Field_Search.getText();
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
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Update_Btn(ActionEvent event) {
        CustData selectedCustomer = Cust_Table.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedCustomer.setCustomerID(Integer.parseInt(Field_ID.getText()));
        selectedCustomer.setNama(Field_Nama.getText());
        selectedCustomer.setAlamat(Field_Alamat.getText());
        selectedCustomer.setNoHp(Integer.parseInt(Field_NoHP.getText()));
        selectedCustomer.setEmail(Field_Email.getText());

        updateCustomer(selectedCustomer);
        clearFields();
        showSuccessAlert("Employee data updated successfully!");
        refreshTable();
    }

      @FXML
    void Switch_Customer(ActionEvent event) {

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

    //FNC

    private void updateCustomer(CustData Customer) {
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Customer SET Nama_Pelanggan = ?, Alamat_Pelanggan = ?, Nomor_Handphone_Pelanggan = ?, Email_Pelanggan = ? WHERE Customer_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Customer.getNama());
                preparedStatement.setString(2, Customer.getAlamat());
                preparedStatement.setInt(3, Customer.getNoHp());
                preparedStatement.setString(4, Customer.getEmail());
                preparedStatement.setInt(5, Customer.getCustomerID());

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
            showErrorAlert("Error updating Customer data in the database: " + e.getMessage());
        }
    }


    private void refreshTable() {
    getList(); 
    Cust_Table.setItems(listTemp); 
    }

    public void deleteRow(String ID) {
        // URL DB
        String jdbcUrl = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        String id = ID;

        try {
            // Membuat koneksigehhhh
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                String deleteServisDetailQuery = "DELETE FROM Customer WHERE Customer_ID = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, id);
                    preparedStatementServisDetail.executeUpdate();
                }
                System.out.println("Data berhasil dihapus.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void searchEmployeeById(String Customer_ID) throws SQLException {
    String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
    listTemp.clear();

    try (Connection connection = DriverManager.getConnection(url)) {
        String sql = "SELECT * FROM Customer WHERE Customer_ID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Customer_ID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int CustomerIDResult = resultSet.getInt("Customer_ID");
                    String CustomerName = resultSet.getString("Nama_Pelanggan");
                    String CustomerAlamat = resultSet.getString("Alamat_Pelanggan");
                    int CustomerNoHP = resultSet.getInt("Nomor_Handphone_Pelanggan");
                    String CustomerEmail = resultSet.getString("Email_Pelanggan");

                    // Add the result to the list
                    listTemp.add(new CustData(CustomerIDResult, CustomerName, CustomerAlamat, CustomerNoHP, CustomerEmail));
                }

                // Update the table with the search result
                Cust_Table.setItems(listTemp);

                if (listTemp.isEmpty()) {
                    showAlert("Information", "Search Result", "No Customer with ID: " + Customer_ID);
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
        Field_ID.clear();
        Field_Nama.clear();
        Field_Alamat.clear();
        Field_Search.clear();
        Field_NoHP.clear();
        Field_Email.clear();
        // Search_Field.clear();
    }

}
