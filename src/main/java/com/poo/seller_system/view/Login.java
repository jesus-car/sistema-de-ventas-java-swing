/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo.seller_system.view;

import com.poo.seller_system.Seller_system;
import com.poo.seller_system.persistence.model.User;
import com.poo.seller_system.persistence.model.dto.UserLoginDTO;
import com.poo.seller_system.service.LoginService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesus
 */
public class Login extends javax.swing.JFrame {
    
    int xMouse;
    int yMouse;
    
    LoginService loginService = LoginService.getInstance();
    
    public Login() {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 537) / 2;
        int y = (screenSize.height - 339) / 2;           

        this.setLocation(x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        loginTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        userLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        userField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        header = new javax.swing.JPanel();
        exitButton = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SISTEMA DE VENTAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/store-image-removebg-preview.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 220, 200));

        background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 340));

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        passwordLabel.setText("Password");
        background.add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, -1, -1));

        loginTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        loginTitle.setText("INICIAR SESION");
        background.add(loginTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 190, -1));
        background.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 210, 10));

        userLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        userLabel.setText("Usuario");
        background.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, -1, -1));
        background.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 210, 10));

        userField.setForeground(new java.awt.Color(204, 204, 204));
        userField.setText("Ingrese su nombre de usuario");
        userField.setBorder(null);
        userField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userFieldMousePressed(evt);
            }
        });
        background.add(userField, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 220, 30));

        passwordField.setForeground(new java.awt.Color(204, 204, 204));
        passwordField.setText("**************");
        passwordField.setBorder(null);
        passwordField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                passwordFieldMousePressed(evt);
            }
        });
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        background.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 210, 30));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        exitButton.setBackground(new java.awt.Color(255, 51, 51));

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitButtonLayout = new javax.swing.GroupLayout(exitButton);
        exitButton.setLayout(exitButtonLayout);
        exitButtonLayout.setHorizontalGroup(
            exitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );
        exitButtonLayout.setVerticalGroup(
            exitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitButtonLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(0, 497, Short.MAX_VALUE)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        background.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 30));

        loginButton.setBackground(new java.awt.Color(0, 153, 255));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Ingresar");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        background.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 90, 30));

        cancelButton.setBackground(new java.awt.Color(255, 0, 0));
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        background.add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 339, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        exitButton.setBackground(new Color(255,102,102));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        exitButton.setBackground(new Color(255,51,51));
    }//GEN-LAST:event_jLabel2MouseExited

    private void userFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userFieldMousePressed
        if(userField.getText().contains("usuario")){
            userField.setText("");
            userField.setForeground(Color.black);
        }
        if(String.valueOf(passwordField.getPassword()).isEmpty()){
            passwordField.setText("**************");
            passwordField.setForeground(Color.gray);
        }

    }//GEN-LAST:event_userFieldMousePressed

    private void passwordFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordFieldMousePressed
        if(String.valueOf(passwordField.getPassword()).contains("*")){
            passwordField.setText("");
            passwordField.setForeground(Color.black); 
        }
        if(userField.getText().isEmpty()){
            userField.setText("Ingrese su nombre de usuario");
            userField.setForeground(Color.gray);
        }


    }//GEN-LAST:event_passwordFieldMousePressed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        
        
        UserLoginDTO loginDTO = new UserLoginDTO(userField.getText(),password);
        
        User userLogin = loginService.login(loginDTO);
        
        if(userLogin == null){
            JOptionPane.showMessageDialog(this, "Las credenciales no son validas", "Aviso!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Seller_system.currentUser = userLogin;
        
        if(!userLogin.isEnabled()){
            JOptionPane.showMessageDialog(this, "El usuario se encuentra deshabilitado. Intente con otro usuario", "Aviso!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        switch(userLogin.getRole().getName()){
            case "USER" -> {
                UserApp userApp = new UserApp();
                userApp.setVisible(true);
            }
            case "ALMACEN" -> {
                GrocerApp grocerApp = new GrocerApp();
                grocerApp.setVisible(true);
            }
            case "ADMIN" -> {
                AdminApp adminApp = new AdminApp();
                adminApp.setVisible(true);
            }
            case "SUPERADMIN" -> {
                MainApp app = new MainApp();
                app.setVisible(true);
            }
            default -> {
            }
        }
        this.dispose();
        
    }//GEN-LAST:event_loginButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel exitButton;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginTitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField userField;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
