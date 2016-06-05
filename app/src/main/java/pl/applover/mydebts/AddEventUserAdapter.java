package pl.applover.mydebts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import pl.applover.mydebts.firebase.Balance;
import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    05.06.2016
 */
public class AddEventUserAdapter extends RecyclerView.Adapter<AddEventUserAdapter.ViewHolder> {

	private List<Balance> balanceList;

	public AddEventUserAdapter(List<Balance> balanceList) {
		this.balanceList = balanceList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.single_add_event_person_layout, parent, false);
		ViewHolder holder = new ViewHolder(view);
		holder.image = (ImageView) view.findViewById(R.id.user_image);
		holder.username = (TextView) view.findViewById(R.id.username);
		return holder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		Balance balance = balanceList.get(position);
		final String user_id = balance.user_id;
		DatabaseReference reference = User.getDatabaseReference().child(user_id);
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				User user = dataSnapshot.getValue(User.class);
				holder.username.setText("" + user.displayName);
				ImageLoader loader = ImageLoader.getInstance();
				loader.displayImage(user.photoUrl, holder.image);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});



	}

	@Override
	public int getItemCount() {
		return balanceList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView image;
		public TextView username;

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
