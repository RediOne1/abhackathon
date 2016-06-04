package pl.applover.mydebts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder> {

	private List<User> userList;

	public PersonsAdapter(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.single_person_layout, parent, false);
		ViewHolder holder = new ViewHolder(view);
		holder.image = (ImageView) view.findViewById(R.id.user_image);
		holder.username = (TextView) view.findViewById(R.id.username);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		User user = userList.get(position);
		holder.username.setText(user.displayName);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.photoUrl, holder.image);
	}

	@Override
	public int getItemCount() {
		return userList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView image;
		public TextView username;

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
