package com.idea.prototype.translator;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class QuickTranslator implements NativeKeyListener, NativeMouseListener {

	private String[] keyBuffer = new String[15];
	private int bufferIndex = 0;
	NotificationBox notificationBox;
	private int mouseX;
	private int mouseY;

	public QuickTranslator() {
		notificationBox = new NotificationBox();

		notificationBox.setVisible(false);
	}

	public static void main(String[] args) {
		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		QuickTranslator quickTranslator = new QuickTranslator();

		GlobalScreen.addNativeKeyListener(quickTranslator);
		GlobalScreen.addNativeMouseListener(quickTranslator);

	}

	private void evalKeyBuffer() {
		int index = (bufferIndex - 2 + keyBuffer.length) % keyBuffer.length;

		if ((isBufferContainKey(index, Keys.LEFT_CONTROL) || isBufferContainKey(index, Keys.RIGHT_CONTROL))
				&& (isBufferContainKey(index, Keys.LEFT_SHIFT) || isBufferContainKey(index, Keys.RIGHT_SHIFT))
				&& isBufferContainKey(index, Keys.Z)) {
			showPopup();
			System.out.println("show from :" + index);
			keyBuffer[index] = null;
			keyBuffer[(index + 1) % keyBuffer.length] = null;
			keyBuffer[(index + 2) % keyBuffer.length] = null;

		}
	}

	private void showPopup() {
		try {
			String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			notificationBox.show(data, mouseX, mouseY);
			// System.out.println("data :" + data);
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private boolean isBufferContainKey(int startIndex, Keys key) {
		for (int i = 0; i < 3; i++) {
			int index = (i + startIndex) % keyBuffer.length;
			if (keyBuffer[index] == null) {
				return false;
			}
			if (keyBuffer[index].equals(key.getCode())) {
				System.out.println("contain key :" + key.getCode());
				return true;
			}
		}
		return false;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + "," + e.getKeyCode());
		keyBuffer[bufferIndex % keyBuffer.length] = NativeKeyEvent.getKeyText(e.getKeyCode());
		evalKeyBuffer();
		printArr(keyBuffer);
		bufferIndex++;
		if (bufferIndex > 1024) {
			bufferIndex = bufferIndex % keyBuffer.length;
		}

	}

	private void printArr(String[] arr) {
		StringBuilder builder = new StringBuilder();
		for (String str : arr) {
			if (str != null) {
				builder.append(str);
			} else {
				builder.append("null");
			}
			builder.append(",");
		}
		System.out.println(builder.toString());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// System.out.println(e.getX() + "," + notificationBox.getX());
		if (e.getX() < notificationBox.getX() || e.getX() > notificationBox.getX() + notificationBox.getWidth()
				|| e.getY() < notificationBox.getY()
				|| e.getY() > notificationBox.getY() + notificationBox.getHeight()) {
			notificationBox.setVisible(false);
		}

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseX = e.getX();
		this.mouseY = e.getY();

	}

}
