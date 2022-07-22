/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Quyết Chiến
 */
public class NguoiHoc {
    private String MaNH,HoTen,DienThoai,Email,GhiChu,MaNV;
    private Date NgaySinh,NgayTao;
    private boolean GioiTinh;

    public NguoiHoc() {
    }

    public NguoiHoc(String MaNH, String HoTen, String DienThoai, String Email, String GhiChu, String MaNV, Date NgaySinh, Date NgayTao, boolean GioiTinh) {
        this.MaNH = MaNH;
        this.HoTen = HoTen;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.GhiChu = GhiChu;
        this.MaNV = MaNV;
        this.NgaySinh = NgaySinh;
        this.NgayTao = NgayTao;
        this.GioiTinh = GioiTinh;
    }

    public String getMaNH() {
        return MaNH;
    }

    public void setMaNH(String MaNH) {
        this.MaNH = MaNH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }
    public String getGioiTinh(){
        if (isGioiTinh() == true) {
            return "Nam";
        }else{
            return "Nữ";
        }
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }
    
    
}
