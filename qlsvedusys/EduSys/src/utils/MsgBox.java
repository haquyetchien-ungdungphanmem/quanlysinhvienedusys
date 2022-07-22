/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Quyết Chiến
 */
public class MsgBox {
    public static void  alert(Component parent, String msg){
        JOptionPane.showMessageDialog(parent, msg,
                "Hệ Thống Quản Lý Đào Tạo",JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean comfirm(Component parent, String msg){
        int result = JOptionPane.showConfirmDialog(parent, msg,
                "Hệ Thống Quản Lý Đào Tạo",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_NO_OPTION;
    }
    public static String  prompt(Component parent, String msg){
        return JOptionPane.showInputDialog(parent, msg,
                "Hệ Thống Quản Lý Đào Tạo",JOptionPane.INFORMATION_MESSAGE);
    }
}
