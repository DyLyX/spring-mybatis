package com.cn.dyl.util.aes;

public class AES {

	// AES算法几个变换函数
	private byte[][] sbox = {
			{99,124,119,123,-14,107,111,-59,48,1,103,43,-2,-41,-85,118},
			{-54,-126,-55,125,-6,89,71,-16,-83,-44,-94,-81,-100,-92,114,-64},
			{-73,-3,-109,38,54,63,-9,-52,52,-91,-27,-15,113,-40,49,21},
			{4,-57,35,-61,24,-106,5,-102,7,18,-128,-30,-21,39,-78,117},
			{9,-125,44,26,27,110,90,-96,82,59,-42,-77,41,-29,47,-124},
			{83,-47,0,-19,32,-4,-79,91,106,-53,-66,57,74,76,88,-49},
			{-48,-17,-86,-5,67,77,51,-123,69,-7,2,127,80,60,-97,-88},
			{81,-93,64,-113,-110,-99,56,-11,-68,-74,-38,33,16,-1,-13,-46},
			{-51,12,19,-20,95,-105,68,23,-60,-89,126,61,100,93,25,115},
			{96,-127,79,-36,34,42,-112,-120,70,-18,-72,20,-34,94,11,-37},
			{-32,50,58,10,73,6,36,92,-62,-45,-84,98,-111,-107,-28,121},
			{-25,-56,55,109,-115,-43,78,-87,108,86,-12,-22,101,122,-82,8},
			{-70,120,37,46,28,-90,-76,-58,-24,-35,116,31,75,-67,-117,-118},
			{112,62,-75,102,72,3,-10,14,97,53,87,-71,-122,-63,29,-98},
			{-31,-8,-104,17,105,-39,-114,-108,-101,30,-121,-23,-50,85,40,-33},
			{-116,-95,-119,13,-65,-26,66,104,65,-103,45,15,-80,84,-69,22},
	};
	private byte[][] rsbox = {
			{82,9,106,-43,48,54,-91,56,-65,64,-93,-98,-127,-13,-41,-5},
			{124,-29,57,-126,-101,47,-1,-121,52,-114,67,68,-60,-34,-23,-53},
			{84,123,-108,50,-90,-62,35,61,-18,76,-107,11,66,-6,-61,78},
			{8,46,-95,102,40,-39,36,-78,118,91,-94,73,109,-117,-47,37},
			{114,-8,-10,100,-122,104,-104,22,-44,-92,92,-52,93,101,-74,-110},
			{108,112,72,80,-3,-19,-71,-38,94,21,70,87,-89,-115,-99,-124},
			{-112,-40,-85,0,-116,-68,-45,10,-9,-28,88,5,-72,-77,69,6},
			{-48,44,30,-113,-54,63,15,2,-63,-81,-67,3,1,19,-118,107},
			{58,-111,17,65,79,103,-36,-22,-105,-14,-49,-50,-16,-76,-26,115},
			{-106,-84,116,34,-25,-83,53,-123,-30,-7,55,-24,28,117,-33,110},
			{71,-15,26,113,29,41,-59,-119,111,-73,98,14,-86,24,-66,27},
			{-4,86,62,75,-58,-46,121,32,-102,-37,-64,-2,120,-51,90,-12},
			{31,-35,-88,51,-120,7,-57,49,-79,18,16,89,39,-128,-20,95},
			{96,81,127,-87,25,-75,74,13,45,-27,122,-97,-109,-55,-100,-17},
			{-96,-32,59,77,-82,42,-11,-80,-56,-21,-69,60,-125,83,-103,97},
			{23,43,4,126,-70,119,-42,38,-31,105,20,99,85,33,12,125}
	};
	private byte[][] mut = {
			{2,3,1,1},
			{1,2,3,1},
			{1,1,2,3},
			{3,1,1,2}
	};
	private byte[][] rmut = {
			{14,11,13,9},
			{9,14,11,13},
			{13,9,14,11},
			{11,13,9,14}
	};
	private byte[] by = {1,2,4,8,16,
			32,64,-128,27,54,
			108,-40,-85,77,-102,};
	private int[] r = { 0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80,
			0x1b, 0x36 };
	byte[][][] allkey = new byte[11][4][4];
	byte[] enbyte = new byte[16];
	byte[] debyte = new byte[16];

	// 字节替代，
	public byte[][] subbyte(byte[][] sub) {
		byte row, col;
		byte[][] temp = new byte[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				col = (byte) (sub[i][j] & 0xf);
				row = (byte) ((sub[i][j] >> 4) & 0xf);
				temp[i][j] = sbox[row][col];
			}
		return temp;
	}

	public byte[][] subbyte(byte[][] sub, int r) {
		byte row, col;
		byte[][] temp = new byte[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				col = (byte) (sub[i][j] & 0xf);
				row = (byte) ((sub[i][j] >> 4) & 0xf);
				temp[i][j] = rsbox[row][col];
			}
		return temp;
	}

	// 行移位
	public byte[][] shift(byte[][] sub) {
		byte temp;
		temp = sub[1][0];
		sub[1][0] = sub[1][1];
		sub[1][1] = sub[1][2];
		sub[1][2] = sub[1][3];
		sub[1][3] = temp;
		temp = sub[2][0];
		sub[2][0] = sub[2][2];
		sub[2][2] = temp;
		temp = sub[2][1];
		sub[2][1] = sub[2][3];
		sub[2][3] = temp;
		temp = sub[3][0];
		sub[3][0] = sub[3][3];
		sub[3][3] = sub[3][2];
		sub[3][2] = sub[3][1];
		sub[3][1] = temp;
		return sub;
	}

