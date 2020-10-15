package com.bolasaideas.springboot.app.view.pdf;

import com.bolasaideas.springboot.app.models.entities.Factura;
import com.bolasaideas.springboot.app.models.entities.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 11/10/2020 - 16:12
 */
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document,
                                    PdfWriter pdfWriter, HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {

        Factura factura = (Factura) map.get("factura");

        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);
        PdfPCell cell;
        Locale locale = localeResolver.resolveLocale(httpServletRequest);
        MessageSourceAccessor mensajes = getMessageSourceAccessor();
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        table.addCell(cell);
        table.addCell(factura.getCliente().getNombre().concat(" ").concat(factura.getCliente().getApellido()));
        table.addCell(factura.getCliente().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        table2.addCell(cell);
        table2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
        table2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
        table2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());

        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[]{2.5f, 1, 1, 1});
        table3.setSpacingAfter(20);
        table3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
        table3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
        table3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
        table3.addCell(mensajes.getMessage("text.factura.form.item.total"));

        for (ItemFactura item : factura.getItems()) {
            table3.addCell(item.getProducto().getNombre());
            table3.addCell(item.getProducto().getPrecio().toString());
            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table3.addCell(cell);
            table3.addCell(item.calcularImporte().toString());
        }

        cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total") + " :"));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table3.addCell(cell);
        table3.addCell(factura.getTotal().toString());

        document.add(table);
        document.add(table2);
        document.add(table3);
    }
}
