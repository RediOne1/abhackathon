package pl.applover.mydebts;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class SelectUsersAdapter extends RecyclerView.Adapter<SelectUsersAdapter.ViewHolder> {

	private List<String> selectedIds;
	private SelectUserListener listener;
	private List<User> userList;

	public SelectUsersAdapter(List<User> userList, List<String> selectedIds, SelectUserListener listener) {
		this.userList = userList;
		this.selectedIds = selectedIds;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.single_select_person_layout, parent, false);
		ViewHolder holder = new ViewHolder(view, new ViewHolder.SelectUserListener() {
			@Override
			public void onUserSelected(int position) {
				User user = userList.get(position);
				if (listener != null)
					listener.onUserSelected(position, user);
			}

			@Override
			public void onUserUnselected(int position) {
				User user = userList.get(position);
				if (listener != null)
					listener.onUserUnselected(position, user);
			}
		});
		holder.name = (TextView) view.findViewById(R.id.username);
		holder.image = (ImageView) view.findViewById(R.id.user_image);
		holder.checkBox = (CheckBox) view.findViewById(R.id.select_person_checkbox);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		User user = userList.get(position);
		ImageLoader loader = ImageLoader.getInstance();

		holder.checkBox.setChecked(selectedIds.contains(user.getUid()));
		holder.name.setText("" + user.displayName);
		loader.displayImage(user.photoUrl, holder.image);


	}

	@Override
	public int getItemCount() {
		return userList.size();
	}

	public interface SelectUserListener {
		void onUserSelected(int position, User user);

		void onUserUnselected(int position, User user);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView name;
		public ImageView image;
		public CheckBox checkBox;

		public ViewHolder(View itemView, final SelectUserListener listener) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (listener != null)
						if (!checkBox.isChecked()) {
							checkBox.setChecked(true);
							listener.onUserSelected(getAdapterPosition());
						} else {
							checkBox.setChecked(false);
							listener.onUserUnselected(getAdapterPosition());
						}
					Log.d("DEBUG_TAG", "" + checkBox.isSelected());
				}
			});
		}

		interface SelectUserListener {
			void onUserSelected(int position);

			void onUserUnselected(int position);
		}
	}
}
