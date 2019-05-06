package com.example.calcy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.CalcyHolder> {

    private ArrayList<Character>symbols;
    private ButtonListener bListener;
    public GreenAdapter(ArrayList<Character>symbols,ButtonListener bListener){
        this.symbols=symbols;
        this.bListener=bListener;
    }
    @NonNull
    @Override
    public CalcyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fn,viewGroup,false);
        return new CalcyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CalcyHolder calcyHolder, int i) {
        calcyHolder.tv.setText(String.valueOf(symbols.get(i)));
    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    public class CalcyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv;
        public CalcyHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            bListener.onCLickButton(pos);
        }
    }
    public interface ButtonListener{
        void onCLickButton(int id);
    }
}
