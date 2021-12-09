package chat;
import com.kuang.pojo.Message;
import com.kuang.pojo.User;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JList;
import javax.swing.*;
public class clientgui extends JFrame implements KeyListener {

		private JPanel panel_north;//北部区域面板
		private JLabel jbl_touxiang;//头像
		private JLabel jbl_friendname;//好友名称

		private JButton send;
		private JButton send1;


		//头像下方7个功能按钮（未实现）
		private JButton btn_func1_north, btn_func2_north, btn_func3_north, btn_func4_north, btn_func5_north, btn_func6_north, btn_func7_north;
		//聊天内容显示面板
		private JTextPane panel_Msg;

		private JPanel panel_south;//南部区域面板
		private JTextPane jtp_input;//消息输入区
		//消息输入区上方9个功能按钮(未实现)
		private JButton btn_func1_south, btn_func2_south, btn_func3_south,btn_func4_south, btn_func5_south, btn_func6_south, btn_func7_south, btn_func8_south, btn_func9_south;
		private JButton recorde_search;//查看消息记录按钮
		private JButton btn_send, btn_close;//消息输入区下方关闭和发送按钮
		private JTextArea recode;
		private JPanel panel_east;//东部面板
		private CardLayout cardLayout;//卡片布局
		//默认东部面板显示一张图,点击查询聊天记录按钮切换到聊天记录面板
		private final JLabel label1 = new JLabel(new ImageIcon("image/dialogimage/hh.gif"));
		private JTextPane panel_Record;//聊天记录显示面板

		private boolean isDragged = false;//鼠标拖拽窗口标志
		private Point frameLocation;//记录鼠标点击位置
		private String myId;//本人账号
		private String myName;
		private String friendId;//好友账号
		private DateFormat df;//日期解析


		private Point tmp,loc;//记录列表位置
		private static final long serialVersionUID = 1L;
//		boolean isDragged = false;//记录鼠标是否是拖拽移动
		private Point frame_temp;//鼠标当前相对窗体的位置坐标
		private Point frame_loc;//窗体的位置坐标
		public static DatagramSocket  s;//客户端端口号
		public static int serverport;//服务端端口号
		public static int fileport;//服务端端文件流口号
		private JLabel jlb_north;//北部背景图片标签
		private JLabel jlb_photo; // 头像
		private JButton btn_min; // 最小化
		private JButton btn_exit; // 退出
		private JLabel jlb_code; // 二维码
		private JLabel after_qqPwd; // 忘记密码
		private JCheckBox remPwd; // 记住密码
		private JCheckBox autoLog; // 自动登录

		private JLabel aga_s;
		private JPasswordField aga_s_F;
		public  static JTextField text2;
		public static JPasswordField  text3;
		public  static JTextField text4;
		public  static JPasswordField text5;
		public static JButton send2;
		public static JButton send3; //注册
		public static JButton send4;
		public static JButton friendchoose;
		public static JLabel l1;
		public static JLabel l2;
		public static JLabel l3;
		public static JLabel l4;
		public static clientgui frame;
		public static clientgui client3;
		public static  clientgui client;
		public JPanel root;
		public static JList<String> jlist;//好友列表
		public static String selfname;//自己的名字
		public receive rea;//一个客户端一个额外的线程
		public static HashMap<String,JTextArea> map;//用在多线程
		public static HashSet<String> chatopen;//聊天框
		public static InetAddress ip; //服务器端ip地址


