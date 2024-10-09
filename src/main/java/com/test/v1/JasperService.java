package com.test.v1;

import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class JasperService {

    private final JasperReportAttestationTemplate jasperReportAttestationTemplate;

    public JasperService() {
        this.jasperReportAttestationTemplate = new JasperReportAttestationTemplate();
    }

    public byte[] generateAttestationReussite(String id,String lang) throws JRException, SQLException {
        return jasperReportAttestationTemplate.generateReport(id,lang);
    }


}