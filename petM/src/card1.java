import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class card1 {
    public static void main(String[] args) throws IOException
    {
        newPage();//方便跳转
    }

    public static void newPage()
    {
        JFrame frame=new JFrame("KKK电子书城");//定义窗口
        GridBagLayout gbaglayout=new GridBagLayout();//布局管理器
        GridBagConstraints contrains=new GridBagConstraints();//单个组件布局属性修改器
        frame.setLayout(gbaglayout);//载入
        
        JButton b1=new JButton("书 城");//按钮-跳转书城
        b1.setFont(new Font("楷体",Font.BOLD,24));
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    new card2().newPage();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        contrains.gridwidth = GridBagConstraints.REMAINDER;//结束此行
        contrains.fill=GridBagConstraints.HORIZONTAL;//水平扩展
        contrains.gridx=0;//左上角定位网格0行0列
        contrains.gridy=0;
        contrains.ipadx=100;//长宽
        contrains.ipady=40;
        contrains.insets=new Insets(0,0,40,0);//外边距
        gbaglayout.setConstraints(b1, contrains);//使用布局
        frame.add(b1);

        JButton b2=new JButton("我 的");//按钮2
        b2.setFont(new Font("楷体",Font.BOLD,24));
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //JOptionPane.showMessageDialog(frame,"请注册或登录...","提示",1);
                try {
                    new card3().newPage();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        contrains.fill=GridBagConstraints.HORIZONTAL;
        contrains.gridx=0;
        contrains.gridy=1;
        contrains.ipadx=100;
        contrains.ipady=40;
        contrains.insets=new Insets(0,0,40,0);
        gbaglayout.setConstraints(b2, contrains);
        frame.add(b2);

        //bg
        JLabel backg=new JLabel();
        ImageIcon bgimg=new ImageIcon("D:/Desktop/Java/petM/src/img/backg.png");
        backg.setIcon(bgimg);
        backg.setBounds(0,0,660,880);
        //得到第二层面板并将标签放在上面
        frame.getLayeredPane().add(backg,new Integer(Integer.MIN_VALUE));
        JPanel contentPanel=(JPanel)frame.getContentPane();
        //设置透明
        contentPanel.setOpaque(false);

        frame.setBounds(400,100,660,880);//窗口大小和位置
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
