/*
 * Copyright 2011 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android_serialport_api.sample;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

public class SendingA extends SerialPortActivity {

	SendingThread mSendingThread;
	byte[] mBuffer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendinga);
		mBuffer = new byte[1024];
		mBuffer[0]=(byte)0xAA;
		mBuffer[1]=(byte)0xAA;
		mBuffer[2]=(byte)0xAA;
		mBuffer[3]=(byte)0x96;
		mBuffer[4]=(byte)0x69;
		mBuffer[5]=(byte)0x00;
		mBuffer[6]=(byte)0x03;
		mBuffer[7]=(byte)0x20;
		mBuffer[8]=(byte)0x01;
		mBuffer[9]=(byte)0x22;

		if (mSerialPort != null) {
			mSendingThread = new SendingThread();
			mSendingThread.start();
		}
	}
	public   String printHexString( byte[] b) {
		String x="";
		for (byte aB : b) {
			String hex = Integer.toHexString(aB & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			x += " " + (hex.toUpperCase());
		}
return x;
	}
	@Override
	protected void onDataReceived(byte[] buffer, int size) {
		// ignore incoming data
		TextView textView1=(TextView)findViewById(R.id.textView1);
		textView1.setText(printHexString(buffer));

	}

	private class SendingThread extends Thread {
		@Override
		public void run() {
			while (!isInterrupted()) {
				try {
					if (mOutputStream != null) {
						mOutputStream.write(mBuffer);
					} else {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
