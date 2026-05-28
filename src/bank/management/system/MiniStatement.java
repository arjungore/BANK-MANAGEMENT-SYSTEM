package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JButton b1;
    JTextArea mini;

    MiniStatement(String pin) {

        super("Mini Statement");

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        setSize(500,700);
        setLocation(20,20);

        JLabel bank = new JLabel("Indian Bank");
        bank.setFont(new Font("Raleway", Font.BOLD, 18));
        bank.setBounds(180,20,200,30);
        add(bank);

        JLabel card = new JLabel();
        card.setFont(new Font("System", Font.BOLD, 14));
        card.setBounds(20,80,400,20);
        add(card);

        JLabel balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("System", Font.BOLD, 14));
        balanceLabel.setBounds(20,580,400,20);
        add(balanceLabel);

        mini = new JTextArea();
        mini.setEditable(false);
        mini.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(mini);
        scroll.setBounds(20,120,440,430);
        add(scroll);

        mini.append("DATE\t\tTYPE\tAMOUNT\n");
        mini.append("-------------------------------------------------------------\n");

        try {

            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery(
                "select * from login where pin = '"+pin+"'"
            );

            while(rs.next()) {

                String cardnumber = rs.getString("cardnumber");

                card.setText(
                    "Card Number:    " +
                    cardnumber.substring(0,4) +
                    "XXXXXXXX" +
                    cardnumber.substring(12)
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        try {

            int balance = 0;

            Conn c1 = new Conn();

            ResultSet rs = c1.s.executeQuery(
                "select * from bank where pin = '"+pin+"'"
            );

            while(rs.next()) {

                mini.append(
                    rs.getString("date") + "\t" +
                    rs.getString("type") + "\t" +
                    rs.getString("amount") + "\n\n"
                );

                if(rs.getString("type").equals("Deposit")) {

                    balance += Integer.parseInt(rs.getString("amount"));

                } else {

                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }

            balanceLabel.setText(
                "Your Total Balance is Rs " + balance
            );

        } catch(Exception e) {
            e.printStackTrace();
        }

        b1 = new JButton("Exit");
        b1.setBounds(180,620,120,30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }

    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);
    }
}