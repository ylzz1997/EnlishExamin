import bean.EnlishEntry;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;

/**
 * Created by lhy on 2018/6/13.
 */
public class 查看词典窗口 extends JFrame {
    JFrame owner;
    HibenateConfig hibenateConfig = new HibenateConfig();
    JTable wordTable;

    final static String[] TABLE_TOP = {"英文","汉语"};
    public 查看词典窗口(JFrame owner) {
        super("查看词典");
        this.owner=owner;
        owner.setEnabled(false);
        this.setBounds(20,20,600,900);
        init();
    }

    private void init() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                owner.setEnabled(true);
                查看词典窗口.this.dispose();
            }
        });
        this.setLayout(null);
        Session session = hibenateConfig.getSession();
        Query hq = session.createQuery("from EnlishEntry");
        List<EnlishEntry> wordList = hq.list();
        Object[][] wordObject = toObjectArray(wordList);

        wordTable = new JTable(wordObject, TABLE_TOP) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollpane = new JScrollPane(wordTable);
        scrollpane.setBounds(0, 0, 595, 700);
        this.setResizable(false);
        this.add(scrollpane);

        JLabel 单词总数 = new JLabel("单词库中共有单词:" + wordList.size());
        单词总数.setBounds(20, 710, 595, 50);
        单词总数.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.add(单词总数);

        JButton 查找单词 = new JButton("查找单词");
        查找单词.setBounds(20, 760, 100, 50);
        查找单词.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        this.add(查找单词);

        查找单词.addActionListener(e ->{
            JOptionPane.showMessageDialog(this,"由于作者太懒，本功能并没有做","提示",JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private Object[][] toObjectArray(List<EnlishEntry> list){
        int size = list.size();
        Object[][] ret = new Object[size][2];
        for(int i =0;i<size;i++){
            ret[i][0] = list.get(i).getEnglish();
            ret[i][1] = list.get(i).getChinese();
        }

        return ret;
    }
}
