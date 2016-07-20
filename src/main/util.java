package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class util {

	//�x��.txt�ļ� 
	public static String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//������Ʒ��Ϣ
	public static Good ParseGoodsInfo(String inputStr) {
		Good good = new Good();

		int barS = inputStr.indexOf('\'');
		int barT = inputStr.indexOf('\'', barS + 1);
		good.setBarcode(inputStr.substring(barS+1, barT));

		int nameS = inputStr.indexOf('\'', barT + 1);
		int nameT = inputStr.indexOf('\'', nameS + 1);
		good.setName(inputStr.substring(nameS+1, nameT));

		int unitS = inputStr.indexOf('\'', nameT + 1);
		int unitT = inputStr.indexOf('\'', unitS + 1);
		good.setUnit(inputStr.substring(unitS+1, unitT));

		int CategoryS = inputStr.indexOf('\'', unitT + 1);
		int CategoryT = inputStr.indexOf('\'', CategoryS + 1);
		good.setCategory(inputStr.substring(CategoryS+1, CategoryT));

		int priceS = inputStr.indexOf(',', CategoryT + 1);
		String pri = inputStr.substring(priceS + 1, inputStr.length() - 1);
		good.setPrice(new Double(pri));

		return good;
	}
	//�����l�δa
	public static Vector<String> parseData(String inputStr){		
		Vector<String> res = new Vector<String>();
		int curs = inputStr.indexOf('\'');
		while(curs != -1){
			int curt = inputStr.indexOf('\'',curs+1);			
			String curStr = inputStr.substring(curs+1,curt);					
			res.add(curStr);			
			curs = inputStr.indexOf('\'',curt+1);
		}
		return res;
	}
}
