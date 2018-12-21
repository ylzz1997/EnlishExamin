import bean.EnlishEntry;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.List;


/**
 * Created by lhy on 2018/6/11.
 */
public class InstantiationExcel {
    private MainForm parent;
    private  HibenateConfig hc;
    private PoiUtil poi;
    private int practiceNum = 20;


    public InstantiationExcel(int practiceNum,MainForm parent) {
        this.practiceNum = practiceNum;
        this.parent = parent;
    }

    public void excute(){
        try {
            hc = new HibenateConfig();
            parent.progressBar.setValue(20);
            poi = new PoiUtil(parent);
            Session session = hc.getSession();
            Query hq =  session.createQuery("from EnlishEntry order by rand()");
            hq.setFetchSize(0);
            hq.setMaxResults(practiceNum);
            List<EnlishEntry> wordList= hq.list();
            parent.progressBar.setValue(30);
            int row =wordList.size()==0? 0:((int) Math.ceil((float)wordList.size()/2.0)-1);
            XWPFDocument englishDoc = poi.newWordDocument();
            XWPFDocument englishDocAnswer = poi.newWordDocument();
            XWPFTable headTable = poi.addHeadTable(englishDoc);
            XWPFTable headTableAnswer = poi.addHeadTable(englishDocAnswer);
            parent.progressBar.setValue(40);
            poi.addNewRow(headTable,row);
            poi.addNewRow(headTableAnswer,row);
            int i = 0;
            for(EnlishEntry word : wordList){
                int wrow = i/2;
                int wcol = i%2 ;
                boolean englishB = Math.random()*1000>=500?true:false;
                poi.addNewEnglishWord(headTable,wrow+1,wcol,word,englishB);
                poi.addNewEnglishWord(headTableAnswer,wrow+1,wcol,word);
                i++;
            }
            parent.progressBar.setValue(70);
            poi.saveDocument(englishDoc,englishDocAnswer);
        }catch (Exception e){
            JOptionPane.showMessageDialog(parent,"出现错误:"+e.getMessage()+"!","错误",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }
}
