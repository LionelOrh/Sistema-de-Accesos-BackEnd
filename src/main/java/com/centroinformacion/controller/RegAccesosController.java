package com.centroinformacion.controller;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.dto.PreRegistroConsultaDTO;
import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.service.RegAccesosService;
import com.centroinformacion.util.AppSettings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/url/registroAcceso")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RegAccesosController {
	
	@Autowired RegAccesosService regAccesosService;
	
	@GetMapping("/consultaReporteAccesos")
	@ResponseBody
	public ResponseEntity<?> consultaReporteAccesos(
	    @RequestParam(name = "login", required = true, defaultValue = "") String login,
	    @RequestParam(name = "fechaAccesoDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoDesde,
	    @RequestParam(name = "fechaAccesoHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoHasta,
	    @RequestParam(name = "estado", required = false, defaultValue = "-1") int estado,
	    @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc
	) {
	    // Ajustar valores vacíos para que sean compatibles con la consulta
	    if (login.trim().isEmpty()) login = "%";
	    if (numDoc.trim().isEmpty()) numDoc = "%";

	    System.out.println("Código: " + login);
	    System.out.println("Fecha Desde: " + fechaAccesoDesde);
	    System.out.println("Fecha Hasta: " + fechaAccesoHasta);
	    System.out.println("Estado: " + estado);
	    System.out.println("Nro Documento: " + numDoc);

	    List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleja(
	        login,
	        fechaAccesoDesde,
	        fechaAccesoHasta,
	        estado,
	        numDoc
	    );

	    return ResponseEntity.ok(lstSalida);
	}

		private static String[] HEADERs = {"CÓDIGO", "NOMBRES", "APELLIDOS", "NRO DOC","FECHA", "HORA", "TIPO DE ACCESO"};
	    private static String SHEET = "Reporte de Accesos";
	    private static String TITLE = "Reporte de Accesos - Entrada y Salida - Internos y Externos";
	    private static int[] HEADER_WIDTH = {3000, 6000, 6000, 4000, 3000,3000, 8000};
	    
	    @PostMapping("/reporteAccesos")
	    public void reporteExcel(
	        @RequestParam(name = "login", required = false, defaultValue = "") String login,
	        @RequestParam(name = "fechaAccesoDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoDesde,
	        @RequestParam(name = "fechaAccesoHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoHasta,
	        @RequestParam(name = "estado", required = false, defaultValue = "-1") int estado, // Cambiado
	        @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc,
	        HttpServletRequest request, HttpServletResponse response
	    ) {
	        try (Workbook excel = new XSSFWorkbook()) {
	            Sheet hoja = excel.createSheet(SHEET);
	            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WIDTH.length - 1));

	            for (int i = 0; i < HEADER_WIDTH.length; i++) {
	                hoja.setColumnWidth(i, HEADER_WIDTH[i]);
	            }

	            // Estilo de cabecera
	            CellStyle estiloCeldaCentrado = crearEstiloCabecera(excel);

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
	                estado,
	                "%" + numDoc + "%"
	            );

	            int rowIdx = 3;
	            for (RegistroAcceso obj : lstSalida) {
	                org.apache.poi.ss.usermodel.Row row = hoja.createRow(rowIdx++);

	                // CÓDIGO
	                row.createCell(0).setCellValue(obj.getUsuario().getLogin());

	                // NOMBRES
	                row.createCell(1).setCellValue(obj.getUsuario().getNombres());

	                // APELLIDOS
	                row.createCell(2).setCellValue(obj.getUsuario().getApellidos());

	                // NRO DOC
	                row.createCell(3).setCellValue(obj.getUsuario().getNumDoc());

	                // Fecha
	                row.createCell(4).setCellValue(obj.getFechaAcceso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

	                // Hora
	                row.createCell(5).setCellValue(obj.getHoraAcceso() != null
	                    ? obj.getHoraAcceso().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
	                    : "Hora no registrada");

	                // ESTADO
	                row.createCell(6).setCellValue(obj.getUsuario().getEstado() == 0 ? "Salida" : "Ingreso"); // Cambiado
	            }

	            response.setContentType("application/vnd.ms-excel");
	            response.addHeader("Content-disposition", "attachment; filename=ReporteAccesos.xlsx");

	            OutputStream outStream = response.getOutputStream();
	            excel.write(outStream);
	            outStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

		
		//REPRESENTANTES
		@GetMapping("/consultaReporteRepresentante")
		@ResponseBody
		public ResponseEntity<?> consultaReporteRepresentante(
		    @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc
		) {
		    // Agrega logs para verificar los valores
		    System.out.println("Nro Documento: " + numDoc);
		    
		 // Verifica si el valor de numDoc es correcto y ajusta la búsqueda
		    if (numDoc.trim().isEmpty()) {
		    	numDoc = "%"; // Si no se pasa numDoc, busca todos los registros
		    } else {
		    	numDoc = "%" + numDoc + "%";
		    }

		    List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleta(
		    	numDoc
		    );

		    return ResponseEntity.ok(lstSalida);
		}
		
		private static String[] HEADER = {"NOMBRES", "APELLIDOS", "CARGO","NRO DOC", "PROVEEDOR"};
	    private static String SHEETs = "Reporte de Accesos";
	    private static String TITLEs = "Reporte de Accesos - Entrada y Salida - Proveedores";
	    private static int[] HEADER_WIDTHs = {6000, 6000, 4000, 3000,8000};
	    
	    @PostMapping("/reporteRepresentante")
	    public void reporteExcelRepresentante(
	        @RequestParam(name = "numDoc", required = false, defaultValue = "") String numDoc,
	        HttpServletRequest request, HttpServletResponse response
	    ) {
	        try (Workbook excel = new XSSFWorkbook()) {
	            Sheet hoja = excel.createSheet(SHEETs);
	            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WIDTHs.length - 1));

	            for (int i = 0; i < HEADER_WIDTHs.length; i++) {
	                hoja.setColumnWidth(i, HEADER_WIDTHs[i]);
	            }

	            // Estilo de cabecera
	            CellStyle estiloCeldaCentrado = crearEstiloCabecera(excel);

	            // Fila 0
	            org.apache.poi.ss.usermodel.Row fila1 = hoja.createRow(0);
	            org.apache.poi.ss.usermodel.Cell celAuxs = fila1.createCell(0);
	            celAuxs.setCellStyle(estiloCeldaCentrado);
	            celAuxs.setCellValue(TITLEs);

	            // Fila 2 para cabecera
	            org.apache.poi.ss.usermodel.Row fila3 = hoja.createRow(2);
	            for (int i = 0; i < HEADER.length; i++) {
	                org.apache.poi.ss.usermodel.Cell celda1 = fila3.createCell(i);
	                celda1.setCellStyle(estiloCeldaCentrado);
	                celda1.setCellValue(HEADER[i]);
	            }

	            List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleta(
	                "%" + numDoc + "%"
	            );

	            int rowIdx = 3;
	            for (RegistroAcceso obj : lstSalida) {
	                org.apache.poi.ss.usermodel.Row row = hoja.createRow(rowIdx++);

	                // NOMBRES
	                row.createCell(0).setCellValue(obj.getRepresentante().getNombres());

	                // APELLIDOS
	                row.createCell(1).setCellValue(obj.getRepresentante().getApellidos());

	                // CARGO
	                row.createCell(2).setCellValue(obj.getRepresentante().getCargo());

	                // NRO DOC
	                row.createCell(3).setCellValue(obj.getRepresentante().getNumDoc());

	                // PROVEEDOR
	                row.createCell(4).setCellValue(obj.getRepresentante().getProveedor().getRazonSocial());
	            }

	            response.setContentType("application/vnd.ms-excel");
	            response.addHeader("Content-disposition", "attachment; filename=ReporteRepresentante.xlsx");

	            OutputStream outStream = response.getOutputStream();
	            excel.write(outStream);
	            outStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private CellStyle crearEstiloCabecera(Workbook excel) {
	        // Crear fuente para el estilo de cabecera
	        org.apache.poi.ss.usermodel.Font fuente = excel.createFont();
	        fuente.setFontHeightInPoints((short) 10);
	        fuente.setFontName("Arial");
	        fuente.setBold(true);
	        fuente.setColor(IndexedColors.WHITE.getIndex());

	        // Crear estilo de celda
	        CellStyle estiloCeldaCentrado = excel.createCellStyle();
	        estiloCeldaCentrado.setWrapText(true);
	        estiloCeldaCentrado.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
	        estiloCeldaCentrado.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
	        estiloCeldaCentrado.setFont(fuente);
	        estiloCeldaCentrado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
	        estiloCeldaCentrado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        return estiloCeldaCentrado;
	    }
	    
	    
	    //Consulata preRegistro
	    
	    @GetMapping("/consultaPreRegistro")
	    @ResponseBody
	    public ResponseEntity<?> consultaPreRegistro(@RequestParam(name = "codigo") String codigo) {
	        PreRegistroConsultaDTO resultado = regAccesosService.buscarPorCodigo(codigo);

	        if (resultado != null) {
	            return ResponseEntity.ok(resultado);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No se encontró ningún usuario o representante con el código proporcionado");
	        }
	    }

}
