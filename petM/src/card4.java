import javax.sound.midi.ControllerEventListener;
import javax.swing.*;

//import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class card4
{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/java1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static Class<?> forName;
    public static void main(String[] agrs)throws IOException
    {
        newPage("圆圈正义");
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

    public static void newPage(String name) throws IOException
    {
        /* mysql连接 */
        Connection conn=linkSql("mumu","mysqlMKL11@");
        ResultSet rs=comSql1(conn, "select content from book where name=\""+name+"\"");
        String content="";
        try
        {
            while(rs.next())
            {
                content=rs.getString("content");
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
        
        JFrame frame=new JFrame("MKL阅读器");
        frame.setLayout(new BorderLayout());

        JLabel jl=new JLabel(name+"\n\n\n",JLabel.CENTER);
        jl.setFont(new Font("宋体",Font.PLAIN,30));
        //jl.setHorizontalAlignment();
        frame.add(jl,BorderLayout.NORTH);
        
        JPanel jp=new JPanel();    //创建一个JPanel对象
        JTextArea jta=new JTextArea(content,20,35);
        jta.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font("楷体",Font.BOLD,24));    //修改字体样式
        jta.setBackground(Color.WHITE);    //设置按钮背景色
        JScrollPane jsp=new JScrollPane(jta);    //将文本域放入滚动窗口
        Dimension size=jta.getPreferredSize();    //获得文本域的首选大小
        jsp.setBounds(110,90,size.width,size.height);
        jp.add(jsp);    //将JScrollPane添加到JPanel容器中
        frame.add(jp,BorderLayout.CENTER);
        

        JButton back=new JButton("返回");//按钮2
        back.setFont(new Font("楷体",Font.BOLD,24));
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    new card3().newPage();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                };
                frame.dispose();
            }
        });
        Dimension preferredSize = new Dimension(660,66);
        back.setPreferredSize(preferredSize );
        frame.add(back,BorderLayout.SOUTH);

        frame.setBounds(400,100,660,880);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
