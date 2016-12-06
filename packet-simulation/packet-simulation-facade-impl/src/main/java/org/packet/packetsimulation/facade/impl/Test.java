package org.packet.packetsimulation.facade.impl;


	public class Test {

		public static void main(String[] args) {
			 Test test = new Test();
	         System.out.println(test.adjustLength(4, 0, ""));
		}
		private String adjustLength(int itemLength, int itemType, String itemValue){
			if(itemType == 0 && !itemValue.equals("")){
				//itemValue = String.format("%010d", 11L);
				//itemValue = String.format("%0" + itemLength + "d", Integer.parseInt(itemValue));
				while (itemValue.length() < itemLength) {  
					StringBuffer sb = new StringBuffer();  
					sb.append("0").append(itemValue);//左补0  				
					itemValue = sb.toString();
				}
			}else{
				itemValue = String.format("%-" + itemLength + "s", itemValue);
			}
			return itemValue;
		}
	}
