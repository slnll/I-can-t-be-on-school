package top.smallway.icantbeoncampus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final List<Person> personList;

    public MyAdapter(List<Person> personList) {
        this.personList = personList;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myadapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.title.setText(person.getTitle());
        holder.type.setText(person.getType());
        holder.time.setText(person.getTime());
        if (person.getStatus().equals("已签到")){
            holder.status.setBackgroundResource(R.color.gree);
        }else {
            holder.status.setBackgroundResource(R.color.red);
        }
        holder.status.setText(person.getStatus());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,type,time,status;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.Sign_title);
            this.type = itemView.findViewById(R.id.Sign_type);
            this.time = itemView.findViewById(R.id.Sign_time);
            this.status=itemView.findViewById(R.id.Sign_status);
        }
    }
}
