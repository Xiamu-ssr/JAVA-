import javax.swing.*;

//import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class card3
{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/java1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static Class<?> forName;
    public static void main(String[] agrs)throws IOException
    {
        newPage();
    }
    public static Connection linkSql(String user,String passwd)
    {
        Connection conn = null;
        try
        {
            // 注册 JDBC 驱动
            forName = Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据...");
            conn = DriverManager.getConnection(DB_URL,user,passwd);
        }
        catch(SQLException se)
        {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e)
        {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        System.out.println("连接成功...");
        return conn;
    }
    public static ResultSet comSql1(Connection conn,String command)
    {
        ResultSet rs=null;
        try
        {
            System.out.println("采集信息...");
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(command);
            //stmt.close();
        }
        catch(SQLException se)
        {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e)
        {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return rs;
    }
    public static void comSql2(Connection conn,String command)
    {
        try
        {
            System.out.println("采集信息...");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(command);
            //stmt.close();
        }
        catch(SQLException se)
        {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e)
        {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }
    public static void addBook(String name,String imgpath,int x,int y,JFrame frame,GridBagLayout gbaglayout,GridBagConstraints contrains,int flag,Connection conn)
    {
        ImageIcon img = new ImageIcon(imgpath);// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img.setImage(img.getImage().getScaledInstance(150,200,Image.SCALE_DEFAULT));
        JLabel l1=new JLabel(name,img,JLabel.CENTER);
        l1.setFont(new Font("楷体",Font.BOLD,16));
        //b1.setBounds(0,0,150,50);
        //contrains.gridwidth = GridBagConstraints.REMAINDER;    //结束行
        contrains.fill=GridBagConstraints.HORIZONTAL;
        contrains.gridx=x;
        contrains.gridy=y;
        //contrains.ipadx=100;
        //contrains.ipady=40;
        contrains.insets=new Insets(0,0,0,0);
        gbaglayout.setConstraints(l1, contrains);
        frame.add(l1);
        JButton b;
        if(flag==0)//未借阅
        {
            b=new JButton("未在库中");
            b.setFont(new Font("楷体",Font.BOLD,16));
            b.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(frame,"此书还未借阅!","提示",1);
                }
            });
            contrains.fill=GridBagConstraints.NONE;
            contrains.gridx=x;
            contrains.gridy=y+1;
            gbaglayout.setConstraints(b, contrains);
            frame.add(b);
        }
        else
        {
            b=new JButton("点击阅读");
            b.setFont(new Font("楷体",Font.BOLD,16));
            b.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(frame,"欢迎使用KKK阅读器!祝你阅读愉快。","提示",1);
                    try {
                        new card4().newPage(name);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            contrains.fill=GridBagConstraints.NONE;
            contrains.gridx=x;
            contrains.gridy=y+1;
            gbaglayout.setConstraints(b, contrains);
            frame.add(b);
        }

    }

    public static void newPage() throws IOException
    {
        /* mysql连接 */
        Connection conn=linkSql("mumu","mysqlMKL11@");
        ResultSet rs=comSql1(conn, "select idbook,name,flag from book");
        int flag[]=new int[6],f=0;
        String name[]=new String[6];
        try
        {
            while(rs.next())
            {
                flag[f]=rs.getInt("flag");
                name[f]=rs.getString("name");
                System.out.println(flag[f]);
                System.out.println(name[f]);
                f++;
            }
            //rs.close();
            //conn.close();
        }
        catch(SQLException se)
        {
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e)
        {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        
        /* 书籍网格布局 */
        JFrame frame=new JFrame("库");
        GridBagLayout gbaglayout=new GridBagLayout();//布局管理器
        GridBagConstraints contrains=new GridBagConstraints();//单个组件布局属性修改器
        frame.setLayout(gbaglayout);//载入
        contrains.weightx=0.5;    // 指定组件的分配区域
        contrains.weighty=0.2;
        for(int i=0;i<3;++i)
        {
            for(int j=0;j<2;++j)
            {
                String imgpath="D:/Desktop/Java/petM/src/img/"+String.valueOf(j+i*2+1)+".png";
                addBook(name[i*2+j],imgpath,j,i*2,frame,gbaglayout,contrains,flag[i*2+j],conn);
            }
        }

        JButton back=new JButton("返回");//按钮2
        back.setFont(new Font("楷体",Font.BOLD,24));
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new card1().newPage();;
                frame.dispose();
            }
        });
        contrains.gridwidth = GridBagConstraints.REMAINDER;    //结束行
        contrains.fill=GridBagConstraints.HORIZONTAL;
        contrains.gridx=0;
        contrains.gridy=8;
        contrains.ipadx=100;
        contrains.ipady=40;
        contrains.insets=new Insets(0,0,0,0);
        gbaglayout.setConstraints(back, contrains);
        frame.add(back);

        JLabel backg=new JLabel();
        ImageIcon bgimg=new ImageIcon("D:/Desktop/Java/petM/src/img/backg.png");
        backg.setIcon(bgimg);
        backg.setBounds(0,0,660,880);
        //得到第二层面板并将标签放在上面
        frame.getLayeredPane().add(backg,new Integer(Integer.MIN_VALUE));
        JPanel contentPanel=(JPanel)frame.getContentPane();
        //设置透明
        contentPanel.setOpaque(false);

        frame.setBounds(400,100,660,880);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
