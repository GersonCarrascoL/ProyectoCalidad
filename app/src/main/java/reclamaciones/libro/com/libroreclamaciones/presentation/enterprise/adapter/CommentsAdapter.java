package reclamaciones.libro.com.libroreclamaciones.presentation.enterprise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.data.model.Comentario;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>{

    List<Comentario> comments;

    public CommentsAdapter(List<Comentario> comments){
        this.comments = comments;
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personComment;
        TextView commentDate;
        RatingBar personRating;
        CommentsViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.comment_card);
            personName = itemView.findViewById(R.id.person_name);
            personRating = itemView.findViewById(R.id.person_rating);
            personComment = itemView.findViewById(R.id.person_comment);
            commentDate = itemView.findViewById(R.id.comment_date);
        }
    }


    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card,parent,false);
        CommentsViewHolder cvh = new CommentsViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.personName.setText(comments.get(position).getNombreUsuario());
        holder.personComment.setText(comments.get(position).getComentario());
        holder.commentDate.setText(comments.get(position).getFecha());
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

}
