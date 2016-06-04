package pl.applover.mydebts.firebase;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class User implements FireObject{

	public String displayName;
	public String mail;
	public Map<String, Boolean> connected_users = new HashMap<>();
	public String photoUrl;
	private String key;

	public User() {

	}

	public User(String mail) {
		this.mail = mail;
	}

	public User(FirebaseUser user) {
		mail = user.getEmail();
		displayName = user.getDisplayName();
		Uri uri = user.getPhotoUrl();
		if (uri != null)
			photoUrl = uri.toString();
	}

	public List<String> getConnectedUsersIds() {
		List<String> usersIds = new ArrayList<>();
		for (String id : connected_users.keySet())
			if (connected_users.get(id))
				usersIds.add(id);
		return usersIds;
	}

	public String getUid() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}