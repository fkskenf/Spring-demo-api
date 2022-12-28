package com.blog.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ByteUtil {
	/*
	 * dest[destPos] += src
	 */
	public static void setBytes(byte[] dest, int destPos, byte[] src) throws Exception {
		System.arraycopy(src, 0, dest, destPos, src.length);
		// return dest;
	}

	public static byte[] toByteL(int src, int len) throws Exception {
		return toByteL(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteL(double src, int len) throws Exception {
		return toByteL(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteL(long src, int len) throws Exception {
		return toByteL(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteL(String src, int len) throws Exception {
		return toByteL(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteR(int src, int len) throws Exception {
		return toByteR(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteR(double src, int len) throws Exception {
		return toByteR(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteR(long src, int len) throws Exception {
		return toByteR(src, len, Charset.defaultCharset());
	}

	public static byte[] toByteR(String src, int len) throws Exception {
		return toByteR(src, len, Charset.defaultCharset());
	}

	/*
	 * left fill 0
	 */
	public static byte[] toByteL(int src, int len, Charset cs) throws Exception {
		String str = Integer.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = "0" + str;
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * left fill 0
	 */
	public static byte[] toByteL(double src, int len, Charset cs) throws Exception {
		String str = Double.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = "0" + str;
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * left fill 0
	 */
	public static byte[] toByteL(long src, int len, Charset cs) throws Exception {
		String str = Long.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = "0" + str;
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * left fill " "
	 */
	public static byte[] toByteL(String src, int len, Charset cs) throws Exception {
		byte[] dest = new byte[len];

		if (src.length() >= len) {
			System.arraycopy(src.getBytes(cs), 0, dest, 0, len);
		} else if (src.length() < len) {
			while (src.length() < len) {
				src = " " + src;
			}
			dest = src.getBytes(cs);
		}
		return dest;
	}

	/*
	 * right fill 0
	 */
	public static byte[] toByteR(int src, int len, Charset cs) throws Exception {
		String str = Integer.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = str + "0";
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * right fill 0
	 */
	public static byte[] toByteR(double src, int len, Charset cs) throws Exception {
		String str = Double.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = str + "0";
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * right fill 0
	 */
	public static byte[] toByteR(long src, int len, Charset cs) throws Exception {
		String str = Long.toString(src);
		byte[] dest = new byte[len];

		if (str.length() >= len) {
			System.arraycopy(str.getBytes(cs), 0, dest, 0, len);
		} else if (str.length() < len) {
			while (str.length() < len) {
				str = str + "0";
			}
			dest = str.getBytes(cs);
		}
		return dest;
	}

	/*
	 * right fill " "
	 */
	public static byte[] toByteR(String src, int len, Charset cs) throws Exception {
		byte[] dest = new byte[len];

		if (src.length() >= len) {
			System.arraycopy(src.getBytes(cs), 0, dest, 0, len);
		} else if (src.length() < len) {
			while (src.length() < len) {
				src = src + " ";
			}
			System.arraycopy(src.getBytes(cs), 0, dest, 0, len);
		}
		return dest;
	}

	/*
	 * header + data + trailer 조합 (CR/LF)
	 */
	public static void creTelegram(byte[] dest, byte[] header, byte[] data, byte[] trailer) {
		// byte[] CRLF = {0x0D, 0x0A};
		int last_len = 0;
		System.arraycopy(header, 0, dest, 0, header.length);
		last_len += header.length;

		// System.arraycopy(CRLF, 0, dest, last_len, CRLF.length);
		// last_len += CRLF.length;

		System.arraycopy(data, 0, dest, last_len, data.length);
		last_len += data.length;

		// System.arraycopy(CRLF, 0, dest, last_len, CRLF.length);
		// last_len += CRLF.length;

		System.arraycopy(trailer, 0, dest, last_len, trailer.length);
		last_len += trailer.length;

		// System.arraycopy(CRLF, 0, dest, last_len, CRLF.length);
	}

	/*
	 * String 문자열에서 오른쪽 패딩 값(" ")을 제거한다. 반각
	 */
	public static String removeR(String src) {
		char space = 0x20;
		int i = src.length() - 1;

		while (i >= 0) {
			if (src.charAt(i) != space) {
				break;
			}
			i--;
		}

		String dest = src.substring(0, i + 1);
		return dest;
	}

	/*
	 * String 문자열에서 왼쪽 패딩 값("0")을 제거한다. 반각
	 */
	public static String removeL(String src) {
		char zero = 0x30;
		int i = 0;

		for (i = 0; i < src.length(); i++) {
			if (src.charAt(i) != zero) {
				break;
			}
		}

		String dest = src.substring(i, src.length());
		return dest;
	}

	public static String toFullChar(String src) // 반각을 전각으로 변환한다
	{
		if (src == null)
			return null;
		StringBuffer strBuf = new StringBuffer();
		char c = 0;
		for (int i = 0; i < src.length(); i++) {
			c = src.charAt(i);
			// 영문 알파벳 이거나 특수 문자
			if (c >= 0x21 && c <= 0x7e)
				c += 0xfee0;
			// 공백
			else if (c == 0x20)
				c = 0x3000;
			strBuf.append(c);
		}
		return strBuf.toString();
	}

	public static String toHalfChar(String src) // 전각을 반각으로 변환한다
	{
		StringBuffer strBuf = new StringBuffer();
		char c = 0;
		for (int i = 0; i < src.length(); i++) {
			c = src.charAt(i);
			// 영문이거나 특수 문자 일경우.
			if (c >= '！' && c <= '～')
				c -= 0xfee0;
			// 공백
			else if (c == '　')
				c = 0x20;
			strBuf.append(c);
		}
		return strBuf.toString();
	}

	public static void creTelegram(byte[] telegram, byte[] common, byte[] data) {
		int last_len = 0;
		System.arraycopy(common, 0, telegram, 0, common.length);
		last_len += common.length;

		// System.arraycopy(CRLF, 0, dest, last_len, CRLF.length);
		// last_len += CRLF.length;

		System.arraycopy(data, 0, telegram, last_len, data.length);
		last_len += data.length;

	}

}