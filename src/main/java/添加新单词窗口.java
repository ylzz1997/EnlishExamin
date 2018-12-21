import bean.EnlishEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by lhy on 2018/6/18.
 */
public class 添加新单词窗口 extends JFrame{
    JFrame owner;
    HibenateConfig hibenateConfig = new HibenateConfig();

    private JTextField yingYuTF;
    private JTextField zhongWenTF;
    public 添加新单词窗口(JFrame owner) {
        super("查看词典");
        this.owner=owner;
        owner.setEnabled(false);
        this.setBounds(50,20,600,200);
        init();
    }

    private void init() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                owner.setEnabled(true);
                添加新单词窗口.this.dispose();
            }
        });
        this.setLayout(null);
        JLabel zhongWen = new JLabel("中文:");
        JLabel yingYu = new JLabel("英语:");
        yingYuTF = new JTextField();
        zhongWenTF = new JTextField();
        JButton btnAdd = new JButton("添加");

        yingYu .setBounds(20,30,50,20);
        yingYu .setFont(new Font("微软雅黑",Font.PLAIN,15));
        this.add(yingYu);

        zhongWen.setBounds(20,100,50,20);
        zhongWen .setFont(new Font("微软雅黑",Font.PLAIN,15));
        this.add(zhongWen);

        yingYuTF.setBounds(60,30,150,20);
        this.add(yingYuTF);

        zhongWenTF.setBounds(60,100,150,20);
        this.add(zhongWenTF);

        btnAdd.setBounds(300,20,100,100);
        btnAdd.setFont(new Font("微软雅黑",Font.PLAIN,30));
        this.add(btnAdd);

        btnAdd.addActionListener(e ->{
            btnAddAction(e);
        });
    }

    private void btnAddAction(ActionEvent e) {
        String zhongWenW = zhongWenTF.getText();
        String yingYuW = yingYuTF.getText();
        Object[] options = {"确定","取消"};
        int reaspons = JOptionPane.showOptionDialog(this,"确认添加 英文:"+yingYuW+" 中文:"+zhongWenW+" 么？","提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if(reaspons==0){
            Session session = hibenateConfig.getSession();
            Transaction tx =  session.beginTransaction();
            EnlishEntry word = new EnlishEntry(yingYuW,zhongWenW);
            session.save(word);
            tx.commit();
        }else {

        }
    }
}
