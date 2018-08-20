package bruno.santos.projetofinal.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bruno.santos.projetofinal.R;
import bruno.santos.projetofinal.model.Guitarra;

public class GuitarraAdapter extends RecyclerView.Adapter {


        private Context context;
        private ArrayList<Guitarra> guitarras;

        //segundo - criar o atributo
        private static ClickListener clickListener;

        public GuitarraAdapter(Context context, ArrayList<Guitarra> guitarras) {
            this.context = context;
            this.guitarras = guitarras;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.linha_guitarras,
                    parent,
                    false);

            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            //getView()
            ViewHolder h = (ViewHolder) holder;

            //Buscando obj cliente do arraylist
            Guitarra g = guitarras.get(position);

            h.tvMarca.setText(g.getMarca());
            h.tvModelo.setText(g.getModelo());
            h.tvPreco.setText(String.valueOf(g.getPreco()));
            h.tvAno.setText(String.valueOf(g.getAno()));


        }

        @Override
        public int getItemCount() {
            return guitarras.size();
        }

        //terceiro - Implementar OnClickListener e OnLongClickListener
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            private final TextView tvMarca;
            private final TextView tvModelo;
            private final TextView tvAno;
            private final TextView tvPreco;

            public ViewHolder(View itemView) {
                super(itemView);

                //Quarto passo - setar os listeners
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

                tvMarca =  itemView.findViewById(R.id.lc_tv_marca);
                tvModelo=  itemView.findViewById(R.id.lc_tv_modelo);
                tvPreco =  itemView.findViewById(R.id.lc_tv_ano);
                tvAno =  itemView.findViewById(R.id.lc_tv_preco);
            }

            @Override
            public void onClick(View view) {
                //quinto passo - setar onItemClick
                clickListener.onItemClick(getAdapterPosition(), view);
            }

            @Override
            public boolean onLongClick(View view) {
                //sexto passo - setar onItemLongClick
                clickListener.onItemLongClick(getAdapterPosition(), view);
                return true;
            }
        }//fecha classe

        //sétimo passo - criar método para receber o ClickListener da MainActivity
        public void setOnItemClickListener(ClickListener clickListener){
            GuitarraAdapter.clickListener = clickListener;
        }

        //primeiro - criar interface
        public interface ClickListener {
            void onItemClick(int position, View v);
            void onItemLongClick(int position, View v);
        }
}
