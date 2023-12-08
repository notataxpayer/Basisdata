public class ShipData {

    private int ID;
    private String Tanggal;
    private String Tujuan;
    private String Status;
    private int Berat;

    public int getBerat() {
        return Berat;
    }

    public void setBerat(int berat) {
        Berat = berat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getTujuan() {
        return Tujuan;
    }

    public void setTujuan(String tujuan) {
        Tujuan = tujuan;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ShipData(int ID, String Tanggal, String Tujuan, String Status, int Berat) {
        this.ID = ID;
        this.Tanggal = Tanggal;
        this.Tujuan = Tujuan;
        this.Status = Status;
        this.Berat = Berat;
    }

}
