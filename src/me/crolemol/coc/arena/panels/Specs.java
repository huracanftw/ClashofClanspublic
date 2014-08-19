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
	
	public enum specsGoldMine{
		lv1(150,1,7,500,200,400,1),
		lv2(300,5,17,1000,400,450,1),
		lv3(700,15,30,1500,600,500,2),
		lv4(1400,60,60,2500,800,550,2),
		lv5(3000,120,84,10000,1000,590,3),
		lv6(7000,360,146,20000,1300,610,3),
		lv7(14000,720,207,30000,1600,630,4),
		lv8(28000,1440,293,50000,1900,660,4),
		lv9(56000,2880,415,75000,2200,680,5),
		lv10(84000,4320,509,100000,2500,710,5),
		lv11(168000,5760,587,150000,3000,750,7);
		int cost;
		int time;
		int exp;
		int capacity;
		int production;
		int health;
		int level;
		specsGoldMine(int cost2,int time2,int exp2, int capacity2,int production2,int health2,int level2){
			cost = cost2;
			time = time2;
			exp = exp2;
			capacity = capacity2;
			production = production2;
			health = health2;
			level = level2;
		}
		public int getElixirCost(){
			return cost;
		}
		public int getGainExp(){
			return exp;
		}
		public int getUpgradeTime(){
			return time;
		}
		public int getProduction(){
			return production;
		}
		public int getCapacity(){
			return capacity;
		}
		public int getHealth(){
			return health;
		}
		public int getMinTownhallLevel(){
			return level;
		}
		}
}
