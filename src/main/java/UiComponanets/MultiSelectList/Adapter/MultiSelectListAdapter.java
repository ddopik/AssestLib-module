package UiComponanets.MultiSelectList.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.networkmodule.R;
import com.example.networkmodule.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by ddopi on 7/6/2017.
 */

public abstract class MultiSelectListAdapter extends BaseAdapter {

    private Context mContext;
    private RealmResults sourcesList;
    final boolean isSelected[];

    public abstract boolean[] getDefaultList(RealmResults<?> sourceList);

    //    {
    // boolean isSelected[]=new boolean[sourcesList.size()];
//        for (int i = 0; i < sourcesList.size(); i++) {
//        this.isSelected[i] = sourcesList.get(i).isDefaultSource(); // in case every item have default flag
//                                                               //to active make every obj in the 'sources list' has default field and call it with isDefaultSource.
//    return isSelected
//    }

    public abstract String getSingSourceTextName(int sourceID);
//    {
//        this.sourcesList.get(position).getSourceName();
//    }

    public abstract boolean getSingleItemDefaultState(int itemId);
//    {
//        boolean defaultSource;  this implementation should be in child class
//        realm.beginTransaction();
//        RealmObject realmObject = realm.where(SourceNews.class).equalTo("sourceID", position).findFirst();
//        defaultSource = realmObject.isDefaultSource();
//        realm.commitTransaction();
//        return SingleDefaultSource;
//    }

//    public MultiSelectListAdapter() {
//        mContext = getContainerContext();
//        this.sourcesList = getSourcesList();
//        isSelected = getDefaultList(sourcesList);
//    }
    public MultiSelectListAdapter(Context context, RealmResults sourceList) {
        mContext = context;
        this.sourcesList = sourceList;
        isSelected = getDefaultList(sourceList);
    }


    @Override
    public int getCount() {
        return sourcesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        boolean defaultSource = getSingleItemDefaultState(position);   //// make the list remeber the items last selection

//        if (inflater == null)
//            inflater = (LayoutInflater) getmContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
//            convertView  = inflater.inflate(R.layout.feed_item, null);
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.source_item, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.sourseListContainer = (LinearLayout) convertView.findViewById(R.id.source_row_container);
            viewHolder.source_name = (CheckedTextView) convertView.findViewById(R.id.row_list_checkedtextview);
            viewHolder.newsImage = (ImageView) convertView.findViewById(R.id.row_list_checkbox_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.source_name.setText(getSingSourceTextName(position));

//        Log.i("flag ", "Index before IF # --->" + defaultSource);
        if (defaultSource) {
            viewHolder.newsImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.set_check));
            viewHolder.sourseListContainer.setBackgroundColor(Color.parseColor("#F16585"));
            viewHolder.source_name.setChecked(true);
//            Log.i("flag ","WithOutClick defaultSource"+"if (true)   --->"+(defaultSource)+"------------>"+defaultSource);

        } else {
            viewHolder.newsImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.set_uncheck));
            viewHolder.sourseListContainer.setBackgroundResource(0);
            viewHolder.source_name.setChecked(false);

        }

        viewHolder.sourseListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the check text view
                boolean flag = viewHolder.source_name.isChecked();
//                viewHolder.source_name.setChecked(!flag);
                isSelected[position] = !isSelected[position];
                Log.i("onClick ", "Flag is  --->" + flag);
                if (viewHolder.source_name.isChecked()) {
                    viewHolder.newsImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.set_uncheck));
                    viewHolder.sourseListContainer.setBackgroundResource(0);
                    viewHolder.source_name.setChecked(false);
                } else {
                    viewHolder.newsImage.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.set_check));
                    viewHolder.sourseListContainer.setBackgroundColor(Color.parseColor("#F16585"));
                    viewHolder.source_name.setChecked(true);

                }

            }
        });


        return convertView;
    }




    public static class ViewHolder {
        @BindView(R2.id.source_row_container)
        LinearLayout sourseListContainer;
        @BindView(R2.id.row_list_checkedtextview)
        CheckedTextView source_name;
        @BindView(R2.id.row_list_checkbox_image)
        ImageView newsImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
