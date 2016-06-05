package pl.applover.mydebts.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * author:  Adrian Kuta
 * date:    05.06.2016
 */
@IgnoreExtraProperties
public class Balance {
	public String user_id;
	public double paid;
	public double expectedValue;
	public String event_id;

	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("balance");
	}

	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("user_id", user_id);
		result.put("paid", paid);
		result.put("expectedValue", expectedValue);
		result.put("event_id", event_id);

		return result;
	}
}
