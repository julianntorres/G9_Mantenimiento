package Controller;

import Interface.RegisterUser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Connection.ConnectionPool;
import Model.UserSession;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RegisterCtr implements ActionListener {
    RegisterUser registerui;
    private UserSession session;

    public RegisterCtr(UserSession session) {
        this.session = session;
        registerui = new RegisterUser();
        registerui.btnRegister.addActionListener(this);
    }
    
     // REFACTORIZACIÓN actionPerformed Method - LONG METHOD - ALEXANDER GONZALES
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.registerui.btnRegister) {
            String nombre = registerui.txt_nombre.getText().trim();
            String apellido = registerui.txt_apellido.getText().trim();
            String pass = registerui.txt_contraseña.getText().trim();
            int permisos_cmb = registerui.cmb_niveles.getSelectedIndex() + 1;
            
            if (validateFields(nombre, apellido, pass)) {
                String permisos_string = getPermisosString(permisos_cmb);
                String sql = "";

                if (permisos_string.equalsIgnoreCase("Administrador")) {
                    System.out.println("Hola, soy un admin validado");
                    sql = String.format("INSERT INTO `admins`(`name_admin`, `ap_admin`, `pass_admin`) VALUES ('%s','%s','%s')", nombre, apellido, pass);
                    JOptionPane.showMessageDialog(null, "Administrador registrado con éxito", "Mensaje del sistema", 1);
                } else if (permisos_string.equalsIgnoreCase("Trabajador")) {
                    System.out.println("Hola, soy un trabajador validado");
                    sql = String.format("INSERT INTO `users`(`name_user`, `ap_user`, `pass_user`) VALUES ('%s','%s','%s')", nombre, apellido, pass);
                    JOptionPane.showMessageDialog(null, "Trabajador registrado con éxito", "Mensaje del sistema", 1);
                }

                try {
                    new ConnectionPool().makeUpdate(sql);

                    // Registrar en la tabla de auditoría
                    int adminId = session.getId();
                    String description = String.format("Registro de usuario: Nombre: %s, Apellido: %s, Tipo: %s", nombre, apellido, permisos_string);
                    insertAuditLog(adminId, description);

                } catch (SQLException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al auditar el usuario", "Mensaje del sistema", 0);
                }
            }
        }
    }

    private boolean validateFields(String nombre, String apellido, String pass) {
        if (nombre.equals("")) {
            registerui.txt_nombre.setBackground(Color.red);
            return false;
        }

        if (apellido.equals("")) {
            registerui.txt_apellido.setBackground(Color.red);
            return false;
        }

        if (pass.equals("")) {
            registerui.txt_contraseña.setBackground(Color.red);
            return false;
        }

        return true;
    }

    
    //Nuevo método - Extract Method
    private String getPermisosString(int permisos_cmb) {
        if (permisos_cmb == 1) {
            return "Administrador";
        } else if (permisos_cmb == 2) {
            return "Trabajador";
        }
        
        return "";
    }

    private void insertAuditLog(int adminId, String description) {
        String sql = String.format("INSERT INTO `audit_admin` (`id_admin`, `description`) VALUES (%d, '%s')", adminId, description);
        try {
            new ConnectionPool().makeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Ocurrió un error al auditar el usuario", "Mensaje del sistema", 0);
        }
    }
}
