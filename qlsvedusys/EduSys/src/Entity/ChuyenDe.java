/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Quyết Chiến
 */
public class ChuyenDe {
    private String MaCD,TenCD,Hinh,MoTa;
    private double HocPhi;
    private int ThoiLuong;

    public ChuyenDe() {
    }

    public ChuyenDe(String MaCD, String TenCD, String Hinh, String MoTa, double HocPhi, int ThoiLuong) {
        this.MaCD = MaCD;
        this.TenCD = TenCD;
        this.Hinh = Hinh;
        this.MoTa = MoTa;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String TenCD) {
        this.TenCD = TenCD;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(double HocPhi) {
        this.HocPhi = HocPhi;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    @Override
    public String toString() {
        return this.TenCD;
    }

    @Override
    public boolean equals(Object obj) {
        ChuyenDe other = (ChuyenDe) obj;
        return other.getMaCD().equals(this.getMaCD());
    }
    
    
    
}
