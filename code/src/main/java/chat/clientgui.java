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

		private JPanel panel_north;//�����������
		private JLabel jbl_touxiang;//ͷ��
		private JLabel jbl_friendname;//��������

		private JButton send;
		private JButton send1;


		//ͷ���·�7�����ܰ�ť��δʵ�֣�
		private JButton btn_func1_north, btn_func2_north, btn_func3_north, btn_func4_north, btn_func5_north, btn_func6_north, btn_func7_north;
		//����������ʾ���
		private JTextPane panel_Msg;

		private JPanel panel_south;//�ϲ��������
		private JTextPane jtp_input;//��Ϣ������
		//��Ϣ�������Ϸ�9�����ܰ�ť(δʵ��)
		private JButton btn_func1_south, btn_func2_south, btn_func3_south,btn_func4_south, btn_func5_south, btn_func6_south, btn_func7_south, btn_func8_south, btn_func9_south;
		private JButton recorde_search;//�鿴��Ϣ��¼��ť
		private JButton btn_send, btn_close;//��Ϣ�������·��رպͷ��Ͱ�ť
		private JTextArea recode;
		private JPanel panel_east;//�������
		private CardLayout cardLayout;//��Ƭ����
		//Ĭ�϶��������ʾһ��ͼ,�����ѯ�����¼��ť�л��������¼���
		private final JLabel label1 = new JLabel(new ImageIcon("image/dialogimage/hh.gif"));
		private JTextPane panel_Record;//�����¼��ʾ���

		private boolean isDragged = false;//�����ק���ڱ�־
		private Point frameLocation;//��¼�����λ��
		private String myId;//�����˺�
		private String myName;
		private String friendId;//�����˺�
		private DateFormat df;//���ڽ���


		private Point tmp,loc;//��¼�б�λ��
		private static final long serialVersionUID = 1L;
