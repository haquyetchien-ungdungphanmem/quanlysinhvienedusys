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
public class KhoaHoc {
    private int MaKH,ThoiLuong;
    private String MaCD,GhiChu,MaNV;
    private Date NgayKG,NgayTao;
    private double HocPhi;

    public KhoaHoc() {
    }

    public KhoaHoc(int MaKH, int ThoiLuong, String MaCD, String GhiChu, String MaNV, Date NgayKG, Date NgayTao, double HocPhi) {
        this.MaKH = MaKH;
        this.ThoiLuong = ThoiLuong;
        this.MaCD = MaCD;
        this.GhiChu = GhiChu;
        this.MaNV = MaNV;
        this.NgayKG = NgayKG;
        this.NgayTao = NgayTao;
        this.HocPhi = HocPhi;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
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

    public Date getNgayKG() {
        return NgayKG;
    }

    public void setNgayKG(Date NgayKG) {
        this.NgayKG = NgayKG;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(double HocPhi) {
        this.HocPhi = HocPhi;
    }

    @Override
    public String toString() {
        return MaKH +" "+ "(" + NgayKG + ')';
    }

    
    
}
