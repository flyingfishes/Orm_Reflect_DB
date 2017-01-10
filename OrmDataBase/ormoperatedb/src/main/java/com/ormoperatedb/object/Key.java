package com.ormoperatedb.object;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class Key extends Item {
	private boolean identity;

	public boolean isIdentity() {
		return identity;
	}

	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "Key [identity=" + identity + "]";
	}
	

}
