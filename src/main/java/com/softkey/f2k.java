package com.softkey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class f2k {

	jsyunew6 f2k;
	private String DevicePath;

	private boolean isNullOrEmpty(String value) {
		if (value != null) {
			return (value.length() == 0);
		}
		return true;
	}

	public f2k() {
		f2k = new jsyunew6();

		//return this.open();
	}

	public String open() {
		DevicePath = f2k.FindPort(0);
		String result = "";
		if (f2k.get_LastError() != 0) {
			result = "请插入加密锁后，再进行操作。";

			DevicePath = "";
		}
		return result;
	}

	public String sn() {
		int ID1, ID2;
		ID1 = f2k.GetID_1(DevicePath);
		if (f2k.get_LastError() != 0) {
			System.out.println("返回ID1错误");
			return "";
		}
		ID2 = f2k.GetID_2(DevicePath);
		if (f2k.get_LastError() != 0) {
			System.out.println("返回ID1错误");
			return "";
		}
		return Integer.toHexString(ID1) + Integer.toHexString(ID2);

	}

	public String version() {
		int version;
		version = f2k.GetVersion(DevicePath);
		if (f2k.get_LastError() != 0) {
			System.out.println("返回版本号错误");
			return "";
		}

		return Integer.toString(version);

	}

	public boolean findPort() {
		return isNullOrEmpty(DevicePath);
	}

	public int getPermission(String str) {
		int permission = 0;
		String ret = "";
		if (!isNullOrEmpty(DevicePath)) {
			short address = 36;// 要读取的数据在加密锁中储存的起始地址
			ret = f2k.YReadString(address, (short) 4, "ffffffff", "ffffffff", DevicePath);

			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(ret);
			ret = "";
			while (m.find()) {
				if (!"".equals(m.group()))
					ret += m.group();
			}

			address = 40;
			ret = f2k.YReadString(address, (short) Short.parseShort(ret), "ffffffff", "ffffffff", DevicePath);

			String[] versions = ret.split("\\|");

			for (String version : versions) {
				String[] vers = version.split(":");

				if (str.equalsIgnoreCase(vers[0])) {
					permission = Integer.parseInt(vers[1]);
				}
			}

		}

		return permission;
	}

	public String getPermission() {
		String ret = "";
		if (!isNullOrEmpty(DevicePath)) {
			short address = 36;// 要读取的数据在加密锁中储存的起始地址
			ret = f2k.YReadString(address, (short) 4, "ffffffff", "ffffffff", DevicePath).trim();

			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(ret);
			ret = "";
			while (m.find()) {
				if (!"".equals(m.group()))
					ret += m.group();
			}

			address = 40;
			ret = f2k.YReadString(address, (short) Short.parseShort(ret.trim()), "ffffffff", "ffffffff", DevicePath);

		}

		return ret;
	}



	public static void main(String args[]) {
		System.out.println(System.getProperty("java.library.path"));

		com.softkey.f2k f2k = new f2k();
		System.out.println(f2k.sn());
		System.out.println(f2k.version());
		System.out.println(f2k.getPermission());
		System.out.println(f2k.getPermission("2"));
	}
}
