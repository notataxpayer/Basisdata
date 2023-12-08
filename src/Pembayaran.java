import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Pembayaran {

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
    private Label lblidpembayaran;

    @FXML
    private Label lbljudul;

    @FXML
    private Label lblsearch;

    @FXML
    private MenuBar mbpembayaran;

    @FXML
    private TableColumn<?, ?> tcidinvoice;

    @FXML
    private TableColumn<?, ?> tcidpelanggan;

    @FXML
    private TableColumn<?, ?> tcidpembayaran;

    @FXML
    private TextField tfidinvoice;

    @FXML
    private TextField tfidpelanggan;

    @FXML
    private TextField tfidpembayaran;

    @FXML
    private TextField tfsearch;

    @FXML
    private TableView<?> tvpembayaran;

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
