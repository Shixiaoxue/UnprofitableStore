package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UnprofitableStore {

	private Map<String, Good> goods; // ���������Ψһ��ʶ��Ʒ
	private Vector<Promotion> goodoffer; // �Ż��嵥����¼�����Żݵ���Ʒ��������

	public UnprofitableStore() throws IOException {
		stock();
		setOfferInfo();
	}

	// ����
	public void stock() throws IOException   {
		goods = new HashMap<String, Good>();
		File file = new File("goodsInfo.txt");
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
			BufferedReader bufferedReader = new BufferedReader(read);
			String inputStr = null;
			while ((inputStr = bufferedReader.readLine()) != null) {
				goods.put(util.ParseGoodsInfo(inputStr).getBarcode(), util.ParseGoodsInfo(inputStr));
			}
			bufferedReader.close();
		} else {
			System.out.println("�Ҳ���ָ�����ļ���");
		}
	}

	// �Żݻ
	public void setOfferInfo() throws IOException {
		goodoffer = new Vector<Promotion>();
		Vector<String> barcodes = new Vector<String>(); // �����һ
		
		String Promotion_twoForOne = util.txt2String(new File("promotionInfo_twoForOne.txt"));
		barcodes = util.parseData(Promotion_twoForOne);
		
		goodoffer.add(new TwoForOnePromotion(barcodes, 1));

		Vector<String> discount = new Vector<String>(); // 95��
		
		String promotion_95Discount = util.txt2String(new File("promotionInfo_95Discount.txt"));
		discount = util.parseData(promotion_95Discount);
		
		goodoffer.add(new DiscountPromotion(discount, 2));

		// ���Żݻ�����ȼ�����
		Collections.sort(goodoffer, new Comparator<Promotion>() {
			public int compare(Promotion left, Promotion right) {
				Promotion l = (Promotion) left;
				Promotion r = (Promotion) right;
				return l.getLevel() - r.getLevel();
			}
		});
	}

	public Map<String, Good> getGoods() {
		return goods;
	}

	public void setGoods(Map<String, Good> goods) {
		this.goods = goods;
	}

	public Vector<Promotion> getGoodoffer() {
		return goodoffer;
	}

	public void setGoodoffer(Vector<Promotion> goodoffer) {
		this.goodoffer = goodoffer;
	}

	public static void main(String[] args) throws IOException {
		
		UnprofitableStore store = new UnprofitableStore();
		String data = util.txt2String(new File("shopinglists.txt"));
		ShoppingList shoplist = new ShoppingList(data, store.goods, store.goodoffer);
		shoplist.printShoppingList();
	}
}
