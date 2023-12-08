public class PaketData {
    private int ID;
    private String Deskripsi;
    private double Nilai;
    private String Dimensi;
   
    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public double getNilai() {
        return Nilai;
    }

    public void setNilai(double nilai) {
        Nilai = nilai;
    }

    public String getDimensi() {
        return Dimensi;
    }

    public void setDimensi(String dimensi) {
        Dimensi = dimensi;
    }

    public PaketData(int ID, String Deskripsi, double Nilai, String Dimensi){
        this.ID = ID;
        this.Deskripsi = Deskripsi;
        this.Nilai = Nilai;
        this.Dimensi = Dimensi;
    }
}
