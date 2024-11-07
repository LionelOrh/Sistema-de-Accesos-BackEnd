package com.centroinformacion.controller;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.service.RegAccesosService;
import com.centroinformacion.util.AppSettings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/url/verConsultaReporte")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RegAccesosController {
	
	@Autowired RegAccesosService regAccesosService;
	
	@GetMapping("/consultaReporteAccesos")
	@ResponseBody
	public ResponseEntity<?> consultaReporteAccesos(
	    @RequestParam(name = "login", required = false, defaultValue = "") String login,
	    @RequestParam(name = "fechaAccesoDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoDesde,
	    @RequestParam(name = "fechaAccesoHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoHasta,
	    @RequestParam(name = "idTipoAcceso", required = false, defaultValue = "-1") int idTipoAcceso,
	    @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc
	) {
	    // Agrega logs para verificar los valores
	    System.out.println("Login: " + login);
	    System.out.println("Fecha Desde: " + fechaAccesoDesde);
	    System.out.println("Fecha Hasta: " + fechaAccesoHasta);
	    System.out.println("Tipo de Acceso: " + idTipoAcceso);
	    System.out.println("Nro Documento: " + numDoc);

	    // Verifica si el valor de login es correcto y ajusta la búsqueda
	    if (login.trim().isEmpty()) {
	        login = "%"; // Si no se pasa login, busca todos los registros
	    } else {
	        login = "%" + login + "%";
	    }
	    
	 // Verifica si el valor de numDoc es correcto y ajusta la búsqueda
	    if (numDoc.trim().isEmpty()) {
	    	numDoc = "%"; // Si no se pasa numDoc, busca todos los registros
	    } else {
	    	numDoc = "%" + numDoc + "%";
	    }

	    List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleja(
	        login,
	        fechaAccesoDesde,
	        fechaAccesoHasta,
	        idTipoAcceso,
	        numDoc
	    );

	    return ResponseEntity.ok(lstSalida);
	}
	
	private static String[] HEADERs = {"CÓDIGO", "NOMBRES", "APELLIDOS", "NRO DOC","FECHA", "HORA", "TIPO DE ACCESO"};
    private static String SHEET = "Reporte de Accesos";
    private static String TITLE = "Reporte de Accesos - Entrada y Salida";
    private static int[] HEADER_WIDTH = {3000, 6000, 6000, 4000, 3000,3000, 8000};
    
	@PostMapping("/reporteAccesos")
	public void reporteExcel(@RequestParam(name = "login", required = false, defaultValue = "") String login,
			@RequestParam(name = "fechaAccesoDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoDesde,
			@RequestParam(name = "fechaAccesoHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoHasta,
			@RequestParam(name = "idTipoAcceso", required = false, defaultValue = "-1") int idTipoAcceso,
			 @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc,
			HttpServletRequest request, HttpServletResponse response) {
		
		try(Workbook excel = new XSSFWorkbook()){
			 Sheet hoja = excel.createSheet(SHEET);
	         hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WIDTH.length - 1));
	         
	         for (int i = 0; i < HEADER_WIDTH.length; i++) {
	                hoja.setColumnWidth(i, HEADER_WIDTH[i]);
	         }
	         
	         // Estilo de cabecera
	            org.apache.poi.ss.usermodel.Font fuente = excel.createFont();
	            fuente.setFontHeightInPoints((short) 10);
	            fuente.setFontName("Arial");
	            fuente.setBold(true);
	            fuente.setColor(IndexedColors.WHITE.getIndex());
	            
	            CellStyle estiloCeldaCentrado = excel.createCellStyle();
	            estiloCeldaCentrado.setWrapText(true);
	            estiloCeldaCentrado.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
	            estiloCeldaCentrado.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
	            estiloCeldaCentrado.setFont(fuente);
	            estiloCeldaCentrado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
	            estiloCeldaCentrado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	            
	         // Estilo de datos
	            CellStyle estiloDatosCentrado = excel.createCellStyle();
	            estiloDatosCentrado.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
	            estiloDatosCentrado.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
	            estiloDatosCentrado.setBorderBottom(BorderStyle.THIN);
	            estiloDatosCentrado.setBorderTop(BorderStyle.THIN);
	            estiloDatosCentrado.setBorderLeft(BorderStyle.THIN);
	            estiloDatosCentrado.setBorderRight(BorderStyle.THIN);
	            
	            // Fila 0
	            org.apache.poi.ss.usermodel.Row fila1 = hoja.createRow(0);
	            org.apache.poi.ss.usermodel.Cell celAuxs = fila1.createCell(0);
	            celAuxs.setCellStyle(estiloCeldaCentrado);
	            celAuxs.setCellValue(TITLE);

	            // Fila 2 para cabecera
	            org.apache.poi.ss.usermodel.Row fila3 = hoja.createRow(2);
	            for (int i = 0; i < HEADERs.length; i++) {
	                org.apache.poi.ss.usermodel.Cell celda1 = fila3.createCell(i);
	                celda1.setCellStyle(estiloCeldaCentrado);
	                celda1.setCellValue(HEADERs[i]);
	            }
	            
	            List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleja(
	            		"%" + login + "%", 
	        	        fechaAccesoDesde,
	        	        fechaAccesoHasta,
	        	        idTipoAcceso,
	        	        "%" + numDoc + "%"
	        	    );
	            
	            int rowIdx = 3;
	            for (RegistroAcceso obj : lstSalida) {
	                org.apache.poi.ss.usermodel.Row row = hoja.createRow(rowIdx++);
	                
	                // CÓDIGO
	                row.createCell(0).setCellValue(obj.getUsuario().getLogin());
	                row.getCell(0).setCellStyle(estiloDatosCentrado);

	                // NOMBRES
	                row.createCell(1).setCellValue(obj.getUsuario().getNombres());
	                row.getCell(1).setCellStyle(estiloDatosCentrado);

	                // APELLIDOS
	                row.createCell(2).setCellValue(obj.getUsuario().getApellidos());
	                row.getCell(2).setCellStyle(estiloDatosCentrado);
	                
	             // NRO DOC
	                row.createCell(3).setCellValue(obj.getUsuario().getNumDoc());
	                row.getCell(3).setCellStyle(estiloDatosCentrado);

	             // Fecha (LocalDate)
	                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                row.createCell(4).setCellValue(obj.getFechaAcceso().format(dateFormatter));
	                row.getCell(4).setCellStyle(estiloDatosCentrado);

	             // Verifica si la hora no es null antes de formatearla
	                if (obj.getHoraAcceso() != null) {
	                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	                    row.createCell(5).setCellValue(obj.getHoraAcceso().format(timeFormatter));
	                } else {
	                    row.createCell(5).setCellValue("Hora no registrada"); // O cualquier mensaje adecuado
	                }
	                row.getCell(5).setCellStyle(estiloDatosCentrado);

	                // TIPO DE ACCESO
	                row.createCell(6).setCellValue(obj.getTipoAcceso());
	                row.getCell(6).setCellStyle(estiloDatosCentrado);
	            }
	            // Configurar respuesta
	            response.setContentType("application/vnd.ms-excel");
	            response.addHeader("Content-disposition", "attachment; filename=ReporteAccesos.xlsx");
	            
	            OutputStream outStream = response.getOutputStream();
	            excel.write(outStream);
	            outStream.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
