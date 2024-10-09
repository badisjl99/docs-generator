package com.test.v1;



import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class JasperReportAttestationTemplate {

    private final String jdbcURL = "jdbc:oracle:thin:@192.168.3.13:1521:bdesp10n";
    private final String username = "sondes";
    private final String password = "sondes";

    public byte[] generateReport(String studentId,String language) throws JRException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            Locale locale = new Locale(language);
            ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

            String sqlQuery = "SELECT ID_ET, NOM_ET, PNOM_ET, NATIONALITE, LIEU_NAIS_ET, CLASSE_COURANTE_ET, TO_CHAR(DATE_NAIS_ET, 'YYYY-MM-DD') as DATE_NAIS_ET FROM SCOESP09.ESP_ETUDIANT WHERE SCOESP09.ESP_ETUDIANT.ID_ET = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, studentId);


            resultSet = preparedStatement.executeQuery();


            String jasperTemplatePath = "src/main/resources/attestation_reussite.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplatePath);


            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, messages);


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);


            return JasperExportManager.exportReportToPdf(jasperPrint);

        } finally {

            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
}

