package 빙고;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Lotto extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lotto app = new Lotto("복권빙고!");
	}

	public Lotto(String s) {
		super(s);
		init();
		subDialog();
		randomNumber();
	}

	boolean[] arr = new boolean[9];
	boolean flag = false;
	int chance = 0;
	JPanel retry = new JPanel();
	JPanel btn_9 = new JPanel();
	JLabel textBox1 = new JLabel("내 돈 :");
	JLabel textBox2 = new JLabel("<<보상>>");
	JLabel textBox3 = new JLabel("만원");
	JTextField won = new JTextField();
	BingoBtn[][] btn2 = new BingoBtn[3][3];
	String money = "400";
	private Font f1;

	String data[][] = { { "6", "10000", "16", "72" }, { "7", "36", "17", "180" }, { "8", "720", "18", "119" },
			{ "9", "360", "19", "36" }, { "10", "80", "20", "306" }, { "11", "252", "21", "1080" },
			{ "12", "108", "22", "144" }, { "13", "72", "23", "1800" }, { "14", "54", "24", "3600" },
			{ "15", "180", "", "" } };

	private void init() {

		f1 = new Font("궁서", Font.BOLD, 20);

		String a[] = { "번호", "금액", "번호", "금액" };

		JTable table = new JTable(data, a);
		JScrollPane sc = new JScrollPane(table);
		JButton btn = new JButton("다시시작");

		JLabel text = new JLabel();
		Container c = getContentPane();
		retry.setLayout(new FlowLayout());
		GridLayout grid = new GridLayout(3, 3);
		btn_9.setLayout(grid);

		c.setBackground(Color.WHITE);
		c.setLayout(null);
		c.add(won);
		c.add(btn_9);
		c.add(retry);
		c.add(textBox1);
		c.add(textBox2);
		c.add(textBox3);
		c.add(sc);
		won.setBounds(110, 265, 100, 30);
		sc.setBounds(30, 50, 240, 200);
		won.setEditable(false);
		won.setText(money);

		retry.setBackground(Color.WHITE);
		retry.setBounds(330, 250, 150, 150);

		textBox1.setBounds(30, 130, 150, 150);
		textBox1.setSize(300, 300);

		textBox2.setBounds(100, -120, 150, 150);
		textBox2.setSize(300, 300);

		textBox3.setBounds(220, 130, 150, 150);
		textBox3.setSize(300, 300);

		retry.add(btn);
		textBox1.add(text);
		textBox2.add(text);
		textBox3.add(text);

		btn_9.setBackground(Color.WHITE);
		btn_9.setBounds(320, 50, 170, 170);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				btn2[i][j] = new BingoBtn();
				btn_9.add(btn2[i][j]);
				int ci = i;
				int cj = j;
				btn2[i][j].addActionListener(event -> {
						click(ci, cj);
						isBingo();
				});
			}
		}

		btn.setFont(f1);
		won.setFont(f1);
		textBox1.setFont(f1);
		textBox2.setFont(f1);
		textBox3.setFont(f1);
		setSize(540, 380);
		setLocation(300, 300);
		setVisible(true);
		btn.addActionListener(event -> {
			subDialog();
		});

	}

	int sum_number = 0;

	private boolean isBingo() {
		int bingoCount = 0;
		sum_number = 0;
		for (int i = 0; i < 3; i++) {
			bingoCount = 0;
			for (int j = 0; j < 3; j++) {
				if (!btn2[i][j].isEnabled()) {
					sum_number += btn2[i][j].getNumber();
					bingoCount++;
				}
			}
			if (bingoCount == 3)
				return true;
		}

		for (int i = 0; i < 3; i++) {
			bingoCount = 0;
			for (int j = 0; j < 3; j++) {
				if (!btn2[j][i].isEnabled()) {
					bingoCount++;
				}
			}
			if (bingoCount == 3) {
				bingoCount=0;
				return true;
			}
		}
		bingoCount = 0;
		for (int i = 0; i < 3; i++) {
			if (!btn2[i][i].isEnabled()) {
				bingoCount++;
			}
			if (bingoCount == 3) {
				bingoCount=0;
				return true;
			}
		}
		bingoCount = 0;
		for (int i = 0; i < 3; i++) {
			if (!btn2[i][2 - i].isEnabled()) {
				bingoCount++;
			}
				
			if (bingoCount == 3) {
				bingoCount=0;
				return true;
			}
		}
			return false;	
	}

	private void click(int x, int y) {
		int r1 = x;
		int r2 = y;
		btn2[r1][r2].setTextNum();
		btn2[r1][r2].setEnabled(false);
		String s_sumNumber;
		String s_mainMoney="";
		// System.out.println("btn2[" + r1 + "][" + r2 + "]");
		if (isBingo()&&result==0) {
			s_sumNumber = Integer.toString(sum_number);
			System.out.println("빙고");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 3; j+=2) {
					if (s_sumNumber.equals(data[i][j])) {
						int imoney = Integer.parseInt(data[i][j+1]);
						int main_Money = Integer.parseInt(money);
						main_Money += imoney;			
						s_mainMoney = Integer.toString(main_Money);
					}
				}
			}
			won.setText(s_mainMoney);
		}

	}
	int result;
	private void subDialog() {
		String[] buttons = { "예(Y)", "아니요(N)" };
		//예=0 , 아니요=1
		result = JOptionPane.showOptionDialog(null, "최대 클릭 세번의 기회!\n200만원을 지불하여 게임을시작하시겠습니까?", "복권사기",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, "두번째값");
		if(result==0) {
			int pay_Money = 200;
			int main_money = Integer.parseInt(money);
			System.out.println(main_money+"<= main_money");
			main_money -= pay_Money;
			System.out.println(main_money+"<= main_money2");
			String cul_money = Integer.toString(main_money);
			won.setText(cul_money);
		}
	}

	private void randomNumber() {
		Random random = new Random();
		int rNumber[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		for (int i = 0; i < rNumber.length; i++) {
			int a = random.nextInt(9);
			int temp;
			temp = rNumber[i];
			rNumber[i] = rNumber[a];
			rNumber[a] = temp;
		}

		int idx = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				btn2[i][j].setNumber(rNumber[idx]);
				btn2[i][j].setName("" + rNumber[idx]);
				btn2[i][j].setFont(f1);
				// btn2[i][j].setTextNum();
				System.out.print(rNumber[idx] + " ");
				idx++;
			}
		}
	}

	class BingoBtn extends JButton {
		private int number;

		public int getNumber() {
			return number;
		}

		public void setTextNum() {
			String str = Integer.toString(number);
			this.setText(str);
		}

		public void setNumber(int number) {
			this.number = number;
		}
	}
}
