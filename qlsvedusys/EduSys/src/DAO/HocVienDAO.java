/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HocVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jdbcHepler.JDBCHepler;

/**
 *
 * @author Quyết Chiến
 */
public class HocVienDAO extends EduSysDAO<HocVien, Integer>{
    
    final String INSERT = "INSERT dbo.HocVien(MaKH,MaNH,Diem) VALUES(?,?,?)";
    final String UPDATE = "UPDATE dbo.HocVien SET MaKH = ?,MaNH = ?,Diem = ? WHERE MaHV = ?";
    final String DELETE = "DELETE FROM dbo.HocVien WHERE MaHV = ?";
    final String SELECT_ALL = "SELECT*FROM dbo.HocVien";
    final String SELECT_BYID = "SELECT*FROM dbo.HocVien WHERE MaHV = ?";
    
    @Override
    public void insert(HocVien entity) {
        JDBCHepler.update(INSERT, entity.getMaKH(),entity.getMaNH(),entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        JDBCHepler.update(UPDATE, entity.getMaKH(),entity.getMaNH(),entity.getDiem(),entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        JDBCHepler.update(DELETE, id);
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBySql(SELECT_BYID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHepler.query(sql, args);
            while (rs.next()) {                
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getDouble("Diem"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public List<HocVien> selectByKhoaHoc(int MaKH){
        String sql  = "SELECT*FROM HocVien WHERE MaKH = ?";
        return this.selectBySql(sql, MaKH);
    }
}
