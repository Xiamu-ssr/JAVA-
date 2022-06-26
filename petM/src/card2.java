import javax.swing.*;

//import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class card2
{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//Mysql 8.0.0及以上驱动
    static final String DB_URL = "jdbc:mysql://localhost:3306/java1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";//使用java1数据库
    private static Class<?> forName;
    public static void main(String[] agrs)throws IOException
    {
        newPage();
    }
    public static Connection linkSql(String user,String passwd)//连接sql方法,返回Connection
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
    public static ResultSet comSql1(Connection conn,String command)//对sql使用命令,有返回值
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
    public static void comSql2(Connection conn,String command)//对sql使用命令,无返回值
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
    //在GridBagBorder中添加书籍组件
    //参数：书籍名-图片路径-网格定位x-网格定位y-窗口-GridBag布局器-GridBag属性器-是否借阅-sql的有效connection
    public static void addBook(String name,String imgpath,int x,int y,JFrame frame,GridBagLayout gbaglayout,GridBagConstraints contrains,int flag,Connection conn)
    {
        ImageIcon img = new ImageIcon(imgpath);// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img.setImage(img.getImage().getScaledInstance(150,200,Image.SCALE_DEFAULT));
        JLabel l1=new JLabel(name,img,JLabel.CENTER);
        l1.setFont(new Font("楷体",Font.CENTER_BASELINE,16));
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
            b=new JButton("借阅");
            b.setFont(new Font("楷体",Font.BOLD,16));
            b.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(frame,"借阅成功!","提示",1);
                    comSql2(conn, "update book set flag=1 where name=\""+name+"\"");
                    try {
                        new card2().newPage();
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
        else
        {
            b=new JButton("还回");
            b.setFont(new Font("楷体",Font.BOLD,16));
            b.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(frame,"书籍已还回,感谢使用。","提示",1);
                    comSql2(conn, "update book set flag=0 where name=\""+name+"\"");
                    try {
                        new card2().newPage();
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
        JFrame frame=new JFrame("书城");
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
                //System.out.println(imgpath);
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
