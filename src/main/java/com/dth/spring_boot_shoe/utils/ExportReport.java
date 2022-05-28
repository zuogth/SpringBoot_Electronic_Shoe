package com.dth.spring_boot_shoe.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.io.SimpleOutputStream;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public String createExcelReport(String template, Map<String, Object> dataSource, Map<String,Object> parameters) throws JRException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream stream = this.getClass().getResourceAsStream("/report/Xls"+template);

        final JasperReport report = JasperCompileManager.compileReport(stream);

        JRMapArrayDataSource source = new JRMapArrayDataSource(new Object[]{dataSource});

        final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,source);

        JRXlsExporter xlsExporter = new JRXlsExporter();


        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(false);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        OutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(baos);
        xlsExporter.setExporterOutput(output);
        xlsExporter.exportReport();

        return Base64.encodeBase64String(baos.toByteArray());
    }


    public String createWordReport(String template, Map<String, Object> dataSource, Map<String,Object> parameters) throws JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream stream = this.getClass().getResourceAsStream("/report/"+template);

        final JasperReport report = JasperCompileManager.compileReport(stream);

        JRMapArrayDataSource source = new JRMapArrayDataSource(new Object[]{dataSource});

        final JasperPrint print = JasperFillManager.fillReport(report, parameters,source);

        JRRtfExporter exporter = new JRRtfExporter();

        exporter.setExporterInput(new SimpleExporterInput(print));
        WriterExporterOutput output = new SimpleWriterExporterOutput(baos);
        exporter.setExporterOutput(output);

        exporter.exportReport();
        return Base64.encodeBase64String(baos.toByteArray());

    }
}
