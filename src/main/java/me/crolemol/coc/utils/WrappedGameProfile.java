package me.crolemol.coc.utils;
import java.util.UUID;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;

public class WrappedGameProfile extends GameProfile{
	private PropertyMap properties;
	public WrappedGameProfile(GameProfile gameprofile){
		this(gameprofile.getId(),gameprofile.getName());
	}
	
	public WrappedGameProfile(UUID id, String name) {
		super(id, name);
			properties = super.getProperties();
	}
	@Override
	public PropertyMap getProperties(){
		return properties;
	}
	public void setProperties(PropertyMap map){
		properties = map;
	}

}
