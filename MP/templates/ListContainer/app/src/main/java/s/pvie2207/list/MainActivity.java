package s.pvie2207.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter turboAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();

    }

    private void initData() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product name i7777, etc.", "Brand new", "Ultra mega Ultra mega Ultra mega hyper brand new", (float) 4999.99, true));
        products.add(new Product(2, "Another product", "Mildly used", "Funky funky lorem ipsum", (float) 9999.99, true));
        products.add(new Product(3, "Another another product", "Old, used", "Lorem lorem lorem lorem lorem lorem lorem lorem", (float) 499.99, false));

        listView = findViewById(R.id.expandableListView);
        turboAdapter = new ExpandableListAdapter(this, products);
        listView.setAdapter(turboAdapter);
    }

}
