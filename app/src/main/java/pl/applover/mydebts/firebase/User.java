package pl.applover.mydebts.firebase;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
@IgnoreExtraProperties
public class User {

	public String displayName;
	public String mail;
	public Map<String, Boolean> connected_users = new HashMap<>();
	public String photoUrl;
	public double price;
	public int eventsCount;
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

	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("users");
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

	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("displayName", displayName);
		result.put("mail", mail);
		result.put("photoUrl", photoUrl);
		result.put("connected_users", connected_users);
		result.put("eventsCount", eventsCount);
		result.put("price", price);
		return result;
	}
}
