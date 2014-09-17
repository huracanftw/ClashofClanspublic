package me.crolemol.coc.utils;

import java.security.PublicKey;

import net.minecraft.util.com.mojang.authlib.properties.Property;

public class WrappedProperty extends Property{

	public WrappedProperty(String value, String name) {
		super(value, name);

	}
	public WrappedProperty(String value, String name,String signature) {
		super(name, value, signature);

	}
	@Override
	public boolean isSignatureValid(PublicKey key){
		return true;
	}
	@Override
	public boolean hasSignature(){
		return false;
	}
}
