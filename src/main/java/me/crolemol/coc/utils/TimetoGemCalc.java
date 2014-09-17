package me.crolemol.coc.utils;

public class TimetoGemCalc {
	public int Calc(Long time3){
		int gems;
		if(time3 == 0){
			gems= 0;
			return gems;
		}
		if(time3 > 0 && time3 <60){
			gems = 1;
			return gems;
		}
		if(time3 > 60 && time3 < 3600){
			gems = (int) (((20-1)/(3600-60))*(time3 - 60)+1);
			return gems;
		}
		if(time3 > 3600 && time3 < 86400){
			gems = (int) (((260-20)/(86400-3600))*(time3 - 3600)+20);
			return gems;
		}
		if(time3 > 86400){
			gems = (int) (((1000-260)/(604800-86400))*(time3 - 86400)+260);
			return gems;
		}
		return 0;

	}
}
