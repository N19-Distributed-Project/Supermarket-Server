package org.supermarket.util;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.supermarket.dao.CustomerDaoImpl;
import org.supermarket.dao.EmployeeDaoImpl;
import org.supermarket.dao.OrderDaoImpl;
import org.supermarket.dao.ProductDaoImpl;
import org.supermarket.entity.Order;
import org.supermarket.entity.OrderDetail;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PrintPdf {
	public static void printedOrder (Order order) throws FileNotFoundException {
		String path = "D:\\Intellij_Workspace\\Distributed_Programing\\BTL\\SuperMarket-Server\\out\\OderPDF\\Order_" + order.getOrderId() + ".pdf";
		PdfWriter pdfWriter = new PdfWriter(path);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		pdfDocument.setDefaultPageSize(PageSize.A4);
		Document document = new Document(pdfDocument);
		float twocol = 285f;
		float twocol150 = twocol + 150f;
		float twocolumnWidth[] = { twocol150, twocol };
		float threecol = 190f;
		float fullwidth[] = { threecol * 3 };
		float fiveColumnWidth[] = { threecol, threecol, threecol, threecol, threecol, threecol };
		
		
		Paragraph onesp = new Paragraph("\n");
		
		Table table = new Table(twocolumnWidth);
		table.addCell(new Cell().add("TMA Co.opmart").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
		Table nestedtabe = new Table(new float[]{ twocol / 2, twocol / 2 });
		nestedtabe.addCell(getHeaderTextCell("Invoice No.1"));
		nestedtabe.addCell(getHeaderTextCellValue("EMP_"+order.getSaleEmployee().getEmployeeId()+"-Order_"+order.getOrderId()+""));
		nestedtabe.addCell(getHeaderTextCell("Invoice Date"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		nestedtabe.addCell(getHeaderTextCellValue(LocalDateTime.now().format(formatter)));
		table.addCell(new Cell().add(nestedtabe).setBorder(Border.NO_BORDER));
		
		Border gb = new SolidBorder(Color.GRAY, 2f);
		Table divider = new Table(fullwidth);
		divider.setBorder(gb);
		document.add(table);
		document.add(onesp);
		document.add(divider);
		document.add(onesp);
		
		Table twoColTable = new Table(twocolumnWidth);
		twoColTable.addCell(getBillingandShippingCell("Billing Information"));
		document.add(twoColTable.setMarginBottom(12f));
		
		Table twoColTable2 = new Table(twocolumnWidth);
		twoColTable2.addCell(getCell10fLeft("Sieu Thi", true));
		twoColTable2.addCell(getCell10fLeft("", true));
		twoColTable2.addCell(getCell10fLeft("TMA Co.opmart", false));
		twoColTable2.addCell(getCell10fLeft("", false));
		document.add(twoColTable2);
		
		Table twoColTable3 = new Table(twocolumnWidth);
		twoColTable3.addCell(getCell10fLeft("Thu Ngan", true));
		twoColTable3.addCell(getCell10fLeft("", true));
		twoColTable3.addCell(getCell10fLeft(order.getSaleEmployee().getName(), false));
		twoColTable3.addCell(getCell10fLeft("", false));
		document.add(twoColTable3);
		
		
		float oneColoumnwidth[] = { twocol150 };
		
		Table oneColTable1 = new Table(oneColoumnwidth);
		oneColTable1.addCell(getCell10fLeft("Chi Nhanh", true));
		oneColTable1.addCell(getCell10fLeft("12 Nguyen Van Bao, P.4, Go Vap, Tp.HCM", false));
		
		document.add(oneColTable1.setMarginBottom(10f));
		
		Table tableDivider2 = new Table(fullwidth);
		Border dgb = new DashedBorder(Color.GRAY, 0.5f);
		document.add(tableDivider2.setBorder(dgb));
		Paragraph producPara = new Paragraph("Products");
		
		document.add(producPara.setBold());
		Table fiveColTable1 = new Table(fiveColumnWidth);
		fiveColTable1.setBackgroundColor(Color.BLACK, 0.7f);
		
		fiveColTable1.addCell(new Cell().add("STT").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER)).setMarginLeft(0f); //////////////////
		fiveColTable1.addCell(new Cell().add("San Pham").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
		fiveColTable1.addCell(new Cell().add("SL").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(- 5f);
		fiveColTable1.addCell(new Cell().add("Don Gia").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		fiveColTable1.addCell(new Cell().add("VAT").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		fiveColTable1.addCell(new Cell().add("Thanh Tien").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(0f);
		document.add(fiveColTable1);
		
		
		List<OrderDetail> orderDetails = order.getShoppingList();
		
		
		Table fiveColTable2 = new Table(fiveColumnWidth);
		
		double totalSum = order.getTotal();
		
		for(int i = 0; i < orderDetails.size(); i++){
			OrderDetail orderDetail = orderDetails.get(i);
			fiveColTable2.addCell(new Cell().add(String.valueOf(i + 1)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER)).setMarginLeft(100f);
			//fiveColTable2.addCell(new Cell().add(orderDetail.getProduct().getProductName()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
			fiveColTable2.addCell(new Cell().add(String.valueOf(orderDetail.getProduct().getProductName())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
			fiveColTable2.addCell(new Cell().add(String.valueOf(orderDetail.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(15f);
			fiveColTable2.addCell(new Cell().add(String.valueOf((int) orderDetail.getProduct().getPrice())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
			fiveColTable2.addCell(new Cell().add(String.valueOf( orderDetail.getProduct().getVAT()*100)+"%").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
			
			fiveColTable2.addCell(new Cell().add(String.valueOf((int) orderDetail.getProduct().getPrice()  * orderDetail.getQuantity()
					+(orderDetail.getProduct().getPrice() * orderDetail.getProduct().getVAT()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(0f);

//            double totalPrice = orderDetail.getProduct().getPrice()*orderDetail.getProduct().getQuantity();
//
//            totalSum += totalPrice;
		}
		
		
		document.add(fiveColTable2.setMarginBottom(20f));
		float onetwo[] = { threecol + 170f, threecol * 2 };
		Table fiveColTable4 = new Table(onetwo);
		fiveColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
		fiveColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
		document.add(fiveColTable4);
		
		Table fiveColTable3 = new Table(fiveColumnWidth);
		fiveColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(75f);
		fiveColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		fiveColTable3.addCell(new Cell().add(String.valueOf((int) totalSum)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(- 425f);
		
		document.add(fiveColTable3);
		document.add(tableDivider2);
		document.add(new Paragraph("\n"));
		document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMarginBottom(15f));
		Table tb = new Table(fullwidth);
		tb.addCell(new Cell().add("Cam on Quy khach!\n").setBold().setBorder(Border.NO_BORDER));
		tb.addCell(new Cell().add("Hen gap lai!\n").setBold().setBorder(Border.NO_BORDER));
		List<String> Tnclist = new ArrayList<>();
		Tnclist.add("Website:www.co-opmart.com.vn");
		
		for(String tnc: Tnclist){
			tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
		}
		
		document.add(tb);
		
		
		document.close();
		
		System.out.println("pdf generated");
		
	}
	
	static Cell getHeaderTextCell (String textValue) {
		return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
	}
	
	static Cell getHeaderTextCellValue (String textValue) {
		return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getBillingandShippingCell (String textValue) {
		return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getCell10fLeft (String textValue, Boolean isBold) {
		Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
		return isBold ? myCell.setBold() : myCell;
	}

}





