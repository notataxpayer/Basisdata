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
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Shipment implements Initializable {
    String statuses;
    ObservableList<ShipData> listTemp = FXCollections.observableArrayList(); // buat table view

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        ID_Colum.setCellValueFactory(new PropertyValueFactory<ShipData, Integer>("ID"));
        Tujuan_Colum.setCellValueFactory(new PropertyValueFactory<ShipData, String>("Tujuan"));
        Tanggal_Collum.setCellValueFactory(new PropertyValueFactory<ShipData, String>("Tanggal"));
        Tatus_Colum.setCellValueFactory(new PropertyValueFactory<ShipData, String>("Status"));
        Berat_Column.setCellValueFactory(new PropertyValueFactory<ShipData, Integer>("Berat"));
        Data_Table.setItems(listTemp);

        
    }

    public void getList() {
        String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Shipment";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    int Id = resultSet.getInt("Shipment_ID");
                    String Tujuan = resultSet.getString("Alamat_tujuan");
                    String Tanggal = resultSet.getString("Tanggal_pengiriman");
                    String Status = resultSet.getString("Status_pengiriman");
                    int Berat = resultSet.getInt("Berat_Barang");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new ShipData(Id, Tujuan, Tanggal, Status, Berat));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                String deleteServisDetailQuery = "DELETE FROM Shipment WHERE Shipment_ID = ?;";
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
    private Button Add_But;

    @FXML
    private MenuItem Customer;

    @FXML
    private MenuItem Employee;

    @FXML
    private MenuItem Login;

    @FXML
    private MenuItem Choose_Done;

    @FXML
    private MenuItem Choose_Shipment;

    @FXML
    private MenuButton Chooser;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TableView<ShipData> Data_Table;

    @FXML
    private Button Delete_But;

    @FXML
    private Button Find_But;

    @FXML
    private TableColumn<ShipData, Integer> ID_Colum;

    @FXML
    private TextField ID_F;

    @FXML
    private TextField Search_F;

    @FXML
    private TableColumn<ShipData, String> Tanggal_Collum;

    @FXML
    private TextField Tanggal_F;

    @FXML
    private TableColumn<ShipData, String> Tujuan_Colum;

    @FXML
    private TextField Tujuan_F;

    @FXML
    private TableColumn<ShipData, String> Tatus_Colum;

    @FXML
    private TextField Berat_F;

     @FXML
    private TableColumn<ShipData, Integer> Berat_Column;


    @FXML
    void AddBtn(ActionEvent event) throws SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Shipment (Shipment_ID, Tanggal_pengiriman, Alamat_tujuan, Berat_barang, Status_pengiriman) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                int ID = Integer.parseInt(ID_F.getText());
                String Tanggal = Tanggal_F.getText();
                String Tujuan = Tujuan_F.getText();
                String Status = statuses;
                int Berat = Integer.parseInt(Berat_F.getText());

                // Set nilai parameter
                preparedStatement.setInt(1, ID);
                preparedStatement.setString(2, Tanggal);
                preparedStatement.setString(3, Tujuan);
                preparedStatement.setInt(4, Berat);
                preparedStatement.setString(5, Status);
                // Eksekusi pernyataan
                preparedStatement.executeUpdate();

                System.out.println("Shipment added successfully!");
                clearFields();
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

      @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void DelBtn(ActionEvent event) {
        int index = Data_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToDelete = ID_Colum.getCellData(index).toString();
        deleteRow(IdToDelete);
        getList();
    }

    @FXML
    void DoneC(ActionEvent event) {
        statuses = "Done";
        Chooser.setText("Done");
    }

    @FXML
    void FinBtn(ActionEvent event) {
        try {
            int id = Integer.parseInt(Search_F.getText());
            searchById(id);
            Data_Table.setItems(listTemp);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID for searching.");
        }
    }

    @FXML
    void ShipC(ActionEvent event) {
        statuses = "Shiping";
        Chooser.setText("Shipping");
    }

    @FXML
    void Switch_Customer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void Swtich_Login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // FNC

    private void clearFields() {
        ID_F.clear();
        Tanggal_F.clear();
        Tujuan_F.clear();
        Berat_F.clear();
        Chooser.setText("Choose");
    }
    private void refreshTable() {
        getList(); // Mendapatkan data dari database
        Data_Table.setItems(listTemp); // Menetapkan data pada tabel
    }
    private void searchById(int id) {
    String url = "jdbc:sqlserver://DYMI\\MSSQLSERVER03;databaseName=proakhir;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
    listTemp.clear();

    try (Connection connection = DriverManager.getConnection(url)) {
        String sql = "SELECT * FROM Shipment WHERE Shipment_ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int Id = resultSet.getInt("Shipment_ID");
                    String Tujuan = resultSet.getString("Alamat_tujuan");
                    String Tanggal = resultSet.getString("Tanggal_pengiriman");
                    String Status = resultSet.getString("Status_pengiriman");
                    int Berat = resultSet.getInt("Berat_Barang");

                    listTemp.add(new ShipData(Id, Tujuan, Tanggal, Status, Berat));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
