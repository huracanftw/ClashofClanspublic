package me.crolemol.coc.arena.panels;

public class Specs {
	public enum specsTownhall{
		lv1(0,0,0,1500),
		lv2(1000,17,5,1600),
		lv3(4000,103,180,1850),
		lv4(25000,293,1440,2100),
		lv5(150000,415,2880,2400),
		lv6(750000,587,5760,2800),
		lv7(1200000,720,8640,3200),
		lv8(2000000,831,11520,3700),
		lv9(3000000,929,14400,4200),
		lv10(4000000,1099,20160,5000);
		
		private int goldprice2 = 0;
		private int gainexp2 = 0;
		private int time2 = 0;
		private int health2 = 0;
		specsTownhall(int goldprice, int gainexp,int time,int health){
			goldprice2 = goldprice;
			gainexp2 = gainexp;
			time2 = time;
			health2 = health;
			
		}
		public int getGoldPrice(){
			return goldprice2;
		}
		public int getGainExp(){
			return gainexp2;
		}
		public int getUpgradeTime(){
			return time2;
		}
		public int getHealth(){
			return health2;
		}
	}
}