//		boolean isDragged = false;//��¼����Ƿ�����ק�ƶ�
		private Point frame_temp;//��굱ǰ��Դ����λ������
		private Point frame_loc;//�����λ������
		public static DatagramSocket  s;//�ͻ��˶˿ں�
		public static int serverport;//����˶˿ں�
		public static int fileport;//����˶��ļ����ں�
		private JLabel jlb_north;//��������ͼƬ��ǩ
		private JLabel jlb_photo; // ͷ��
		private JButton btn_min; // ��С��
		private JButton btn_exit; // �˳�
		private JLabel jlb_code; // ��ά��
		private JLabel after_qqPwd; // ��������
		private JCheckBox remPwd; // ��ס����
		private JCheckBox autoLog; // �Զ���¼

		private JLabel aga_s;
		private JPasswordField aga_s_F;
		public  static JTextField text2;
		public static JPasswordField  text3;
		public  static JTextField text4;
		public  static JPasswordField text5;
		public static JButton send2;
		public static JButton send3; //ע��
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
		public static JList<String> jlist;//�����б�
		public static String selfname;//�Լ�������
		public receive rea;//һ���ͻ���һ��������߳�
		public static HashMap<String,JTextArea> map;//���ڶ��߳�
		public static HashSet<String> chatopen;//�����
		public static InetAddress ip; //��������ip��ַ


		public static void main(String[] args) {
			try {
				ip=InetAddress.getLocalHost();
				serverport=13000;
				fileport=13001;
				client=null;
				map=new HashMap<>();
				frame = new clientgui();//�򿪵�½����
//				frame.setTitle("��¼����");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public  void putmap(String a,JTextArea b){
		map.put(a,b);
	}
		public clientgui() {//��½����

			Container root = this.getContentPane();

			//���ò���
			//�������Ͻ���С���͹رհ�ť
			btn_min = new JButton(new ImageIcon("image/login/min.jpg"));
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ע�������,���ʵ�ִ�����С��
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			btn_min.setBounds(373, 0, 28, 29);
			root.add(btn_min);

			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ע�������,���ʵ�ִ��ڹر�
					System.exit(0);
				}
			});
			btn_exit.setBounds(401, 0, 28, 29);
			root.add(btn_exit);

			// ����
			jlb_north = new JLabel(new ImageIcon("image/login/login.jpg"));
			jlb_north.setBounds(0,0,430,182);
			root.add(jlb_north);

			//�����в��˺������������ͼƬ��ǩ
			jlb_photo = new JLabel(new ImageIcon("image/login/cw.png"));
			jlb_photo.setBounds(20, 192, 82, 78);
			root.add(jlb_photo);
	        l1=new JLabel("�˺�:");
	        l2=new JLabel("����:");
	        send2=new JButton(new ImageIcon("image/login/loginbutton.png"));

	        send3=new JButton(new ImageIcon("image/login/reg_login.png"));
	        text2=new JTextField();
	        text3=new JPasswordField ();
	        l1.setBounds(120, 195, 40, 25);
	        l2.setBounds(120, 240, 40, 25);

	        text2.setBounds(155,195,194,30);//�˺�
	        text3.setBounds(155,240,194,30);//����
	        send2.setBounds(155,299,194,30);//��¼
	        send3.setBounds(30, 285, 62, 25);//ע��
	        root.add(l1);
	        root.add(l2);	        
	        root.add(text2);
	        root.add(text3);
	        root.add(send2);
	        root.add(send3);

			// �����ά��
			jlb_code = new JLabel(new ImageIcon("image/login/code.png"));
			jlb_code.setBounds(380,299,40,40);
			root.add(jlb_code);

			//���������������"��������"
			after_qqPwd = new JLabel("��������");
			after_qqPwd.setForeground(Color.GRAY);
			after_qqPwd.setBounds(360,240,78,30);
			root.add(after_qqPwd);
			//����"��ס����"��ѡ��
			remPwd = new JCheckBox("��ס����");
			remPwd.setBounds(155,277,85,15);
			root.add(remPwd);
			//����"�Զ���¼"��ѡ��
			autoLog = new JCheckBox("�Զ���¼");
			autoLog.setBounds(270,277,85,15);
			root.add(autoLog);

			//ע������¼�������
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//����ͷ�
					isDragged = false;
					//���ָ�
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//��갴��
					//��ȡ�����Դ���λ��
					frame_temp = new Point(e.getX(),e.getY());
					isDragged = true;
					//���ı�Ϊ�ƶ���ʽ
					if(e.getY() < 182)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});


			//ע������¼�������
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//ָ����Χ�ڵ��������ק
					if(e.getY() < 182){
						//����������ק�ƶ�
						if(isDragged) {
							frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
									getLocation().y+e.getY()-frame_temp.y);
							//��֤�����Դ���λ�ò���,ʵ���϶�
							setLocation(frame_loc);
						}
					}
				}
			});

			root.setLayout(null);
			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//�޸Ĵ���Ĭ��ͼ��
			this.setSize(430,345);//���ô����С
			this.setLocation(600,300);
			this.setUndecorated(true);//ȥ���Դ�װ�ο�
			this.setVisible(true);//���ô���ɼ�
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
			text3.addKeyListener(new KeyListener() {//�س���Ӧ
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
				//������Ϣ
				oout = new ObjectOutputStream(bout);
				oout.writeObject(mess);
				oout.flush();
				byte[] Buff=bout.toByteArray();       //ת��Ϊ�ֽ�����
				DatagramPacket dPacket=new DatagramPacket(Buff,Buff.length,ip,serverport);
				s=new DatagramSocket();
				s.send(dPacket);
				//�ȴ���Ϣ����,һֱ��������
				byte []rebuf=new byte[1024*8];
				DatagramPacket recePacket=new DatagramPacket(rebuf,rebuf.length);
				s.receive(recePacket);

				String message=new String(recePacket.getData(),0,recePacket.getLength());
				JOptionPane.showMessageDialog(null, message,"", JOptionPane.INFORMATION_MESSAGE);//��ʾ��Ϣ����½�ɹ�&ʧ��
				if(message.equals("��½�ɹ�")){
					frame.dispose();//���ٵ�¼����
					//�򿪺����б�
					clientgui client=new clientgui(s);
					client.setTitle(selfname+"�б�");
					client.setVisible(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public clientgui(DatagramSocket s){//�����б�

			Container root = this.getContentPane();

			//���ô����С
			this.setSize(274,600);
			//���ò���
			root.setLayout(null);
			//���Ͻ���С����ť
			JButton btn_min = new JButton(new ImageIcon("image/friendlist/fmin.png"));
			btn_min.setBounds(217, 0, 28, 28);
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//������С��
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			root.add(btn_min);
			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));

			//���Ͻ��˳���ť
			JButton btn_exit = new JButton(new ImageIcon("image/friendlist/fexit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ע�������,���ʵ�ִ��ڹر�
					System.exit(0);
				}
			});

			root.add(btn_exit);
			//���Ͻ�qq��ǩ
			JLabel jbl_leftTop = new JLabel(new ImageIcon("image/friendlist/biaoti.png"));
			jbl_leftTop.setBounds(0, 0, 44, 21);
			root.add(jbl_leftTop);
			//qqͷ��
			JLabel jbl_photo = new JLabel(new ImageIcon("image/login/cw.png"));
			jbl_photo.setBounds(10, 23, 78, 78);
			root.add(jbl_photo);
			//qq�ǳ�
			JLabel jbl_qqName = new JLabel(selfname+"("+rea+")");
			jbl_qqName.setBounds(109, 32, 68, 21);
			root.add(jbl_qqName);

			//����ǩ��
			Font font = new Font("����", Font.BOLD, 10);
			JTextField jtf_personalSign1 = new JTextField();
			jtf_personalSign1.setText("Where there is a will, there is a way!");
			jtf_personalSign1.setFont(font);
//			jtf_personalSign1.setForeground(new Color());
			jtf_personalSign1.setBounds(45, 105, 217, 23);
			root.add(jtf_personalSign1);

			//����״̬ѡ���б�
			String[] status = {"����","����","����"};
			JComboBox<String> online_status = new JComboBox<>(status);
			online_status.setSelectedIndex(0);
			online_status.setBounds(195, 32, 63, 21);
			root.add(online_status);

			//������
			JTextField jtf_search = new JTextField("search");
			jtf_search.setBounds(0, 134, 247, 23);
			root.add(jtf_search);

			//������ť
			JButton btn_search = new JButton(new ImageIcon("image/friendlist/search.png"));
			btn_search.setBounds(247, 134, 30, 23);
			root.add(btn_search);

			//�ϰ벿�ֱ���ͼ
			JLabel jbl_background = new JLabel(new ImageIcon("image/friendlist/beijing.png"));
			jbl_background.setBounds(0, 0, 274, 156);
			root.add(jbl_background);

			// �����б�ͼƬ
			JLabel qun_ground = new JLabel(new ImageIcon("image/friendlist/qun.png"));
			qun_ground.setBounds(0, 157, 274, 59);
			root.add(qun_ground);

			// ˽�İ�ťͼƬ
			friendchoose=new JButton(new ImageIcon("image/friendlist/chat.png"));
			jlist=new JList<String>(new DefaultListModel<String>());//���ڸ����б�
			friendchoose.setBounds(150,540,95,31);//ѡ��ť˽��
			jlist.setBounds(20,220,220,315);//�����б�

			// �ײ�8����ť
			JButton btn_l1 = new JButton(new ImageIcon("image/friendlist/mainmenue.png"));
			btn_l1.setBounds(0, 577, 30, 23);
			root.add(btn_l1);

			//��ť2
			JButton btn_l2 = new JButton(new ImageIcon("image/friendlist/shezhi.png"));
			btn_l2.setBounds(30, 577, 30, 23);
			root.add(btn_l2);
			//��ť3
			JButton btn_l3 = new JButton(new ImageIcon("image/friendlist/messagemanage.png"));
			btn_l3.setBounds(60, 577, 30, 23);
			root.add(btn_l3);
			//��ť4
			JButton btn_l4 = new JButton(new ImageIcon("image/friendlist/filehleper.png"));
			btn_l4.setBounds(90, 577, 30, 23);
			root.add(btn_l4);
			//��ť5
			JButton btn_l5 = new JButton(new ImageIcon("image/friendlist/shoucang.png"));
			btn_l5.setBounds(120, 577, 30, 23);
			root.add(btn_l5);
			//��ť6
			JButton btn_l6 = new JButton(new ImageIcon("image/friendlist/tubiao8.png"));
			btn_l6.setBounds(150, 577, 30, 23);
			root.add(btn_l6);
			//��ť7
			JButton btn_l7 = new JButton(new ImageIcon("image/friendlist/tubiao9.png"));
			btn_l7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btn_l7.setBounds(180, 577, 30, 23);
			root.add(btn_l7);
			//��ť8
			JButton btn_l8 = new JButton(new ImageIcon("image/friendlist/apl.png"));
			btn_l8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btn_l8.setBounds(210, 577, 64, 23);
			root.add(btn_l8);
			//�����������ѵ�¼���û��б�
			Thread u=null;
			try {
				//���̼߳�ظ�����Ϣ
				byte[] buf=new byte[1024*8];
				DatagramPacket dp=new DatagramPacket(buf,buf.length);
				s.receive(dp);//������Ϣ���������������

				// �����л�������Ϣ����
				ByteArrayInputStream bint=new ByteArrayInputStream(buf);
				ObjectInputStream oint=new ObjectInputStream(bint);
				Message message=(Message)oint.readObject();       //�����л����ָ�����
				createuserlist(message);


				//ר�Ž����б���Ϣ ��дһ��������б���ϢҲ���ǵ�¼�û�
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
					//�ر�socket����֪ͨ������
					try {
						Message clse=new Message(3);
						clse.setSenduser(getTitle().substring(0,getTitle().length()-2));
						ByteArrayOutputStream bout=new ByteArrayOutputStream();
						ObjectOutputStream oout = null;
						oout = new ObjectOutputStream(bout);
						oout.writeObject(clse);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
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
								JOptionPane.showMessageDialog(null, "��ѡ�����","", JOptionPane.INFORMATION_MESSAGE);
							else if(chatopen.contains(mes))
								JOptionPane.showMessageDialog(null, "���Ѵ򿪸öԻ���","", JOptionPane.INFORMATION_MESSAGE);
							else{
								chatopen.add(mes);
								clientgui jt=new clientgui(mes,rea);
								jt.setTitle(selfname+"(��)��"+mes);
								jt.setVisible(true);
							}
						}
					}
				}
			});


