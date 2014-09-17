package me.crolemol.coc.utils;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.util.com.google.common.collect.Iterables;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
import net.minecraft.util.org.apache.commons.codec.Charsets;
import net.minecraft.util.org.apache.commons.codec.binary.Base64;

public class OwnClass {

	@SuppressWarnings("deprecation")
	public WrappedGameProfile changetextures2(Type textureType, String url) {
		final WrappedGameProfile profile = new WrappedGameProfile(Bukkit
				.getServer().getOfflinePlayer("Notch").getUniqueId(), "Notch");
		MinecraftServer.getServer().av().fillProfileProperties(profile, false);
		String json = new String(Base64.decodeBase64(Iterables.getFirst(
				profile.getProperties().get("textures"), null).getValue()),
				Charsets.UTF_8);
		String textureString = textureType.name();
		String[] jsonarray = json.split("\"" + textureString, 2);
		String jsonarray2 = jsonarray[1].split("}", 2)[1];
		String newproperty = String.format(jsonarray[0] + "\"" + textureString
				+ "\":{\"url\":\"" + url + "\"" + jsonarray2);
		String encodedprop = Base64.encodeBase64String(newproperty.getBytes(Charsets.UTF_8));
		WrappedProperty prop = new WrappedProperty("textures", encodedprop);
		PropertyMap map = profile.getProperties();
		map.put("textures", prop);
		profile.setProperties(map);
		return profile;
	}

	@SuppressWarnings("deprecation")
	public GameProfile getgp() {
		GameProfile gp = new GameProfile(Bukkit.getPlayer("trolbanaan")
				.getUniqueId(), "trolbanaan");
		MinecraftServer.getServer().av().fillProfileProperties(gp, true);
		return gp;
	}

	public String getSignature() {
		String json = new String(Base64.decodeBase64(Iterables.getFirst(
				getgp().getProperties().get("textures"), null).getValue()),
				Charsets.UTF_8);
		return json;
	}
}
