package pl.applover.mydebts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {

	private static final String TAG = "DEBUG_TAG";
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mAuth = FirebaseAuth.getInstance();

		Button signUp = (Button) findViewById(R.id.sign_up_button);
		Button signIn = (Button) findViewById(R.id.sign_in_button);

		signIn.setOnClickListener(this);
		signUp.setOnClickListener(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		mAuth.removeAuthStateListener(this);
	}


	@Override
	public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
		FirebaseUser user = firebaseAuth.getCurrentUser();
		if (user != null) {
			// User is signed in
			startActivity(new Intent(this, MainActivity.class));
			finish();
			Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
		} else {
			// User is signed out
			Log.d(TAG, "onAuthStateChanged:signed_out");
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.sign_up_button)
			signUp();
		else if (id == R.id.sign_in_button)
			signIn();
	}

	private void signUp() {
		startActivity(new Intent(this, SignUpActivity.class));
	}

	private void signIn() {
		TextInputEditText mailEditText = (TextInputEditText) findViewById(R.id.mail);
		TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password);

		String mail = mailEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		mAuth.signInWithEmailAndPassword(mail, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

						// If sign in fails, display a message to the user. If sign in succeeds
						// the auth state listener will be notified and logic to handle the
						// signed in user can be handled in the listener.
						if (!task.isSuccessful()) {
							Snackbar.make(findViewById(R.id.sign_in_button), R.string.something_went_wrong, Snackbar.LENGTH_LONG)
									.setAction(android.R.string.ok, null);
						}
					}
				});
	}
}
