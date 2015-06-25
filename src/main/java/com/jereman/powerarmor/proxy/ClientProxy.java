package com.jereman.powerarmor.proxy;

import com.jereman.powerarmor.init.Blocks;
import com.jereman.powerarmor.init.JeremanItems;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders(){
		JeremanItems.registerRenders();
		Blocks.registerRenders();
	}
		
}
