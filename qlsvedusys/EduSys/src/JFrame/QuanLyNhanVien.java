/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.MsgBox;

/**
 *
 * @author Quyết Chiến
 */
public class QuanLyNhanVien extends javax.swing.JDialog {
    NhanVienDAO nvDao = new NhanVienDAO();
    int row = 0;
    /**
     * Creates new form QuanLyNhanVien
     */
    public QuanLyNhanVien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        fillTable();
        updateStatus();
    }
    public void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tbQLNV.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvDao.selectAll();
            for (NhanVien nv : list) {
                model.addRow(new Object[]{
                    nv.getMaNV(),nv.getMatKhau(),nv.getHoTen(),nv.getVaiTro()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi Truy Vấn!");
        }
    }
    public void edit(){
        try {
            String maNV = (String) tbQLNV.getValueAt(row, 0);
            NhanVien entity = nvDao.selectById(maNV);
            if (entity != null) {
                setForm(entity);
                updateStatus();
                tabTong.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "lỗi truy vấn");
        }
    }
    public void setForm(NhanVien entity){
        txtMaNV.setText(entity.getMaNV());
        txtHoVaTen.setText(entity.getHoTen());
        txtPass.setText(entity.getMatKhau());
        txtXPasss.setText(entity.getMatKhau());
        rdTruongPhong.setSelected(entity.isVaiTro());
        rdNhanVien.setSelected(!entity.isVaiTro());
    }
    NhanVien getForm(){
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoTen(txtHoVaTen.getText());
        nv.setMatKhau(new String(txtPass.getPassword()));
        nv.setVaiTro(rdTruongPhong.isSelected());
        return nv;
    }
    void updateStatus(){
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tbQLNV.getRowCount()-1;
        txtMaNV.setEditable(!edit);
        
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }
    void clearForm(){
        this.setForm(new NhanVien());
        this.updateStatus();
        row = -1;
        updateStatus();
    }
    void insert(){
        NhanVien nv = getForm();
        String confirm = new  String(txtXPasss.getPassword());
        if (confirm.equals(nv.getMatKhau())) {
            try {
                nvDao.insert(nv);
                this.fillTable();
                this.clearForm();
                
                MsgBox.alert(this, "Thêm Mới Thành Công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Thêm Mới Thất Bại");
            }
        }
        else{
            MsgBox.alert(this, "Xác Nhận Mật Khẩu Không Đúng!");
        }
    }
    void update(){
        NhanVien nv = getForm();
        String confirm = new  String(txtXPasss.getPassword());
        if (!confirm.equals(nv.getMatKhau())) {
            MsgBox.alert(this, "Xác Nhận Mật Khẩu Không Đúng!");
        }else{
            try {
                nvDao.update(nv);
                this.fillTable();
                MsgBox.alert(this, "Cập Nhật Thành Công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Cập Nhật Thất Bại!");
            }
        }
        
    }
    void delete(){
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn Không Có Quyền Xóa Nhân Viên");
        }else{
            String maNV = txtMaNV.getText();
            if (maNV.equals(Auth.user.getMaNV())) {
                MsgBox.alert(this, "Bạn Không Thể Xóa Chính Mình");
            } else if (MsgBox.comfirm(this, "Bạn Chắc Chắn Muốn Xóa Nhân Viên Này ?")) {
                String manv = txtMaNV.getText();
                try {
                    nvDao.delete(manv);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa OK");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Xóa Thất Bại!");
                }
            }
        }
    }
    void first(){
        this.row = 0;
        this.edit();
    }
    void prev(){
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }
    void next(){
        if (this.row < tbQLNV.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    void last(){
        this.row = tbQLNV.getRowCount()-1;
        this.edit();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabTong = new javax.swing.JTabbedPane();
        tabCapNhat = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtHoVaTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdTruongPhong = new javax.swing.JRadioButton();
        rdNhanVien = new javax.swing.JRadioButton();
        txtPass = new javax.swing.JPasswordField();
        txtXPasss = new javax.swing.JPasswordField();
        tabDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbQLNV = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản Lý Nhân Viên");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dau.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/lui.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tien.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cuoi.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Mã Nhân Viên");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mật Khẩu");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Xác Nhận Mật Khẩu");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Họ Và Tên");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Vai Trò");

        buttonGroup1.add(rdTruongPhong);
        rdTruongPhong.setText("Trưởng Phòng");

        buttonGroup1.add(rdNhanVien);
        rdNhanVien.setText("Nhân Viên");

        javax.swing.GroupLayout tabCapNhatLayout = new javax.swing.GroupLayout(tabCapNhat);
        tabCapNhat.setLayout(tabCapNhatLayout);
        tabCapNhatLayout.setHorizontalGroup(
            tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCapNhatLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCapNhatLayout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi)
                        .addGap(38, 38, 38)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
                    .addComponent(txtXPasss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabCapNhatLayout.createSequentialGroup()
                        .addComponent(rdTruongPhong)
                        .addGap(18, 18, 18)
                        .addComponent(rdNhanVien))
                    .addComponent(jLabel5)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabCapNhatLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnFirst, btnLast, btnMoi, btnNext, btnPrev, btnSua, btnThem, btnXoa});

        tabCapNhatLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtHoVaTen, txtMaNV, txtPass, txtXPasss});

        tabCapNhatLayout.setVerticalGroup(
            tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXPasss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTruongPhong)
                    .addComponent(rdNhanVien))
                .addGap(18, 18, 18)
                .addGroup(tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirst)
                    .addGroup(tabCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnMoi))
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabCapNhatLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnFirst, btnLast, btnMoi, btnNext, btnPrev, btnSua, btnThem, btnXoa});

        tabCapNhatLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtHoVaTen, txtMaNV, txtPass, txtXPasss});

        tabTong.addTab("Cập Nhật", tabCapNhat);

        tbQLNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Mật Khẩu", "Họ Và Tên", "Vai Trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbQLNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbQLNVMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbQLNV);

        javax.swing.GroupLayout tabDanhSachLayout = new javax.swing.GroupLayout(tabDanhSach);
        tabDanhSach.setLayout(tabDanhSachLayout);
        tabDanhSachLayout.setHorizontalGroup(
            tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabDanhSachLayout.setVerticalGroup(
            tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDanhSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabTong.addTab("Danh Sách", tabDanhSach);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("Quản Lý Nhân Viên");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTong)
            .addGroup(layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabTong, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbQLNVMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbQLNVMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tbQLNV.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tbQLNVMousePressed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNhanVien dialog = new QuanLyNhanVien(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdNhanVien;
    private javax.swing.JRadioButton rdTruongPhong;
    private javax.swing.JPanel tabCapNhat;
    private javax.swing.JPanel tabDanhSach;
    private javax.swing.JTabbedPane tabTong;
    private javax.swing.JTable tbQLNV;
    private javax.swing.JTextField txtHoVaTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtXPasss;
    // End of variables declaration//GEN-END:variables
}
