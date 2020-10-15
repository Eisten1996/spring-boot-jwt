package com.bolasaideas.springboot.app.view.xlsx;

import com.bolasaideas.springboot.app.models.entities.Factura;
import com.bolasaideas.springboot.app.models.entities.ItemFactura;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 12/10/2020 - 11:22
 */
@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"factura_vew\"");
        Factura factura = (Factura) map.get("factura");
        Sheet sheet = workbook.createSheet("Factura Spring");

        MessageSourceAccessor mensajes = getMessageSourceAccessor();

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getEmail());

        sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
        sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
        sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
        header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
        header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
        header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);

        int rownum = 10;
        for (ItemFactura itemFactura : factura.getItems()) {
            Row fila = sheet.createRow(rownum++);
            cell = fila.createCell(0);
            cell.setCellValue(itemFactura.getProducto().getNombre());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(1);
            cell.setCellValue(itemFactura.getProducto().getPrecio());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(2);
            cell.setCellValue(itemFactura.getCantidad());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(3);
            cell.setCellValue(itemFactura.calcularImporte());
            cell.setCellStyle(tbodyStyle);
        }
        Row filaTotal = sheet.createRow(rownum);
        cell = filaTotal.createCell(2);
        cell.setCellValue(mensajes.getMessage("text.factura.form.total") + ": ");
        cell.setCellStyle(tbodyStyle);

        cell = filaTotal.createCell(3);
        cell.setCellValue(factura.getTotal());
        cell.setCellStyle(tbodyStyle);
    }
}
