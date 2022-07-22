/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame;

import DAO.ChuyenDeDAO;
import DAO.HocVienDAO;
import DAO.KhoaHocDAO;
import DAO.NguoiHocDAO;
import Entity.ChuyenDe;
import Entity.HocVien;
import Entity.KhoaHoc;
import Entity.NguoiHoc;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.MsgBox;

/**
 *
 * @author Quyết Chiến
 */
public class QuanLyHocVien extends javax.swing.JDialog {
    ChuyenDeDAO cdDAO = new ChuyenDeDAO();
    KhoaHocDAO khDAO = new KhoaHocDAO();
    NguoiHocDAO nhDao = new NguoiHocDAO();
    HocVienDAO hvDao = new HocVienDAO();
    /**
     * Creates new form QuanLyHocVien
     */
    public QuanLyHocVien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        fillCBBChuyenDe();
    }
    void fillCBBChuyenDe(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChuyenDe.getModel();
        model.removeAllElements();
        try {
            List<ChuyenDe> list = cdDAO.selectAll();
            for (ChuyenDe chuyenDe : list) {
            model.addElement(chuyenDe);
        }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "lỗi fillCBBChuyenDe");
        }
        
//        fillCBBKhoaHoc();
    }
    void fillCBBKhoaHoc(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbKhoaHoc.getModel();
        model.removeAllElements();
        try {
            ChuyenDe chuyenDe = (ChuyenDe) cbbChuyenDe.getSelectedItem();
            if (chuyenDe != null) {
                List<KhoaHoc> list = khDAO.selectBYMACD(chuyenDe.getMaCD());
                for (KhoaHoc khoaHoc : list) {
                    model.addElement(khoaHoc);
                }
            }
            fillTbleHocVien();
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi fillCBBKhoaHoc");
        }
    }
    public void fillTableNguoiHoc(){
        DefaultTableModel model = (DefaultTableModel) tbNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cbbKhoaHoc.getSelectedItem();
            if (kh != null) {
                System.out.println("MaKH: "+kh.getMaKH());
                String key = txtTim.getText();
                List<NguoiHoc> list = nhDao.selectNOT(kh.getMaKH(), key);
                for (NguoiHoc nh : list) {
                    model.addRow(new Object[]{
                        nh.getMaNH(),nh.getHoTen(),nh.getNgaySinh(),nh.getGioiTinh(),
                        nh.getDienThoai(),nh.getEmail()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "lỗi fillTableNguoiHoc");
        }
    }
    public void fillTbleHocVien(){
        DefaultTableModel model = (DefaultTableModel) tbHocVien.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cbbKhoaHoc.getSelectedItem();
            if (kh != null) {
                System.out.println("fillTableHocVien MaKH : "+kh.getMaKH());
                List<HocVien> list = hvDao.selectByKhoaHoc(kh.getMaKH());
                System.out.println("list: "+list.size());
                for (int i = 0; i < list.size(); i++) {
                    HocVien hv = list.get(i);
                    String Hoten = nhDao.selectById(hv.getMaNH()).getHoTen();
                    model.addRow(new Object[]{
                        i+1,hv.getMaHV(),hv.getMaNH(),Hoten,hv.getDiem()
                    });
                }
            }
            fillTableNguoiHoc();
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi fillTableHocVien");
        }
    }
    void addHocVien(){
        KhoaHoc kh = (KhoaHoc) cbbKhoaHoc.getSelectedItem();
        for (int row : tbNguoiHoc.getSelectedRows()) {
            HocVien hv = new HocVien();
            hv.setMaKH(kh.getMaKH());
            hv.setDiem(0);
            hv.setMaNH((String) tbNguoiHoc.getValueAt(row, 0));
            System.out.println("=>"+hv.getMaKH()+"-"+hv.getMaNH()+"-"+hv.getDiem());
            hvDao.insert(hv);
        }
        fillTbleHocVien();
        tabs.setSelectedIndex(0);
    }
    void removeHocVien(){
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn Không Có Quyền Xóa!");
        }else{
            if (MsgBox.comfirm(this, "Bạn Muốn Xóa Các Học Viên Được Chọn???")) {
                for (int row : tbHocVien.getSelectedRows()) {
                    int maHV = (int) tbHocVien.getValueAt(row, 1);
                    hvDao.delete(maHV);
                }
                fillTbleHocVien();
            }
        }
    }
    void upDateDiem(){
        DefaultTableModel model = (DefaultTableModel) tbHocVien.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int maHV = Integer.parseInt(model.getValueAt(i, 1).toString()) ;
            HocVien hocVien = hvDao.selectById(maHV);
            hocVien.setDiem(Double.parseDouble(model.getValueAt(i, 4).toString()));
            hvDao.update(hocVien);
            
        }
        MsgBox.alert(this, "Cập Nhật Thành Công");
        this.fillTbleHocVien();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbbChuyenDe = new javax.swing.JComboBox<>();
        cbbKhoaHoc = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHocVien = new javax.swing.JTable();
        btnXoaKH = new javax.swing.JButton();
        btnCapNhatDiem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNguoiHoc = new javax.swing.JTable();
        btnThemVaoKH = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản Lý Học Viên");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setText("Chuyên Đề");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Khóa Học");

        cbbChuyenDe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbChuyenDeItemStateChanged(evt);
            }
        });
        cbbChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChuyenDeActionPerformed(evt);
            }
        });

        tbHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HV", "Mã NH", "Họ Và Tên", "Điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbHocVien);
        if (tbHocVien.getColumnModel().getColumnCount() > 0) {
            tbHocVien.getColumnModel().getColumn(0).setResizable(false);
            tbHocVien.getColumnModel().getColumn(1).setResizable(false);
            tbHocVien.getColumnModel().getColumn(2).setResizable(false);
            tbHocVien.getColumnModel().getColumn(3).setResizable(false);
            tbHocVien.getColumnModel().getColumn(3).setPreferredWidth(200);
            tbHocVien.getColumnModel().getColumn(4).setResizable(false);
        }

        btnXoaKH.setText("Xóa Khỏi Khóa Học");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnCapNhatDiem.setText("Cập Nhật Điểm");
        btnCapNhatDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapNhatDiem)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhatDiem)
                    .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCapNhatDiem, btnXoaKH});

        tabs.addTab("Học Viên", jPanel2);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Tìm Kiếm");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search.png"))); // NOI18N
        btnTim.setText("Tìm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnTim)
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTim)
                    .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã  NH", "Họ Và Tên", "Giới Tính", "Ngày Sinh", "Điên Thoại", "Email"
            }
        ));
        jScrollPane2.setViewportView(tbNguoiHoc);
        if (tbNguoiHoc.getColumnModel().getColumnCount() > 0) {
            tbNguoiHoc.getColumnModel().getColumn(0).setResizable(false);
        }

        btnThemVaoKH.setText("Thêm Học Viên Vào Khóa Học");
        btnThemVaoKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemVaoKH)
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThemVaoKH, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Người Học", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbbChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cbbKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbbChuyenDe, cbbKhoaHoc});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tabs))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbChuyenDe, cbbKhoaHoc});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChuyenDeActionPerformed
        // TODO add your handling code here:
        fillCBBKhoaHoc();
    }//GEN-LAST:event_cbbChuyenDeActionPerformed

    private void cbbChuyenDeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbChuyenDeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbChuyenDeItemStateChanged

    private void btnThemVaoKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoKHActionPerformed
        // TODO add your handling code here:
        addHocVien();
    }//GEN-LAST:event_btnThemVaoKHActionPerformed

    private void btnCapNhatDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDiemActionPerformed
        // TODO add your handling code here:
        upDateDiem();
    }//GEN-LAST:event_btnCapNhatDiemActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        removeHocVien();
    }//GEN-LAST:event_btnXoaKHActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyHocVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyHocVien dialog = new QuanLyHocVien(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCapNhatDiem;
    private javax.swing.JButton btnThemVaoKH;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JComboBox<String> cbbChuyenDe;
    private javax.swing.JComboBox<String> cbbKhoaHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tbHocVien;
    private javax.swing.JTable tbNguoiHoc;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