	public byte[][] shift(byte[][] sub, int mode) {
		byte temp;
		temp = sub[3][0];
		sub[3][0] = sub[3][1];
		sub[3][1] = sub[3][2];
		sub[3][2] = sub[3][3];
		sub[3][3] = temp;
		temp = sub[2][0];
		sub[2][0] = sub[2][2];
		sub[2][2] = temp;
		temp = sub[2][1];
		sub[2][1] = sub[2][3];
		sub[2][3] = temp;
		temp = sub[1][0];
		sub[1][0] = sub[1][3];
		sub[1][3] = sub[1][2];
		sub[1][2] = sub[1][1];
		sub[1][1] = temp;
		return sub;
	}

	// 列混合
	public byte[][] mix(byte[][] sub) {
		byte count = 0;
		byte[][] temp = new byte[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				while (count < 4) {
					temp[i][j] = (byte) (temp[i][j] ^ mu(mut[i][count],
							sub[count][j]));
					count++;
				}
				count = 0;
			}

		}
		return temp;
	}

	public byte[][] mix(byte[][] sub, int mode) {
		byte count = 0;
		byte[][] temp = new byte[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				while (count < 4) {
					temp[i][j] = (byte) (temp[i][j] ^ mu(rmut[i][count],
							sub[count][j]));
					count++;
				}
				count = 0;
			}

		}
		return temp;
	}

	// 轮密钥加
	public byte[][] add(byte sub[][], byte[][] roundkey) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				sub[i][j] = (byte) (sub[i][j] ^ roundkey[i][j]);
			}
		return sub;
	}

	// 列混合中所用函数
	public byte mu(byte b, byte c) {
		byte ret = 0, count1 = 0, count2 = 0;
		byte[] barray = new byte[8];
		byte[] carray = new byte[8];
		byte[] pro = new byte[15];
		if (b == 1 | c == 0)
			return c;
		if (c == 1)
			return b;
		for (int i = 0; i < 8 && b != 0; i++) {
			barray[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
			count1++;
		}
		for (int i = 0; i < 8 && c != 0; i++) {
			carray[i] = (byte) (c & 1);
			c = (byte) (c >> 1);
			count2++;
		}
		for (int i = 0; i < count1; i++)
			for (int j = 0; j < count2; j++) {
				if (barray[i] > 0 & carray[j] > 0)
					pro[i + j] = (byte) ((pro[i + j] + 1) % 2);
			}
		for (int m = 0; m < count1 + count2; m++) {
			if (pro[m] > 0)
				ret = (byte) ((by[m]) ^ (ret));

		}
		return ret;
	}

	// 密钥扩展
	public byte[][][] key(byte[][] okey) {
		byte[][][] retarray = new byte[11][4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				retarray[0][j][i] = okey[i][j];
			}
		for (int i = 1; i < 11; i++) {
			retarray[i] = tkey(retarray[i - 1], r[i]);
		}
		return retarray;
	}

	// 密钥扩展中所用函数
	public byte[][] tkey(byte[][] okey, int ri) {
		byte[][] temp = new byte[4][4];
		byte col, row;
		col = (byte) (okey[1][3] & 0xf);
		row = (byte) ((okey[1][3] >> 4) & 0xf);
		temp[0][0] = (byte) (ri ^ sbox[row][col] ^ okey[0][0]);
		col = (byte) (okey[2][3] & 0xf);
		row = (byte) ((okey[2][3] >> 4) & 0xf);
		temp[1][0] = (byte) (sbox[row][col] ^ okey[1][0]);
		col = (byte) (okey[3][3] & 0xf);
		row = (byte) ((okey[3][3] >> 4) & 0xf);
		temp[2][0] = (byte) (sbox[row][col] ^ okey[2][0]);
		col = (byte) (okey[0][3] & 0xf);
		row = (byte) ((okey[0][3] >> 4) & 0xf);
		temp[3][0] = (byte) (sbox[row][col] ^ okey[3][0]);
		for (int i = 1; i < 4; i++) {
			temp[0][i] = (byte) (temp[0][i - 1] ^ okey[0][i]);
			temp[1][i] = (byte) (temp[1][i - 1] ^ okey[1][i]);
			temp[2][i] = (byte) (temp[2][i - 1] ^ okey[2][i]);
			temp[3][i] = (byte) (temp[3][i - 1] ^ okey[3][i]);
		}
		return temp;
	}

	// 格式化输入密钥
	public byte[][] kformat(String key) {
		byte[][] b = new byte[4][4];
		int[] ret = new int[16];
		int t, count = 0, i = 0;
		for (; i + count < 15 & (i < key.length()); i++) {
			t = key.charAt(i);
			if (t < 128)
				ret[i + count] = t;
			else {
				ret[i + count + 1] = t & 0xff;
				t = t >> 8;
				ret[i + count] = t;
				count++;
			}
		}
		if (i + count == 15)
			if (key.charAt(i) < 128)
				ret[15] = key.charAt(i);
		for (int j = 0; j < 4; j++)
			for (int k = 0; k < 4; k++)
				b[j][k] = (byte) ret[j * 4 + k];

		return b;
	}

}
