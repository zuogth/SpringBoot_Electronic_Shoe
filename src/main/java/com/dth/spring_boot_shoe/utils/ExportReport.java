package com.dth.spring_boot_shoe.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Hiu
 * Date: 5/24/2022
 */
@Service
public class ExportReport {

    // Method to create the pdf file using the employee list datasource.
    public String createPdfReport(String templateName, Map<String, Object> dataSources, Map<String, Object> parameters) throws JRException {
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream("/report/"+templateName);

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        JRMapArrayDataSource source = new JRMapArrayDataSource(new Object[]{dataSources});
        //final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees, false);

        // Filling the report with the employee data and additional parameters information.
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

        // Users can change as per their project requrirements or can take it as request input requirement.
        // For simplicity, this tutorial will automatically place the file under the "c:" drive.
        // If users want to download the pdf file on the browser, then they need to use the "Content-Disposition" technique.
        return Base64.encodeBase64String(JasperExportManager.exportReportToPdf(print));
    }

}
