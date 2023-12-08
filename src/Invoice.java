import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Invoice {

    @FXML
    private AnchorPane apinvoice;

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<?, ?> icidshipment;

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
    private MenuBar mbinvoice;

    @FXML
    private TableColumn<?, ?> tcidinvoice;

    @FXML
    private TableColumn<?, ?> tcidpelanggan;

    @FXML
    private TableColumn<?, ?> tcjumlahharga;

    @FXML
    private Button tffind;

    @FXML
    private TextField tfidinvoice;

    @FXML
    private TextField tfidpelanggan;

    @FXML
    private TextField tfidshipment;

    @FXML
    private TextField tfjumlahharga;

    @FXML
    private TextField tfsearch;

    @FXML
    private TableView<?> tvinvoice;

    @FXML
    void Add(ActionEvent event) {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Find(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {

    }

}
