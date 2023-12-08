public class EmployeeData {
    private int ID;
    private String Nama;
    private String Posisi;
    private int Gaji;
    private String Tanggal_perekrutan;

    public EmployeeData(int ID, String Nama, String Posisi, int employeeSalary, String Tanggal_perekrutan) {
        this.ID = ID;
        this.Nama = Nama;
        this.Posisi = Posisi;
        this.Gaji = employeeSalary;
        this.Tanggal_perekrutan = Tanggal_perekrutan;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getPosisi() {
        return Posisi;
    }

    public void setPosisi(String posisi) {
        Posisi = posisi;
    }

    public int getGaji() {
        return Gaji;
    }

    public void setGaji(int i) {
        Gaji = i;
    }

    public String getTanggal_perekrutan() {
        return Tanggal_perekrutan;
    }

    public void setTanggal_perekrutan(String tanggal_perekrutan) {
        Tanggal_perekrutan = tanggal_perekrutan;
    }

}
