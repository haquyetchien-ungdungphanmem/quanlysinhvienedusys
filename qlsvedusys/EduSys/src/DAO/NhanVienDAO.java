/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;
import utils.Auth;

/**
 *
 * @author Quyết Chiến
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    final String INSERT = "INSERT dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro) VALUES(?,?,?,?)";
    final String UPDATE = "UPDATE dbo.NhanVien SET MatKhau = ?,HoTen = ?,VaiTro = ? WHERE MaNV = ?";
    final String DELETE = "DELETE FROM dbo.NhanVien WHERE MaNV = ?";
    final String SELECT_ALL = "SELECT*FROM dbo.NhanVien";
    final String SELECT_BYID = "SELECT*FROM dbo.NhanVien WHERE MaNV = ?";
    final String UPDATE_FK = "UPDATE dbo.KhoaHoc SET MaNV = ? WHERE MaNV = ? \n"
                            + "UPDATE dbo.NguoiHoc SET MaNV = ? WHERE MaNV = ? ";
    @Override

    public void insert(NhanVien entity) {
        JDBCHepler.update(INSERT, entity.getMaNV(), entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JDBCHepler.update(UPDATE, entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JDBCHepler.update(UPDATE_FK, Auth.user.getMaNV(),id,Auth.user.getMaNV(),id);
        JDBCHepler.update(DELETE, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

}
