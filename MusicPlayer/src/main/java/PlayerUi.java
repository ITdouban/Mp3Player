import Stylechange.NewButton;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class PlayerUi {
    JPanel panel = new JPanel(new BorderLayout());
    public JPanel paneltop = new JPanel();
    public JPanel panelmainer = new JPanel();
    public JPanel panelfooter = new JPanel();
    public JPanel menu = new JPanel();
    public JPanel panelleft = new JPanel();
    public JScrollPane scrollmenu = null;
    JTable detailtable = null;
//            new JScrollPane( textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
    public JPanel panellefttop = new JPanel();
    public JPanel panelleftmiddle = new JPanel();
    public JPanel panelleftbottom = new JPanel(); //左边栏分为上下两块
    public JPanel othermenu = new JPanel();    //别人的清单
    public JPanel mymenu = new JPanel();       //我的歌单
    public JPanel playnow = new JPanel(); //展示现在播放状态的区域
    public JPanel contain = new JPanel();      //歌曲内容
    public JPanel containwrap = new JPanel();  //歌单内容页，与此相对应的是详细内容
    public JPanel containwrap2 = new JPanel();  //歌词内容页
    public JPanel containtop = new JPanel();
    public JPanel containbottom = new JPanel(); //控制区
    public JLabel mymenutitle = new JLabel("我的歌单");
    final JList mylist = new JList();   //我的歌单
    public JLabel othermenutitle = new JLabel("别人都在听");
    final JList otherlist = new JList(); //别人歌单
    Vector<String> otherv = new Vector<String>(); //别人的歌单
    Vector<String> myv = new Vector<String>(); //别my人的歌单
    public JPanel musicbuttons = new JPanel(); //所有的播放相关按键
    public JPanel pplaybutton = new JPanel();  //包裹播放按钮
//    private JPanel pstopbutton = new JPanel();  //包裹暂停按钮
    public JPanel plastbutton = new JPanel(); //包裹上一首按钮
    public JPanel pnextbutton = new JPanel(); //包裹下一首按钮
    public JPanel pstylebutton = new JPanel(); //包裹播放方式按钮
    public JButton playbutton = new NewButton("播放") ;//播放暂停按钮
    public JButton lastbutton = new NewButton("上"); //上一首按钮
    public JButton nextbutton = new NewButton("下"); //下一首按钮
    public JButton stylebutton = new JButton(); //播放方式切换按钮
    JPanel listcoverwrap = new JPanel(); //

    JPanel containtopright = new JPanel();
    JLabel covertitle = new JLabel("每日歌曲更新");
    JPanel playnowcover = new JPanel();
    CardLayout layout = null; //这是为了歌单以及内容切换展示而设置的一种卡片布局
    JButton changelist = new JButton("歌单"); //切换为展示清单
    JButton changecontent = new JButton("歌词"); //切换为展示内容
    public int currentProgress = 0; //当前音乐播放进度
    public int maxmusicprogress = 100; //准确来说放的是歌曲的长度
    public int minmusicprogress = 0;



    public static void main(String[] args){
        PlayerUi playerui = new PlayerUi();
        JFrame frame = new JFrame("一个美丽的音乐播放器");
        frame.setSize(1200,800);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





//        placeComponents(panel);

        playerui.initui(frame);
        frame.setVisible(true);
        System.out.println("xxx");
    }
    public void initui(JFrame frame){
        layout(frame);
        playerMymenu(panel);
        playerOthermenu();
        playerContain();
        playerFooter();
        playerNow();
        playercontaindetal();
    }
    //页面基本布局
    public void layout(JFrame frame){
        panel.setLayout(new BorderLayout(0,0));
        panel.setBackground(Color.BLUE);

        frame.add(panel);
        //以上构建了最大的container，一下添加内部组件或面板


//        panel.add(menu);
        //基础borderlayout划分
        paneltop.setPreferredSize(new Dimension(1200,65));
        paneltop.setBackground(new Color(198,47,47));
        panelmainer.setSize(1200,600);
//        panelmainer.setBackground(Color.green);
        panelleft.setPreferredSize(new Dimension(260,800));
        panelleft.setBackground(new Color(245,245,247));
        panelfooter.setPreferredSize(new Dimension(1200,65));
        panelfooter.setBackground(new Color(246,246,248));
        panel.add(panelfooter,BorderLayout.SOUTH);
        panel.add(panelleft,BorderLayout.WEST);
        panel.add(paneltop,BorderLayout.NORTH);
        panel.add(panelmainer,BorderLayout.CENTER);
//        menu.setSize(150,700);

        //panelleft再分为上下
//        panelleft.setLayout(null);
//
        panelleft.setLayout(new GridLayout(3,1));
;
        panelleft.add(panelleftmiddle);
        panelleft.add(panellefttop);
        panelleft.add(playnow);




        //左边区域的滚动条
        panellefttop.setLayout(new GridLayout(1,1));
        scrollmenu = new JScrollPane( mymenu, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        panellefttop.add(scrollmenu);
        panelleftmiddle.setLayout(new GridLayout(1,1));
        scrollmenu = new JScrollPane( othermenu, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        panelleftmiddle.add(scrollmenu);

        //右边列表页区域滚动条
//        panelmainer.add(containwrap);
//        panelmainer.setLayout(new GridLayout(1,1));
        containwrap.setLayout(new GridLayout(1,1));
        scrollmenu = new JScrollPane( contain, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        containwrap.add(scrollmenu);

        //右边区域一共有两层,一层是歌单，一层是具体内容页， 用卡片式布局实现
        layout = new CardLayout(0,0);
        panelmainer.setLayout(layout);

//        panelmainer.add(btn01, "btn01");
        panelmainer.add(containwrap, "list");
        panelmainer.add(containwrap2, "content"); // 先显示第二个
        layout.show(panelmainer, "content");

        //menu上下分隔
        menu.setLayout(new GridLayout(1,1));


        //设置左右分隔线
//        menu.setLayout(new BorderLayout(0,0));
//        contain.setLayout(new BorderLayout(0,0));
//        JSplitPane jsplitpane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,menu,contain);
//        jsplitpane1.setDividerLocation(300);
//        jsplitpane1.setDividerSize(1);
//        jsplitpane1.setEnabled(false);
//        frame.add(jSplitpane);

//        panelmainer.add(jsplitpane1);
        //设置上下分割线


//        JSplitPane jsplitpane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,panellefttop,panelleftbottom);
////        jsplitpane2.setDividerLocation(400);
//        jsplitpane3.setDividerSize(1);
//        jsplitpane3.setEnabled(false);
//        panelleft.add(jsplitpane3);
//        panellefttop.setBackground(Color.red);
//        panelleftbottom.setBackground(Color.green);
//        panellefttop.setPreferredSize(new Dimension(260,500));
//        panelleftbottom.setPreferredSize(new Dimension(260,100));
//        panelleft.setLayout(new GridLayout(1,1));


//        JSplitPane jsplitpane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,othermenu,mymenu);
////        jsplitpane2.setDividerLocation(400);
//        jsplitpane2.setDividerSize(1);
//        jsplitpane2.setEnabled(false);
//        menu.add(jsplitpane2);








    }
    //具体实现歌单内部
    public void playerMymenu(JPanel panel){
        mymenu.setBackground(new Color(246,246,248));
        for(int i = 0;i<100;i++)
        myv.add(new String("杂七杂八的民谣"));




        mylist.setListData(myv);

        Box box1 = Box.createHorizontalBox();
        box1.add(mymenutitle);
        box1.add(Box.createHorizontalGlue());
        Box box2 = Box.createHorizontalBox();
        box2.add(mylist);
//        box2.add(Box.createHorizontalGlue());
        Box vbox = Box.createVerticalBox();
        vbox.add(box1);
        vbox.add(box2);
        mymenu.add(vbox);


        mylist.setFixedCellWidth(400);
        mymenutitle.setFont(new Font("宋体",Font.BOLD,20));
        mymenu.setBackground(new Color(245,245,247));

        mylist.setLayout(new GridLayout(1,1));
        mymenu.setLayout(new GridLayout(1,1));
        mylist.setBackground(new Color(245,245,247));





//        JButton button = new JButton("歌单管理");
//        button.setLocation(180,300);
//        button.setSize(100,30);
//        mymenu.add(button);
//        Box box3 = Box.createHorizontalBox();
//        box3.add(button);
//        Box vBox = Box.createVerticalBox();
//        vBox.add(box2);
//        vBox.add(box3);

//        othermenu.add(vBox);


        //        list.




    }
    public void playerOthermenu(){
//        othermenu.setBackground(Color.white);

        otherv.add("学号1702003100x的歌单");
        otherv.add("学号1702003100x的歌单");
        otherv.add("学号1702003100x的歌单");
        otherv.add("学号1702003100x的歌单");
        otherlist.setListData(otherv);

        Box box1 = Box.createHorizontalBox();
        box1.add(othermenutitle);
        box1.add(Box.createHorizontalGlue());
        Box box2 = Box.createHorizontalBox();
        box2.add(otherlist);
//        box2.add(Box.createHorizontalGlue());
        Box vbox = Box.createVerticalBox();
        vbox.add(box1);
        vbox.add(box2);

        othermenu.add(vbox);


        othermenutitle.setFont(new Font("微软雅黑",Font.BOLD,16));
        otherlist.setBackground(new Color(245,245,247));
        othermenu.setBackground(new Color(245,245,247));
        othermenutitle.setForeground(new Color(97,97,97));


//        othermenu.setLayout(new FlowLayout(FlowLayout.LEFT));
//        otherlist.setLayout(new GridLayout(1,1));
//        othermenu.setLayout(new GridLayout(1,1));
        otherlist.setFixedCellWidth(400);

    }
    public void playerContain(){
        // 表头（列名）
        String[] columnNames = {"序号", "姓名", "语文", "数学", "英语", "总分"};
        // 表格所有行数据
        Object[][] rowData = {
                {1, "张三", 80, 80, 80, 240},
                {2, "John", 70, 80, 90, 240},
                {3, "Sue", 70, 70, 70, 210},
                {4, "Jane", 80, 70, 60, 210},
                {5, "Joe_05", 80, 70, 60, 210},
                {6, "Joe_06", 80, 70, 60, 210},
                {7, "Joe_07", 80, 70, 60, 210},
                {8, "Joe_08", 80, 70, 60, 210},
                {9, "Joe_09", 80, 70, 60, 210},
                {10, "Joe_10", 80, 70, 60, 210},
                {11, "Joe_11", 80, 70, 60, 210},
                {12, "Joe_12", 80, 70, 60, 210},
                {13, "Joe_13", 80, 70, 60, 210},
                {14, "Joe_14", 80, 70, 60, 210},
                {15, "Joe_15", 80, 70, 60, 210},
                {16, "Joe_16", 80, 70, 60, 210},
                {17, "Joe_17", 80, 70, 60, 210},
                {18, "Joe_18", 80, 70, 60, 210},
                {19, "Joe_19", 80, 70, 60, 210},
                {20, "Joe_20", 80, 70, 60, 210}
        };

        Box box1 = Box.createHorizontalBox();
        box1.add(containtop);
//        box1.add(Box.createHorizontalGlue());
        Box box2 = Box.createHorizontalBox();
        box2.add(containbottom);
//        box2.add(Box.createHorizontalGlue());
        Box vbox = Box.createVerticalBox();
        vbox.add(box1);
        vbox.add(box2);
        contain.add(vbox);

         detailtable = new JTable(rowData,columnNames);
        //设置表格内容颜色
        detailtable.setForeground(Color.BLACK);
//        detailtable.setBackground(new co);
        detailtable.setSelectionBackground(new Color(236,236,237));
//        detailtable

        //设置表头
//        detailtable.getTableHeader().setFocusTraversalKe;

        //设置行高
        detailtable.setRowHeight(30);
        containbottom.add(detailtable);



        contain.setBackground(new Color(249,246,247));
        contain.setLayout(new GridLayout(1,1));


        containtop.setSize(700,600);
        containtop.setBackground(new Color(248,244,245));
//        contain.add(containtop);
        containbottom.setSize(700,200);
        containbottom.setBackground(Color.green);
        containbottom.setLayout(new GridLayout(1,1));

//        containtop.setLayout();




        //这是歌单具体内容的上部分

        listcoverwrap.setPreferredSize(new Dimension(140,140));
        listcoverwrap.setBackground(Color.PINK);


        containtop.setLayout(new FlowLayout(FlowLayout.LEFT));

        containtop.add(listcoverwrap);
        containtop.add(covertitle);
        //
//        containwrap.add(contain);
    }
    public void playerFooter(){


        panelfooter.setBorder(BorderFactory.createLineBorder(new Color(225,225,226)));
//        musicbuttons.setLayout(new GridLayout(1,3));
        //按钮部分
//        musicbuttons.setLayout(new BorderLayout());
        musicbuttons.setLayout(null);
        musicbuttons.setLayout(new GridLayout(1,3));
        musicbuttons.setOpaque(false);

//        musicbuttons.L
//        panelfooter.setLayout(new GridLayout(1,4));
        panelfooter.setLayout(new BorderLayout());


        panelfooter.add(musicbuttons,BorderLayout.WEST);
        musicbuttons.add(plastbutton);
        musicbuttons.add(pplaybutton);
        musicbuttons.add(pnextbutton);


        plastbutton.setSize(42,42);
        plastbutton.add(lastbutton);
        plastbutton.setOpaque(false);
        lastbutton.setBackground(new Color(232,60,60));
        lastbutton.setFocusPainted(false);
        lastbutton.setBorder(BorderFactory.createLineBorder(new Color(232,60,60)));
//        panelfooter.add(plastbutton);

        pplaybutton.setSize(50,50);
        pplaybutton.setOpaque(false);
        pplaybutton.add(playbutton);
        playbutton.setBackground(new Color(232,60,60));
        playbutton.setFocusPainted(false);
//        panelfooter.add(playbutton);


        pnextbutton.setSize(50,50);
        pnextbutton.setOpaque(false);
        pnextbutton.add(nextbutton);
        nextbutton.setBackground(new Color(232,60,60));
        nextbutton.setFocusPainted(false);
//        panelfooter.add(nextbutton);

        final JProgressBar musicprogressbar = new JProgressBar();
        musicprogressbar.setMinimum(minmusicprogress);
        musicprogressbar.setMaximum(maxmusicprogress);
        musicprogressbar.setValue(10);
        musicprogressbar.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
              System.out.println("当前进度值："+musicprogressbar.getValue()+";"+"进度百分比:"+musicprogressbar.getPercentComplete());

            }
        });
        panelfooter.add(musicprogressbar,BorderLayout.CENTER);
        musicprogressbar.setStringPainted(true);

//        musicprogressbar.setBackground(Color.green);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask(){
//            public void run() {
//                currentProgress++;
//                if (currentProgress > maxmusicprogress) {
//                    currentProgress = minmusicprogress;
//                }
//                musicprogressbar.setValue(currentProgress);
//            }
//
//        },500);
        new Thread(){
            public void run(){
                for(int i = 0;i < 100; i++){
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    musicprogressbar.setValue(i);
                }

            }
        }.start();


        JPanel test = new JPanel();
        test.setPreferredSize(new Dimension(20,20));
        test.setBackground(Color.green);
        panelfooter.add(test,BorderLayout.EAST);






    }
    //这个是初始化现在播放情况的界面
    public void playerNow() {


        playnow.setLayout(new FlowLayout(FlowLayout.LEFT));

        playnowcover.setBackground(Color.PINK);
        playnowcover.setPreferredSize(new Dimension(80,80));
        playnow.add(playnowcover);
        playnow.setBackground(new Color(245,245,247));

        playnow.add(changecontent);
        playnow.add(changelist);
        changecontent.setFocusPainted(false);
        changelist.setFocusPainted(false);
        changecontent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                layout.show(panelmainer, "content");

            }
        });
        changelist.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                layout.show(panelmainer, "list");

            }
        });

//



    }

    //这个函数是用于歌词具体内容页
    public void playercontaindetal(){
//        contain.setPreferredSize(new Dimension(0,0));
        JPanel bigmusiccover = new JPanel();
        bigmusiccover.setPreferredSize(new Dimension(420,420));
        bigmusiccover.setBackground(Color.pink);
        containwrap2.add(bigmusiccover);




    }






}
