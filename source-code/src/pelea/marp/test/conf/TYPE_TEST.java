package pelea.marp.test.conf;

public enum TYPE_TEST {
	BYVALUE_NOISY_ACTIONS_ONE(1),
	BYVALUE_NOISY_ACTIONS_TWO(2),
	BYVALUE_NOISY_ACTIONS_THREE(3),
	BYACT_TRGS_NOISY_ACTIONS_ONE(4),
	BYACT_TRGS_NOISY_ACTIONS_TWO(5),
	BYACT_TRGS_NOISY_ACTIONS_THREE(6);
	
	int value;
	TYPE_TEST(int v){
		value = v;
	}
	
	public TYPE_TEST getType(int v){
		
		if(v == 1)
			return BYVALUE_NOISY_ACTIONS_ONE;
		if(v == 2)
			return BYVALUE_NOISY_ACTIONS_TWO;
		if(v == 3)
			return BYVALUE_NOISY_ACTIONS_THREE;
		if(v == 4)
			return BYACT_TRGS_NOISY_ACTIONS_ONE;
		if(v == 5)
			return BYACT_TRGS_NOISY_ACTIONS_TWO;
		
		return BYACT_TRGS_NOISY_ACTIONS_THREE;
		
	}
	
};