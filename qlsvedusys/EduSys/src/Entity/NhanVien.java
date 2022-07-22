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
public class NhanVien {
    private String MaNV,MatKhau,HoTen;
    private boolean VaiTro;

    public NhanVien() {
    }

    public NhanVien(String MaNV, String MatKhau, String HoTen, boolean VaiTro) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.VaiTro = VaiTro;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }
    public String getVaiTro(){
        if (isVaiTro() == true) {
            return "Trưởng Phòng";
        }else{
            return "Nhân Viên";
        }
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }
    
    
}
