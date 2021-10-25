package minesweeper;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Minesweeper extends JFrame implements MouseListener, ActionListener {
    int yukseklik,genislik,mS,kordinat[][];
    int kalan_Mayin;
    int count =0;
    javax.swing.JFrame frame = new javax.swing.JFrame("Mayin Tarlasi");
    javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
    javax.swing.JButton jButton1 = new javax.swing.JButton();
    javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
    javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    javax.swing.JTextField jTextField2 = new javax.swing.JTextField();
    JPanel S = new JPanel();
    JPanel W = new JPanel();
    JPanel E = new JPanel();
    Container grid = new Container();
    JButton[][] dugmeler;
 
    public void mouseClicked(MouseEvent me) {
        try {
            if(SwingUtilities.isRightMouseButton(me)){
                for (int i = 0; i < yukseklik; i++) {
                    for (int j = 0; j < genislik; j++) {
                        if (me.getSource() == dugmeler[i][j]) {
                            if (dugmeler[i][j].isEnabled()) {
                                Image img = ImageIO.read(getClass().getResource("/kaynak/f1.png"));
                                kalan_Mayin--;
                                dugmeler[i][j].setEnabled(false);
                                dugmeler[i][j].setDisabledIcon(new ImageIcon(img));
                            }
                            else{
                            kalan_Mayin++;
                            Image img = ImageIO.read(getClass().getResource("/kaynak/b1.png"));
                            dugmeler[i][j].setEnabled(true);
                            dugmeler[i][j].setIcon(new ImageIcon(img));
                            }
                        }
                    }
                }
                jTextField1.setText(String.valueOf(kalan_Mayin));
            }
        } 
        catch (Exception e) {
        }
        if (tumMayinlerIsaretlendi()) {
            try {
                getKazan();
            } catch (Exception ex) {
                Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
                }
    }
    public void mousePressed(MouseEvent me) {
        try {
            
        } catch (Exception e) {
        }
    }
    public void mouseReleased(MouseEvent me) {
        try {
            
        } catch (Exception e) {
        }
    }
    public void mouseEntered(MouseEvent me) {
        try {
            
        } catch (Exception e) {
        }
    }
    public void mouseExited(MouseEvent me) {
        try {
            
        } catch (Exception e) {
        }
    }
    public void actionPerformed(ActionEvent ae) {
        try {
            for (int i = 0; i < yukseklik; i++) {
                for (int j = 0; j < genislik; j++) {
                    if (ae.getSource() == dugmeler[i][j]) {
                        if (kordinat[i][j]==-1) {
                            getYenilme();
                        }
                        else{
                            if (kordinat[i][j]==0) {
                                dugmeler[i][j].setEnabled(false);
                                dominolistesi(i, j);
                            }
                            else{
                                dugmeler[i][j].setEnabled(false);
                                String s = "/kaynak/"+String.valueOf(kordinat[i][j])+".png";
                                Image img = ImageIO.read(getClass().getResource(s));
                                dugmeler[i][j].setDisabledIcon(new ImageIcon(img));
                            }
                        }
                    }
                }
            }
            if (KazandinMi()) {
                getKazan();
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Minesweeper(int yukseklik, int genislik, int mS) {
        this.yukseklik = yukseklik;
        this.genislik = genislik;
        this.mS = mS;
        kalan_Mayin=mS;
        kordinat = new int[yukseklik][genislik];
        dugmeler = new JButton[yukseklik][genislik];
        frame.setLayout(new BorderLayout(5,5));
        frame.setSize(genislik*30+110,yukseklik*30+180);
        S.setSize(new Dimension(genislik*30+20, 10));
        W.setSize(new Dimension(10, yukseklik*30));
        E.setSize(new Dimension(10, yukseklik*30));
        grid.setLayout(new GridLayout(yukseklik,genislik));
        frame.setJMenuBar(createMenuBar());
        //frame.setResizable(false);
    }
    public Minesweeper() {
        this.yukseklik = 9;
        this.genislik = 9;
        this.mS = 10;
        this.kalan_Mayin=mS;
        kordinat = new int[yukseklik][genislik];
        dugmeler = new JButton[yukseklik][genislik];
        frame.setLayout(new BorderLayout(5,5));
        frame.setSize(genislik*30+110,yukseklik*30+180);
        S.setSize(new Dimension(genislik*30+20, 10));
        W.setSize(new Dimension(10, yukseklik*30));
        E.setSize(new Dimension(10, yukseklik*30));
        grid.setLayout(new GridLayout(yukseklik,genislik));
        frame.setJMenuBar(createMenuBar());
        //frame.setResizable(false);
    }
    
    public void olustur(){
        count = 0;
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                try {
                    Image img = ImageIO.read(getClass().getResource("/kaynak/b1.png"));
                    dugmeler[i][j]=new JButton();
                    dugmeler[i][j].setIcon(new ImageIcon(img));
                    dugmeler[i][j].addActionListener(this);
                    dugmeler[i][j].addMouseListener(this);
                    grid.add(dugmeler[i][j]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        Random rnd = new Random();
        ArrayList<Integer> mayinTablosu = new ArrayList<>(mS);
        int sayac = 0;
        while (sayac != mS) {         
            int h=rnd.nextInt(yukseklik);
            int w=rnd.nextInt(genislik);
            int x =Integer.parseInt(h+""+w);
            if(!mayinTablosu.contains(x)) {
                mayinTablosu.add(x);
                sayac++;
                kordinat[h][w]=-1;
            }
        }
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (kordinat[i][j]!=-1) {
                    int x=kacMayin(i, j);
                    kordinat[i][j]=x;
                }
            }
        }
//        for (int i = 0; i < yukseklik; i++) { مشان رسم المخطط عالتشغيل
//            for (int j = 0; j < genislik; j++) {
//                if ((kordinat[i][j])==-1) {
//                    System.out.print("x"+" ");
//                }
//                else{
//                    System.out.print(kordinat[i][j]+" ");
//                }
//            }
//            System.out.println("");
//        }
        
        jLabel1.setText("Kalan Mayin");
        jLabel2.setText("Gecen Sure");
        jTextField1.setForeground(Color.red);
        jTextField1.setText(String.valueOf(kalan_Mayin));
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField2.setForeground(Color.red);
        jTextField2.setText("         ");
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jButton1.setBackground(Color.white);
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kaynak/sm.png")));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniOlustur();
            }
        });

        frame.add(grid,BorderLayout.CENTER);
        frame.add(jPanel2,BorderLayout.NORTH);
        frame.add(S,BorderLayout.SOUTH);
        frame.add(W,BorderLayout.WEST);
        frame.add(E,BorderLayout.EAST);
 
        frame.setLocationRelativeTo(null);      
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void yeniOlustur(){
        count=0;
        //System.out.println("------------");
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                dugmeler[i][j].setEnabled(true);
                kordinat[i][j]=0;
                try {
                    Image img = ImageIO.read(getClass().getResource("/kaynak/b1.png"));
                    dugmeler[i][j].setIcon(new ImageIcon(img));
                    
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        Random rnd = new Random();
        ArrayList<Integer> mTablosu = new ArrayList<>(mS);
        int sayac = 0;
        while (sayac != mS) {         
            int h=rnd.nextInt(yukseklik);
            int w=rnd.nextInt(genislik);
            int x =Integer.parseInt(h+""+w);
            if(!mTablosu.contains(x)) {
                mTablosu.add(x);
                sayac++;
                kordinat[h][w]=-1;
            }
        }
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (kordinat[i][j]!=-1) {
                    int x=kacMayin(i, j);
                    kordinat[i][j]=x;
                }
            }
        }
//        for (int i = 0; i < yukseklik; i++) {   مشان رسم المخطط عالتشغيل
//            for (int j = 0; j < genislik; j++) {
//                if ((kordinat[i][j])==-1) {
//                    System.out.print("x"+" ");
//                }
//                else{
//                    System.out.print(kordinat[i][j]+" ");
//                }
//            }
//            System.out.println("");
//        }
    }
    
    JMenuBar createMenuBar() {
       JMenuBar menuBar = new JMenuBar();
       JMenu menu = new JMenu("Ayarlar");
       JMenu about = new JMenu("Hakkinda");
       
       JMenuItem Hakkinda = new JMenuItem("Odev-Ders Bilgisi");
       JMenuItem Hakkinda2 = new JMenuItem("Hoca Bilgileri");
       JMenuItem Hakkinda3 = new JMenuItem("Ogrenci Bilgileri");
       
       JMenuItem kolaySec = new JMenuItem("Kolay");
       JMenuItem OrtaSec = new JMenuItem("Orta");
       JMenuItem ZorSec = new JMenuItem("Zor");
       JMenuItem Ozel = new JMenuItem("Ozel");
       JMenuItem Cik = new JMenuItem("Oyundan Cik");
       
       kolaySec.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               frame.setEnabled(false);
               frame.setVisible(false);
               Minesweeper m = new Minesweeper(9, 9, 10);
               m.olustur();
               m.start();
               //JOptionPane.showMessageDialog(null,"Kolay","Kolay", JOptionPane.PLAIN_MESSAGE);
           }
       });
       OrtaSec.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               frame.setEnabled(false);
               frame.setVisible(false);
               Minesweeper m = new Minesweeper(16, 16, 40);
               m.olustur();
               m.start();
               //JOptionPane.showMessageDialog(null,"Orta","Orta", JOptionPane.PLAIN_MESSAGE);
           }
       });
       ZorSec.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               frame.setEnabled(false);
               frame.setVisible(false);
               Minesweeper m = new Minesweeper(16, 30, 99);
               m.olustur();
               m.start();
               //JOptionPane.showMessageDialog(null,"Zor","Zor", JOptionPane.PLAIN_MESSAGE);
           }
       });
       Ozel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                JFrame fr = new JFrame("Custom");
                Container container = fr.getContentPane();
                GroupLayout groupLayout = new GroupLayout(container);
                container.setLayout(groupLayout);
                groupLayout.setAutoCreateGaps(true);
                groupLayout.setAutoCreateContainerGaps(true);
                groupLayout.preferredLayoutSize(container);
                JLabel label_1 = new JLabel("Yukseklik");
                JLabel label_2 = new JLabel("Genislik");
                JLabel label_3 = new JLabel("Mayin Sayisi");
                JTextField textField_1 = new JTextField();
                JTextField textField_2 = new JTextField();
                JTextField textField_3 = new JTextField();
                JButton button_1 = new JButton("Olustur");
                JButton button_2 = new JButton("iptal");
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String yy =textField_1.getText();
                        int y = Integer.parseInt(yy);
                        String gg =textField_2.getText();
                        int g = Integer.parseInt(gg);
                        String z =textField_3.getText();
                        int m = Integer.parseInt(z);
                        frame.setEnabled(false);
                        frame.setVisible(false);
                        fr.setEnabled(false);
                        fr.setVisible(false);
                        Minesweeper mm = new Minesweeper(y, g, m);
                        mm.olustur();  
                        mm.start();
                    }
                });
                button_2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        fr.setVisible(false);
                    }
                });
                
                groupLayout.setHorizontalGroup(
                    groupLayout.createSequentialGroup()
                
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(label_1)
                    .addComponent(label_2)
                    .addComponent(label_3)
                    )
                
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(textField_1)
                    .addComponent(textField_2)
                    .addComponent(textField_3)
 
                    
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(button_1)
                        .addComponent(button_2)
                        
                        )
                    )
                );
 
        
            groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label_1)
                    .addComponent(textField_1)
                    )
                
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label_2)
                    .addComponent(textField_2)
                    )
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label_3)
                    .addComponent(textField_3)
                    )
                
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(button_1)
                    .addComponent(button_2)
                    
                    )
            );
        fr.setLocationRelativeTo(frame);
        fr.pack();                                          
        fr.setVisible(true);
        
        //JOptionPane.showMessageDialog(null,"Ozel","Ozel", JOptionPane.PLAIN_MESSAGE);
           }
       }); 
       Cik.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ae) {
               frame.setEnabled(false);
               frame.setVisible(false);
               System.exit(0);
           }
       });
       Hakkinda.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ae) {
               JOptionPane.showMessageDialog(null,"Ders Kodu : BMU112\nDers Adi : Algoritma ve Programlama 2","Odev-Ders bilgisi", JOptionPane.PLAIN_MESSAGE);
           }
       });
       Hakkinda2.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ae) {
               JOptionPane.showMessageDialog(null,"Doç. Dr. ilhab AYDIN","Sorumlu Hoca Bilgisi", JOptionPane.PLAIN_MESSAGE);
           }
       });
       Hakkinda3.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ae) {
               JOptionPane.showMessageDialog(null,"Öğrenci No : 16260122 \nÖğrenci Adı : GIYAS ALETK ","Öğrenci Bilgisi", JOptionPane.PLAIN_MESSAGE);
           }
       });
       about.add(Hakkinda);
       about.add(Hakkinda2);
       about.add(Hakkinda3);
       menu.add(kolaySec);
       menu.add(OrtaSec);
       menu.add(ZorSec);
       menu.add(Ozel);
       menu.add(Cik);
       menuBar.add(menu);
       menuBar.add(about);
       return menuBar;
   }
    
    public final int kacMayin(int i, int j){
        int s=0;
        if ((i+1)<yukseklik && (j+1)<genislik) 
            if (kordinat[i+1][j+1] == -1) 
                s++;
        if ((i-1)>=0 && (j-1)>=0) 
            if (kordinat[i-1][j-1] ==-1) 
                s++;
        if (i+1 < yukseklik) 
            if (kordinat[i+1][j] ==-1) 
                s++;
        if (j+1 < genislik) 
            if (kordinat[i][j+1] ==-1) 
                s++;
        if (i-1 >=0) 
            if (kordinat[i-1][j] ==-1) 
                s++;
        if (j-1 >= 0) 
            if (kordinat[i][j-1] ==-1) 
                s++;
        if ((i+1)<yukseklik && (j-1)>=0) 
            if (kordinat[i+1][j-1] ==-1) 
                s++;
        if ((i-1)>=0 && (j+1)<genislik) 
            if (kordinat[i-1][j+1] ==-1) 
                s++;
        
        return s;
    }
    public void bas(int i,int j) throws IOException{
        if ((i+1)<yukseklik && (j+1)<genislik){ 
            if (kordinat[i+1][j+1] !=-1){ 
                if (kordinat[i+1][j+1]!=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i+1][j+1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i+1][j+1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i+1][j+1].setEnabled(false);
                }
                else{
                    dugmeler[i+1][j+1].setEnabled(false);
                }
            }
        }
        if ((i-1)>=0 && (j-1)>=0) {
            if (kordinat[i-1][j-1] !=-1) {
                if (kordinat[i-1][j-1]!=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i-1][j-1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i-1][j-1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i-1][j-1].setEnabled(false);
                } 
                else {
                    dugmeler[i-1][j-1].setEnabled(false);
                }
            }
        }
        if (i+1 < yukseklik) {
            if (kordinat[i+1][j] !=-1) {
                if (kordinat[i+1][j] != 0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i+1][j])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i+1][j].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i+1][j].setEnabled(false);
                } 
                else {
                    dugmeler[i+1][j].setEnabled(false);
                }
            }
        }
        if (j+1 < genislik) {
            if (kordinat[i][j+1] !=-1) {
                if (kordinat[i][j+1] !=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i][j+1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i][j+1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i][j+1].setEnabled(false);
                } 
                else {
                    dugmeler[i][j+1].setEnabled(false);
                }
            }
        }
        if (i-1 >=0) {
            if (kordinat[i-1][j] !=-1) {
                if (kordinat[i-1][j] !=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i-1][j])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i-1][j].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i-1][j].setEnabled(false);
                } 
                else {
                    dugmeler[i-1][j].setEnabled(false);
                }
            }
        }
        if (j-1 >= 0) {
            if (kordinat[i][j-1] !=-1) {
                if (kordinat[i][j-1] !=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i][j-1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i][j-1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i][j-1].setEnabled(false);
                } 
                else {
                    dugmeler[i][j-1].setEnabled(false);
                }
            }
        }
        if ((i+1)<yukseklik && (j-1)>=0) {
            if (kordinat[i+1][j-1] !=-1) {
                if (kordinat[i+1][j-1]!=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i+1][j-1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i+1][j-1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i+1][j-1].setEnabled(false);
                } 
                else {
                    dugmeler[i+1][j-1].setEnabled(false);
                }
            }
        }
        if ((i-1)>=0 && (j+1)<genislik) {
            
            if (kordinat[i-1][j+1] !=-1) {
                if (kordinat[i-1][j+1] !=0) {
                    String s = "/kaynak/"+String.valueOf(kordinat[i-1][j+1])+".png";
                    Image img = ImageIO.read(getClass().getResource(s));
                    dugmeler[i-1][j+1].setDisabledIcon(new ImageIcon(img));
                    dugmeler[i-1][j+1].setEnabled(false);
                } 
                else {
                    dugmeler[i-1][j+1].setEnabled(false);
                } 
            }
        }
    }
    
    public void dominolistesi(int i, int j) throws IOException{
        ArrayList<Integer> aa = new ArrayList<>(genislik*yukseklik);
        aa.add(Integer.parseInt(i+""+j));
        int ii=i,jj=j;
        int s=0;
        boolean b =true;
        bas(i, j);
        while (b) {
            ii=aa.get(s)/10;
            jj=aa.get(s)%10;
            int X[] = { 1,-1, 0, 0, 1,-1, 1,-1 }; 
            int Y[] = { 0, 0, 1,-1, 1,-1,-1, 1 }; 
            for (int k = 0; k < 8; k++) {
                ii+=X[k];
                jj+=Y[k];
                if (((ii>=0)&&(ii<yukseklik))&&((jj>=0)&&(jj<yukseklik))) {
                    if ((!aa.contains(Integer.parseInt(ii+""+jj)))&& kordinat[ii][jj]!= -1) {
                        aa.add(Integer.parseInt(ii+""+jj));
                    }
                    if (kordinat[ii][jj]== -1) {
                    b=false;
                    }
                }
            }
            s++;
        }
        for (int k = 0; k < aa.size(); k++) {
            bas(aa.get(k)/10, aa.get(k)%10);
        }
        
    }
    
    public void getYenilme() {
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (kordinat[i][j]==-1) {
                    try {
                        Image img = ImageIO.read(getClass().getResource("/kaynak/m2.png"));    
                        dugmeler[i][j].setDisabledIcon(new ImageIcon(img));
                        dugmeler[i][j].setEnabled(false);
                        dugmeler[i][j].setBackground(Color.WHITE);
                    } 
                    catch (Exception e) {
                        }
                    
                    
                }
                else{
                    dugmeler[i][j].setEnabled(false);
                }
            }
        }
        int q =JOptionPane.showConfirmDialog(null, "Maalesef Kayip ettin \n Yeniden oynamak ister misin?" , "Kaybettin",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (q== JOptionPane.YES_OPTION) {
            frame.setEnabled(false);
            frame.setVisible(false);
            Minesweeper mm = new Minesweeper(9, 9, 10);
            mm.olustur();
            mm.start();
        }
        else if(q==JOptionPane.NO_OPTION){
            frame.setEnabled(false);
            frame.setVisible(false);
            System.exit(0);
        }
    }
    public void getKazan() throws Exception{
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (kordinat[i][j]==-1) {
                    Image img = ImageIO.read(getClass().getResource("/kaynak/m1.png"));
                    dugmeler[i][j].setIcon(new ImageIcon(img));
                    dugmeler[i][j].setBackground(Color.white);
               }
                else
                    dugmeler[i][j].setEnabled(false);
            }
        }
        int x =JOptionPane.showConfirmDialog(null, "Tebrikeler Kazandin yeniden oynamak istermisin ?" , "Kazndin",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (x== JOptionPane.YES_OPTION) {
            frame.setEnabled(false);
            frame.setVisible(false);
            Minesweeper m = new Minesweeper(9, 9, 10);
            m.olustur();
            m.start();
        }
        else if(x== JOptionPane.NO_OPTION){
            frame.setEnabled(false);
            frame.setVisible(false);
            System.exit(0);
        }
    }
    

    public boolean KazandinMi() {
        int s=0;
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (! dugmeler[i][j].isEnabled()) {
                    s++;
                }
            }
        }
        return (s== genislik*yukseklik - mS);
    }
    public boolean tumMayinlerIsaretlendi() {
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                if (kordinat[i][j]==-1) {
                    if (dugmeler[i][j].isEnabled()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    Timer t = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            jTextField2.setText(String.valueOf(count));
            count++;
        }
    };
    public void start(){
        t.scheduleAtFixedRate(task, 1000, 1000);
    }
    
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(9,9,10);
        m.olustur();
        m.start();
    }
}

