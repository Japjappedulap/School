package s.pvie2207.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<Product> productList;
    private Context context;

    ExpandableListAdapter(Context context, List<Product> products) {
        this.context = context;
        productList = products;
    }

    @Override
    public int getGroupCount() {
        return productList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return productList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return productList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = productList.get(groupPosition).getName();
        float price = productList.get(groupPosition).getPrice();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_header, null);
        }

        TextView listItemHeaderTitle = convertView.findViewById(R.id.listItemHeaderTitle);
        listItemHeaderTitle.setText(headerTitle);
        TextView listItemHeaderPrice = convertView.findViewById(R.id.listItemHeaderPrice);
        listItemHeaderPrice.setText(Float.toString(price));

        return convertView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String shortDescription = productList.get(groupPosition).getShortDescription();
        String longDescription = productList.get(groupPosition).getLongDescription();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_content, null);
        }

        TextView listItemShortDescription = convertView.findViewById(R.id.listItemContentShortDescription);
        listItemShortDescription.setText(shortDescription);

        TextView listItemLongDescription = convertView.findViewById(R.id.listItemContentLongDescription);
        listItemLongDescription.setText(longDescription);

        Button buyButton = convertView.findViewById(R.id.listItemContentButtonAction1);
        buyButton.setText("BUY");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
