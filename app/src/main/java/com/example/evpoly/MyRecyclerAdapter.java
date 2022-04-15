//Rrcycler View Adapter
package com.example.evpoly;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements ItemTouchHelperListener {

    private ArrayList<FriendItem> mFriendList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mFriendList.get(position));
    }

    public void setFriendList(ArrayList<FriendItem> list){
        this.mFriendList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mFriendList.size();
    }



    @Override
    public boolean onItemMove(int from_position, int to_position) {
//        ChattingListDataModel number = mFriendList.get(from_position); // 이동할 객체 저장
//        mFriendList.remove(from_position); // 이동할 객체 삭제
//        mFriendList.add(to_position , number); // 이동하고 싶은 position 에 추가
//        notifyItemMoved(from_position,to_position); // Adapter에 데이터 이동알림
         return true;
   }
    @Override
    public void onItemSwipe(int position) {
        /*onItemSwipe*/
        //아이템의 포지션값을 받아 해당 아이템을 Swipe할때 로직을 구현
        mFriendList.remove(position); // 스와이프 한 객체 삭제
        notifyItemRemoved(position); //Adapter에 데이터 이동알림
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.profile);
            name = (TextView) itemView.findViewById(R.id.name);
            message = (TextView) itemView.findViewById(R.id.message);

        }

        void onBind(FriendItem item) {
            //profile.setImageResource(item.getResourceId());
            name.setText(item.getName());
            message.setText(item.getMessage());
            Log.i("MyRecyclerAdapter", item.getMessage());
            if (item.getMessage().startsWith("EV") == false) {
                //showButtonAlertDialog();
                itemView.setBackgroundColor(Color.YELLOW);

            }
        }



    }
}
