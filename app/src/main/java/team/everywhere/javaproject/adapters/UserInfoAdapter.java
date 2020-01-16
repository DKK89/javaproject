package team.everywhere.javaproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import team.everywhere.javaproject.R;
import team.everywhere.javaproject.models.User;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ItemViewHolder> {
    Context context;
    ArrayList<User> userArrayList;

    public UserInfoAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_info, parent, false);
        return new UserInfoAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(userArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdx, tvId, tvEmail, tvName, tvSex, tvAge;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdx = itemView.findViewById(R.id.tvIdx);
            tvId = itemView.findViewById(R.id.tvId);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvName = itemView.findViewById(R.id.tvName);
            tvSex = itemView.findViewById(R.id.tvSex);
            tvAge = itemView.findViewById(R.id.tvAge);
        }

        public void onBind(User user) {
            tvIdx.setText(String.valueOf(user.getIdx()));
            tvId.setText(user.getId());
            tvEmail.setText(user.getEmail());
            tvName.setText(user.getName());
            String sex;

            if (user.getSex().equals("0")) {
                sex = "남자";
            } else {
                sex = "여자";
            }
            tvSex.setText(sex);

            tvAge.setText(String.valueOf(user.getAge()));
        }
    }
}
