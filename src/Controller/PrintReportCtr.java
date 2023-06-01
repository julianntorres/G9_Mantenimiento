package Controller;

import Interface.PrintReport;
import Connection.ConnectionPool;
import Model.UserSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class PrintReportCtr implements ActionListener {
    PrintReport printnui;
    UserSession session;

    public PrintReportCtr() {
        printnui = new PrintReport();
        printnui.btnImprimir.addActionListener(this);
    }

    public PrintReportCtr(UserSession session) {
        this.session = session;
        printnui = new PrintReport();
        printnui.btnImprimir.addActionListener(this);
    }

    // REFACTORIZACIÓN actionPerformed Method - LONG METHOD - ALEXANDER GONZALES
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.printnui.btnImprimir) {
            StringBuilder reportTypes = new StringBuilder();
            try {
                int currentAdminID = getCurrentAdminID();
                processSelectedReportTypes(reportTypes);
                logAuditEvent(currentAdminID, reportTypes.toString());
            } catch (SQLException ex) {
                handleReportGenerationError(ex);
            }
        }
    }

    private int getCurrentAdminID() {
        return session.getId();
    }

    private void processSelectedReportTypes(StringBuilder reportTypes) {
        if (this.printnui.jCheckBox1.isSelected()) {
            reportTypes.append("1 ");
            generateReport("1");
        }
        if (this.printnui.jCheckBox2.isSelected()) {
            reportTypes.append("2 ");
            generateReport("2");
        }

    }

    private void generateReport(String reportType) {
        WriterThread writerThread = new WriterThread(reportType);
        writerThread.start();
    }

    private void logAuditEvent(int adminID, String reportTypes) throws SQLException {
        String sqlAudit = String.format("INSERT INTO `audit_admin`(`id_admin`, `description`) VALUES ('%d','%s')",
                adminID, "Impresión de reporte: " + reportTypes);
        new ConnectionPool().makeUpdate(sqlAudit);
    }

    private void handleReportGenerationError(SQLException ex) {
        System.out.println(ex);
        JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el reporte, intente nuevamente",
                "Mensaje del sistema", 0);
    }
    
}
