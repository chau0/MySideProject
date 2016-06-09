package com.idea.prototype.translator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class NotificationBox {
	JWindow frame;
	GridBagConstraints constraints;
	private long time;
	JLabel headingLabel;
	JScrollPane scrollPane;
	JTextArea messageLabel;

	MouseAdapter mouseListener;

	public NotificationBox() {
		frame = new JWindow();
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		frame.setLayout(new BorderLayout());
		mouseListener = new MouseAdapter() {
			private int mx, my;

			@Override
			public void mouseMoved(MouseEvent e) {
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (frame.isVisible()) {
					Point p = frame.getLocation();
					p.x += e.getXOnScreen() - mx;
					p.y += e.getYOnScreen() - my;
					mx = e.getXOnScreen();
					my = e.getYOnScreen();
					frame.setLocation(p);
					frame.repaint();
				}
			}
		};
		frame.setVisible(false);

	}

	public void show(String message, int x, int y) {

		messageLabel = new JTextArea(10, 20);
		messageLabel.setText(message);
		messageLabel.setWrapStyleWord(true);
		messageLabel.setLineWrap(true);
		messageLabel.setBackground(new Color(255, 255, 225));
		messageLabel.setFont(new Font("Serif", Font.TRUETYPE_FONT, 15));
		messageLabel.setEditable(true);
		frame.add(messageLabel, constraints);

		scrollPane = new JScrollPane(messageLabel);
		frame.add(scrollPane);
		frame.setSize(400, 200);
		messageLabel.addMouseListener(mouseListener);
		messageLabel.addMouseMotionListener(mouseListener);

		frame.setVisible(true);
		setLocation(x, y);

		System.out.println("show ----------");
		// frame.addMouseMotionListener(mouseListener);
		// frame.addMouseListener(mouseListener);

	}

	public void setLocation(int x, int y) {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (x + frame.getWidth() > scrSize.getWidth()) {
			x = (int) (scrSize.getWidth() - frame.getWidth());
		}
		if (y + frame.getHeight() > scrSize.getHeight()) {
			y = (int) (scrSize.getHeight() - frame.getHeight() - 30);
		}
		frame.setLocation(x, y);
		frame.setAlwaysOnTop(true);
		frame.setAlwaysOnTop(false);
		frame.repaint();
	}

	public void setVisible(boolean isVisible) {
		if (!isVisible) {
			if (messageLabel != null) {
				frame.remove(messageLabel);
			}
			if (scrollPane != null) {
				frame.remove(scrollPane);
			}
			frame.repaint();
		}
		frame.setVisible(isVisible);

	}

	public int getX() {
		return frame.getX();
	}

	public int getY() {
		return frame.getY();
	}

	public int getHeight() {
		return frame.getHeight();
	}

	public int getWidth() {
		return frame.getWidth();
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
				box.show(
						"I can't change the direction of the wind, but I can adjust my sails to always reach my destination.",
						10, 19);
				box.setVisible(true);
			}
		});

	}
}
