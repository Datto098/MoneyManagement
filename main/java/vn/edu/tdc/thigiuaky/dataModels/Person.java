package vn.edu.tdc.thigiuaky.dataModels;

public class Person {
    //Fields
    private String hoTen;
    private String bangCap;
    private String soThich;

    //Getter - setter

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getBangCap() {
        return bangCap;
    }

    public void setBangCap(String bangCap) {
        this.bangCap = bangCap;
    }

    public String getSoThich() {
        return soThich;
    }

    public void setSoThich(String soThich) {
        this.soThich = soThich;
    }

    //Constructor

    public Person(){
        this.hoTen = "none";
        this.bangCap = "none";
        this.soThich = "none";
    }
    public Person(String hoTen, String bangCap, String soThich) {
        this.hoTen = hoTen;
        this.bangCap = bangCap;
        this.soThich = soThich;
    }

    @Override
    public String toString() {
        return hoTen+ " # "+ bangCap + " # " + soThich;
    }
}
