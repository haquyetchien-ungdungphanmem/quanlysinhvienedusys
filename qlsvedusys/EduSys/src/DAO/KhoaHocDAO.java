/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhoaHoc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;

/**
 *
 * @author Quyết Chiến
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer> {

    final String INSERT = "INSERT dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)VALUES(?,?,?,?,?,?,?)";
    final String UPDATE = "UPDATE dbo.KhoaHoc SET MaCD = ?, HocPhi = ?,ThoiLuong = ?,NgayKG = ?,GhiChu = ?,MaNV = ?,NgayTao = ? WHERE MaKH = ?";
    final String DELETE = "DELETE FROM dbo.KhoaHoc WHERE MaKH = ?";
    final String SELECT_ALL = "SELECT*FROM dbo.KhoaHoc";
    final String SELECT_BYID = "SELECT*FROM dbo.KhoaHoc WHERE MaKH = ?";
    final String SELECT_BYMACD = "SELECT*FROM dbo.KhoaHoc WHERE MaCD = ?";
    @Override
    public void insert(KhoaHoc entity) {
        JDBCHepler.update(INSERT, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        JDBCHepler.update(UPDATE, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao(), entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        JDBCHepler.update(DELETE, id);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setMaCD(rs.getString("MaCD"));
                kh.setHocPhi(rs.getDouble("HocPhi"));
                kh.setThoiLuong(rs.getInt("ThoiLuong"));
                kh.setNgayKG(rs.getDate("NgayKG"));
                kh.setGhiChu(rs.getString("GhiChu"));
                kh.setMaNV(rs.getString("MaNV"));
                kh.setNgayTao(rs.getDate("NgayTao"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public List<KhoaHoc> selectBYMACD( String maCD) {
        return this.selectBySql(SELECT_BYMACD,maCD);
    }

}
