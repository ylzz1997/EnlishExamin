import com.sun.java.accessibility.util.java.awt.ButtonTranslator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * Created by lhy on 2018/6/11.
 */
public class MainForm extends JFrame {
    private InstantiationExcel ie;
    private JTextField practiceText;
    public JProgressBar progressBar;
    MainForm(){
        super("今日英语练习");
        this.setSize(500,390);
        init();
    }

    private void init(){
        this.setLayout(null);
        JLabel welText = new JLabel();
        welText.setText("点击下方按钮生成测试卷 ↓");
        welText.setFont(new Font("微软雅黑",Font.BOLD,30));
        welText.setBounds(50,10,350,50);
        this.add(welText);
        JButton start = new JButton("点击生成!");
        start.setBounds(18,60,450,70);
        start.setFont(new Font("微软雅黑",Font.BOLD,50));
        start.setForeground(new Color(255,0,0));
        this.add(start);

        JLabel practiceLable = new JLabel();
        practiceLable.setText("单词数量:");
        practiceLable.setBounds(20,140,70,30);
        this.add(practiceLable);

        practiceText = new JTextField();
        practiceText.setBounds(80,140,200,30);
        practiceText.setText("");
        this.add(practiceText);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBounds(20,250,450,50);
        this.add(progressBar);
        start.addActionListener((e)->{
            startOnClick(e);
        });

        JButton 查看词典 = new JButton("查看词典");
        查看词典.setBounds(20,180,150,50);
        this.add(查看词典);

        JButton 添加新单词 = new JButton("添加新单词");
        添加新单词.setBounds(200,180,150,50);
        this.add(添加新单词);

        查看词典.addActionListener((e)->{
            查看词典_点击方法(e);
        });

        添加新单词.addActionListener((e)->{
            添加新单词_点击方法(e);
        });
    }

    private void startOnClick(ActionEvent e){
        JButton but = (JButton)e.getSource();
        if (!practiceText.getText().equals("")&&isNumeric(practiceText.getText())){
            progressBar.setValue(10);
            but.setEnabled(false);
            ie = new InstantiationExcel(Integer.parseInt(practiceText.getText()),this);
            ie.excute();
        }else {
            JOptionPane.showMessageDialog(this,"请输入数字","错误",JOptionPane.ERROR_MESSAGE);
        }
        but.setEnabled(true);
        progressBar.setValue(0);
    }

    private void 查看词典_点击方法(ActionEvent e){
        查看词典窗口 词典窗口对象 = new 查看词典窗口(this);
        词典窗口对象.setVisible(true);
    }

    private void 添加新单词_点击方法(ActionEvent e){
        添加新单词窗口 新单词窗口对象 = new 添加新单词窗口(this);
        新单词窗口对象.setVisible(true);
    }

    private static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
