package pl.applover.mydebts.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    05.06.2016
 */
@IgnoreExtraProperties
public class Balance {
	public String user_id;
	public float paid;
	public float to_paid;
	public String event_id;

}
