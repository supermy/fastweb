package org.supermy.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.LoggerFactory;

public class CommandUtil {
	private static org.slf4j.Logger log = LoggerFactory
			.getLogger(CommandUtil.class);

	public static void main(String[] args) {
		loadFastweb();
		backupFastweb();
	}
	

	public static void exec(String command) {
		log.debug("command exec:{}", command);
		try {
			Process process = Runtime.getRuntime().exec(command);
			InputStreamReader ir = new InputStreamReader(process
					.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			while ((line = input.readLine()) != null)
				log.debug(line);
			input.close();

		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void loadFastweb(){
		//load("fastweb","/home/my/source/fastweb/src/load-data.sql");
		
		load("fastweb",CommandUtil.class.getResource("load-data.sql").getPath());
		
	}
	
	public static void load(String db,String fPath) {
		try {
//			String fPath = "/home/my/source/fastweb/src/load-data.sql";
			Runtime rt = Runtime.getRuntime();

			Process child = rt
					.exec("mysql -uroot -pboecd "+db+" --default-character-set=utf8 ");
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();

			log.debug("/* Load OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void backupFastweb(){
		//backup("fastweb","/home/my/source/fastweb/src/load-data_backup.sql");
		backup("fastweb",CommandUtil.class.getResource("load-data_backup.sql").getPath());
	}

	public static void backup(String db,String sqlfile) {
		try {
			Runtime rt = Runtime.getRuntime();

			Process child = rt
					.exec("mysqldump -uroot -pboecd "+db+" --default-character-set=utf8 ");// 设置导出编码为utf8。这里必须是utf8

			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

			InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			// 要用来做导入用的sql目标文件：
			FileOutputStream fout = new FileOutputStream(sqlfile);
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();

			// 别忘记关闭输入输出流
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();

			System.out.println("/* Output OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
