package com.idea.prototype.translator;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class NotificationBox {
	JDialog frame;
	GridBagConstraints constraints;
	private long time;
	JLabel headingLabel ;

	JTextArea messageLabel;
	
	MouseListener mouseListener;

	public NotificationBox() {
		frame = new JDialog();
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		frame.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.getContentPane().setBackground(new Color(255, 255, 225));

		
		mouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
               System.out.println("x:"+e.getX());
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		frame.addMouseListener(mouseListener);
	}

	public void create(String message, String header, long time) {
		this.time = time;
		Insets insets;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 10f;
		insets = new Insets(5, 5, 0, 0);
		constraints.insets = insets;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		messageLabel = new JTextArea();
		messageLabel.setText("<html><p style=\"width:200px\">" + message + "</p></html>");
		messageLabel.setFont(new Font("Serif", Font.TRUETYPE_FONT, 20));
		frame.add(messageLabel, constraints);

		//
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.LAST_LINE_END;
		insets = new Insets(5, 180 - (header.length() - 13) * 3, 5, 5);
		constraints.insets = insets;
		headingLabel = new JLabel(header);
		headingLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		headingLabel.setForeground(Color.BLUE);
		frame.add(headingLabel, constraints);

	}

	public void setVisible(boolean isVisible) {
		frame.setVisible(isVisible);
		if (isVisible) {
			System.out.println("--------------show");
			frame.setVisible(true);
			frame.pack();
			Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(scrSize.width - frame.getWidth() - 30, 30);
			frame.setAlwaysOnTop(true);
			frame.setAlwaysOnTop(false);
			frame.repaint();
//			new Thread() {
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(time);
//						// frame.dispose();
//						frame.remove(messageLabel);
//						frame.remove(headingLabel);
//						frame.repaint();
//						// frame.setVisible(false);
//						frame.setLocation(scrSize.width + 500, 30);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				};
//			}.start();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				NotificationBox box = new NotificationBox();
				box.create(
						"I can't change the direction of the wind, but I can adjust my sails to always reach my destination."
								+ "",
						"Jimmy Dean", 5000);
				box.setVisible(true);
			}
		});

	}
}
