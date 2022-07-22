/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame;


import DAO.NguoiHocDAO;
import Entity.NguoiHoc;


import java.util.ArrayList;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.MsgBox;
import utils.XDate;

/**
 *
 * @author Quyết Chiến
 */
public class QuanLyNguoiHoc extends javax.swing.JDialog {
    
    NguoiHocDAO nhdao = new NguoiHocDAO();
    int row = 0;
    
    

    /**
     * Creates new form QuanLyNguoiHoc
     */
    public QuanLyNguoiHoc(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        fillTable();
        updateStatus();

    }

    public void timKiem() {
        this.fillTableTim();
        this.row = -1;
    }

    public void fillTableTim() {
        DefaultTableModel model = (DefaultTableModel) tbNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String tim = txtTim.getText();
            List<NguoiHoc> list = nhdao.selectByKeyword(tim);
            for (NguoiHoc nh : list) {
                model.addRow(new Object[]{
                    nh.getMaNH(), nh.getHoTen(), nh.getGioiTinh(), nh.getNgaySinh(), nh.getDienThoai(), nh.getEmail(), nh.getMaNV(), nh.getNgayTao()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi Truy Vấn!");
        }
    }

    public void edit() {
        try {
            String maNV = (String) tbNguoiHoc.getValueAt(row, 0);
            NguoiHoc entity = nhdao.selectById(maNV);
            if (entity != null) {
                setForm(entity);
                updateStatus();
                tabs.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "lỗi truy vấn");
        }
    }

    public void setForm(NguoiHoc entity) {
        txtmaNH.setText(entity.getMaNH());
        txtHoTen.setText(entity.getHoTen());
        txtngaySinh.setText(XDate.toString(entity.getNgaySinh(), "dd-MM-yyyy"));
        txtSdt.setText(entity.getDienThoai());
        txtEmail.setText(entity.getEmail());
        txtGhiChu.setText(entity.getGhiChu());
        rdNam.setSelected(entity.isGioiTinh());
        rdNu.setSelected(!entity.isGioiTinh());
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tbNguoiHoc.getRowCount() - 1;
        txtmaNH.setEditable(!edit);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFisrt.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tbNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            List<NguoiHoc> list = nhdao.selectAll();
            for (NguoiHoc nh : list) {
                model.addRow(new Object[]{
                    nh.getMaNH(), nh.getHoTen(), nh.getGioiTinh(), nh.getNgaySinh(), nh.getDienThoai(), nh.getEmail(), nh.getMaNV(), nh.getNgayTao()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi Truy Vấn");
        }
    }

    public NguoiHoc getForm() {
        
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtmaNH.getText());
        nh.setHoTen(txtHoTen.getText());
        nh.setGioiTinh(rdNam.isSelected() ? true : false);
        
        nh.setNgaySinh(XDate.toDate(txtngaySinh.getText(), "dd-MM-yyyy"));
        nh.setDienThoai(txtSdt.getText());
        nh.setEmail(txtEmail.getText());
        nh.setGhiChu(txtGhiChu.getText());
        return nh;
    }
    void delete(){
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn Không Có Quyền Xóa");
        }else{
            if (MsgBox.comfirm(this, "Bạn Chắc Chắn Muốn Xóa Người Học Này ?")) {
                String maNH = txtmaNH.getText();
                try {
                    
                    nhdao.delete(maNH);
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

    void clearForm() {
        this.setForm(new NguoiHoc());
        this.updateStatus();
        row = -1;
        updateStatus();
    }

    void insert() {
        NguoiHoc nh = getForm();
        try {
            nhdao.insert(nh);
            this.fillTable();
            this.clearForm();

            MsgBox.alert(this, "Thêm Mới Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm Mới Thất Bại");
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
        if (this.row < tbNguoiHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    void last(){
        this.row = tbNguoiHoc.getRowCount()-1;
        this.edit();
    }
    void update(){
        try {
                NguoiHoc nh = getForm();
                nhdao.update(nh);
                this.fillTable();
                MsgBox.alert(this, "Cập Nhật Thành Công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Cập Nhật Thất Bại!");
            }
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
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFisrt = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtmaNH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtngaySinh = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNguoiHoc = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản Lý Người Học");

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

        btnFisrt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dau.png"))); // NOI18N
        btnFisrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFisrtActionPerformed(evt);
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
        jLabel1.setText("Mã Người Học");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Họ Và Tên");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Giới Tính");

        buttonGroup1.add(rdNam);
        rdNam.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdNu.setText("Nữ");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Ngày Sinh");

        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Điện Thoại");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Email");

        jLabel7.setText("Ghi Chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setRows(5);
        txtGhiChu.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFisrt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast))
                    .addComponent(txtHoTen)
                    .addComponent(txtmaNH)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdNu))
                                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))))
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnFisrt, btnLast, btnMoi, btnNext, btnPrev, btnSua, btnThem, btnXoa});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtEmail, txtngaySinh});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdNam)
                    .addComponent(rdNu)
                    .addComponent(txtngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMoi)
                        .addComponent(btnXoa)
                        .addComponent(btnThem)
                        .addComponent(btnSua))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnFisrt)
                        .addComponent(btnPrev)
                        .addComponent(btnNext)
                        .addComponent(btnLast))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnFisrt, btnLast, btnMoi, btnNext, btnPrev, btnSua, btnThem, btnXoa});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtHoTen, txtmaNH});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtEmail, txtSdt, txtngaySinh});

        tabs.addTab("Cập Nhật", jPanel2);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Tìm Kiếm");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnTim.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTim, txtTim});

        tbNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ Và Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Email", "Mã NV", "Ngày ĐK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbNguoiHocMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbNguoiHoc);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Danh Sách", jPanel1);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Quản Lý Người Học");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_btnTimActionPerformed

    private void tbNguoiHocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNguoiHocMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tbNguoiHoc.rowAtPoint(evt.getPoint());
           
            edit();
        }
    }//GEN-LAST:event_tbNguoiHocMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFisrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFisrtActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFisrtActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNguoiHoc dialog = new QuanLyNguoiHoc(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFisrt;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tbNguoiHoc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtmaNH;
    private javax.swing.JTextField txtngaySinh;
    // End of variables declaration//GEN-END:variables
}
