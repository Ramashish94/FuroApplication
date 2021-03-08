package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.model.chooseChallange.Challenge;
import com.app.furoapp.model.chooseChallange.Subcategory;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Challenge> listDataHeader;
    ChooseChallangeInterface chooseChallangeInterface;
    private HashMap<Challenge, List<Subcategory>> listDataChild;

    public ExpandableAdapter(Context context, List<Challenge> listDataHeader, HashMap<Challenge, List<Subcategory>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }


    @Override
    public Subcategory getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getSubcategory();
        final String childIcon=getChild(groupPosition,childPosition).getBlueIcon();
        final String childIcon1=getChild(groupPosition,childPosition).getBlueIcon();
        final String childIcon2=getChild(groupPosition,childPosition).getBlueIcon();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);
        ImageView imageView=convertView.findViewById(R.id.imgview);
        Picasso.with(context).load(childIcon).error(R.drawable.ic_launcher_background).into(imageView);

        txtListChild.setText(childText);
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseChallangeInterface != null) {
                    chooseChallangeInterface.chooseChallangeItem(getChild(groupPosition, childPosition).getSubcategory(),getGroup(groupPosition).getChallenge());


                }




            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public Challenge getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getChallenge();
        String header = getGroup(groupPosition).getIcon();
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.header_layout, null);

        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout,null,false);
//        }

        ImageView lblListHeader = convertView.findViewById(R.id.image);
        TextView lblList = convertView.findViewById(R.id.textdata);
        Picasso.with(context).load(header).error(R.drawable.ic_launcher_background).into(lblListHeader);
        lblList.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface ChooseChallangeInterface {
        void chooseChallangeItem(String subCategory,String challenge);

    }

    public void setChooseChallangeInterface(ChooseChallangeInterface contentFeedFoodInterface) {
        this.chooseChallangeInterface = contentFeedFoodInterface;

    }
}