//			���˽��֮ǰ��Ҫ����û�
			friendchoose.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String mes=(String)jlist.getSelectedValue();
					if(mes==null)
						JOptionPane.showMessageDialog(null, "��ѡ�����","", JOptionPane.INFORMATION_MESSAGE);
					else if(chatopen.contains(mes))
						JOptionPane.showMessageDialog(null, "���Ѵ򿪸öԻ���","", JOptionPane.INFORMATION_MESSAGE);
					else{
						chatopen.add(mes);
						clientgui jt=new clientgui(mes,rea);
						jt.setTitle(selfname+"(��)��"+mes);
						jt.setVisible(true);
					}
				}
			});


			//ȥ���䶨��װ�ο�
			this.setUndecorated(true);
			this.setLocationRelativeTo(null);
			//���ô���ɼ�
			this.setVisible(true);


			//����������¼�
			this.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					isDragged = false;
					//��ק����ͼ��ָ�
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//�޶���Χ�ڿ���ק
					if(e.getY()<30)
					{
						//��ȡ��갴��λ��
						tmp = new Point(e.getX(), e.getY());
						isDragged = true;
						//��קʱ�������ͼ��
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
					}
				}
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (isDragged) {
						//��������봰�����λ�ò���
						loc = new Point(getLocation().x + e.getX() - tmp.x,
								getLocation().y + e.getY() - tmp.y);
						setLocation(loc);
					}
				}

			});

		}

		//��JList�ؼ������б� �����û��б�
		public static void createuserlist(Message message){
			String []liststr=new String[message.getList().size()];
			message.getList().toArray(liststr);
			jlist.setListData(liststr);
		}
		public clientgui(int flag1) {//ע�����


			Container root = this.getContentPane();
	        this.setBounds(0, 0, 430, 500);
			this.setLocation(700,400);
	        this.setContentPane(root);
	        this.setLayout(null);
	        this.setName("ע���û�");

			//����������ͼƬ��ǩ
			jlb_north = new JLabel(new ImageIcon("image/login/login.jpg"));
			jlb_north.setBounds(0,0,430,182);
			root.add(jlb_north);

			//�������Ͻ���С���͹رհ�ť
			btn_min = new JButton(new ImageIcon("image/login/min.jpg"));
			btn_min.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ע�������,���ʵ�ִ�����С��
					setExtendedState(JFrame.ICONIFIED);
				}
			});
			btn_min.setBounds(373, 0, 28, 29);
			root.add(btn_min);

			btn_exit = new JButton(new ImageIcon("image/login/exit.png"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ע�������,���ʵ�ִ��ڹر�
					System.exit(0);
				}
			});
			btn_exit.setBounds(401, 0, 28, 29);
			root.add(btn_exit);

			JCheckBox jch = new JCheckBox("�����Ķ���ͬ����ط�������");
			jch.setBounds(120,370,200,30);
			root.add(jch);

			l3=new JLabel("�˺�:");
	        l4=new JLabel("����:");
			aga_s = new JLabel("�ٴ��������룺");
	        send4=new JButton(new ImageIcon("image/login/register.png"));
	        text4=new JTextField();
	        text5=new JPasswordField ();
	        aga_s_F=new JPasswordField ();
	        l3.setBounds(80,240,50,30); //�˺�
	        l4.setBounds(80,285,50,30); //����
			aga_s.setBounds(30,330,100,30);
	        text4.setBounds(120, 240, 194, 30);//�˺�
	        text5.setBounds(120,285,194,30);//����
			aga_s.setBounds(30,330,100,30); //�ٴ��������� label
			aga_s_F.setBounds(120,330,194,30); //�ٴ��������� �����
	        send4.setBounds(170,410,90,33);//ע��
	        root.add(l3);
	        root.add(l4);	        
	        root.add(text4);
	        root.add(text5);
	        root.add(send4);
			root.add(aga_s);
			root.add(aga_s_F);

			//ע������¼�������
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//����ͷ�
					isDragged = false;
					//���ָ�
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//��갴��
					//��ȡ�����Դ���λ��
					frame_temp = new Point(e.getX(),e.getY());
					isDragged = true;
					//���ı�Ϊ�ƶ���ʽ
					if(e.getY() < 182)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});

			//ע������¼�������
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//ָ����Χ�ڵ��������ק
					if(e.getY() < 182){
						//����������ק�ƶ�
						if(isDragged) {
							frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
									getLocation().y+e.getY()-frame_temp.y);
							//��֤�����Դ���λ�ò���,ʵ���϶�
							setLocation(frame_loc);
						}
					}
				}
			});

			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//�޸Ĵ���Ĭ��ͼ��
			this.setUndecorated(true);//ȥ���Դ�װ�ο�
			this.setVisible(true);//���ô���ɼ�


            send4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	String username=text4.getText();
                	String mima= String.valueOf(text5.getPassword());
                	if(username.equals(""))
                	{
                		JOptionPane.showMessageDialog(null, "�˺�Ϊ��","", JOptionPane.INFORMATION_MESSAGE);
                	}
					else if(mima.equals("")){
						JOptionPane.showMessageDialog(null, "����Ϊ��","", JOptionPane.INFORMATION_MESSAGE);
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
							// ������תΪ�ֽ�֮��ͨ�� dp ���ͣ������ֽ�֮�����л��ɶ���
							ByteArrayOutputStream bout=new ByteArrayOutputStream();
							ObjectOutputStream oout = null;
							oout = new ObjectOutputStream(bout);
							oout.writeObject(mess);
							oout.flush();
							byte[] buff=bout.toByteArray();       //ת��Ϊ�ֽ�����
							DatagramPacket dp=new DatagramPacket(buff,buff.length,ip,serverport);
							s=new DatagramSocket();
							s.send(dp);
							//�ȴ���Ϣ����
							byte[] rebuf=new byte[8*1024];
							dp=new DatagramPacket(rebuf,rebuf.length);
							s.receive(dp);
							String message=new String(dp.getData(),0,dp.getLength());
							JOptionPane.showMessageDialog(null, message,"", JOptionPane.INFORMATION_MESSAGE);
							if(message.equals("ע��ɹ�"))
								client3.dispose();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						//����ע�ᴰ��
						client3.dispose();
					}
                }
			});
		}
		public clientgui(String name,receive rea) {//�����


			//��ȡ��������
			Container root = this.getContentPane();
			//���ò���
			this.setContentPane(root);
			root.setLayout(null);
			df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
			//�������
			panel_north = new JPanel();
			panel_north.setBounds(0, 0, 729, 92);
			panel_north.setLayout(null);
			//��ӱ������
			root.add(panel_north);
			//���Ͻǻ�ɫͷ��
			jbl_touxiang = new JLabel(new ImageIcon("image/dialogimage/lin.png"));
			jbl_touxiang.setBounds(10, 10, 42, 42);
			panel_north.add(jbl_touxiang);
			//ͷ���ҷ���������ĶԷ�����
			jbl_friendname = new JLabel(selfname+"("+friendId+")");
			jbl_friendname.setBounds(62, 21, 105, 20);
			panel_north.add(jbl_friendname);

			//���Ͻ���С����ť
			btn_min = new JButton(new ImageIcon ("image/dialogimage/min.png"));
			btn_min.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));
			btn_min.setBounds(668, 0, 30, 30);
			panel_north.add(btn_min);

			//���Ͻǹرհ�ť
			btn_exit = new JButton(new ImageIcon ("image/dialogimage/exit.jpg"));
			btn_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btn_exit.setBounds(698, 0, 30, 30);
			panel_north.add(btn_exit);

			//ͷ���·����ܰ�ť

			//���ܰ�ť1
			btn_func1_north = new JButton(new ImageIcon("image/dialogimage/yuyin.png"));
			btn_func1_north.setBounds(10, 62, 36, 30);
			panel_north.add(btn_func1_north);
			//���ܰ�ť2
			btn_func2_north = new JButton(new ImageIcon("image/dialogimage/shipin.png"));
			btn_func2_north.setBounds(61, 62, 36, 30);
			panel_north.add(btn_func2_north);
			//���ܰ�ť3
			btn_func3_north = new JButton(new ImageIcon("image/dialogimage/tranfile.jpg"));
			btn_func3_north.setBounds(112, 62, 36, 30);
			panel_north.add(btn_func3_north);
			//���ܰ�ť4
			btn_func4_north = new JButton(new ImageIcon("image/dialogimage/createteam.jpg"));
			btn_func4_north.setBounds(163, 62, 36, 30);
			panel_north.add(btn_func4_north);
			//���ܰ�ť5
			btn_func5_north = new JButton(new ImageIcon("image/dialogimage/yuancheng.png"));
			btn_func5_north.setBounds(214, 62, 36, 30);
			panel_north.add(btn_func5_north);
			//���ܰ�ť6
			btn_func6_north = new JButton(new ImageIcon("image/dialogimage/sharedisplay.png"));
			btn_func6_north.setBounds(265, 62, 36, 30);
			panel_north.add(btn_func6_north);

			//���ܰ�ť7
			btn_func7_north = new JButton(new ImageIcon("image/dialogimage/yingyong.jpg"));
			btn_func7_north.setBounds(318, 62, 36, 30);
			btn_func7_north.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			panel_north.add(btn_func7_north);
			//���ñ�����屳��ɫ

			panel_north.setBackground(new Color(22, 154, 228));

			//�ϲ����
			panel_south = new JPanel();
			panel_south.setBounds(0, 370, 446, 170);
			panel_south.setLayout(null);
			//����ϲ����
			root.add(panel_south);

			//���ܰ�ť1
			btn_func1_south = new JButton(new ImageIcon("image/dialogimage/word.png"));
			btn_func1_south.setBounds(10, 0, 30, 30);
			panel_south.add(btn_func1_south);
			//���ܰ�ť2
			btn_func2_south = new JButton(new ImageIcon("image/dialogimage/biaoqing.png"));
			btn_func2_south.setBounds(47, 0, 30, 30);
			panel_south.add(btn_func2_south);
			//���ܰ�ť3
			btn_func3_south = new JButton(new ImageIcon("image/dialogimage/magic.jpg"));
			btn_func3_south.setBounds(84, 0, 30, 30);

			panel_south.add(btn_func3_south);
			//���ܰ�ť4
			btn_func4_south = new JButton(new ImageIcon("image/dialogimage/zhendong.jpg"));
			btn_func4_south.setBounds(121, 0, 30, 30);
			panel_south.add(btn_func4_south);
			//���ܰ�ť5
			btn_func5_south = new JButton(new ImageIcon("image/dialogimage/yymessage.jpg"));
			btn_func5_south.setBounds(158, 0, 30, 30);
			panel_south.add(btn_func5_south);
			//���ܰ�ť6
			btn_func6_south = new JButton(new ImageIcon("image/dialogimage/dgninput.jpg"));
			btn_func6_south.setBounds(195, 0,30, 30);
			panel_south.add(btn_func6_south);
			
			//���ܰ�ť7
			btn_func7_south = new JButton(new ImageIcon("image/dialogimage/sendimage.jpg"));
			btn_func7_south.setBounds(232, 0, 30, 30);
			btn_func7_south.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			panel_south.add(btn_func7_south);

			//��ѯ�����¼
			recorde_search = new JButton(new ImageIcon("image/dialogimage/recorde.png"));
			recorde_search.addActionListener(e-> {
				System.out.println("������������¼");
				cardLayout.next(panel_east);
			});
			recorde_search.setBounds(350, 0, 96, 30);
			panel_south.add(recorde_search);
			//��Ϣ�رհ�ť
			btn_close = new JButton(new ImageIcon("image/dialogimage/close.jpg"));
			btn_close.setBounds(210, 145, 64, 24);

			panel_south.add(btn_close);

			//��Ϣ���Ͱ�ť
			send = new JButton(new ImageIcon("image/dialogimage/send.jpg"));
			send.setBounds(370, 145, 64, 24);

			panel_south.add(send);

			//��Ϣ�����ļ���ť
			send1 = new JButton(new ImageIcon("image/dialogimage/sendfile.jpg"));
			send1.setBounds(290, 145, 64, 24);
			panel_south.add(send1);

			//�������(ͼƬ�������¼)
			panel_east = new JPanel();
			//��Ƭ����
			cardLayout = new CardLayout(2,2);
			panel_east.setLayout(cardLayout);
			panel_east.setBounds(444, 91, 285, 418);
			//��Ӷ������
			root.add(panel_east);

			//��ʾ�����¼���
			JTextArea recode = new JTextArea();
			recode.setText("-----------------------------�����¼--------------------------\n\n");
			JScrollPane scrollPane_Record = new JScrollPane(recode);
			scrollPane_Record.setBounds(2, 2, 411, 410);
			//��ӵ��������
			panel_east.add(label1); // GitͼƬ
			panel_east.add(scrollPane_Record);


			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//����ͷ�
					isDragged = false;
					//���ָ�
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				@Override
				public void mousePressed(MouseEvent e) {
					//��갴��
					//��ȡ�����Դ���λ��
					frameLocation = new Point(e.getX(),e.getY());
					isDragged = true;
					//����Ϊ�ƶ���ʽ
					if(e.getY() < 92)
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			});
			//ע������¼�������
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//ָ����Χ�ڵ��������ק
					if(e.getY() < 92){
						//����������ק�ƶ�
						if(isDragged) {
							Point loc = new Point(getLocation().x+e.getX()-frameLocation.x,
									getLocation().y+e.getY()-frameLocation.y);
							//��֤�����Դ���λ�ò���,ʵ���϶�
							setLocation(loc);
						}
					}
				}
			});

			this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//�޸Ĵ���Ĭ��ͼ��
			this.setSize(728, 553);//���ô����С
			this.setUndecorated(true);//ȥ���Դ�װ�ο�
			this.setVisible(true);//���ô���ɼ�
			this.setLocationRelativeTo(null);



			//�в�����������ʾ����
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
					chatopen.remove(name);//�������رմӼ����Ƴ�
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

			text.addKeyListener(new KeyListener() {//�س���Ӧ
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

			//�����ļ�
			send1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//ѡ���ļ�
					choosefile ch=new choosefile();
					File file=null;
					file=ch.choose();
					if(file==null)
						return;
					String filename=file.getName();
					//ѡ�����ļ���Ȼ����Message��ȥ�����
					Message receive=new Message(4,selfname,name,filename,file,file.length());
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					ObjectOutputStream oout = null;
					try {
						oout = new ObjectOutputStream(bout);
						oout.writeObject(receive);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
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
					//���÷��ͷ������շ�
					mes.setCharmessage(text.getText());
					//������Ϣ
					ByteArrayOutputStream bout=new ByteArrayOutputStream();
					ObjectOutputStream oout = null;
					oout = new ObjectOutputStream(bout);
					oout.writeObject(mes);
					oout.flush();
					byte[] sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
					DatagramPacket dp=new DatagramPacket(sendBuff,sendBuff.length,ip,serverport);
					s.send(dp);
					mes.setSendTime(df.format(new Date()));
					String time = mes.getSendTime();
					text1.append(time+":" + selfname+"(���Լ�)˵��"+text.getText()+"\r\n");
					recode.append(time+":" + selfname+"(���Լ�)˵��"+text.getText()+"\r\n");
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
	public receive(DatagramSocket s,Message message,int port,InetAddress serverip,int fileport){//�ͻ����Լ���socket������˵�socket������˵�ip��ַ
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
				Message save=(Message) oint.readObject();       //�����л����ָ�����

				if (save.getFlag() == 1) {//���º����б�
					clientgui.createuserlist(save);
				} else if (save.getFlag() == 2) {//������Ϣ
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
						a.append(save.getSenduser()+"(���Լ�)�������ļ�:"+save.getFile().getName()+"\r\n");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else if(save.getFlag()==5){
					long len=save.getFileleng();
					a=clientgui.map.get(save.getSenduser());
					a.append(save.getSenduser()+"�������ļ���"+save.getFilenmane()+"\r\n");
					int n = JOptionPane.showConfirmDialog(null, save.getSenduser()+"����"+save.getFilenmane()+"�Ƿ���ܣ�", null,JOptionPane.YES_NO_OPTION);
					if(n==1){
						String str = "�ܾ�����";
						dp = new DatagramPacket(str.getBytes(), str.getBytes().length, dp.getAddress(), dp.getPort());
						s.send(dp);
					}else{
						//����ļ���
						choosefile ch=new choosefile();
						File file=ch.choosedir();//ѡ���ļ���
						String filepath="";
						filepath=file.getAbsolutePath();//���·��
						if(filepath.equals(""))
							save.setResult("�ܾ�����");
						else
							save.setResult("ͬ�����");

						ByteArrayOutputStream bout=new ByteArrayOutputStream();
						ObjectOutputStream oout = null;
						oout = new ObjectOutputStream(bout);
						oout.writeObject(save);
						oout.flush();
						byte[] sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
						dp=new DatagramPacket(sendBuff,sendBuff.length,dp.getAddress(),dp.getPort());
						s.send(dp);
						if(save.getResult().equals("ͬ�����")){
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