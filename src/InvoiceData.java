public class InvoiceData {

    private int IDInvoice;
    private int IDShipment;
    private int IDPelanggan;
    private int JumlahHarga;

    public InvoiceData(int IDInvoice, int IDShipment, int IDPelanggan, int JumlahHarga) {
        this.IDInvoice = IDInvoice;
        this.IDShipment = IDShipment;
        this.IDPelanggan = IDPelanggan;
        this.JumlahHarga = JumlahHarga;
    }

    public int getIDInvoice() {
        return IDInvoice;
    }

    public void setIDInvoice(int idinvoice) {
        IDInvoice = idinvoice;
    }

    public int getIDShipment() {
        return IDShipment;
    }

    public void setIDShipment(int idshipment) {
        IDShipment = idshipment;
    }

    public int getIDPelanggan() {
        return IDPelanggan;
    }

    public void setIDPelanggan(int idpelanggan) {
        IDPelanggan = idpelanggan;
    }

    public Integer getJumlahHarga() {
        return JumlahHarga;
    }

    public void setJumlahHarga(int jumlahharga) {
        JumlahHarga = jumlahharga;
    }

}