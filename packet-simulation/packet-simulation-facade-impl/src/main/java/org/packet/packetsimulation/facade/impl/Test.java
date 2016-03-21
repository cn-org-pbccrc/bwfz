package org.packet.packetsimulation.facade.impl;


	public class Test {

		public static void main(String[] args) {
			 Test test = new Test();
	         System.out.println(test.lpad(5, 23));
		}
		private String lpad(int length, int number) {
	         String f = "%05d";
	         return String.format("%04d", 1234);
	     }
	}
