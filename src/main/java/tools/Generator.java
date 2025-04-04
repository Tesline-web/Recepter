package tools;
import com.itextpdf.layout.element.Paragraph;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import models.Service;

import java.io.IOException;

public abstract class Generator {

    public static void generatePDF() {
        String dest = "";

        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Facture"));
            float[] columnWidths = {200F, 200F};
            Table table = new Table(columnWidths);
        }catch(IOException e) {

        }
    }
    public static double SumAmount(ObservableList<Service> services){
        double sum = 0.0;
        for(Service service : services){
            sum += service.getPrice();
        }
        return sum;
    }
}
