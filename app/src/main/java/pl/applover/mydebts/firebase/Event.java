package pl.applover.mydebts.firebase;

import com.google.firebase.database.DatabaseReference;
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
public class Event {

	public String title;
	private String key;
	public Map<String, Boolean> connected_users = new HashMap<>();

	public Event() {
	}

	public Event(String title){
		this.title = title;
	}

	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("events");
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
