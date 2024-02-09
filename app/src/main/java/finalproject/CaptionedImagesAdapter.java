package finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import ps.example.mmoy.R;

public class CaptionedImagesAdapter
        extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private Book[] book;

    public CaptionedImagesAdapter(Book[] book) {
        this.book = book;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.mah_card_captioned_image,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), book[position].getImageID());
        imageView.setImageDrawable(dr);
        TextView txt = (TextView) cardView.findViewById(R.id.txtName);
        txt.setText(book[position].getTitle());
        TextView cat = (TextView) cardView.findViewById(R.id.txtCat);
        cat.setText(book[position].getCategory());
        TextView des = (TextView) cardView.findViewById(R.id.txtDes);
        des.setText(book[position].getDesc());
        TextView price = (TextView) cardView.findViewById(R.id.txtPrice);
        price.setText(book[position].getPrice() + "");

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSee.title = book[position].getTitle();
                bookSee.category = book[position].getCategory();
                bookSee.description = book[position].getDesc();
                bookSee.price = book[position].getPrice();

//                String url = "http://10.0.2.2:5000/getID/" + book[position].getTitle();
//                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                        null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject obj = response.getJSONObject(i);
//                                bookList.add(new Book(obj.getString("title"), obj.getString("category"), obj.getString("desc"), Double.parseDouble(obj.getString("price")), R.drawable.diavolo));
//                            } catch (JSONException exception) {
//                                Log.d("Error", exception.toString());
//                            }
//                        }
//                        RecyclerView recycler = findViewById(R.id.pizza_recycler);
//
//
//                        Book[] allBooks = new Book[bookList.size()];
//                        for (int i = 0; i < bookList.size(); i++) {
//                            Book currentBook = bookList.get(i);
//                            allBooks[i] = new Book(currentBook.getTitle(), currentBook.getCategory(), currentBook.getDesc(),
//                                    currentBook.getPrice(), currentBook.getImageID());
//                        }
//
//                        // Set up RecyclerView adapter
//                        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(allBooks);
//                        recycler.setAdapter(adapter);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(MainActivity.this, error.toString(),
//                                Toast.LENGTH_SHORT).show();
//                        Log.d("Error_json", error.toString());
//                    }
//                });
//                queue.add(request);


                Intent intent = new Intent(cardView.getContext(), bookSee.class);
                cardView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return book.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}