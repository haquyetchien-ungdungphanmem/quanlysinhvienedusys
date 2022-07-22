/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.ChuyenDe;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;

/**
 *
 * @author Quyết Chiến
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    final String INSERT = "INSERT dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)VALUES(?,?,?,?,?,?)";
    final String UPDATE = "UPDATE dbo.ChuyenDe SET TenCD = ?,HocPhi = ?, ThoiLuong = ?, Hinh = ?,MoTa = ? WHERE MaCD = ?";
    final String DELETE = "DELETE FROM dbo.HocVien WHERE MaKH IN (SELECT MaKH FROM dbo.KhoaHoc WHERE MaCD = ?) DELETE FROM dbo.KhoaHoc WHERE MaCD = ? DELETE FROM dbo.ChuyenDe WHERE MaCD = ?";
    final String SELECT_ALL = "SELECT*FROM dbo.ChuyenDe";
    final String SELECT_BYID = "SELECT*FROM dbo.ChuyenDe WHERE MaCD = ?";

    @Override
    public void insert(ChuyenDe entity) {
        JDBCHepler.update(INSERT, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        JDBCHepler.update(UPDATE, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        JDBCHepler.update(DELETE, id,id,id);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL);
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString("MaCD"));
                cd.setTenCD(rs.getString("TenCD"));
                cd.setHocPhi(rs.getDouble("HocPhi"));
                cd.setThoiLuong(rs.getInt("ThoiLuong"));
                cd.setHinh(rs.getString("Hinh"));
                cd.setMoTa(rs.getString("MoTa"));
                list.add(cd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
