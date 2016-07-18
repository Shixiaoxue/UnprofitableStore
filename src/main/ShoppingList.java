package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

public class ShoppingList {
	private Vector<ShoppingTerm> shoplist;
	private Vector<Promotion> goodoffer;
	
	public ShoppingList(String inputStr, Map<String, Good> goods, Vector<Promotion> goodoffer) {
		// TODO Auto-generated constructor stub
		
		shoplist = new Vector<ShoppingTerm>();
		this.goodoffer = goodoffer;
		
		Vector<String> goodinfo = parseData(inputStr);
		
		for(String str : goodinfo){	
			int inter = str.indexOf('-');
			String barcode = str.substring(0,inter);
			int num = Integer.parseInt(str.substring(inter+1));
			
			Good curGood = goods.get(barcode);
			ShoppingTerm term = new ShoppingTerm(curGood, num);
			shoplist.add(term);
		}
		//printShoppingList();
	}
	
	/**
	 * ��ӡ�����嵥
	 * @throws FileNotFoundException 
	 */
	public String printShoppingList() throws FileNotFoundException 
	{
		PrintStream printStream = new PrintStream(new FileOutputStream("receipt.txt"));
        System.setOut(printStream);
		
		DecimalFormat df = new DecimalFormat("######0.00");
		double total = 0.0;
		double save = 0.0;
		
//		String twoForOneInfo = "�����һ��Ʒ��\n";
		
		boolean twoforOneFlag = false;
		
		System.out.println("*<ûǮ׬�̵�>�����嵥*");
		for(ShoppingTerm term : shoplist){
			
			String printStr = "";
			printStr += "���ƣ�"+term.getGood().getName()+"�� ";
			printStr += "������"+term.getNum()+term.getGood().getUnit()+"��";
			
//			DecimalFormat df = new DecimalFormat("######0.00");
			double price = term.getGood().getPrice();
			double subtotal = price * term.getNum();
			printStr += "���ۣ�"+df.format(price)+"��Ԫ����";
			
			// �Ż���Ϣ�ж�
			for(Promotion promotion : goodoffer){				
				if(promotion.isPromoting(term.getGood().getBarcode())){	
					
					if(!twoforOneFlag){
						twoforOneFlag = promotion instanceof TwoForOnePromotion;
					}
					term.setPromotion(promotion);			
					break;
				}
			}
			
			double subsave = term.getSave();
			save += subsave;
			
			printStr += "С�ƣ�"+df.format(subtotal-subsave)+"��Ԫ��";	
			
			if(term.getPromotion() instanceof DiscountPromotion){
				printStr += ",��ʡ"+df.format(subsave)+"��Ԫ��";
			}
			
			System.out.println(printStr);	
			total += subtotal;	
		}
		
		System.out.println("---------------------------------------------");
		
		if(twoforOneFlag){	
			System.out.println("�����һ��Ʒ��"); 
			for(ShoppingTerm term : shoplist){
				if(term.getPromotion() instanceof TwoForOnePromotion){
					System.out.println("���ƣ�"+term.getGood().getName()+","+"������"+ term.getNum() / 3);
				}		
			}
		}
		
		System.out.println("---------------------------------------------");
		String printStr = "";
		printStr += "�ܼƣ�"+df.format(total-save)+"��Ԫ��\n";
		if(save > 1e-8){
			printStr += "��ʡ��"+df.format(save)+"��Ԫ��\n";
		}
		System.out.println(printStr);
		System.out.println("*********************************************");
		
		String receipt = txt2String(new File("receipt.txt"));
		return receipt;
	}
	
	public String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                result = result + "\n" +s;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
	
	/**
	 * ������������������
	 * @param inputStr
	 * @return
	 */
	private Vector<String> parseData(String inputStr){		
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
