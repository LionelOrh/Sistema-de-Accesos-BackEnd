package com.centroinformacion.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import com.centroinformacion.entity.Editorial;
import com.centroinformacion.entity.Revista;
import com.centroinformacion.service.EditorialService;
import com.centroinformacion.util.AppSettings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


@RestController
@RequestMapping("/url/consultaEditorial")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class EditorialConsultaController {
	@Autowired
	private EditorialService editorialService;
	
	@GetMapping("/consultaEditorialPorParametros")
	@ResponseBody
	public ResponseEntity<?> consultaEditorialPorParametros(
			@RequestParam(name = "razonSocial" , required = true , defaultValue = "") String razonSocial,
			@RequestParam(name = "direccion" , required = true , defaultValue = "") String direccion,
			@RequestParam(name = "ruc" , required = true , defaultValue = "") String ruc,
			@RequestParam(name = "gerente" , required = true , defaultValue = "") String gerente,
			@RequestParam(name = "fecDesde" , required = true , defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecDesde,
			@RequestParam(name = "fecHasta" , required = true , defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecHasta,
			@RequestParam(name = "estado" , required = true , defaultValue = "") int estado,
			@RequestParam(name = "idPais" , required = false , defaultValue = "-1") int idPais			){
		
		List<Editorial> lstSalida =editorialService.listaCompleja("%"+razonSocial+"%", "%"+direccion+"%", "%"+ruc+"%", "%"+gerente+"%", fecDesde, fecHasta, estado, idPais);
		return ResponseEntity.ok(lstSalida);
	}
	private static String[] HEADERs = { "CÓDIGO", "RAZON SOCIAL","DIRECCION","RUC","GERENTE", "FECHA CREACIÓN", "ESTADO", "PAÍS" };
	private static String SHEET = "Listado de Editorial";
	private static String TITLE = "Listado de Editorial - Autor: Jans Vargas";
	private static int[] HEADER_WITH = { 3000, 10000, 10000, 6000, 6000, 6000, 6000,6000 };
	
	@PostMapping("/reporteEditorialExcel")
	public void reporteExcel(
			@RequestParam(name = "razonSocial", required = true, defaultValue = "") String razonSocial,
			@RequestParam(name = "direccion", required = true, defaultValue = "") String direccion,
			@RequestParam(name = "ruc", required = true, defaultValue = "") String ruc,
			@RequestParam(name = "gerente", required = true, defaultValue = "") String gerente,
			@RequestParam(name = "fecDesde", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecDesde,
			@RequestParam(name = "fecHasta", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecHasta,
			@RequestParam(name = "estado", required = true, defaultValue = "") int estado,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			HttpServletRequest request, HttpServletResponse response) {

		Workbook excel = null;
		try  {
			excel = new XSSFWorkbook();
					
			// Se crear la hoja del Excel
			Sheet hoja = excel.createSheet(SHEET);

			// Agrupar
			hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WITH.length - 1));

			// Se establece el ancho de las columnas
			for (int i = 0; i < HEADER_WITH.length; i++) {
				hoja.setColumnWidth(i, HEADER_WITH[i]);
			}

			// Fuenta
			Font fuente = excel.createFont();
			fuente.setFontHeightInPoints((short) 10);
			fuente.setFontName("Arial");
			fuente.setBold(true);
			fuente.setColor(IndexedColors.WHITE.getIndex());

			// Estilo
			CellStyle estiloCeldaCentrado = excel.createCellStyle();
			estiloCeldaCentrado.setWrapText(true);
			estiloCeldaCentrado.setAlignment(HorizontalAlignment.CENTER);
			estiloCeldaCentrado.setVerticalAlignment(VerticalAlignment.CENTER);
			estiloCeldaCentrado.setFont(fuente);
			estiloCeldaCentrado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
			estiloCeldaCentrado.setFillPattern(FillPatternType.SOLID_FOREGROUND);


	        // Estilo para datos
	        CellStyle estiloDatosCentrado = excel.createCellStyle();
	        estiloDatosCentrado.setAlignment(HorizontalAlignment.CENTER);
	        estiloDatosCentrado.setVerticalAlignment(VerticalAlignment.CENTER);
	        estiloDatosCentrado.setBorderBottom(BorderStyle.THIN);
	        estiloDatosCentrado.setBorderTop(BorderStyle.THIN);
	        estiloDatosCentrado.setBorderLeft(BorderStyle.THIN);
	        estiloDatosCentrado.setBorderRight(BorderStyle.THIN);
	        
	        CellStyle estiloDatosIzquierdo = excel.createCellStyle();
	        estiloDatosIzquierdo.setAlignment(HorizontalAlignment.LEFT);
	        estiloDatosIzquierdo.setVerticalAlignment(VerticalAlignment.CENTER);
	        estiloDatosIzquierdo.setBorderBottom(BorderStyle.THIN);
	        estiloDatosIzquierdo.setBorderTop(BorderStyle.THIN);
	        estiloDatosIzquierdo.setBorderLeft(BorderStyle.THIN);
	        estiloDatosIzquierdo.setBorderRight(BorderStyle.THIN);
	        
			// Fila 0
			Row fila1 = hoja.createRow(0);
			Cell celAuxs = fila1.createCell(0);
			celAuxs.setCellStyle(estiloCeldaCentrado);
			celAuxs.setCellValue(TITLE);

			// Fila 1
			Row fila2 = hoja.createRow(1);
			Cell celAuxs2 = fila2.createCell(0);
			celAuxs2.setCellValue("");

			// Fila 2
			Row fila3 = hoja.createRow(2);
			for (int i = 0; i < HEADERs.length; i++) {
				Cell celda1 = fila3.createCell(i);
				celda1.setCellStyle(estiloCeldaCentrado);
				celda1.setCellValue(HEADERs[i]);
			}

			// formato para fecha
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			// Fila 3....n
			List<Editorial> lstSalida =editorialService.listaCompleja("%"+razonSocial+"%","%" +direccion+"%","%"+ruc+"%","%"+gerente+"%",fecDesde, fecHasta, estado, idPais);
			
			//List<Revista> lstSalida = revistaService.listaTodos();
			// Filas de datos
			int rowIdx = 3;
			for (Editorial obj : lstSalida) {
				Row row = hoja.createRow(rowIdx++);

				Cell cel0 = row.createCell(0);
				cel0.setCellValue(obj.getIdEditorial());
				cel0.setCellStyle(estiloDatosCentrado);
				
				Cell cel1 = row.createCell(1);
				cel1.setCellValue(obj.getRazonSocial());
				cel1.setCellStyle(estiloDatosIzquierdo);
				
				Cell cel2 = row.createCell(2);
				cel2.setCellValue(obj.getDireccion());
				cel2.setCellStyle(estiloDatosIzquierdo);
				
				Cell cel3 = row.createCell(3);
				cel3.setCellValue(obj.getRuc());
				cel3.setCellStyle(estiloDatosIzquierdo);
				
				Cell cel4 = row.createCell(4);
				cel4.setCellValue(obj.getGerente());
				cel4.setCellStyle(estiloDatosIzquierdo);
				
				Cell cel5 = row.createCell(5);
				cel5.setCellValue(sdf.format(obj.getFechaCreacion()));
				cel5.setCellStyle(estiloDatosCentrado);
				
				Cell cel6 = row.createCell(6);
				cel6.setCellValue(obj.getEstado()==1?AppSettings.ACTIVO_DESC:AppSettings.INACTIVO_DESC);
				cel6.setCellStyle(estiloDatosCentrado);
				
				Cell cel7 = row.createCell(7);
				cel7.setCellValue(obj.getPais().getNombre());
				cel7.setCellStyle(estiloDatosCentrado);
				
				
			}

			// Tipo de archivo y nombre de archivo
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-disposition", "attachment; filename=ReporteEditorial.xlsx");

			OutputStream outStream = response.getOutputStream();
			excel.write(outStream);
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (excel != null)
					excel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	@PostMapping("/reporteEditorialPDF")
	public void reportePDF(@RequestParam(name = "razonSocial", required = true, defaultValue = "") String razonSocial,
			@RequestParam(name = "direccion", required = true, defaultValue = "") String direccion,
			@RequestParam(name = "ruc", required = true, defaultValue = "") String ruc,
			@RequestParam(name = "gerente", required = true, defaultValue = "") String gerente,
			@RequestParam(name = "fecDesde", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecDesde,
			@RequestParam(name = "fecHasta", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecHasta,
			@RequestParam(name = "estado", required = true, defaultValue = "") int estado,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			HttpServletRequest request, HttpServletResponse response) {
		try {

			// PASO 1 Fuente de datos
			List<Editorial> lstSalida =editorialService.listaCompleja("%"+razonSocial+"%","%" +direccion+"%","%"+ruc+"%","%"+gerente+"%",fecDesde, fecHasta, estado, idPais);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);

			// PASO 2 Diseño de reporte
			String fileReporte = request.getServletContext().getRealPath("/reporteEditorial.jasper");

			// PASO3 parámetros adicionales
			Map<String, Object> params = new HashMap<String, Object>();

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(new File(fileReporte)));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

			// PASO 5 parametros en el Header del mensajes HTTP
			response.setContentType("application/pdf");
			response.addHeader("Content-disposition", "attachment; filename=ReporteAutor.pdf");

			// PASO 6 Se envia el pdf
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


