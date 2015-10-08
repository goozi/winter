package com.qihang.winter.core.extend.swftools;

import com.qihang.winter.core.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {

	InputStream is;
	String type;

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (type.equals("Error")) {
					LogUtil.info("Error	:" + line);
				} else {
					LogUtil.info("文件转换:" + line);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
   