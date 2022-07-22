/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;

/**
 *
 * @author Quyết Chiến
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql,String[] cols,Object...args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {                
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException();
        }
    }
    public List<Object[]> getBangDiem(Integer makh){
        String sql = "{CALL sp_BangDiem(?)}";
        String[] cols = {"MaNH","HoTen","Diem"};
        return getListOfArray(sql, cols, makh);
    }
   public List<Object[]> getNguoiHoc() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{CALL sp_LuongNguoiHoc}";
                rs = JDBCHepler.query(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("Nam"),
                        rs.getInt("SoLuong"),
                        rs.getDate("DauTien"),
                        rs.getDate("CuoiCung")
                    };
                    list.add(model);

                }
            } finally {
                rs.getStatement().getConnection().close();

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
        return list;

    }
    public List<Object[]> getDiemTheoChuyenDe() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_DiemChuyenDe()}";
                rs = JDBCHepler.query(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("ChuyenDe"),
                        rs.getInt("SoHV"),
                        rs.getDouble("DiemThapNhat"),
                        rs.getDouble("DiemCaoNhat"),
                        rs.getDouble("DiemTrungBinh")
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    public List<Object[]> getDoanhThu(Integer nam){
        String sql = "{CALL sp_DoanhThu(?)}";
        String[] cols = {"ChuyenDe","SoKH","SoHV","DoanhThu","ThapNhat","CaoNhat","TrungBinh"};
        return getListOfArray(sql, cols,nam);
    }
    public List<Integer> getNamKhaiGiang() {
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHepler.query("select distinct year(NgayKG) as nam from KhoaHoc order by year(NgayKG) desc");
                while (rs.next()) {
                    int nam = rs.getInt(1);
                    list.add(nam);
                }
            }finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
