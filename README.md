# **UnProfitableStore**

## 需求描述:


###商店进行购物结算时会使用收银机系统，这台收银机会在结算时根据客户的购物车中的商品和商店正在进行的优惠活动进行结算和打印购物小票。

##language:java
##platform:windows eclipse 
##encoding: GBK


##注：商品信息，促销信息，购物清单，测试数据以及打印的小票均以‘.txt’的形式读取和输出。

####goodInfo.txt          商品信息
####promotionInfo*.txt    促销信息
####shopinglists.txt      购物清单
####*_test.txt            测试数据
####receipt.txt           购物小票

####src                   main/test 包
####build.gradle          gradle 构建信息

#
#### UnprofitableStore.java中包含main()函数
####对ShoppingList.java 进行测试 -> ShoppingListTest.java
