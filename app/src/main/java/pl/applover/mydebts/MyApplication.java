package pl.applover.mydebts;

import android.app.Application;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class MyApplication extends Application implements FirebaseAuth.AuthStateListener {

	@Override
	public void onCreate() {
		super.onCreate();

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.build();

		ImageLoader.getInstance().init(config);

		FirebaseAuth.getInstance().addAuthStateListener(this);


	}

	@Override
	public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
		FirebaseUser fireUser = firebaseAuth.getCurrentUser();

		if (fireUser != null) {
			User user = new User(fireUser);
			UserProfileChangeRequest builder = new UserProfileChangeRequest.Builder()
					.setPhotoUri(Uri.parse("http://applover.pl/debt/3.png"))
					.setDisplayName("Adrian Kuta")
					.build();


			fireUser.updateProfile(builder);

			DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
			reference
					.child("users")
					.child(fireUser.getUid())
					.setValue(user);
			FirebaseAuth.getInstance().removeAuthStateListener(MyApplication.this);

		}
	}
}
