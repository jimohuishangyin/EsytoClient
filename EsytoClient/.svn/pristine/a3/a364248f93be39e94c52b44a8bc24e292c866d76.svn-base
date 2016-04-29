package com.ec2.yspay.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import android.content.Context;
import android.content.res.AssetManager;

public class RSAUtils {

	public static String PublicKey = "PublicKey.txt";
	private static final String PATH = Constants.PATH_FILE;
	private static final String FILE_NAME = "PublicKey.txt";

	// private static String PrivateKey = "D:/PrivateKey.txt";

	public static String PublicKey(Context context) {
		File dbFile = null;
		String filePath = PATH + "/" + FILE_NAME;
		try {
			File dir = new File(PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!FileTools.fileIsExists(filePath)) {
				FileOutputStream os = new FileOutputStream(filePath);
				AssetManager assetManager = context.getAssets();
				InputStream is = assetManager.open(PublicKey);
				byte[] buffer = new byte[1024 * 10];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
				os.flush();
				os.close();
				is.close();
				Log.d("assetPublicKey", " assetPublicKey copy success...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
		
	}


	public static KeyPair getKeyPair(String filePath, Context context)
			throws Exception {
		File file = new File(filePath);
		FileInputStream is = new FileInputStream(file);  
		ObjectInputStream oos = new ObjectInputStream(is);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		is.close();
		return kp;
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// RSAPublicKey rsap = (RSAPublicKey)
		// RSAUtil.generateKeyPair().getPublic();
		// String test = "hello world";
		String test = "{params:{name:你好，易商云;id:13579}};{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}"
				+ "{params:{name:你好，易商云;id:2}}" + "{params:{name:你好，易商云;id:2}}";// 要加密的字符串
																				// byte[]
																				// en_test
																				// =
																				// encrypt(getKeyPair(PublicKey).getPublic(),
																				// test.getBytes());
		// byte[] de_test = decrypt(getKeyPair(PrivateKey).getPrivate(),
		// en_test);
		// System.out.println(new String(de_test));
	}

}
