/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NguoiHoc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;
import utils.Auth;

/**
 *
 * @author Quyết Chiến
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String>{
    
    final String INSERT = "INSERT dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)VALUES(?,?,?,?,?,?,?,?)";
    final String UPDATE = "UPDATE dbo.NguoiHoc SET HoTen = ?,NgaySinh = ?,GioiTinh = ?,DienThoai = ?,Email = ?,GhiChu = ? WHERE MaNH = ?";
    final String DELETE = "DELETE FROM dbo.HocVien WHERE MaNH  = ? DELETE FROM dbo.NguoiHoc WHERE MaNH = ?";
    final String SELECT_ALL = "SELECT*FROM dbo.NguoiHoc";
    final String SELECT_BYID = "SELECT*FROM dbo.NguoiHoc WHERE MaNH = ?";
    final String SELECT_NOT = "SELECT*FROM dbo.NguoiHoc "
                            + "WHERE HoTen LIKE ? AND "
                            + "MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH = ? )";
    
    @Override
    public void insert(NguoiHoc entity) {
        JDBCHepler.update(INSERT, entity.getMaNH(),entity.getHoTen(),entity.getNgaySinh(),entity.isGioiTinh(),entity.getDienThoai(),entity.getEmail(),entity.getGhiChu(),Auth.user.getMaNV());
    }

    @Override
    public void update(NguoiHoc entity) {
        JDBCHepler.update(UPDATE,entity.getHoTen(),entity.getNgaySinh(),entity.isGioiTinh(),entity.getDienThoai(),entity.getEmail(),entity.getGhiChu(), entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        JDBCHepler.update(DELETE, id,id);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL);
    }

    @Override
    public NguoiHoc selectById(String id) {
         List<NguoiHoc> list = this.selectBySql(SELECT_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {                
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));
                nh.setHoTen(rs.getString("HoTen"));
                nh.setNgaySinh(rs.getDate("NgaySinh"));
                nh.setGioiTinh(rs.getBoolean("GioiTinh"));
                nh.setDienThoai(rs.getString("DienThoai"));
                nh.setEmail(rs.getString("Email"));
                nh.setGhiChu(rs.getString("GhiChu"));
                nh.setMaNV(rs.getString("MaNV"));
                nh.setNgayTao(rs.getDate("NgayTao"));
                list.add(nh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public List<NguoiHoc> selectByKeyword(String keyword){
        String sql = "SELECT*FROM dbo.NguoiHoc WHERE HoTen LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    public List<NguoiHoc> selectNOT(int makh,String key){
        return this.selectBySql(SELECT_NOT, "%"+key+"%",makh);
    }
    
}
