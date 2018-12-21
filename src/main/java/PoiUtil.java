import bean.EnlishEntry;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lhy on 2018/6/11.
 */

public class PoiUtil {
    MainForm parent;

    public PoiUtil() {
    }

    public PoiUtil(MainForm parent) {
        this.parent = parent;
    }

    public XWPFDocument newWordDocument(){
       return new XWPFDocument();
    }

    public XWPFTable addHeadTable(XWPFDocument doc){
        XWPFTable headTable = doc.createTable(2,4);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        XWPFTableCell headCell = headTable.getRow(0).getCell(0);
        CTTc cttc = headCell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        CTTblPr tablePr = headTable.getCTTbl().addNewTblPr();
        CTTblWidth width = tablePr.addNewTblW();
        width.setW(BigInteger.valueOf(8000));
        headTable.getRow(0).setHeight(900);
        headTable.getRow(1).setHeight(900);
        headTable.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(8000));
        headTable.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        headTable.getRow(1).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        headTable.getRow(1).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        headTable.getRow(1).getCell(3).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        XWPFRun headRun = headCell.getParagraphs().get(0).createRun();
        headRun.setFontSize(25);
        headRun.setBold(true);
        headRun.setText(sdf.format(date)+"的测验");
        return headTable;
    }

    public void addNewRow(XWPFTable tb,int row){
        for(int i=0;i<row;i++){
            XWPFTableRow rowN = tb.createRow();
            rowN.setHeight(900);
            int j = 0;
            while (rowN.getCell(j)!=null){
                rowN.getCell(j).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
                j++;
            }
        }
        try {
            mergeCellsHorizontal(tb,0,0,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewEnglishWord(XWPFTable tb, int row, int col, EnlishEntry word, boolean A){
        XWPFTableRow rowT = tb.getRow(row);
        if(A){
            rowT.getCell(col*2).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
            rowT.getCell(col*2).getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
            XWPFRun run = rowT.getCell(col*2).getParagraphs().get(0).createRun();
            run.setFontSize(20);
            run.setBold(true);
            run.setText(word.getEnglish());
        }else {
            XWPFRun run = rowT.getCell(col*2+1).getParagraphs().get(0).createRun();
            rowT.getCell(col*2+1).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
            rowT.getCell(col*2+1).getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
            run.setFontSize(20);
            run.setBold(true);
            run.setText(word.getChinese());
        }
    }

    public void addNewEnglishWord(XWPFTable tb, int row, int col, EnlishEntry word){
        XWPFTableRow rowT = tb.getRow(row);
            rowT.getCell(col*2).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
            rowT.getCell(col*2).getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
            XWPFRun runA = rowT.getCell(col*2).getParagraphs().get(0).createRun();
            runA.setFontSize(20);
            runA.setBold(true);
            runA.setText(word.getEnglish());
            XWPFRun runB = rowT.getCell(col*2+1).getParagraphs().get(0).createRun();
            rowT.getCell(col*2+1).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
            rowT.getCell(col*2+1).getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
            runB.setFontSize(20);
            runB.setBold(true);
            runB.setText(word.getChinese());
        }

    public void saveDocument(XWPFDocument doc){
        FileNameExtensionFilter filter=new FileNameExtensionFilter("*.docx","docx");
        JFileChooser fc=new JFileChooser();
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int result=fc.showSaveDialog(parent);
        if (result==JFileChooser.APPROVE_OPTION) {
            File file=fc.getSelectedFile();
            if (!file.getPath().endsWith(".docx")) {
                file=new File(file.getPath()+".docx");
            }
            try {
                OutputStream os = new FileOutputStream(file);
                doc.write(os);
                os.close();
                parent.progressBar.setValue(100);
                JOptionPane.showMessageDialog(parent,"生成完毕！","提示",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent,"出现保存错误:"+e.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }finally {
                doc = null;
            }
        }
        }

    public void saveDocument(XWPFDocument doc,XWPFDocument docAnswer){
        FileNameExtensionFilter filter=new FileNameExtensionFilter("*.docx","docx");
        JFileChooser fc=new JFileChooser();
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int result=fc.showSaveDialog(parent);
        if (result==JFileChooser.APPROVE_OPTION) {
            File file=fc.getSelectedFile();
            File answerFile=new File(file.getPath()+"参考答案");
            if (!file.getPath().endsWith(".docx")) {
                file=new File(file.getPath()+".docx");
            }
            if (!answerFile.getPath().endsWith(".docx")) {
                answerFile=new File(answerFile.getPath()+".docx");
            }
            try {
                OutputStream os = new FileOutputStream(file);
                OutputStream osa = new FileOutputStream(answerFile);
                doc.write(os);
                docAnswer.write(osa);
                os.close();
                osa.close();
                parent.progressBar.setValue(100);
                JOptionPane.showMessageDialog(parent,"生成完毕！","提示",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent,"出现保存错误:"+e.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }finally {
                doc = null;
                docAnswer=null;
            }
        }
    }

    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) throws Exception {
        if(fromCell>toCell){
            throw new Exception("初始单元格大于到达单元格");
        }

        for (int cellIndex = fromCell+1; cellIndex <= toCell; cellIndex++) {
            table.getRow(row).getCell(fromCell+1).getCTTc().newCursor().removeXml();
            table.getRow(row).removeCell(fromCell+1);
            System.out.print("删除成功！行"+row+"列"+cellIndex);
        }

        table.getRow(row).getCell(fromCell).getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(toCell+1-fromCell));
    }
}