		public static void main(String[] args) {
			try {
				ip=InetAddress.getLocalHost();
				serverport=13000;
				fileport=13001;
				client=null;
				map=new HashMap<>();
				frame = new clientgui();//打开登陆窗口
//				frame.setTitle("登录窗口");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public  void putmap(String a,JTextArea b){
		map.put(a,b);
	}
		public clientgui() {//登陆界面

			Container root = this.getContentPane();

			//设置布局
			//处理右上角最小化和关闭按钮
			btn_min = new JButton(new ImageIcon("image/login/min.jpg"));
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//注册监听器,点击实现窗口最小化
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			btn_min.setBounds(373, 0, 28, 29);
			root.add(btn_min);

			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//注册监听器,点击实现窗口关闭
					System.exit(0);
				}
			});
			btn_exit.setBounds(401, 0, 28, 29);
			root.add(btn_exit);

			// 背景
			jlb_north = new JLabel(new ImageIcon("image/login/login.jpg"));
			jlb_north.setBounds(0,0,430,182);
			root.add(jlb_north);

			//处理中部账号密码框左边企鹅图片标签
			jlb_photo = new JLabel(new ImageIcon("image/login/cw.png"));
			jlb_photo.setBounds(20, 192, 82, 78);
			root.add(jlb_photo);
	        l1=new JLabel("账号:");
	        l2=new JLabel("密码:");
	        send2=new JButton(new ImageIcon("image/login/loginbutton.png"));

	        send3=new JButton(new ImageIcon("image/login/reg_login.png"));
	        text2=new JTextField();
	        text3=new JPasswordField ();
	        l1.setBounds(120, 195, 40, 25);
	        l2.setBounds(120, 240, 40, 25);

	        text2.setBounds(155,195,194,30);//账号
	        text3.setBounds(155,240,194,30);//密码
	        send2.setBounds(155,299,194,30);//登录
	        send3.setBounds(30, 285, 62, 25);//注册
	        root.add(l1);
	        root.add(l2);	        
	        root.add(text2);
	        root.add(text3);
	        root.add(send2);
	        root.add(send3);

			// 处理二维码
			jlb_code = new JLabel(new ImageIcon("image/login/code.png"));
			jlb_code.setBounds(380,299,40,40);
			root.add(jlb_code);

			//处理密码输入框后的"忘记密码"
			after_qqPwd = new JLabel("忘记密码");
			after_qqPwd.setForeground(Color.GRAY);
			after_qqPwd.setBounds(360,240,78,30);
			root.add(after_qqPwd);
			//处理"记住密码"单选框
			remPwd = new JCheckBox("记住密码");
			remPwd.setBounds(155,277,85,15);
			root.add(remPwd);
			//处理"自动登录"单选框
			autoLog = new JCheckBox("自动登录");
			autoLog.setBounds(270,277,85,15);
			root.add(autoLog);

			//注册鼠标事件监听器
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//鼠标释放
					isDragged = false;
					//光标恢复
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//鼠标按下
					//获取鼠标相对窗体位置
					frame_temp = new Point(e.getX(),e.getY());
					isDragged = true;
					//光标改变为移动形式
					if(e.getY() < 182)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});


			//注册鼠标事件监听器
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//指定范围内点击鼠标可拖拽
					if(e.getY() < 182){
						//如果是鼠标拖拽移动
						if(isDragged) {
							frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
									getLocation().y+e.getY()-frame_temp.y);
							//保证鼠标相对窗体位置不变,实现拖动
							setLocation(frame_loc);
						}
					}
				}
			});

			root.setLayout(null);
			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//修改窗体默认图标
			this.setSize(430,345);//设置窗体大小
			this.setLocation(600,300);
			this.setUndecorated(true);//去掉自带装饰框
			this.setVisible(true);//设置窗体可见
			this.setLocationRelativeTo(null);

            send2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
					loginthing(text2.getText(), String.valueOf(text3.getPassword()));
				}
			});

            send3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	client3 = new clientgui(1);

    				client3.setVisible(true);
				}
			});
			text3.addKeyListener(new KeyListener() {//回车响应
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						loginthing(text2.getText(), String.valueOf(text3.getPassword()));
					}
				}
				public void keyReleased(KeyEvent e) {
				}
				public void keyTyped(KeyEvent e) {
				}
			});
		}

		public void loginthing(String sname,String mima){
			selfname=sname;
			User user=new User();
			user.setName(selfname);
			user.setPwd(mima);
			user.setId(22);
			user.setFlag(1);

			Message mess=new Message(user);
			mess.setFlag(1);
			mess.setUseport(serverport);
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			ObjectOutputStream oout = null;

			try {
				//发送信息
				oout = new ObjectOutputStream(bout);
				oout.writeObject(mess);
				oout.flush();
				byte[] Buff=bout.toByteArray();       //转化为字节数组
				DatagramPacket dPacket=new DatagramPacket(Buff,Buff.length,ip,serverport);
				s=new DatagramSocket();
				s.send(dPacket);
				//等待信息回送,一直堵塞在这
				byte []rebuf=new byte[1024*8];
				DatagramPacket recePacket=new DatagramPacket(rebuf,rebuf.length);
				s.receive(recePacket);

				String message=new String(recePacket.getData(),0,recePacket.getLength());
				JOptionPane.showMessageDialog(null, message,"", JOptionPane.INFORMATION_MESSAGE);//提示消息，登陆成功&失败
				if(message.equals("登陆成功")){
					frame.dispose();//销毁登录界面
					//打开好友列表
					clientgui client=new clientgui(s);
					client.setTitle(selfname+"列表");
					client.setVisible(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public clientgui(DatagramSocket s){//聊天列表

			Container root = this.getContentPane();

			//设置窗体大小
			this.setSize(274,600);
			//设置布局
			root.setLayout(null);
			//右上角最小化按钮
			JButton btn_min = new JButton(new ImageIcon("image/friendlist/fmin.png"));
			btn_min.setBounds(217, 0, 28, 28);
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//窗体最小化
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			root.add(btn_min);
			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));

			//右上角退出按钮
			JButton btn_exit = new JButton(new ImageIcon("image/friendlist/fexit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//注册监听器,点击实现窗口关闭
					System.exit(0);
				}
			});

			root.add(btn_exit);
			//左上角qq标签
			JLabel jbl_leftTop = new JLabel(new ImageIcon("image/friendlist/biaoti.png"));
			jbl_leftTop.setBounds(0, 0, 44, 21);
			root.add(jbl_leftTop);
			//qq头像
			JLabel jbl_photo = new JLabel(new ImageIcon("image/login/cw.png"));
			jbl_photo.setBounds(10, 23, 78, 78);
			root.add(jbl_photo);
			//qq昵称
			JLabel jbl_qqName = new JLabel(selfname+"("+rea+")");
			jbl_qqName.setBounds(109, 32, 68, 21);
			root.add(jbl_qqName);

			//个性签名
			Font font = new Font("黑体", Font.BOLD, 10);
			JTextField jtf_personalSign1 = new JTextField();
			jtf_personalSign1.setText("Where there is a will, there is a way!");
			jtf_personalSign1.setFont(font);
//			jtf_personalSign1.setForeground(new Color());
			jtf_personalSign1.setBounds(45, 105, 217, 23);
			root.add(jtf_personalSign1);

			//在线状态选择列表
			String[] status = {"在线","隐身","离线"};
			JComboBox<String> online_status = new JComboBox<>(status);
			online_status.setSelectedIndex(0);
			online_status.setBounds(195, 32, 63, 21);
			root.add(online_status);

			//搜索框
			JTextField jtf_search = new JTextField("search");
			jtf_search.setBounds(0, 134, 247, 23);
			root.add(jtf_search);

			//搜索按钮
			JButton btn_search = new JButton(new ImageIcon("image/friendlist/search.png"));
			btn_search.setBounds(247, 134, 30, 23);
			root.add(btn_search);

			//上半部分背景图
			JLabel jbl_background = new JLabel(new ImageIcon("image/friendlist/beijing.png"));
			jbl_background.setBounds(0, 0, 274, 156);
			root.add(jbl_background);

			// 好友列表图片
			JLabel qun_ground = new JLabel(new ImageIcon("image/friendlist/qun.png"));
			qun_ground.setBounds(0, 157, 274, 59);
			root.add(qun_ground);

			// 私聊按钮图片
			friendchoose=new JButton(new ImageIcon("image/friendlist/chat.png"));
			jlist=new JList<String>(new DefaultListModel<String>());//用于更新列表
			friendchoose.setBounds(150,540,95,31);//选择按钮私聊
			jlist.setBounds(20,220,220,315);//好友列表

			// 底部8个按钮
			JButton btn_l1 = new JButton(new ImageIcon("image/friendlist/mainmenue.png"));
			btn_l1.setBounds(0, 577, 30, 23);
			root.add(btn_l1);

			//按钮2
			JButton btn_l2 = new JButton(new ImageIcon("image/friendlist/shezhi.png"));
			btn_l2.setBounds(30, 577, 30, 23);
			root.add(btn_l2);
			//按钮3
			JButton btn_l3 = new JButton(new ImageIcon("image/friendlist/messagemanage.png"));
			btn_l3.setBounds(60, 577, 30, 23);
			root.add(btn_l3);
			//按钮4
			JButton btn_l4 = new JButton(new ImageIcon("image/friendlist/filehleper.png"));
			btn_l4.setBounds(90, 577, 30, 23);
			root.add(btn_l4);
			//按钮5
			JButton btn_l5 = new JButton(new ImageIcon("image/friendlist/shoucang.png"));
			btn_l5.setBounds(120, 577, 30, 23);
			root.add(btn_l5);
			//按钮6
			JButton btn_l6 = new JButton(new ImageIcon("image/friendlist/tubiao8.png"));
			btn_l6.setBounds(150, 577, 30, 23);
			root.add(btn_l6);
			//按钮7
			JButton btn_l7 = new JButton(new ImageIcon("image/friendlist/tubiao9.png"));
			btn_l7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btn_l7.setBounds(180, 577, 30, 23);
			root.add(btn_l7);
			//按钮8
			JButton btn_l8 = new JButton(new ImageIcon("image/friendlist/apl.png"));
			btn_l8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btn_l8.setBounds(210, 577, 64, 23);
			root.add(btn_l8);
			//向服务端请求已登录的用户列表
			Thread u=null;
			try {
				//主线程监控更新信息
				byte[] buf=new byte[1024*8];
				DatagramPacket dp=new DatagramPacket(buf,buf.length);
				s.receive(dp);//接受信息，若无则堵塞在这

				// 反序列化接受消息对象
				ByteArrayInputStream bint=new ByteArrayInputStream(buf);
				ObjectInputStream oint=new ObjectInputStream(bint);
				Message message=(Message)oint.readObject();       //反序列化，恢复对象
				createuserlist(message);


				//专门接受列表信息 ，写一个类接受列表消息也就是登录用户
				rea=new receive(s,message,serverport,ip,fileport);
				u=new Thread(rea);
				u.start();
				chatopen=new HashSet<>();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			root.add(jlist);
			root.add(friendchoose);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					//关闭socket，并通知服务器
					try {
						Message clse=new Message(3);
						clse.setSenduser(getTitle().substring(0,getTitle().length()-2));
						ByteArrayOutputStream bout=new ByteArrayOutputStream();
						ObjectOutputStream oout = null;
						oout = new ObjectOutputStream(bout);
						oout.writeObject(clse);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //转化为字节数组
						DatagramPacket dp=new DatagramPacket(sendBuff,sendBuff.length,ip,serverport);
						s.send(dp);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
			jlist.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (jlist.getSelectedIndex() != -1) {
						if (e.getClickCount() == 2) {
							String mes=(String)jlist.getSelectedValue();
							if(mes==null)
								JOptionPane.showMessageDialog(null, "请选择好友","", JOptionPane.INFORMATION_MESSAGE);
							else if(chatopen.contains(mes))
								JOptionPane.showMessageDialog(null, "您已打开该对话框","", JOptionPane.INFORMATION_MESSAGE);
							else{
								chatopen.add(mes);
								clientgui jt=new clientgui(mes,rea);
								jt.setTitle(selfname+"(你)和"+mes);
								jt.setVisible(true);
							}
						}
					}
				}
			});


//			点击私聊之前需要点击用户
			friendchoose.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String mes=(String)jlist.getSelectedValue();
					if(mes==null)
						JOptionPane.showMessageDialog(null, "请选择好友","", JOptionPane.INFORMATION_MESSAGE);
					else if(chatopen.contains(mes))
						JOptionPane.showMessageDialog(null, "您已打开该对话框","", JOptionPane.INFORMATION_MESSAGE);
					else{
						chatopen.add(mes);
						clientgui jt=new clientgui(mes,rea);
						jt.setTitle(selfname+"(你)和"+mes);
						jt.setVisible(true);
					}
				}
			});


			//去除其定义装饰框
			this.setUndecorated(true);
			this.setLocationRelativeTo(null);
			//设置窗体可见
			this.setVisible(true);


			//添加鼠标监听事件
			this.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					isDragged = false;
					//拖拽结束图标恢复
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//限定范围内可拖拽
					if(e.getY()<30)
					{
						//获取鼠标按下位置
						tmp = new Point(e.getX(), e.getY());
						isDragged = true;
						//拖拽时更改鼠标图标
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
					}
				}
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (isDragged) {
						//设置鼠标与窗体相对位置不变
						loc = new Point(getLocation().x + e.getX() - tmp.x,
								getLocation().y + e.getY() - tmp.y);
						setLocation(loc);
					}
				}

			});

		}

		//在JList控件更新列表 创建用户列表
		public static void createuserlist(Message message){
			String []liststr=new String[message.getList().size()];
			message.getList().toArray(liststr);
			jlist.setListData(liststr);
		}
		public clientgui(int flag1) {//注册界面


			Container root = this.getContentPane();
	        this.setBounds(0, 0, 430, 500);
			this.setLocation(700,400);
	        this.setContentPane(root);
	        this.setLayout(null);
	        this.setName("注册用户");

			//处理北部背景图片标签
			jlb_north = new JLabel(new ImageIcon("image/login/login.jpg"));
			jlb_north.setBounds(0,0,430,182);
			root.add(jlb_north);

			//处理右上角最小化和关闭按钮
			btn_min = new JButton(new ImageIcon("image/login/min.jpg"));
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//注册监听器,点击实现窗口最小化
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			btn_min.setBounds(373, 0, 28, 29);
			root.add(btn_min);

			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//注册监听器,点击实现窗口关闭
					System.exit(0);
				}
			});
			btn_exit.setBounds(401, 0, 28, 29);
			root.add(btn_exit);

			JCheckBox jch = new JCheckBox("我已阅读并同意相关服务条款");
			jch.setBounds(120,370,200,30);
			root.add(jch);

			l3=new JLabel("账号:");
	        l4=new JLabel("密码:");
			aga_s = new JLabel("再次输入密码：");
	        send4=new JButton(new ImageIcon("image/login/register.png"));
	        text4=new JTextField();
	        text5=new JPasswordField ();
	        aga_s_F=new JPasswordField ();
	        l3.setBounds(80,240,50,30); //账号
	        l4.setBounds(80,285,50,30); //密码
			aga_s.setBounds(30,330,100,30);
	        text4.setBounds(120, 240, 194, 30);//账号
	        text5.setBounds(120,285,194,30);//密码
			aga_s.setBounds(30,330,100,30); //再次输入密码 label
			aga_s_F.setBounds(120,330,194,30); //再次输入密码 输入框
	        send4.setBounds(170,410,90,33);//注册
	        root.add(l3);
	        root.add(l4);	        
	        root.add(text4);
	        root.add(text5);
	        root.add(send4);
			root.add(aga_s);
			root.add(aga_s_F);

			//注册鼠标事件监听器
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//鼠标释放
					isDragged = false;
					//光标恢复
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//鼠标按下
					//获取鼠标相对窗体位置
					frame_temp = new Point(e.getX(),e.getY());
					isDragged = true;
					//光标改变为移动形式
					if(e.getY() < 182)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});

			//注册鼠标事件监听器
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//指定范围内点击鼠标可拖拽
					if(e.getY() < 182){
						//如果是鼠标拖拽移动
						if(isDragged) {
							frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
									getLocation().y+e.getY()-frame_temp.y);
							//保证鼠标相对窗体位置不变,实现拖动
							setLocation(frame_loc);
						}
					}
				}
			});

			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//修改窗体默认图标
			this.setUndecorated(true);//去掉自带装饰框
			this.setVisible(true);//设置窗体可见


            send4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	String username=text4.getText();
                	String mima= String.valueOf(text5.getPassword());
                	if(username.equals(""))
                	{
                		JOptionPane.showMessageDialog(null, "账号为空","", JOptionPane.INFORMATION_MESSAGE);
                	}
					else if(mima.equals("")){
						JOptionPane.showMessageDialog(null, "密码为空","", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						User user=new User();
						user.setName(username);
						user.setPwd(mima);
						user.setId(22);
						user.setFlag(0);
						Message mess=new Message(user);
						mess.setFlag(1);
						try {
							// 将对象转为字节之后通过 dp 发送，接受字节之后反序列化成对象
							ByteArrayOutputStream bout=new ByteArrayOutputStream();
							ObjectOutputStream oout = null;
							oout = new ObjectOutputStream(bout);
							oout.writeObject(mess);
							oout.flush();
							byte[] buff=bout.toByteArray();       //转化为字节数组
							DatagramPacket dp=new DatagramPacket(buff,buff.length,ip,serverport);
							s=new DatagramSocket();
							s.send(dp);
							//等待信息接收
							byte[] rebuf=new byte[8*1024];
							dp=new DatagramPacket(rebuf,rebuf.length);
							s.receive(dp);
							String message=new String(dp.getData(),0,dp.getLength());
							JOptionPane.showMessageDialog(null, message,"", JOptionPane.INFORMATION_MESSAGE);
							if(message.equals("注册成功"))
								client3.dispose();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						//销毁注册窗口
						client3.dispose();
					}
                }
			});
		}
		public clientgui(String name,receive rea) {//聊天框


			//获取窗口容器
			Container root = this.getContentPane();
			//设置布局
			this.setContentPane(root);
			root.setLayout(null);
			df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
			//北部面板
			panel_north = new JPanel();
			panel_north.setBounds(0, 0, 729, 92);
			panel_north.setLayout(null);
			//添加北部面板
			root.add(panel_north);
			//左上角灰色头像
			jbl_touxiang = new JLabel(new ImageIcon("image/dialogimage/lin.png"));
			jbl_touxiang.setBounds(10, 10, 42, 42);
			panel_north.add(jbl_touxiang);
			//头像右方正在聊天的对方姓名
			jbl_friendname = new JLabel(selfname+"("+friendId+")");
			jbl_friendname.setBounds(62, 21, 105, 20);
			panel_north.add(jbl_friendname);

			//右上角最小化按钮
			btn_min = new JButton(new ImageIcon ("image/dialogimage/min.png"));
			btn_min.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));
			btn_min.setBounds(668, 0, 30, 30);
			panel_north.add(btn_min);

			//右上角关闭按钮
			btn_exit = new JButton(new ImageIcon ("image/dialogimage/exit.jpg"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btn_exit.setBounds(698, 0, 30, 30);
			panel_north.add(btn_exit);

			//头像下方功能按钮

			//功能按钮1
			btn_func1_north = new JButton(new ImageIcon("image/dialogimage/yuyin.png"));
			btn_func1_north.setBounds(10, 62, 36, 30);
			panel_north.add(btn_func1_north);
			//功能按钮2
			btn_func2_north = new JButton(new ImageIcon("image/dialogimage/shipin.png"));
			btn_func2_north.setBounds(61, 62, 36, 30);
			panel_north.add(btn_func2_north);
			//功能按钮3
			btn_func3_north = new JButton(new ImageIcon("image/dialogimage/tranfile.jpg"));
			btn_func3_north.setBounds(112, 62, 36, 30);
			panel_north.add(btn_func3_north);
			//功能按钮4
			btn_func4_north = new JButton(new ImageIcon("image/dialogimage/createteam.jpg"));
			btn_func4_north.setBounds(163, 62, 36, 30);
			panel_north.add(btn_func4_north);
			//功能按钮5
			btn_func5_north = new JButton(new ImageIcon("image/dialogimage/yuancheng.png"));
			btn_func5_north.setBounds(214, 62, 36, 30);
			panel_north.add(btn_func5_north);
			//功能按钮6
			btn_func6_north = new JButton(new ImageIcon("image/dialogimage/sharedisplay.png"));
			btn_func6_north.setBounds(265, 62, 36, 30);
			panel_north.add(btn_func6_north);

			//功能按钮7
			btn_func7_north = new JButton(new ImageIcon("image/dialogimage/yingyong.jpg"));
			btn_func7_north.setBounds(318, 62, 36, 30);
			btn_func7_north.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			panel_north.add(btn_func7_north);
			//设置北部面板背景色

			panel_north.setBackground(new Color(22, 154, 228));

			//南部面板
			panel_south = new JPanel();
			panel_south.setBounds(0, 370, 446, 170);
			panel_south.setLayout(null);
			//添加南部面板
			root.add(panel_south);

			//功能按钮1
			btn_func1_south = new JButton(new ImageIcon("image/dialogimage/word.png"));
			btn_func1_south.setBounds(10, 0, 30, 30);
			panel_south.add(btn_func1_south);
			//功能按钮2
			btn_func2_south = new JButton(new ImageIcon("image/dialogimage/biaoqing.png"));
			btn_func2_south.setBounds(47, 0, 30, 30);
			panel_south.add(btn_func2_south);
			//功能按钮3
			btn_func3_south = new JButton(new ImageIcon("image/dialogimage/magic.jpg"));
			btn_func3_south.setBounds(84, 0, 30, 30);

			panel_south.add(btn_func3_south);
			//功能按钮4
			btn_func4_south = new JButton(new ImageIcon("image/dialogimage/zhendong.jpg"));
			btn_func4_south.setBounds(121, 0, 30, 30);
			panel_south.add(btn_func4_south);
			//功能按钮5
			btn_func5_south = new JButton(new ImageIcon("image/dialogimage/yymessage.jpg"));
			btn_func5_south.setBounds(158, 0, 30, 30);
			panel_south.add(btn_func5_south);
			//功能按钮6
			btn_func6_south = new JButton(new ImageIcon("image/dialogimage/dgninput.jpg"));
			btn_func6_south.setBounds(195, 0,30, 30);
			panel_south.add(btn_func6_south);
			
			//功能按钮7
			btn_func7_south = new JButton(new ImageIcon("image/dialogimage/sendimage.jpg"));
			btn_func7_south.setBounds(232, 0, 30, 30);
			btn_func7_south.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			panel_south.add(btn_func7_south);

			//查询聊天记录
			recorde_search = new JButton(new ImageIcon("image/dialogimage/recorde.png"));
			recorde_search.addActionListener(e-> {
				System.out.println("点击查找聊天记录");
				cardLayout.next(panel_east);
			});
			recorde_search.setBounds(350, 0, 96, 30);
			panel_south.add(recorde_search);
			//消息关闭按钮
			btn_close = new JButton(new ImageIcon("image/dialogimage/close.jpg"));
			btn_close.setBounds(210, 145, 64, 24);

			panel_south.add(btn_close);

			//消息发送按钮
			send = new JButton(new ImageIcon("image/dialogimage/send.jpg"));
			send.setBounds(370, 145, 64, 24);

			panel_south.add(send);

			//消息发送文件按钮
			send1 = new JButton(new ImageIcon("image/dialogimage/sendfile.jpg"));
			send1.setBounds(290, 145, 64, 24);
			panel_south.add(send1);

			//东部面板(图片和聊天记录)
			panel_east = new JPanel();
			//卡片布局
			cardLayout = new CardLayout(2,2);
			panel_east.setLayout(cardLayout);
			panel_east.setBounds(444, 91, 285, 418);
			//添加东部面板
			root.add(panel_east);

			//显示聊天记录面板
			JTextArea recode = new JTextArea();
			recode.setText("-----------------------------聊天记录--------------------------\n\n");
			JScrollPane scrollPane_Record = new JScrollPane(recode);
			scrollPane_Record.setBounds(2, 2, 411, 410);
			//添加到东部面板
			panel_east.add(label1); // Git图片
			panel_east.add(scrollPane_Record);


			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//鼠标释放
					isDragged = false;
					//光标恢复
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//鼠标按下
					//获取鼠标相对窗体位置
					frameLocation = new Point(e.getX(),e.getY());
					isDragged = true;
					//光标改为移动形式
					if(e.getY() < 92)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});
			//注册鼠标事件监听器
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//指定范围内点击鼠标可拖拽
					if(e.getY() < 92){
						//如果是鼠标拖拽移动
						if(isDragged) {
							Point loc = new Point(getLocation().x+e.getX()-frameLocation.x,
									getLocation().y+e.getY()-frameLocation.y);
							//保证鼠标相对窗体位置不变,实现拖动
							setLocation(loc);
						}
					}
				}
			});

			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//修改窗体默认图标
			this.setSize(728, 553);//设置窗体大小
			this.setUndecorated(true);//去掉自带装饰框
			this.setVisible(true);//设置窗体可见
			this.setLocationRelativeTo(null);



			//中部聊天内容显示部分
			JTextArea text1 = new JTextArea();
			JScrollPane scrollPane_Msg = new JScrollPane(text1);
			scrollPane_Msg.setBounds(0, 92, 446, 270);
			root.add(scrollPane_Msg);
			JTextField text = new JTextField();
			text.setBounds(0, 35, 446, 105);

	        root.setVisible(true);
	        this.setContentPane(root);
	        this.setLayout(null);

			panel_south.add(text);

	        text.addKeyListener(this);
			putmap(name,text1);
			rea.message.setFlag(2);
			rea.message.setSenduser(selfname);
			rea.message.setReceiveuser(name);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					chatopen.remove(name);//当聊天框关闭从集合移除
				}
			});

            send.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
					sendmessage(rea,text,text1,name);
				}
			});

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					chatopen.remove(name);
				}
			});

			text.addKeyListener(new KeyListener() {//回车响应
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						sendmessage(rea,text,text1,name);
					}
				}
				public void keyReleased(KeyEvent e) {
				}
				public void keyTyped(KeyEvent e) {
				}
			});

			//发送文件
			send1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//选择文件
					choosefile ch=new choosefile();
					File file=null;
					file=ch.choose();
					if(file==null)
						return;
					String filename=file.getName();
					//选择完文件，然后发送Message过去服务端
					Message receive=new Message(4,selfname,name,filename,file,file.length());
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					ObjectOutputStream oout = null;
					try {
						oout = new ObjectOutputStream(bout);
						oout.writeObject(receive);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //转化为字节数组
						DatagramPacket dp=new DatagramPacket(sendBuff,sendBuff.length,ip,serverport);
						s.send(dp);
					} catch (IOException ex) {
						ex.printStackTrace();
					}


				}
			});
		}
		public void sendmessage(receive rea, JTextField text, JTextArea text1,String name){
			try {
				if(!text.getText().equals(""))
				{
					rea.message.setReceiveuser(name);
					Message mes= rea.re();
					//设置发送方，接收方
					mes.setCharmessage(text.getText());
					//发送信息
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					ObjectOutputStream oout = null;
					oout = new ObjectOutputStream(bout);
					oout.writeObject(mes);
					oout.flush();
					byte[] sendBuff=bout.toByteArray();       //转化为字节数组
					DatagramPacket dp=new DatagramPacket(sendBuff,sendBuff.length,ip,serverport);
					s.send(dp);
					mes.setSendTime(df.format(new Date()));
					String time = mes.getSendTime();
					text1.append(time+":" + selfname+"(你自己)说："+text.getText()+"\r\n");
					recode.append(time+":" + selfname+"(你自己)说："+text.getText()+"\r\n");
					text.setText("");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}


}
class receive implements Runnable{
	private JTextArea a;
	private JTextField b;
	private DatagramSocket s;
	public Message message;
	private  int port;
	private int fileport;
	private InetAddress serverip;
	public receive(DatagramSocket s,Message message,int port,InetAddress serverip,int fileport){//客户端自己的socket，服务端的socket，服务端的ip地址
		this.s=s;
		this.message=message;
		this.port=port;
		this.serverip=serverip;
		this.fileport=fileport;
	}

	public Message re(){
		return message;
	}

	public void run() {
		try {
			byte []buf=new byte[1024*8];
			DatagramPacket dp = new DatagramPacket(buf,buf.length);

			while (true) {
				s.receive(dp);
				ByteArrayInputStream bint=new ByteArrayInputStream(buf);
				ObjectInputStream oint=new ObjectInputStream(bint);
				Message save=(Message) oint.readObject();       //反序列化，恢复对象

				if (save.getFlag() == 1) {//更新好友列表
					clientgui.createuserlist(save);
				} else if (save.getFlag() == 2) {//接受信息
					a=clientgui.map.get(save.getSenduser());
					a.append(save.getCharmessage());
				}else if(save.getFlag()==3){
					s.close();
					break;
				}
				else if(save.getFlag()==4){
					try {
						fliedeverty senta=new fliedeverty();
						DatagramSocket filesocket=new DatagramSocket();

						senta.sentfile(save.getFile(),filesocket,fileport,serverip);
						a=clientgui.map.get(save.getReceiveuser());
						a.append(save.getSenduser()+"(你自己)发送了文件:"+save.getFile().getName()+"\r\n");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else if(save.getFlag()==5){
					long len=save.getFileleng();
					a=clientgui.map.get(save.getSenduser());
					a.append(save.getSenduser()+"发来了文件："+save.getFilenmane()+"\r\n");
					int n = JOptionPane.showConfirmDialog(null, save.getSenduser()+"发来"+save.getFilenmane()+"是否接受？", null,JOptionPane.YES_NO_OPTION);
					if(n==1){
						String str = "拒绝接受";
						dp = new DatagramPacket(str.getBytes(), str.getBytes().length, dp.getAddress(), dp.getPort());
						s.send(dp);
					}else{
						//获得文件夹
						choosefile ch=new choosefile();
						File file=ch.choosedir();//选择文件夹
						String filepath="";
						filepath=file.getAbsolutePath();//获得路径
						if(filepath.equals(""))
							save.setResult("拒绝接受");
						else
							save.setResult("同意接受");

						ByteArrayOutputStream bout=new ByteArrayOutputStream();
						ObjectOutputStream oout = null;
						oout = new ObjectOutputStream(bout);
						oout.writeObject(save);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //转化为字节数组
						dp=new DatagramPacket(sendBuff,sendBuff.length,dp.getAddress(),dp.getPort());
						s.send(dp);
						if(save.getResult().equals("同意接受")){
							fliedeverty recei=new fliedeverty();
							recei.receivefile(filepath,s,save,len);
						}
					}
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}